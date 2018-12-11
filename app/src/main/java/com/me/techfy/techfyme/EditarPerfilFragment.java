package com.me.techfy.techfyme;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditarPerfilFragment extends Fragment {

    private TextView editarNome;
    private TextView editarSobrenome;
    private TextView editarEmail;
    private TextView editarNascimento;
    private TextView editarSenha;
    private TextView editarConfirmacaoSenha;
    private CircleImageView imagePerfil;
    private FirebaseAuth firebaseAuth;
    private StorageReference storageRef;
    private FirebaseStorage storage;
    private static final int REQUEST_IMAGE_CAPTURE = 1;



    public EditarPerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_editar_perfil, container, false);
        editarNome = view.findViewById(R.id.edit_nome_usuario_id);
        editarSobrenome = view.findViewById(R.id.edit_sobrenome_usuario_id);
        imagePerfil = view.findViewById(R.id.edit_image_user_cadastro);
        editarEmail = view.findViewById(R.id.edit_email_usuario_id);
        firebaseAuth = FirebaseAuth.getInstance();

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();


        imagePerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCameraImage();
            }
        });

        buscarDadosUsuario();


        return view;

    }



    public void buscarDadosUsuario(){

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {

            if (user.getPhotoUrl() == null) {

                storageRef.child(firebaseAuth.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Got the download URL for 'users/me/profile.png'
                        Picasso.get().load(uri).into(imagePerfil);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });
            } else {
                Picasso.get().load(user.getPhotoUrl()).into(imagePerfil);
            }

            for (UserInfo userInfo : firebaseAuth.getCurrentUser().getProviderData()) {
                if (userInfo.getProviderId().equals("facebook.com")) {
                    definirDadosFacebook();
                } else if (userInfo.getProviderId().equals("google.com")){
                    definirDadosGoogle();
                }else{
                    editarNome.setText(user.getDisplayName());
                }
            }
        }
    }


    private void definirDadosGoogle() {
        //Toast.makeText(getApplicationContext(), "Usuário logado pelo Gmail", Toast.LENGTH_LONG).show();
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        String personEmail = acct.getEmail();
        editarEmail.setText(personEmail);
    }

    private void definirDadosFacebook() {
        //Toast.makeText(getApplicationContext(), "Usuário logado pelo Facebook", Toast.LENGTH_LONG).show();
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.v("LoginActivity", response.toString());

                        // Application code
                        String email = null;
                        try {
                            email = object.getString("email");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        editarEmail.setText(email);
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "email");
        request.setParameters(parameters);
        request.executeAsync();
    }



    private void getCameraImage() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            getActivity().startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE);

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imagePerfil.setImageBitmap(imageBitmap);

        }
    }
    public void salvarImagemNoFirebase() {
        // Get the data from an ImageView as bytes
        imagePerfil.setDrawingCacheEnabled(true);
        imagePerfil.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imagePerfil.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] dataByte = baos.toByteArray();

        UploadTask uploadTask = storageRef.child(firebaseAuth.getUid()).putBytes(dataByte);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getApplicationContext(), "Falhou ao salvar foto!" + exception.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("autenticacao", exception.getMessage());
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getApplicationContext(), "Foto armazenada!", Toast.LENGTH_SHORT).show();
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });
    }


}


