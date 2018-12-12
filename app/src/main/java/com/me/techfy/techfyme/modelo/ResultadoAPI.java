package com.me.techfy.techfyme.modelo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultadoAPI {

    @SerializedName("articles")
    private List<Noticia> noticiaList;
    @SerializedName("status")
    private String status;
    @SerializedName("totalResults")
    private int totalResultados;

    public List<Noticia> getNoticiaList() {
        return noticiaList;
    }

    public void setNoticiaList(List<Noticia> noticiaList) {
        this.noticiaList = noticiaList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResultados() {
        return totalResultados;
    }

    public void setTotalResultados(int totalResultados) {
        this.totalResultados = totalResultados;
    }
}
