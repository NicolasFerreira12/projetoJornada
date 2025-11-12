package com.careersim.shop;

public class ShopItem
{
    private String nome;
    private int preco;
    private Runnable efeito;

    public ShopItem(String nome, int preco, Runnable efeito)
    {
        this.nome = nome;
        this.preco = preco;
        this.efeito = efeito;
    }

    public void aplicarEfeito()
    {
        efeito.run();
    }

    public int getPreco() { return preco; }
    public String getNome() { return nome; }

    @Override
    public String toString()
    {
        return nome + " (" + preco + " gold)";
    }
}
