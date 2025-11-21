package com.careersim.model;

import java.util.ArrayList;
import java.util.List;

public class Desenvolvedor
{
    private String nome;
    private String nivel;
    private int experiencia;
    private int gold;
    private int energia;
    private List<String> habilidades;

    public Desenvolvedor(String nome)
    {
        this.nome = nome;
        this.nivel = "Estagiário";
        this.experiencia = 0;
        this.gold = 100;
        this.energia = 100;
        this.habilidades = new ArrayList<>();
    }

    public void estudar()
    {
        if (energia < 10)
            return;

        experiencia += 20;
        energia -= 10;

        if (Math.random() < 0.2)
            habilidades.add("Nova skill #" + (habilidades.size() + 1));

        subirDeCargo();
    }

    public void trabalharEmProjeto()
    {
        if (energia < 15)
            return;

        experiencia += 40;
        gold += 30;
        energia -= 15;

        subirDeCargo();
    }

    public void subirDeCargo()
    {
        if (experiencia >= 1000 && nivel.equals("Estagiário"))
            nivel = "Júnior";
        else if (experiencia >= 3000 && nivel.equals("Júnior"))
            nivel = "Pleno";
        else if (experiencia >= 6000 && nivel.equals("Pleno"))
            nivel = "Sênior";
        else if (experiencia >= 10000 && nivel.equals("Sênior"))
            nivel = "CEO";
    }

    // Getters e setters

    public String getNome()
    {
        return nome;
    }

    public String getNivel()
    {
        return nivel;
    }

    public int getExperiencia()
    {
        return experiencia;
    }

    public void setExperiencia(int experiencia)
    {
        this.experiencia = experiencia;
    }

    public int getGold()
    {
        return gold;
    }

    public void setGold(int gold)
    {
        this.gold = gold;
    }

    public int getEnergia()
    {
        return energia;
    }

    public void setEnergia(int energia)
    {
        this.energia = energia;
    }

    public List<String> getHabilidades()
    {
        return habilidades;
    }
}