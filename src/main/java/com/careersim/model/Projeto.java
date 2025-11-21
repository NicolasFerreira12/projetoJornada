package com.careersim.model;

public class Projeto
{
    private String nome;
    private int dificuldade;
    private int recompensaEmXP;
    private int recompensaEmGold;

    // Construtor com 3 parâmetros (calcula gold automaticamente)

    public Projeto(String nome, int dificuldade, int recompensaEmXP)
    {
        this.nome = nome;
        this.dificuldade = dificuldade;
        this.recompensaEmXP = recompensaEmXP;
        this.recompensaEmGold = recompensaEmXP / 2; // Calcula automaticamente
    }

    // Construtor com 4 parâmetros (permite definir gold manualmente)

    public Projeto(String nome, int dificuldade, int recompensaEmXP, int recompensaEmGold)
    {
        this.nome = nome;
        this.dificuldade = dificuldade;
        this.recompensaEmXP = recompensaEmXP;
        this.recompensaEmGold = recompensaEmGold;
    }

    public void concluirProjeto(Desenvolvedor dev)
    {
        dev.setExperiencia(dev.getExperiencia() + recompensaEmXP);
        dev.setGold(dev.getGold() + recompensaEmGold);
    }

    @Override
    public String toString()
    {
        return nome + " (Dificuldade " + dificuldade + ")";
    }
}