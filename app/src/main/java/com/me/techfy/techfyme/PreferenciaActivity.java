package com.me.techfy.techfyme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PreferenciaActivity extends Activity {

    public static final String CHAVE_LISTA = "chaveLista" ;

    private Button home;
    private Button btnEnviar;
    private CheckBox checkBoxAndroid;
    private CheckBox checkBoxApple;
    private CheckBox checkBoxBlockchain;
    private CheckBox checkBoxCloud;
    private CheckBox checkBoxCriptomoedas;
    private CheckBox checkBoxEBusiness;
    private CheckBox checkBoxGames;
    private CheckBox checkBoxInteligenciaArtificial;
    private CheckBox checkBoxMobile;
    private CheckBox checkBoxSistemaOperacional;




    private List<CheckBox> checkBoxList = new ArrayList<>();
    private List<Integer> checkBoxListApoio = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencia);
        intViews();

        checkBoxList.add(checkBoxAndroid);
        checkBoxList.add(checkBoxApple);
        checkBoxList.add(checkBoxBlockchain);
        checkBoxList.add(checkBoxCloud);
        checkBoxList.add(checkBoxCriptomoedas);
        checkBoxList.add(checkBoxEBusiness);
        checkBoxList.add(checkBoxGames);
        checkBoxList.add(checkBoxInteligenciaArtificial);
        checkBoxList.add(checkBoxMobile);
        checkBoxList.add(checkBoxSistemaOperacional);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxListApoio.clear();
                for (CheckBox checkBox:checkBoxList){
                    if (checkBox.isChecked()){
                        checkBoxListApoio.add(checkBox.getId());
                    }
                }

                if (checkBoxListApoio.size() == 4){
                    Bundle bundle = new Bundle();
                    bundle.putIntegerArrayList(CHAVE_LISTA, (ArrayList<Integer>) checkBoxListApoio);
                    Intent intent = new Intent(v.getContext(), MenuHomeActivity.class);
                    intent.putExtras(home());
                    startActivity(intent);

                    } else {
                        Toast.makeText(PreferenciaActivity.this, "Selecione 4 favoritos", Toast.LENGTH_LONG).show();
                    }
                }
            });
    }


    public void home(View view) {
        Intent intent = new Intent(view.getContext(), MenuHomeActivity.class);
        startActivity(intent);
    }

    private void intViews() {
        checkBoxAndroid = (CheckBox) findViewById(R.id.cat_android);
        checkBoxApple = (CheckBox) findViewById(R.id.cat_apple);
        checkBoxBlockchain = (CheckBox) findViewById(R.id.cat_blockchain);
        checkBoxCloud = (CheckBox) findViewById(R.id.cat_cloud);
        checkBoxCriptomoedas = (CheckBox) findViewById(R.id.cat_criptomoedas);
        checkBoxEBusiness= (CheckBox) findViewById(R.id.cat_business);
        checkBoxGames = (CheckBox) findViewById(R.id.cat_games);
        checkBoxInteligenciaArtificial = (CheckBox) findViewById(R.id.cat_ia);
        checkBoxMobile = (CheckBox) findViewById(R.id.cat_mobile);
        checkBoxSistemaOperacional = (CheckBox) findViewById(R.id.cat_so);


        btnEnviar = (Button) findViewById(R.id.btn_enviar);
    }

    public Bundle home() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("ck_android", checkBoxAndroid.isChecked());
        bundle.putBoolean("ck_apple", checkBoxApple.isChecked());
        bundle.putBoolean("ck_blockchain", checkBoxBlockchain.isChecked());
        bundle.putBoolean("ck_cloud", checkBoxCloud.isChecked());
        bundle.putBoolean("ck_criptomoedas", checkBoxCriptomoedas.isChecked());
        bundle.putBoolean("ck_ebusiness", checkBoxEBusiness.isChecked());
        bundle.putBoolean("ck_games", checkBoxGames.isChecked());
        bundle.putBoolean("ck_inteligenciaartificial", checkBoxInteligenciaArtificial.isChecked());
        bundle.putBoolean("ck_mobile", checkBoxMobile.isChecked());
        bundle.putBoolean("ck_sistemaoperacional", checkBoxSistemaOperacional.isChecked());

        return bundle;

    }
}
