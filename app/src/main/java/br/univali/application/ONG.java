package br.univali.application;

public class ONG {
    private final String nome;
    private final String descricao;

    public ONG(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
}

