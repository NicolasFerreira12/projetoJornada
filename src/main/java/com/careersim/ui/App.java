package com.careersim.ui;

import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

public class App extends Application
{
    private int xp = 0;
    private int energia = 100;
    private int gold = 0;

    private Label lblStatus;
    private Label lblXP;
    private Label lblGold;
    private ProgressBar barraEnergia;

    private final Random random = new Random();

    @Override
    public void start(Stage stage)
    {
        // ===== LAYOUT PRINCIPAL =====
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #1E1E2F, #3A3A58);");

        // ===== TOPO (HUD COM MOEDA E GOLD) =====
        Image imgMoeda = new Image(getClass().getResourceAsStream("/imagens/moeda.png"));
        ImageView moedaView = new ImageView(imgMoeda);
        moedaView.setFitWidth(32);
        moedaView.setFitHeight(32);
        moedaView.setPreserveRatio(true);
        moedaView.setStyle("-fx-background-color: transparent;");

        lblGold = new Label(String.valueOf(gold));
        lblGold.setStyle("-fx-font-size: 18px; -fx-text-fill: gold; -fx-font-weight: bold;");

        HBox goldBox = new HBox(8);
        goldBox.setAlignment(Pos.CENTER_RIGHT);
        goldBox.getChildren().addAll(moedaView, lblGold);
        goldBox.setPadding(new Insets(10));
        Tooltip.install(moedaView, new Tooltip("Gold"));

        root.setTop(goldBox);
        BorderPane.setAlignment(goldBox, Pos.TOP_RIGHT);

        // ===== CENTRO (CONTEÚDO PRINCIPAL) =====
        VBox centro = new VBox(15);
        centro.setAlignment(Pos.CENTER);

        lblStatus = new Label("Bem-vindo à Jornada do Desenvolvedor!");
        lblStatus.setStyle("-fx-font-family: 'TT Rounds Neue Trial Light'; -fx-text-fill: white; -fx-font-size: 18px;");

        lblXP = new Label("XP: 0");
        lblXP.setStyle("-fx-text-fill: #08CB00; -fx-font-weight: bold;");

        barraEnergia = new ProgressBar(energia / 100.0);
        barraEnergia.setPrefWidth(250);

        Label lblEnergia = new Label("Energia");
        lblEnergia.setStyle("-fx-text-fill: #22D3EE; -fx-font-weight: bold;");

        Button btnEstudar = new Button("Estudar");
        Button btnTrabalhar = new Button("Trabalhar");
        Button btnLoja = new Button("Loja");
        Button btnCafe = new Button("Tomar Café");

        estilizarBotao(btnEstudar, "#3B82F6");
        estilizarBotao(btnTrabalhar, "#22C55E");
        estilizarBotao(btnLoja, "#F59E0B");
        estilizarBotao(btnCafe, "#8B5CF6");

        btnEstudar.setOnAction(e -> estudar());
        btnTrabalhar.setOnAction(e -> trabalhar());
        btnLoja.setOnAction(e -> abrirLoja());
        btnCafe.setOnAction(e -> tomarCafe());

        HBox botoes = new HBox(10, btnEstudar, btnTrabalhar, btnLoja, btnCafe);
        botoes.setAlignment(Pos.CENTER);

        centro.getChildren().addAll(lblStatus, lblXP, lblEnergia, barraEnergia, botoes);
        root.setCenter(centro);

        // ===== CENA E STAGE =====
        Scene scene = new Scene(root, 800, 600);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/imagens/logoJanela.png")));
        stage.setTitle("Jornada do Desenvolvedor");
        stage.setScene(scene);
        stage.show();

        // ===== REGENERAÇÃO AUTOMÁTICA =====
        iniciarRegeneracaoDeEnergia();
    }

    // ===== LÓGICA DO JOGO =====
    private void estudar()
    {
        if (energia >= 10)
        {
            energia -= 10;
            int ganho = 10 + random.nextInt(10);
            xp += ganho;
            lblStatus.setText("Você estudou e ganhou +" + ganho + " XP!");
        }
        else
        {
            lblStatus.setText("😴 Você está cansado demais para estudar!");
        }
        atualizarInterface();
    }

    private void trabalhar()
    {
        if (energia >= 20)
        {
            energia -= 20;
            int ganhoXP = 25 + random.nextInt(15);
            int ganhoGold = 5 + random.nextInt(10);
            xp += ganhoXP;
            gold += ganhoGold;
            lblStatus.setText("Você completou um projeto e ganhou +" + ganhoXP + " XP e +" + ganhoGold + " gold!");

            animarLabelGold(); // 💰 anima o label sempre que ganha moedas
        }
        else
        {
            lblStatus.setText("😫 Sem energia! Talvez um café ajude...");
        }
        atualizarInterface();
    }

    private void abrirLoja()
    {
        lblStatus.setText("🛒 A loja será implementada futuramente!");
    }

    private void tomarCafe()
    {
        if (energia < 100)
        {
            energia = Math.min(100, energia + 30);
            lblStatus.setText("☕ Você tomou um café e se sente renovado!");
        }
        else
        {
            lblStatus.setText("😎 Energia cheia! Mas nunca é tarde para outro café...");
        }
        atualizarInterface();
    }

    private void iniciarRegeneracaoDeEnergia()
    {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
            if (energia < 100)
            {
                energia = Math.min(energia + 2, 100);
                atualizarInterface();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void atualizarInterface()
    {
        lblXP.setText("XP: " + xp);
        lblGold.setText(String.valueOf(gold));
        barraEnergia.setProgress(energia / 100.0);
    }

    // ===== ANIMAÇÃO DO LABEL DE GOLD =====

    private void animarLabelGold()
    {
        ScaleTransition scale = new ScaleTransition(Duration.millis(150), lblGold);
        scale.setFromX(1.0);
        scale.setFromY(1.0);
        scale.setToX(1.4);
        scale.setToY(1.4);
        scale.setAutoReverse(true);
        scale.setCycleCount(2);
        scale.play();
    }

    private void estilizarBotao(Button btn, String cor)
    {
        btn.setStyle("-fx-background-color: " + cor + "; -fx-text-fill: white; -fx-font-weight: bold;");
        btn.setPrefWidth(110);
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
