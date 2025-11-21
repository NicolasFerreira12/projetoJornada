package com.careersim.shop;

import com.careersim.model.Item;
import com.careersim.ui.App;
import java.util.ArrayList;
import java.util.List;

public class Loja
{
    private List<Item> todosItens;

    public Loja()
    {
        this.todosItens = new ArrayList<>();
        inicializarItens();
    }

    private void inicializarItens()
    {
        // Adiciona todos os itens possíveis

        todosItens.add(new ItemCafe());
        todosItens.add(new ItemBoost());
        todosItens.add(new ItemEasterEgg());
    }

    // Retorna apenas os itens que devem aparecer para o jogador atual

    public List<Item> getItensDisponiveis(App app)
    {
        List<Item> disponiveis = new ArrayList<>();

        for (Item item : todosItens)
        {
            if (item.deveAparecer(app))
            {
                disponiveis.add(item);
            }
        }

        return disponiveis;
    }
}