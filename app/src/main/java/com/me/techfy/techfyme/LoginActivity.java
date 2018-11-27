package com.me.techfy.techfyme;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    public static final String CHAVE_EMAIL = "chave_email";
    public static final String VEIO_DO_LOGIN = "veio_do_login";
    Button botaoLogin;
    TextView cadastrar;
    private static final String TAG = "Login";
    private FirebaseAuth mAuth;
    private CallbackManager callbackManager;
    TextInputLayout email;
    TextInputLayout senha;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setIcon(R.drawable.techfyme_logo_action_bar);

        mAuth = FirebaseAuth.getInstance();

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Intent intent = new Intent(LoginActivity.this, MenuHomeActivity.class);
                        bundleUsuarioLogado(intent, Profile.getCurrentProfile().getName());
                        startActivity(intent);

                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException exception) {
                    }
                });

        botaoLogin = findViewById(R.id.button_login);
        cadastrar = findViewById(R.id.text_register);
        email = findViewById(R.id.edit_text_email_id);
        senha = findViewById(R.id.edit_text_password_id);

        botaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginClicado();

            }
        });

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarUsuario();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            goToHome(currentUser.getEmail());
        }
    }

    private void goToHome(String nome) {
        Intent intent = new Intent(this, PreferenciaActivity.class);
        bundleUsuarioLogado(intent, nome);
        startActivity(intent);
    }

    private void bundleUsuarioLogado(Intent intent, String nome) {
        bundle = new Bundle();
        bundle.putString(CHAVE_EMAIL, nome);
        bundle.putBoolean(VEIO_DO_LOGIN, true);
        bundle.putBoolean(MenuHomeActivity.VEIO_DA_HOME, false);
        intent.putExtras(bundle);
    }

    public void cadastrarUsuario() {
        Intent intent = new Intent(this, CadastroActivity.class);
        startActivity(intent);
    }

    public void loginClicado() {


        final TextInputLayout email = findViewById(R.id.edit_text_email_id);
        TextInputLayout senha = findViewById(R.id.edit_text_password_id);

        final String emailDigitado = email.getEditText().getText().toString();
        String senhaDigitada = senha.getEditText().getText().toString();

        mAuth.signInWithEmailAndPassword(emailDigitado, senhaDigitada)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            goToHome(emailDigitado);
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

}