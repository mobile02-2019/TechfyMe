package com.me.techfy.techfyme.modelo;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "updateNoticia")
public class NoticiaDb {

    @PrimaryKey
    private Integer id;

    @ColumnInfo(name = "dataDbNoticia")
    private Date dataDbNoticia;

    @ColumnInfo(name = "tituloNoticia")
    private String titulo;

    @ColumnInfo(name = "fonteNoticia")
    private String fonte;

    @ColumnInfo(name = "descricaoNoticia")
    private String descricao;

    @ColumnInfo(name = "textoCompletoNoticia")
    private  String textoCompleto;

    @ColumnInfo(name = "imagemUrlNoticia")
    private String imagemUrl;

    @ColumnInfo(name = "dataCriacaoNoticia")
    private String dataCriacao;

    @ColumnInfo(name = "linkMateriaNoticia")
    private String linkDaMateria;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataDbNoticia() {
        return dataDbNoticia;
    }

    public void setDataDbNoticia(Date dataDbNoticia) {
        this.dataDbNoticia = dataDbNoticia;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFonte() {
        return fonte;
    }

    public void setFonte(String fonte) {
        this.fonte = fonte;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTextoCompleto() {
        return textoCompleto;
    }

    public void setTextoCompleto(String textoCompleto) {
        this.textoCompleto = textoCompleto;
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
