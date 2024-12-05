package br.univali.application;

public class Campanha {
    private String nome; // Nome da campanha
    private String descricao; // Descrição da campanha
    private ONG ong; // ONG associada à campanha
    private int imagem; // Imagem da campanha (placeholder, usando R.drawable)

    // Construtor
    public Campanha(String nome, String descricao, ONG ong, int imagem) {
        this.nome = nome;
        this.descricao = descricao;
        this.ong = ong;
        this.imagem = imagem;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public ONG getOng() {
        return ong;
    }

    public int getImagem() {
        return imagem;
    }
}
