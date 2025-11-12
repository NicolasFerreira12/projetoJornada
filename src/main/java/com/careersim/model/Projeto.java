package com.careersim.model;

public class Projeto
{
    private String nome;
    private int dificuldade;
    private int recompensaEmXP;

    public Projeto(String nome, int dificuldade, int recompensaEmXP)
    {
        this.nome = nome;
        this.dificuldade = dificuldade;
        this.recompensaEmXP = recompensaEmXP;
    }

    public void concluirProjeto(Desenvolvedor dev)
    {
        dev.setExperiencia(dev.getExperiencia() + recompensaEmXP);
        dev.setGold(dev.getGold() + (recompensaEmXP / 2));
    }

    @Override
    public String toString()
    {
        return nome + " (Dificuldade " + dificuldade + ")";
    }
}
