package com.me.techfy.techfyme.modelo;

import android.content.Context;

import com.me.techfy.techfyme.DAO.FirebasePreferenciaDatabaseCall;
import com.me.techfy.techfyme.DAO.PreferenciaDAO;
import com.me.techfy.techfyme.DTO.PreferenciaDTO;

public class Preferencia {

    private PreferenciaDAO preferenciaDAO;

    public Preferencia () {
        preferenciaDAO = new PreferenciaDAO();
    }

    public void salvar (PreferenciaDTO preferenciaDTO) {
        preferenciaDAO.salvar(preferenciaDTO);
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

}