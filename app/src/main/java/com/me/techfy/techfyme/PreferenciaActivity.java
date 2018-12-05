package com.me.techfy.techfyme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.me.techfy.techfyme.DAO.FirebasePreferenciaDatabaseCall;
import com.me.techfy.techfyme.DTO.PreferenciaDTO;
import com.me.techfy.techfyme.modelo.Preferencia;

import java.util.ArrayList;
import java.util.List;

public class PreferenciaActivity extends Activity implements FirebasePreferenciaDatabaseCall {

    public static final String CK_ANDROID = "Android";
    public static final String CK_APPLE = "Apple";
    public static final String CK_BLOCKCHAIN = "Blockchain";
    public static final String CK_CLOUD = "Cloud";
    public static final String CK_CRIPTOMOEDAS = "Criptomoedas";
    public static final String CK_EBUSINESS = "E-Business";
    public static final String CK_GAMES = "Games";
    public static final String CK_INTELIGENCIAARTIFICIAL = "Inteligência Artificial";
    public static final String CK_MOBILE = "Mobile";
    public static final String CK_SISTEMAOPERACIONAL = "Sistema Operacional";

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
    private List<String> checkBoxListApoio = new ArrayList<>();

    private Preferencia preferencia;
    private String userId;
    private Bundle bundle;

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

        preferencia = new Preferencia();
        Intent intent = getIntent();
        bundle = intent.getExtras();
        //userId = FirebaseAuth.getInstance();

        String auxiliar = bundle.getString(LoginActivity.CHAVE_EMAIL);
        if(!(auxiliar == null)) {
            userId = bundle.getString(LoginActivity.CHAVE_EMAIL);
        }

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PreferenciaDTO preferenciaDTO = new PreferenciaDTO();
                preferenciaDTO.setUsuarioId(userId);

                checkBoxListApoio.clear();
                for (CheckBox checkBox : checkBoxList) {
                    if (checkBox.isChecked()) {
                        checkBoxListApoio.add(checkBox.getText().toString());
                        preferenciaDTO.adicionar(checkBox.getText().toString());
                    }
                }

                if (checkBoxListApoio.size() == 4) {
                    preferencia.salvar(preferenciaDTO);
                    irParaHome();
                } else {
                    Toast.makeText(PreferenciaActivity.this, "Selecione 4 favoritos", Toast.LENGTH_LONG).show();
                    preferenciaDTO.limpar();
                }
            }
        });

        preferencia.carregar(getUserIDComoDatabaseKey(), this);

        PreferenciaDTO _preferenciaDTO =  preferencia.lerDoSQLite(this.getUserIDComoDatabaseKey(), this);
        if (_preferenciaDTO != null && _preferenciaDTO.getChecados() != null && _preferenciaDTO.getChecados().size() == 4) {
            onDataChange(_preferenciaDTO);
        }
            if(bundle.getBoolean(LoginActivity.VEIO_DO_LOGIN, false)) {
                irParaHome();
            }

    }

    @NonNull
    private String getUserIDComoDatabaseKey() {
        return userId;
    }

    public void irParaHome() {
        Intent intent = new Intent(this, MenuHomeActivity.class);
        intent.putExtras(getBundleToHome());
        startActivity(intent);
    }

    private void intViews() {
        checkBoxAndroid = findViewById(R.id.cat_android);
        checkBoxApple = findViewById(R.id.cat_apple);
        checkBoxBlockchain = findViewById(R.id.cat_blockchain);
        checkBoxCloud = findViewById(R.id.cat_cloud);
        checkBoxCriptomoedas = findViewById(R.id.cat_criptomoedas);
        checkBoxEBusiness = findViewById(R.id.cat_business);
        checkBoxGames = findViewById(R.id.cat_games);
        checkBoxInteligenciaArtificial = findViewById(R.id.cat_ia);
        checkBoxMobile = findViewById(R.id.cat_mobile);
        checkBoxSistemaOperacional = findViewById(R.id.cat_so);

        btnEnviar = findViewById(R.id.btn_enviar);
    }

    public Bundle getBundleToHome() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(CK_ANDROID, checkBoxAndroid.isChecked());
        bundle.putBoolean(CK_APPLE, checkBoxApple.isChecked());
        bundle.putBoolean(CK_BLOCKCHAIN, checkBoxBlockchain.isChecked());
        bundle.putBoolean(CK_CLOUD, checkBoxCloud.isChecked());
        bundle.putBoolean(CK_CRIPTOMOEDAS, checkBoxCriptomoedas.isChecked());
        bundle.putBoolean(CK_EBUSINESS, checkBoxEBusiness.isChecked());
        bundle.putBoolean(CK_GAMES, checkBoxGames.isChecked());
        bundle.putBoolean(CK_INTELIGENCIAARTIFICIAL, checkBoxInteligenciaArtificial.isChecked());
        bundle.putBoolean(CK_MOBILE, checkBoxMobile.isChecked());
        bundle.putBoolean(CK_SISTEMAOPERACIONAL, checkBoxSistemaOperacional.isChecked());

        return bundle;
    }

    @Override
    public void onDataChange(PreferenciaDTO preferenciaDTO) {
        if (preferenciaDTO != null && preferenciaDTO.getChecados() != null && preferenciaDTO.getChecados().size() == 4) {

            for (String checado : preferenciaDTO.getChecados()) {
                if (checado.equals(CK_ANDROID)) {
                    checkBoxAndroid.setChecked(true);
                }
                if (checado.equals(CK_APPLE)) {
                    checkBoxApple.setChecked(true);
                }
                if (checado.equals(CK_BLOCKCHAIN)) {
                    checkBoxBlockchain.setChecked(true);
                }
                if (checado.equals(CK_CLOUD)) {
                    checkBoxCloud.setChecked(true);
                }
                if (checado.equals(CK_CRIPTOMOEDAS)) {
                    checkBoxCriptomoedas.setChecked(true);
                }
                if (checado.equals(CK_EBUSINESS)) {
                    checkBoxEBusiness.setChecked(true);
                }
                if (checado.equals(CK_GAMES)) {
                    checkBoxGames.setChecked(true);
                }
                if (checado.equals(CK_INTELIGENCIAARTIFICIAL)) {
                    checkBoxInteligenciaArtificial.setChecked(true);
                }
                if (checado.equals(CK_MOBILE)) {
                    checkBoxMobile.setChecked(true);
                }
                if (checado.equals(CK_SISTEMAOPERACIONAL)) {
                    checkBoxSistemaOperacional.setChecked(true);
                }

            }

            preferencia = new Preferencia();
            preferenciaDTO.setUsuarioId(userId);

            preferencia.apagarDoSQLite(preferenciaDTO, this);

            preferencia. gravarNoSQLite(preferenciaDTO, this);
            if (bundle.getBoolean(MenuHomeActivity.VEIO_DA_HOME, true)) {
            } else {
                irParaHome();
            }

        }
    }

    @Override
    public void onDataCanceled() {

        // TODO exibir mensagem de erro

    }

}