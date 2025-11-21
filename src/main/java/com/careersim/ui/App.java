package com.careersim.ui;

import com.careersim.model.Item;
import com.careersim.shop.Loja;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;
import java.util.Random;

public class App extends Application
{
    private int xp = 0;
    private int energia = 100;
    private int energiaMaxima = 100;
    private int gold = 0;
    private int nivel = 1;
    private double goldBonus = 1.0;
    private boolean atingiuCEO = false;

    private Label lblStatus;
    private Label lblGold;
    private Label lblCargo;
    private ProgressBar barraEnergia;
    private ProgressBar barraXP;

    private final Random random = new Random();
    private Loja loja = new Loja();

    @Override
    public void start(Stage stage)
    {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #1E1E2F, #3A3A58);");

        // TOPO

        Image imgMoeda = new Image(getClass().getResourceAsStream("/imagens/moeda.png"));
        ImageView moedaView = new ImageView(imgMoeda);
        moedaView.setFitWidth(32);
        moedaView.setFitHeight(32);

        lblGold = new Label(String.valueOf(gold));
        lblGold.setStyle("-fx-font-size: 18px; -fx-text-fill: gold; -fx-font-weight: bold;");

        barraXP = new ProgressBar(0);
        barraXP.setPrefWidth(150);
        barraXP.setStyle("-fx-accent: #22C55E;");

        lblCargo = new Label("Cargo: Júnior");
        lblCargo.setStyle("-fx-text-fill: #A5B4FC; -fx-font-size: 14px; -fx-font-weight: bold;");

        HBox topBox = new HBox(15, moedaView, lblGold, barraXP, lblCargo);
        topBox.setAlignment(Pos.CENTER_RIGHT);
        topBox.setPadding(new Insets(10));

        root.setTop(topBox);
        BorderPane.setAlignment(topBox, Pos.TOP_RIGHT);

        // CENTRO

        VBox centerBox = new VBox(15);
        centerBox.setAlignment(Pos.CENTER);

        lblStatus = new Label("Bem-vindo à Jornada do Desenvolvedor!");
        lblStatus.setStyle("-fx-text-fill: white; -fx-font-size: 18px;");

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

        centerBox.getChildren().addAll(lblStatus, lblEnergia, barraEnergia, botoes);
        root.setCenter(centerBox);

        Scene scene = new Scene(root, 800, 600);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/imagens/logoJanela.png")));
        stage.setTitle("Jornada do Desenvolvedor");
        stage.setScene(scene);
        stage.show();

        iniciarRegeneracaoDeEnergia();
    }

    // Lógica do jogo

    private void estudar()
    {
        if (energia >= 15)
        {
            energia -= 10;
            int ganho = 10 + random.nextInt(10);
            xp += ganho;

            lblStatus.setText("Você estudou e ganhou +" + ganho + " XP!");
            verificarNivel();
        }
        else
        {
            lblStatus.setText("Você está cansado demais para estudar!");
        }
        atualizarInterface();
    }

    private void trabalhar()
    {
        if (energia >= 20)
        {
            energia -= 20;

            int ganhoXP = 1 + random.nextInt(5);
            int ganhoGoldBase;

            switch (nivel)
            {
                case 1: // Júnior
                    ganhoGoldBase = 5 + random.nextInt(6); // 5–10
                    break;
                case 2: // Pleno
                    ganhoGoldBase = 10 + random.nextInt(11); // 10–20
                    break;
                case 3: // Sênior
                    ganhoGoldBase = 20 + random.nextInt(16); // 20–35
                    break;
                default: // CEO ou acima
                    ganhoGoldBase = 40 + random.nextInt(21); // 40–60
                    break;
            }

            int ganhoGold = (int) (ganhoGoldBase * goldBonus);
            xp += ganhoXP;
            gold += ganhoGold;

            lblStatus.setText("Você completou um projeto e ganhou +" + ganhoXP + " XP e +" + ganhoGold + " gold!");
            verificarNivel();
        }
        else
        {
            lblStatus.setText("Sem energia! Talvez um café ajude.");
        }
        atualizarInterface();
    }

