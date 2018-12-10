package com.me.techfy.techfyme.modelo;

import android.content.Context;

import com.me.techfy.techfyme.DAO.FirebasePreferenciaDatabaseCall;
import com.me.techfy.techfyme.DAO.PreferenciaDAO;
import com.me.techfy.techfyme.DTO.PreferenciaDTO;

public class Preferencia {

    private Integer id;
    private String preferenciaSelecionada1;
    private String preferenciaSelecionada2;
    private String preferenciaSelecionada3;
    private String preferenciaSelecionada4;

    private PreferenciaDAO preferenciaDAO;

    public Preferencia () {
        preferenciaDAO = new PreferenciaDAO();
    }

    public void salvar (PreferenciaDTO usuarios) {
        preferenciaDAO.salvar(usuarios);
    }

    public void carregar (String usuarioId, FirebasePreferenciaDatabaseCall listener) {
        preferenciaDAO.carregar(usuarioId, listener);
    }

    public void gravarNoSQLite(PreferenciaDTO preferenciaDTO, Context context) {
        preferenciaDAO.gravarNoSQLite(preferenciaDTO,context);
    }
    public PreferenciaDTO lerDoSQLite(String usuarioId, Context context) {
       return preferenciaDAO.lerDoSQLite(usuarioId,context);
    }

    public void apagarDoSQLite(PreferenciaDTO usuarios, Context context) {
        preferenciaDAO.apagarDoSQLite(usuarios, context);
    }

    //TODO mudei preferencia aqui
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPreferenciaSelecionada1() {
        return preferenciaSelecionada1;
    }

    public void setPreferenciaSelecionada1(String preferenciaSelecionada1) {
        this.preferenciaSelecionada1 = preferenciaSelecionada1;
    }

    public String getPreferenciaSelecionada2() {
        return preferenciaSelecionada2;
    }

    public void setPreferenciaSelecionada2(String preferenciaSelecionada2) {
        this.preferenciaSelecionada2 = preferenciaSelecionada2;
    }

    public String getPreferenciaSelecionada3() {
        return preferenciaSelecionada3;
    }

    public void setPreferenciaSelecionada3(String preferenciaSelecionada3) {
        this.preferenciaSelecionada3 = preferenciaSelecionada3;
    }

    public String getPreferenciaSelecionada4() {
        return preferenciaSelecionada4;
    }

    public void setPreferenciaSelecionada4(String preferenciaSelecionada4) {
        this.preferenciaSelecionada4 = preferenciaSelecionada4;
    }
}