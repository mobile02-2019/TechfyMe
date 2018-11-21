package com.me.techfy.techfyme;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.me.techfy.techfyme.CadastroActivity;
import com.me.techfy.techfyme.HomeActivity;
import com.me.techfy.techfyme.R;

public class LoginActivity extends AppCompatActivity {

    public static final String CHAVE_EMAIL = "chave_email";
    Button botaoLogin;
    TextView cadastrar;
    private static final String TAG = "Login";
    private FirebaseAuth mAuth;
    private CallbackManager callbackManager;

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
                        Intent intent = new Intent(LoginActivity.this, PreferenciaActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString(CHAVE_EMAIL, Profile.getCurrentProfile().getName());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

        botaoLogin = findViewById(R.id.button_login);
        cadastrar = findViewById(R.id.text_register);


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
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            goToHome();
        }
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        if (isLoggedIn) {
            Intent intent = new Intent(this,PreferenciaActivity.class);
            startActivity(intent);
        }
    }

    private void goToHome(){
        Intent intent = new Intent(this,MenuHomeActivity.class);
        startActivity(intent);
    }

    public void cadastrarUsuario() {
        Intent intent = new Intent(this, CadastroActivity.class);
        startActivity(intent);

    }


    public void loginClicado (){

        EditText email = findViewById(R.id.edit_text_email_id);
        EditText senha = findViewById(R.id.edit_text_password_id);


        mAuth.signInWithEmailAndPassword(email.getText().toString(), senha.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            goToHome();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });







//        Intent intent = new Intent(this, MenuHomeActivity.class);
//
//        Bundle bundle = new Bundle();
//
//        final TextInputEditText emailDigitado = findViewById(R.id.edit_text_email_id);
//        final TextInputEditText passwordDigitado = findViewById(R.id.edit_text_password_id);
//
//
//        if((emailDigitado.getText().toString().equals("GuiSartori") || emailDigitado.getText().toString().equals(""))  && (passwordDigitado.getText().toString().equals("2018") || passwordDigitado.getText().toString().equals(""))){
//            bundle.putString(CHAVE_EMAIL, emailDigitado.getText().toString());
//            intent.putExtras(bundle);
//            startActivity(intent);
//        }   else {
//            emailDigitado.setTextColor(getResources().getColor(R.color.colorErro));
//            passwordDigitado.setTextColor(getResources().getColor(R.color.colorErro));
//
//            Snackbar.make(botaoLogin, "Email e/ou senha incorreto(s)", BaseTransientBottomBar.LENGTH_LONG)
//
//                    .setAction("Ok, entendi", new View.OnClickListener() {
//
//                        @Override
//                        public void onClick(View v) {
//                            emailDigitado.setTextColor(getResources().getColor(R.color.colorPrimary));
//                            passwordDigitado.setTextColor(getResources().getColor(R.color.colorPrimary));
//                        }
//                    }).show();
//        }

    }

}






