package com.careersim.shop;

import com.careersim.model.Item;
import com.careersim.ui.App;

public class ItemBoost extends Item {
    private static final int BOOST_QUANTIDADE = 20;
    private static final int BOOST_MAXIMO = 200;

    public ItemBoost() {
        super("Energy Boost",
                "Aumenta sua energia máxima em +20 (máx: +200)",
                100,
                "⚡");
    }

    @Override
    public String usar(App app)
    {
        try
        {
            int energiaMaximaAtual = app.getEnergiaMaxima();
            int boostAtual = energiaMaximaAtual - 100; // 100 é a energia base

            // Verifica se já atingiu o limite de boost

            if (boostAtual >= BOOST_MAXIMO)
            {
                return "Você já atingiu o limite máximo de energia (" + (100 + BOOST_MAXIMO) + ")!";
            }

            // Calcula quanto pode adicionar

            int boostRestante = BOOST_MAXIMO - boostAtual;
            int boostAAdicionar = Math.min(BOOST_QUANTIDADE, boostRestante);

            int novaEnergiaMaxima = energiaMaximaAtual + boostAAdicionar;
            app.setEnergiaMaxima(novaEnergiaMaxima);

            // Também restaura a energia para o novo máximo

            app.setEnergia(novaEnergiaMaxima);

            int boostTotal = novaEnergiaMaxima - 100;

            return "Energy Boost ativado! Energia máxima aumentada para " + novaEnergiaMaxima + " (+" + boostTotal + " de boost total)";

        } catch (Exception ex)
        {
            System.err.println("Erro ao usar Energy Boost: " + ex.getMessage());
            ex.printStackTrace();
            return "Erro ao usar Energy Boost. Tente novamente.";
        }
    }
}