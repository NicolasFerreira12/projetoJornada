package com.careersim.model;

public class Desafio
{
    private String descricao;
    private int xpGanho;
    private int energiaPerdida;

    public Desafio(String descricao, int xpGanho, int energiaPerdida)
    {
        this.descricao = descricao;
        this.xpGanho = xpGanho;
        this.energiaPerdida = energiaPerdida;
    }

    public void enfrentar(Desenvolvedor dev)
    {
        dev.setExperiencia(dev.getExperiencia() + xpGanho);
        dev.setEnergia(Math.max(dev.getEnergia() - energiaPerdida, 0));

        dev.subirDeCargo();
    }

    public String getDescricao() { return descricao; }
}
