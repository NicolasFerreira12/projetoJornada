package com.careersim.shop;

import com.careersim.model.Item;
import com.careersim.ui.App;

public class ItemCafe extends Item {

    public ItemCafe() {
        super("Café Premium",
                "Restaura 30% da sua energia atual",
                25,
                "☕");
    }

    @Override
    public String usar(App app)
    {
        try
        {
            int energiaAtual = app.getEnergia();
            int energiaMaxima = app.getEnergiaMaxima();

            // Calcula 30% da energia máxima
            int restauracao = (int) (energiaMaxima * 0.30);

            int novaEnergia = Math.min(energiaMaxima, energiaAtual + restauracao);
            app.setEnergia(novaEnergia);

            return "Você tomou um Café Premium e recuperou " + (novaEnergia - energiaAtual) + " de energia!";

        } catch (Exception ex)
        {
            System.err.println("Erro ao usar Café Premium: " + ex.getMessage());
            ex.printStackTrace();
            return "Erro ao usar Café Premium. Tente novamente.";
        }
    }
}