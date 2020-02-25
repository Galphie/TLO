package com.galphredsy.tlo;

public class Usuario {
    String id_usuario;
    String password;

    public Usuario() {
        super();
    }
    public Usuario(String id_usuario, String password) {
        super();
        this.id_usuario = id_usuario;
        this.password = password;
    }


    public String getId_usuario() {
        return id_usuario;
    }


    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id_usuario='" + id_usuario + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
