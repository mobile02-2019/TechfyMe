package com.me.techfy.techfyme.modelo;

import android.media.Image;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Noticia implements Serializable {

    @SerializedName("title")
    private String titulo;
    @SerializedName("name")
    private String fonte;
    @SerializedName("description")
    private String descricao;
    @SerializedName("content")
    private  String textoCompleto;
    @SerializedName("urlToImage")
    private String imagemUrl;
    @SerializedName("publishedAt")
    private String dataCriacao;
    @SerializedName("url")
    private String linkDaMateria;



    public String getTextoCompleto() {
        return textoCompleto;
    }

    public void setTextoCompleto(String textoCompleto) {
        this.textoCompleto = textoCompleto;
    }

    public String getFonte() {
        return fonte;
    }

    public void setFonte(String fonte) {
        this.fonte = fonte;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getLinkDaMateria() {
        return linkDaMateria;
    }

    public void setLinkDaMateria(String linkDaMateria) {
        this.linkDaMateria = linkDaMateria;
    }
}
