package com.me.techfy.techfyme.DTO;

import android.widget.ImageView;

import java.util.Date;

public class UsuarioDTO {

    private String nome;
    private String sobrenome;
    private String email;
    private Date dataNascimento;
    private Integer genero;
    private ImageView imagemUsuario;



  //  private List<Integer> checados;


    public ImageView getImagemUsuario() {
        return imagemUsuario;
    }

    public void setImagemUsuario(ImageView imagemUsuario) {
        this.imagemUsuario = imagemUsuario;
    }

    private PreferenciaDTO preferencias;

    public UsuarioDTO() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Integer getGenero() {
        return genero;
    }

    public void setGenero(Integer genero) {
        this.genero = genero;
    }

    public PreferenciaDTO getPreferencias() {
        return preferencias;
    }

    public void setPreferencias(PreferenciaDTO preferencias) {
        this.preferencias = preferencias;
    }

}