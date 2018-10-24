package com.me.techfy.techfyme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class CadastroActivity extends AppCompatActivity {

   private Button botaoCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        String [] GENEROS = getResources().getStringArray(R.array.lista_de_generos);

        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,GENEROS);
        MaterialBetterSpinner textView = (MaterialBetterSpinner) findViewById(R.id.spinner_id);
        textView.setAdapter(adapterSpinner);

        botaoCadastro = findViewById(R.id.button_cadastro_id);
        botaoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),PreferenciaActivity.class);
                startActivity(intent);
            }
        });

        }
    }




