package com.me.techfy.techfyme;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CadastroActivity extends AppCompatActivity {

    private Button botaoCadastro;
    private static final String TAG = "Cadastro";
    private FirebaseAuth mAuth;
    private String emBranco = "";
    private TextInputEditText nome;
    private TextInputEditText sobrenome;
    private TextInputEditText email;
    private TextInputEditText senha;
    private TextInputEditText confirmaSenha;
    private List<TextInputEditText> listaCampo = new ArrayList<>();
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private CircleImageView imagemUser;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private EditText emailCadastrado;

    public boolean verificarCampos(List<TextInputEditText> listaCampo) {
        boolean retorno = true;
        for (TextInputEditText textInputEditText : listaCampo) {
            if (textInputEditText.getEditableText().toString().equals(emBranco)) {
                ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.colorErro));
                ViewCompat.setBackgroundTintList(textInputEditText, colorStateList);
                Toast.makeText(this, "Preencha os espaços obrigatórios", Toast.LENGTH_LONG).show();
                retorno = false;
            } else {
                retorno = true;
            }
        }
        return retorno;
    }

    public boolean senhasIguais(final TextInputEditText senha, final TextInputEditText confirmaSenha) {
        boolean retorno;
        if (senha.getEditableText().toString().equals(confirmaSenha.getEditableText().toString())) {
            retorno = true;

        } else {
            retorno = false;
            senha.setTextColor(getResources().getColor(R.color.colorErro));
            confirmaSenha.setTextColor(getResources().getColor(R.color.colorErro));
            Snackbar.make(botaoCadastro, "Senhas diferentes", BaseTransientBottomBar.LENGTH_LONG).setAction("OK entendi", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    senha.setTextColor(getResources().getColor(R.color.colorPrimary));
                    confirmaSenha.setTextColor(getResources().getColor(R.color.colorPrimary));


                }
            }).show();
        }
        return retorno;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setIcon(R.drawable.techfyme_logo_action_bar);

        mAuth = FirebaseAuth.getInstance();

        storage = FirebaseStorage.getInstance();

        storageRef = storage.getReference();

        String[] GENEROS = getResources().getStringArray(R.array.lista_de_generos);

        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, GENEROS);
        MaterialBetterSpinner textView = findViewById(R.id.spinner_id);
        textView.setAdapter(adapterSpinner);

        nome = findViewById(R.id.nome_usuario_id);
        sobrenome = findViewById(R.id.sobrenome_usuario_id);
        email = findViewById(R.id.email_usuario_id);
        senha = findViewById(R.id.senha_usuario_id);
        confirmaSenha = findViewById(R.id.confirmacao_senha_usuario_id);
        botaoCadastro = findViewById(R.id.button_cadastro_id);
        botaoCadastro.setOnClickListener(v -> cadastrarUsuario());

        imagemUser = findViewById(R.id.edit_image_user_cadastro);
        imagemUser.setOnClickListener(v -> getCameraImage());


    }

    private void cadastrarUsuario() {

        final EditText nomeCadastrado = findViewById(R.id.nome_usuario_id);
        emailCadastrado = findViewById(R.id.email_usuario_id);
        EditText senhaCadastrado = findViewById(R.id.senha_usuario_id);

        mAuth.createUserWithEmailAndPassword(emailCadastrado.getText().toString(), senhaCadastrado.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(nomeCadastrado.getText().toString())
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User profile updated.");
                                                goToPreferencia();
                                            }
                                        }
                                    });
                            salvarImagemNoFirebase();

                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(CadastroActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            goToPreferencia();
                        }
                    }
                });


    }

    private void goToPreferencia() {
        Intent intent = new Intent(this, PreferenciaActivity.class);
        startActivity(intent);
    }




    private void getCameraImage() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imagemUser.setImageBitmap(imageBitmap);

        }
    }

    public void salvarImagemNoFirebase() {
        // Get the data from an ImageView as bytes
        imagemUser.setDrawingCacheEnabled(true);
        imagemUser.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imagemUser.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] dataByte = baos.toByteArray();

        UploadTask uploadTask = storageRef.child(mAuth.getUid()).putBytes(dataByte);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(CadastroActivity.this, "Falhou ao salvar foto!" + exception.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("autenticacao", exception.getMessage());
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(CadastroActivity.this, "Foto armazenada!", Toast.LENGTH_SHORT).show();
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });
    }

}




