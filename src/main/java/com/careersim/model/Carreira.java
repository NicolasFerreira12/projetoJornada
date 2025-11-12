package com.careersim.model;

import java.util.ArrayList;
import java.util.List;

public class Carreira
{
    private Desenvolvedor dev;
    private List<Projeto> projetos = new ArrayList<>();
    private List<Desafio> desafios = new ArrayList<>();

    public Carreira(Desenvolvedor dev)
    {
        this.dev = dev;
    }

    public Projeto gerarProjetoAleatorio()
    {
        Projeto p = new Projeto("Sistema #" + (projetos.size() + 1),
                (int) (Math.random() * 5) + 1,
                100 + (int)(Math.random() * 100));
        projetos.add(p);
        return p;
    }

    public Desafio gerarDesafio()
    {
        Desafio d = new Desafio("Bug crítico inesperado!",
                50 + (int)(Math.random() * 50),
                30 + (int)(Math.random() * 30));
        desafios.add(d);
        return d;
    }

    public Desenvolvedor getDev()
    {
        return dev;
    }
}
