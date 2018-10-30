package com.me.techfy.techfyme;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;

public class CadastroActivity extends AppCompatActivity {

   private Button botaoCadastro;
   private String emBranco = "";
   private TextInputEditText nome;
    private TextInputEditText sobrenome;
    private TextInputEditText email;
    private TextInputEditText senha;
    private TextInputEditText confirmaSenha;
    private List<TextInputEditText> listaCampo = new ArrayList<>();

     //       ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.colorErro));
       //     ViewCompat.setBackgroundTintList(campo, colorStateList);

    public boolean verificarCampos(List<TextInputEditText> listaCampo){
       boolean retorno = true;
        for (TextInputEditText textInputEditText : listaCampo) {
            if (textInputEditText.getEditableText().toString().equals(emBranco)){
                ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.colorErro));
                ViewCompat.setBackgroundTintList(textInputEditText, colorStateList);
               Toast.makeText(this,"Preencha os espacos",Toast.LENGTH_LONG).show();
                retorno = false;

                }else{
                retorno = true;
            }

        }
        return  retorno;
    }

    public boolean senhasIguais(final TextInputEditText senhaI, final TextInputEditText confirmaSenhaI){
        boolean retorno;
        if (senhaI.getEditableText().toString().equals(confirmaSenhaI.getEditableText().toString())){
            retorno = true;
        }else{
            retorno = false;
            senhaI.setTextColor(getResources().getColor(R.color.colorErro));
            confirmaSenhaI.setTextColor(getResources().getColor(R.color.colorErro));
            Snackbar.make(botaoCadastro,"Senhas diferentes",BaseTransientBottomBar.LENGTH_LONG)
                    .setAction(" OK entendi", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            senhaI.setTextColor(getResources().getColor(R.color.colorPrimary));
                            confirmaSenhaI.setTextColor(getResources().getColor(R.color.colorPrimary));

                        }
                    }).show()
            ;
            }
            return retorno;
        }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        String [] GENEROS = getResources().getStringArray(R.array.lista_de_generos);

        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,GENEROS);
        MaterialBetterSpinner textView = (MaterialBetterSpinner) findViewById(R.id.spinner_id);
        textView.setAdapter(adapterSpinner);
        nome = findViewById(R.id.nome_usuario_id);
        sobrenome = findViewById(R.id.sobrenome_usuario_id);
        email = findViewById(R.id.email_usuario_id);
        senha = findViewById(R.id.senha_usuario_id);
        confirmaSenha = findViewById(R.id.confirmacao_senha_usuario_id);


        botaoCadastro = findViewById(R.id.button_cadastro_id);
        botaoCadastro.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                listaCampo.add(nome);
                listaCampo.add(sobrenome);
                listaCampo.add(email);
                listaCampo.add(senha);

                if ( verificarCampos(listaCampo) && senhasIguais(senha,confirmaSenha)){
                    Intent intent = new Intent(v.getContext(), HomeActivity.class);
                    startActivity(intent);
                    }



            }
        });

        }


    }




