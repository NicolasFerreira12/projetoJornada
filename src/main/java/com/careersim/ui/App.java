package com.careersim.ui;

import com.careersim.model.*;
import com.careersim.shop.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application
{
    @Override
    public void start(Stage stage)
    {
        Desenvolvedor dev = new Desenvolvedor("Jogador");
        Carreira carreira = new Carreira(dev);
        Loja loja = new Loja(dev);

        TextArea log = new TextArea();
        log.setEditable(false);

        Button estudar = new Button("Estudar");
        Button trabalhar = new Button("Trabalhar");
        Button gerarProjeto = new Button("Gerar Projeto");
        Button gerarDesafio = new Button("Gerar Desafio");
        Button lojaBtn = new Button("Loja");

        estudar.setOnAction(e ->
        {
            dev.estudar();
            log.appendText("Você estudou! XP: " + dev.getExperiencia() + "\n");
        });

        trabalhar.setOnAction(e ->
        {
            dev.trabalharEmProjeto();
            log.appendText("Você trabalhou! XP: " + dev.getExperiencia() + ", Gold: " + dev.getGold() + "\n");
        });

        gerarProjeto.setOnAction(e ->
        {
            Projeto p = carreira.gerarProjetoAleatorio();
            log.appendText("Novo projeto criado!\n");
        });

        gerarDesafio.setOnAction(e ->
        {
            Desafio d = carreira.gerarDesafio();
            d.aplicarConsequencia(dev);
            log.appendText("Desafio enfrentado: XP=" + dev.getExperiencia() + " Gold=" + dev.getGold() + "\n");
        });

        lojaBtn.setOnAction(e -> loja.abrirLoja());

        VBox root = new VBox(10, estudar, trabalhar, gerarProjeto, gerarDesafio, lojaBtn, log);
        root.setPadding(new Insets(10));
        stage.setScene(new Scene(root, 400, 400));
        stage.setTitle("Projeto Jornada");
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}
