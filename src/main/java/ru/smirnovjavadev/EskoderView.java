package ru.smirnovjavadev;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class EskoderView {
    private final ComboBox<String> typeComboBox = new ComboBox<>();
    private final ComboBox<String> productComboBox = new ComboBox<>();
    private final VBox detailsBox = new VBox(5);
    private final TextField searchField = new TextField();
    private final Button searchButton = new Button("Найти");
    private final ScrollPane scrollPane = new ScrollPane();
    private final VBox mainLayout;
    private final double BASE_WINDOW_HEIGHT = 400;
    private final double BASE_WINDOW_WIDTH = 434;
    private final double ITEM_HEIGHT = 30;
    private final double MAX_WINDOW_HEIGHT = 700;


    public EskoderView() {
        setupUI();
        mainLayout = createMainLayout();
        detailsBox.setId("detailsBox"); // для CSS #detailsBox
    }

    private void setupUI() {
        typeComboBox.setPromptText("Выберите тип ЛКМ");
        productComboBox.setPromptText("Выберите продукт");
        searchField.setPromptText("Поиск по названию продукта");

        scrollPane.setContent(detailsBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setMinHeight(150);

        detailsBox.setPadding(new Insets(10));
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
    }

    private VBox createMainLayout() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.setPrefSize(BASE_WINDOW_WIDTH, BASE_WINDOW_HEIGHT);

        HBox searchBox = new HBox(10, searchField, searchButton);
        searchBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(searchField, Priority.ALWAYS);

        layout.getChildren().addAll(
                searchBox,
                typeComboBox,
                productComboBox,
                scrollPane
        );

        return layout;
    }

    public void adjustWindowHeight(int itemCount) {
        double calculatedHeight = BASE_WINDOW_HEIGHT + (itemCount * ITEM_HEIGHT);
        double newHeight = Math.min(Math.max(calculatedHeight, BASE_WINDOW_HEIGHT), MAX_WINDOW_HEIGHT);

        Scene scene = mainLayout.getScene();
        if (scene != null && scene.getWindow() != null) {
            scene.getWindow().setHeight(newHeight);
            scrollPane.setVvalue(0);
        }
    }

    public void resetWindowHeight() {
        adjustWindowHeight(0);
    }

    // Геттеры
    public ComboBox<String> getTypeComboBox()   { return typeComboBox; }
    public ComboBox<String> getProductComboBox(){ return productComboBox; }
    public VBox getDetailsBox()                 { return detailsBox; }
    public TextField getSearchField()           { return searchField; }
    public Button getSearchButton()             { return searchButton; }
    public VBox getLayout()                     { return mainLayout; }

    // Работа с деталями
    public void clearDetails() {
        detailsBox.getChildren().clear();
    }

    public void addDetailRow(HBox row) {
        detailsBox.getChildren().add(row);
    }

    public void addProductDetails(Product product) {
        addProductHeader(product.getName());
        for (Item item : product.getItems()) {
            HBox row = createDetailRow(item.getId(), item.getVolume());
            addDetailRow(row);
        }
    }

    public void addProductHeader(String productName) {
        Label header = new Label(productName);
        detailsBox.getChildren().add(header);
    }

    public static HBox createDetailRow(int id, String volume) {
        TextField idField = new TextField(String.valueOf(id));
        idField.setEditable(false);
        idField.setPrefWidth(80);

        Label volumeLabel = new Label(volume);
        volumeLabel.setPrefWidth(100);

        Button copyButton = new Button("Копировать");
        copyButton.setOnAction(e -> {
            ClipboardContent content = new ClipboardContent();
            content.putString(String.valueOf(id));
            Clipboard.getSystemClipboard().setContent(content);
        });

        HBox row = new HBox(10, copyButton, idField, volumeLabel);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }
}
