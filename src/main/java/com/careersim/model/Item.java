package com.careersim.model;

import com.careersim.ui.App;

public abstract class Item
{
    private String nome;
    private String descricao;
    private int preco;
    private String emoji;

    public Item(String nome, String descricao, int preco, String emoji)
    {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.emoji = emoji;
    }

    // Método abstrato que cada tipo de item implementa
    public abstract String usar(App app);

    // Verifica se o item deve aparecer na loja

    public boolean deveAparecer(App app) {
        return true; // Por padrão, todos aparecem
    }

    public String getNome()
    {
        return nome;
    }

    public String getDescricao()
    {
        return descricao;
    }

    public int getPreco()
    {
        return preco;
    }

    public String getEmoji()
    {
        return emoji;
    }

    @Override
    public String toString()
    {
        return emoji + " " + nome + " - " + preco + " gold";
    }
}