    private void abrirLoja()
    {
        Stage stageLoja = new Stage();
        stageLoja.setTitle("Loja");

        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #1E1E2F, #3A3A58);");

        Label titulo = new Label("-- LOJA --");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #F59E0B;");

        Label saldo = new Label("Seu Gold: " + gold);
        saldo.setStyle("-fx-font-size: 18px; -fx-text-fill: gold; -fx-font-weight: bold;");

        // Pega apenas os itens disponíveis para este jogador
        List<Item> itensDisponiveis = loja.getItensDisponiveis(this);

        ListView<Item> listaItens = new ListView<>();
        listaItens.getItems().addAll(itensDisponiveis);
        listaItens.setPrefHeight(300);
        listaItens.setStyle("-fx-background-color: #2D2D44; -fx-border-color: #4A4A6A; -fx-control-inner-background: #2D2D44;");

        // Customizar células da lista - VERSÃO CORRIGIDA

        listaItens.setCellFactory(param -> new ListCell<Item>() {
            @Override
            protected void updateItem(Item item, boolean empty)
            {
                super.updateItem(item, empty);
                if (empty || item == null)
                {
                    setText(null);
                    setGraphic(null);
                    setStyle("-fx-background-color: transparent;");
                } else
                {
                    // Criar o texto com quebra de linha

                    String textoCompleto = item.toString() + "\n   " + item.getDescricao();
                    setText(textoCompleto);
                    setGraphic(null);
                    setStyle("-fx-text-fill: white; " +
                            "-fx-font-size: 13px; " +
                            "-fx-padding: 10; " +
                            "-fx-background-color: #2D2D44; " +
                            "-fx-border-color: #4A4A6A; " +
                            "-fx-border-width: 0 0 1 0;");

                    // Estilo quando passar o mouse

                    setOnMouseEntered(e -> {
                        if (!isEmpty())
                        {
                            setStyle("-fx-text-fill: white; " +
                                    "-fx-font-size: 13px; " +
                                    "-fx-padding: 10; " +
                                    "-fx-background-color: #3A3A58; " +
                                    "-fx-border-color: #4A4A6A; " +
                                    "-fx-border-width: 0 0 1 0;");
                        }
                    });

                    setOnMouseExited(e -> {
                        if (!isEmpty() && !isSelected())
                        {
                            setStyle("-fx-text-fill: white; " +
                                    "-fx-font-size: 13px; " +
                                    "-fx-padding: 10; " +
                                    "-fx-background-color: #2D2D44; " +
                                    "-fx-border-color: #4A4A6A; " +
                                    "-fx-border-width: 0 0 1 0;");
                        }
                    });
                }
            }
        });

        Label lblDescricao = new Label("Selecione um item para ver os detalhes");
        lblDescricao.setStyle("-fx-text-fill: #A5B4FC; -fx-font-size: 14px;");
        lblDescricao.setWrapText(true);
        lblDescricao.setMaxWidth(550);

        Button btnComprar = new Button("Comprar");
        estilizarBotao(btnComprar, "#22C55E");
        btnComprar.setDisable(true);

        Button btnFechar = new Button("Fechar");
        estilizarBotao(btnFechar, "#EF4444");

        // Quando selecionar um item

        listaItens.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                lblDescricao.setText("📝 " + newVal.getDescricao() +
                        "\nPreço: " + newVal.getPreco() + " gold");
                btnComprar.setDisable(gold < newVal.getPreco());
            }
        });

        // Ao clicar em comprar

        btnComprar.setOnAction(e -> {
            Item itemSelecionado = listaItens.getSelectionModel().getSelectedItem();

            if (itemSelecionado != null && gold >= itemSelecionado.getPreco())
            {
                gold -= itemSelecionado.getPreco();
                String mensagem = itemSelecionado.usar(this);

                lblStatus.setText(mensagem);
                saldo.setText("Seu Gold: " + gold);
                atualizarInterface();

                // Atualiza a lista de itens (caso o Easter Egg tenha aparecido/sumido)

                List<Item> novosItens = loja.getItensDisponiveis(this);
                listaItens.getItems().clear();
                listaItens.getItems().addAll(novosItens);

                // Atualizar botão de comprar

                if (itemSelecionado != null)
                {
                    btnComprar.setDisable(gold < itemSelecionado.getPreco());
                }

                // Mostrar confirmação

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Compra realizada!");
                alert.setHeaderText(null);
                alert.setContentText(mensagem);
                alert.showAndWait();
            }
        });

        btnFechar.setOnAction(e -> stageLoja.close());

        HBox botoes = new HBox(10, btnComprar, btnFechar);
        botoes.setAlignment(Pos.CENTER);

        root.getChildren().addAll(titulo, saldo, listaItens, lblDescricao, botoes);

        Scene scene = new Scene(root, 600, 500);
        stageLoja.setScene(scene);
        stageLoja.show();
    }

    private void tomarCafe()
    {
        if (energia < energiaMaxima)
        {
            energia = Math.min(energiaMaxima, energia + 30);
            lblStatus.setText("Café de testes!");
        }
        else
        {
            lblStatus.setText("Energia cheia!");
        }
        atualizarInterface();
    }

    // Sistema de nível

    private void verificarNivel()
    {
        if (atingiuCEO)
        {
            return;
        }

        while (xp >= 100 && !atingiuCEO)
        {
            xp -= 100;
            nivel++;

            if (nivel == 2)
                lblCargo.setText("Cargo: Pleno");
            else if (nivel == 3)
                lblCargo.setText("Cargo: Sênior");
            else if (nivel >= 4)
            {
                lblCargo.setText("Cargo: CEO");
                atingiuCEO = true;
                xp = 100; // barra cheia
                lblStatus.setText("Você atingiu o topo da carreira: CEO!");
                break;
            }

            goldBonus *= 1.2;
            lblStatus.setText("Parabéns! Você foi promovido a " + lblCargo.getText().replace("Cargo: ", "") + "!");
        }
    }

    private void iniciarRegeneracaoDeEnergia()
    {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e ->
        {
            if (energia < energiaMaxima)
            {
                energia = Math.min(energia + 2, energiaMaxima);
                atualizarInterface();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void atualizarInterface()
    {
        lblGold.setText(String.valueOf(gold));
        barraEnergia.setProgress(energia / (double) energiaMaxima);
        barraXP.setProgress(Math.min(xp / 100.0, 1.0));
    }

    private void estilizarBotao(Button btn, String cor)
    {
        btn.setStyle("-fx-background-color: " + cor + "; -fx-text-fill: white; -fx-font-weight: bold;");
        btn.setPrefWidth(110);
    }

    // Getters e Setters

    public int getXp()
    {
        return xp;
    }

    public void setXp(int xp)
    {
        this.xp = xp;
        verificarNivel();
        atualizarInterface();
    }

    public int getEnergia()
    {
        return energia;
    }

    public void setEnergia(int energia)
    {
        this.energia = Math.min(energiaMaxima, energia);
        atualizarInterface();
    }

    public int getEnergiaMaxima()
    {
        return energiaMaxima;
    }

    public void setEnergiaMaxima(int energiaMaxima)
    {
        this.energiaMaxima = energiaMaxima;
    }

    public int getGold()
    {
        return gold;
    }

    public void setGold(int gold)
    {
        this.gold = gold;
        atualizarInterface();
    }

    public double getGoldBonus()
    {
        return goldBonus;
    }

    public void setGoldBonus(double goldBonus)
    {
        this.goldBonus = goldBonus;
    }

    public boolean isAtingiuCEO()
    {
        return atingiuCEO;
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}