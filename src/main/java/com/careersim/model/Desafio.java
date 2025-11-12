package com.careersim.model;

public class Desafio
{
    private String descricao;
    private int impactoEmXP;
    private int impactoFinanceiro;

    public Desafio(String descricao, int impactoEmXP, int impactoFinanceiro)
    {
        this.descricao = descricao;
        this.impactoEmXP = impactoEmXP;
        this.impactoFinanceiro = impactoFinanceiro;
    }

    public void aplicarConsequencia(Desenvolvedor dev)
    {
        dev.setExperiencia(Math.max(0, dev.getExperiencia() - impactoEmXP));
        dev.setGold(Math.max(0, dev.getGold() - impactoFinanceiro));
    }

    @Override
    public String toString()
    {
        return descricao + " (Impacto XP: -" + impactoEmXP + ", Gold: -" + impactoFinanceiro + ")";
    }
}
