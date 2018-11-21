package com.me.techfy.techfyme;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;

public class CadastroActivity extends AppCompatActivity {

    private Button botaoCadastro;
    private static final String TAG = "Cadastro";
    private FirebaseAuth mAuth;

    private String emBranco ="";
    private TextInputEditText nome;
    private TextInputEditText sobrenome;
    private TextInputEditText email;
    private TextInputEditText senha;
    private TextInputEditText confirmaSenha;
    private List<TextInputEditText> listaCampo = new ArrayList<>();

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

    public boolean senhasIguais(final TextInputEditText senha,final TextInputEditText confirmaSenha){
        boolean retorno;
        if(senha.getEditableText().toString().equals(confirmaSenha.getEditableText().toString())){
            retorno = true;

        }else{
            retorno = false;
            senha.setTextColor(getResources().getColor(R.color.colorErro));
            confirmaSenha.setTextColor(getResources().getColor(R.color.colorErro));
            Snackbar.make(botaoCadastro,"Senhas diferentes", BaseTransientBottomBar.LENGTH_LONG).setAction("OK entendi", new View.OnClickListener() {
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

        String [] GENEROS = getResources().getStringArray(R.array.lista_de_generos);

        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,GENEROS);
        MaterialBetterSpinner textView = (MaterialBetterSpinner) findViewById(R.id.spinner_id);
        textView.setAdapter(adapterSpinner);

        nome =findViewById(R.id.nome_usuario_id);
        sobrenome =findViewById(R.id.sobrenome_usuario_id);
        email =findViewById(R.id.email_usuario_id);
        senha =findViewById(R.id.senha_usuario_id);
        confirmaSenha =findViewById(R.id.confirmacao_senha_usuario_id);
        botaoCadastro = findViewById(R.id.button_cadastro_id);
        botaoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cadastrarUsuario();


//                Intent intent = new Intent(v.getContext(),PreferenciaActivity.class);
//                listaCampo.add(nome);
//                listaCampo.add(sobrenome);
//                listaCampo.add(email);
//                listaCampo.add(senha);
//
//                if (verificarCampos(listaCampo)&& senhasIguais(senha,confirmaSenha)){
//                    startActivity(intent);
//                }



            }
        });

        }

    private void cadastrarUsuario() {

        final EditText nomeCadastrado = findViewById(R.id.nome_usuario_id);
        EditText emailCadastrado = findViewById(R.id.email_usuario_id);
        EditText senhaCadastrado = findViewById(R.id.senha_usuario_id);

        mAuth.createUserWithEmailAndPassword(emailCadastrado.getText().toString(), senhaCadastrado.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            user = FirebaseAuth.getInstance().getCurrentUser();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(nomeCadastrado.getText().toString())
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User profile updated.");
                                                goToLogin();
                                            }
                                        }
                                    });
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(CadastroActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            goToLogin();
                        }

                        // ...
                    }
                });



    }

    private void goToLogin() {
        finish();
    }
}




