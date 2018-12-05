package com.me.techfy.techfyme.DTO;

import java.util.ArrayList;
import java.util.List;

public class PreferenciaDTO {

    private List<String> checados;
    private String usuarioId;
    private String databaseKey;
    private String usuarioEmail;

    public PreferenciaDTO() {
        checados = new ArrayList<>();
    }

    public String getDatabaseKey() {
        if(!(databaseKey == null)) return databaseKey;
        if (usuarioId == null) return null;
        databaseKey = usuarioId.replace(".", "");
        return databaseKey;
    }

    public void setUsuarioId(String userId) {
        this.usuarioId = userId;
    }

    public void adicionar (String opcao) {
        checados.add(opcao);
    }

    public List<String> getChecados () {
        return checados;
    }

    public void limpar () {
        checados.clear();
    }

    public String getChecadosAsString () {
        String retorno = "";
        for (String numero : checados) {
            retorno += numero + ",";
        }
        return retorno;
    }

    public void setUsuarioEmail(String email) {
        usuarioEmail = email;
    }
}