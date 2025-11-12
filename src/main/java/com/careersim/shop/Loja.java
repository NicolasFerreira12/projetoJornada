package com.careersim.shop;

import com.careersim.model.Desenvolvedor;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import java.util.ArrayList;
import java.util.List;

public class Loja
{
    private Desenvolvedor dev;
    private List<ShopItem> itens;

    public Loja(Desenvolvedor dev)
    {
        this.dev = dev;
        this.itens = new ArrayList<>();

        itens.add(new ShopItem("Café Energético ☕", 50, () -> {
            dev.setEnergia(Math.min(100, dev.getEnergia() + 30));
        }));

        itens.add(new ShopItem("Curso Online 💻", 200, () -> {
            dev.setExperiencia(dev.getExperiencia() + 150);
        }));

        itens.add(new ShopItem("Monitor UltraWide 🖥️", 500, () -> {
            dev.setExperiencia(dev.getExperiencia() + 300);
            dev.setEnergia(Math.min(100, dev.getEnergia() + 10));
        }));
    }

    public void abrirLoja()
    {
        StringBuilder conteudo = new StringBuilder("Gold atual: " + dev.getGold() + "\n\n");
        for (int i = 0; i < itens.size(); i++)
        {
            ShopItem item = itens.get(i);
            conteudo.append((i + 1) + ". " + item.getNome() + " - " + item.getPreco() + " gold\n");
        }

        Alert alert = new Alert(AlertType.INFORMATION, conteudo.toString(), ButtonType.CLOSE);
        alert.setHeaderText("Loja de Upgrades");
        alert.showAndWait();

        // Easter Egg: Café dos Deuses ☕
        if (dev.getExperiencia() >= 777 && dev.getGold() >= 777)
        {
            Alert easterEgg = new Alert(AlertType.INFORMATION);
            easterEgg.setTitle("☕ Café dos Deuses");
            easterEgg.setHeaderText("VOCÊ DESBLOQUEOU O CAFÉ DOS DEUSES!");
            easterEgg.setContentText("Seu próximo café concede energia infinita temporariamente!");
            easterEgg.showAndWait();

            dev.setEnergia(999); // energia especial!
        }
    }
}
