package serverest.model;

import com.github.javafaker.Faker;

public class ProdutoDTO {

    private String nome;
    private Integer preco;
    private String descricao;
    private Integer quantidade;

    public ProdutoDTO(String nome, Integer preco, String descricao, Integer quantidade) {
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.quantidade = quantidade;
    }

    public String getNome() {
        return nome;
    }

    public Integer getPreco() {
        return preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }
}