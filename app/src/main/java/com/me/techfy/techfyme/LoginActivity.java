package com.me.techfy.techfyme;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
                Intent intent = new Intent(view.getContext(), HomeActivity.class);
                startActivity(intent);



            }
        });

        login = findViewById(R.id.button_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(), HomeActivity.class);
                Bundle bundle = new Bundle();

                TextInputEditText emailDigitado = findViewById(R.id.edit_text_email_id);
                TextInputEditText passwordDigitado = findViewById(R.id.edit_text_password_id);

                bundle.putString(CHAVE_EMAIL, emailDigitado.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);


            }
        });




    }

}
