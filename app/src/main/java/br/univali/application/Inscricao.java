package br.univali.application;

public class Inscricao {

    private String userId;
    private String name;
    private String email;
    private String cpf;

    // Construtor
    public Inscricao(String userId, String name, String email, String cpf) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
    }

    // Getters e Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
