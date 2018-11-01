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
    private CheckBox checkBoxGoogle;
    private CheckBox checkBoxApple;
    private CheckBox checkBoxMobile;
    private CheckBox checkBoxFuturo;
    private CheckBox checkBoxAndroid;
    private CheckBox checkBoxIos;
    private CheckBox checkBoxSo;
    private CheckBox checkBoxMercado;
    private List<CheckBox> checkBoxList = new ArrayList<>();
    private List<Integer> checkBoxListApoio = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencia);
        intViews();

        checkBoxList.add(checkBoxAndroid);
        checkBoxList.add(checkBoxGoogle);
        checkBoxList.add(checkBoxApple);
        checkBoxList.add(checkBoxMobile);
        checkBoxList.add(checkBoxFuturo);
        checkBoxList.add(checkBoxIos);
        checkBoxList.add(checkBoxSo);
        checkBoxList.add(checkBoxMercado);

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
        checkBoxGoogle = (CheckBox) findViewById(R.id.cat_google);
        checkBoxApple = (CheckBox) findViewById(R.id.cat_apple);
        checkBoxMobile = (CheckBox) findViewById(R.id.cat_mobile);
        checkBoxFuturo = (CheckBox) findViewById(R.id.cat_futuro);
        checkBoxAndroid = (CheckBox) findViewById(R.id.cat_android);
        checkBoxIos = (CheckBox) findViewById(R.id.cat_ios);
        checkBoxSo = (CheckBox) findViewById(R.id.cat_so);
        checkBoxMercado = (CheckBox) findViewById(R.id.cat_mercado);
        btnEnviar = (Button) findViewById(R.id.btn_enviar);
    }

    public Bundle home() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("ck_android", checkBoxAndroid.isChecked());
        bundle.putBoolean("ck_google", checkBoxGoogle.isChecked());
        bundle.putBoolean("ck_apple", checkBoxApple.isChecked());
        bundle.putBoolean("ck_mobile", checkBoxMobile.isChecked());
        bundle.putBoolean("ck_futuro", checkBoxFuturo.isChecked());
        bundle.putBoolean("ck_ios", checkBoxIos.isChecked());
        bundle.putBoolean("ck_so", checkBoxSo.isChecked());
        bundle.putBoolean("ck_mercado", checkBoxMercado.isChecked());

        return bundle;

    }
}
