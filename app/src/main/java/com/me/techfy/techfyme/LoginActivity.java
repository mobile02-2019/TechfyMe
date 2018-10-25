package com.me.techfy.techfyme;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.me.techfy.techfyme.CadastroActivity;
import com.me.techfy.techfyme.HomeActivity;
import com.me.techfy.techfyme.R;

public class LoginActivity extends AppCompatActivity {
    public static final String CHAVE_EMAIL = "chave_email";

    private TextView register;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        register = findViewById(R.id.text_register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CadastroActivity.class);
                startActivity(intent);



            }
        });

        login = findViewById(R.id.button_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (LoginActivity.this, MenuHomeActivity.class);
                Bundle bundle = new Bundle();
                startActivity(intent);

                TextInputEditText emailDigitado = findViewById(R.id.edit_text_email_id);
                TextInputEditText passwordDigitado = findViewById(R.id.edit_text_password_id);

                Button botaoLogin = findViewById(R.id.button_login);

                if(emailDigitado.getText().toString().equals(passwordDigitado.getText().toString())){
                    bundle.putString(CHAVE_EMAIL, emailDigitado.getText().toString());
                    intent.putExtras(bundle);
                    startActivity(intent);

                } else {
                    emailDigitado.setTextColor(getResources().getColor(R.color.colorErro));
                    passwordDigitado.setTextColor(getResources().getColor(R.color.colorErro));

                    Snackbar.make(botaoLogin, "Email e/ou senha incorreto", Snackbar.LENGTH_LONG);

                }




            }
        });




    }

}
