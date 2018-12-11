package com.me.techfy.techfyme;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.me.techfy.techfyme.modelo.Preferencia;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    public static final String CHAVE_EMAIL = "chave_email";
    public static final String VEIO_DO_LOGIN = "veio_do_login";
    private static final int RC_SIGN_IN = 1000;
    Button botaoLogin;
    TextView cadastrar;
    private static final String TAG = "Login";
    private FirebaseAuth mAuth;
    private CallbackManager callbackManager;
    TextInputLayout email;
    TextInputLayout senha;
    Bundle bundle;
    private LoginButton loginFacebook;
    private GoogleSignInClient mGoogleSignInClient;

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private ArrayList<String> listaNoticiaChecada = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setIcon(R.drawable.techfyme_logo_action_bar);

        mAuth = FirebaseAuth.getInstance();
        loginFacebook = findViewById(R.id.login_face);
        loginFacebook.setReadPermissions("email","public_profile");
        callbackManager = CallbackManager.Factory.create();

        //LOGIN COM FACEBOOK
        loginFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });

        botaoLogin = findViewById(R.id.button_login);
        cadastrar = findViewById(R.id.text_register);
        email = findViewById(R.id.edit_text_email_id);
        senha = findViewById(R.id.edit_text_password_id);

        botaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getEditText().getText().toString().equals("") || senha.getEditText().getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Para acesso ao aplicativo, " +
                            "escolha uma das formas de login", Toast.LENGTH_LONG).show();
                }else {
                    loginClicado();
                }
            }
        });

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarUsuario();
            }
        });

        // LOGIN COM GOOGLE
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mAuth = FirebaseAuth.getInstance();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        Button btnLoginGoogle = findViewById(R.id.btn_login_google);

        btnLoginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.btn_login_google:
                        signIn();
                        break;
                }
            }
        });

    }

            private void handleFacebookAccessToken(AccessToken token) {
            Log.d(TAG, "handleFacebookAccessToken:" + token);

            AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "signInWithCredential:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent intent = new Intent(getApplicationContext(),PreferenciaActivity.class);
                                irParaPreferencia(user.getUid());
                                startActivity(intent);

                            } else {
                                Log.w(TAG, "signInWithCredential:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            // TODO passar nome para home
                            String nome = "Home";
                            irParaPreferencia(nome);
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        /*FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            irParaPreferencia(currentUser.getEmail());
        }*/
        if(mAuth.getCurrentUser()!=null){
            FirebaseUser user = mAuth.getCurrentUser();
            verificarDadosPreferencia();
        }
    }

    private void verificarDadosPreferencia() {
        try {
            //referencia database firebase
            database = FirebaseDatabase.getInstance();
            myRef = database.getReference("preferences/" + mAuth.getUid());
            //tenta buscar preferencia
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //tenta atribuir preferencia do firebase
                    Preferencia preference = dataSnapshot.getValue(Preferencia.class);
                    Log.d(TAG, "Value is: " + preference);

                    //se existir preferencias, criar array de string com os generos e enviar para Home
                    if (preference != null) {
                        //abre um intent
                        //Cria bundle
                        Bundle bundleParaHome = new Bundle();
                        //adiciona na lista string de generos
                        listaNoticiaChecada.add(preference.getPreferenciaSelecionada1());
                        listaNoticiaChecada.add(preference.getPreferenciaSelecionada2());
                        listaNoticiaChecada.add(preference.getPreferenciaSelecionada3());
                        listaNoticiaChecada.add(preference.getPreferenciaSelecionada4());
                        //adiciona lista de string no bundle
                        bundleParaHome.putStringArrayList("checados" , listaNoticiaChecada);
                        Intent intent = new Intent(getApplicationContext(),MenuHomeActivity.class);
                        //adiciona bundle no intent
                        intent.putExtras(bundleParaHome);
                        startActivity(intent);
                    }else{//se nao existir preferencia, vai para tela de preferencias para serem criadas
                        //abre a outra Activity
                        Intent intent = new Intent(getApplicationContext(),PreferenciaActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } catch (Exception ex) {

        }
    }

    private void irParaPreferencia(String nome) {
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

    //EMAIL E SENHA
    public void loginClicado () {

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
                            irParaPreferencia(emailDigitado);
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

}