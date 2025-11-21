package com.careersim.shop;

import com.careersim.model.Item;
import com.careersim.ui.App;

public class ItemEasterEgg extends Item
{

    public ItemEasterEgg() {
        super("Segredo do CEO",
                "Um item misterioso... Apenas CEOs com 1000+ gold podem descobrir!",
                1000,
                "🎁");
    }

    @Override
    public boolean deveAparecer(App app)
    {
        // Só aparece se for CEO E tiver pelo menos 1000 gold

        return app.isAtingiuCEO() && app.getGold() >= 1000;
    }

    @Override
    public String usar(App app)
    {
        try
        {
            // Efeito especial do Easter Egg

            app.setGoldBonus(app.getGoldBonus() * 3.0); // 3x exp
            app.setEnergia(app.getEnergiaMaxima()); // recuperar energia

            return "=== EASTER EGG ATIVADO! ===\n\n" +
                    "Você descobriu o Segredo do CEO!\n" +
                    "- Bônus de Gold TRIPLICADO!\n" +
                    "- Energia restaurada completamente!\n\n" +
                    "Parabéns, verdadeiro CEO!";

        } catch (Exception ex)
        {
            System.err.println("Erro ao ativar Easter Egg: " + ex.getMessage());
            ex.printStackTrace();
            return "Erro ao ativar o Segredo do CEO. Tente novamente.";
        }
    }
}