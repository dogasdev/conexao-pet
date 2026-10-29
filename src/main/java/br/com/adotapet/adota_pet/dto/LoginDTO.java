package br.com.adotapet.adota_pet.dto;

public class LoginDTO {

    private String identificador;
    private String senha;

    public LoginDTO() {
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}