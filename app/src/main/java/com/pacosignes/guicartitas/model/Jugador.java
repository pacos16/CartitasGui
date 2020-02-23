package com.pacosignes.guicartitas.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Jugador {

    @SerializedName("correo")
    @Expose
    private String correo;
    @SerializedName("contraseña")
    @Expose
    private String contrasenya;
    @SerializedName("nickname")
    @Expose
    private String nickName;

    public Jugador(){

    }
    public Jugador(String correo, String contrasenya, String nickName) {
        this.correo = correo;
        this.contrasenya = contrasenya;
        this.nickName = nickName;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "correo='" + correo + '\'' +
                ", contrasenya='" + contrasenya + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
