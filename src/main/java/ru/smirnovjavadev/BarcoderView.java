package ru.smirnovjavadev;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;
import javafx.scene.Scene;

public class BarcoderView {
    private final ComboBox<String> typeComboBox = new ComboBox<>();
    private final ComboBox<String> productComboBox = new ComboBox<>();
    private final VBox detailsBox = new VBox(5);
    private final TextField searchField = new TextField();
    private final Button searchButton = new Button("Найти");
    private final ScrollPane scrollPane = new ScrollPane();
    private final VBox mainLayout;
    private final double BASE_WINDOW_HEIGHT = 400;
    private final double ITEM_HEIGHT = 30;
    private final double MAX_WINDOW_HEIGHT = 700;

    public BarcoderView() {
        setupUI();
        mainLayout = createMainLayout();
        detailsBox.setId("detailsBox");
    }

    private void setupUI() {
        typeComboBox.setPromptText("Выберите тип ЛКМ");
        productComboBox.setPromptText("Выберите продукт");
        searchField.setPromptText("Поиск по названию продукта");

        // Настройка ScrollPane
        scrollPane.setContent(detailsBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setMinHeight(150);

        // Настройка detailsBox
        detailsBox.setPadding(new Insets(10));
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
    }

    private VBox createMainLayout() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.setPrefSize(434, BASE_WINDOW_HEIGHT);

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

    // Методы для управления окном
    public void adjustWindowHeight(int itemCount) {
        double calculatedHeight = BASE_WINDOW_HEIGHT + (itemCount * ITEM_HEIGHT);
        double newHeight = Math.min(Math.max(calculatedHeight, BASE_WINDOW_HEIGHT), MAX_WINDOW_HEIGHT);

        Scene scene = mainLayout.getScene();
        if (scene != null && scene.getWindow() != null) {
            scene.getWindow().setHeight(newHeight);
            scrollPane.setVvalue(0); // Прокрутка в начало
        }
    }

    public void resetWindowHeight() {
        adjustWindowHeight(0);
    }

    // Геттеры
    public ComboBox<String> getTypeComboBox() { return typeComboBox; }
    public ComboBox<String> getProductComboBox() { return productComboBox; }
    public VBox getDetailsBox() { return detailsBox; }
    public TextField getSearchField() { return searchField; }
    public Button getSearchButton() { return searchButton; }
    public VBox getLayout() { return mainLayout; }

    // Методы для работы с содержимым
    public void clearDetails() {
        detailsBox.getChildren().clear();
    }

    public void addDetailRow(HBox row) {
        detailsBox.getChildren().add(row);
    }

    public void addProductHeader(String productName) {
        Label header = new Label(productName);
        detailsBox.getChildren().add(header);
    }

    public static HBox createDetailRow(int id, String volume) {
        TextField idField = new TextField(String.valueOf(id));
        idField.setEditable(false);
        idField.setPrefWidth(80);
        idField.getStyleClass().add("id-field");

        Label volumeLabel = new Label(volume);
        volumeLabel.setPrefWidth(100);
        volumeLabel.getStyleClass().add("volume-label");

        Button copyButton = new Button("Копировать");
        copyButton.setOnAction(e -> {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(String.valueOf(id));
            clipboard.setContent(content);
        });

        HBox row = new HBox(10, copyButton, idField, volumeLabel);
        row.setAlignment(Pos.CENTER_LEFT);
        row.getStyleClass().add("detail-row");
        return row;
    }
}