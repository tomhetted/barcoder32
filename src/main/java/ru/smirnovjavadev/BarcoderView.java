package ru.smirnovjavadev;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class BarcoderView {
    private final ComboBox<String> typeComboBox = new ComboBox<>();
    private final ComboBox<String> productComboBox = new ComboBox<>();
    private final VBox detailsBox = new VBox(5);
    private final TextField searchField = new TextField();
    private final Button searchButton = new Button("Найти");
    private final VBox mainLayout;

    public BarcoderView() {
        setupUI();
        mainLayout = createMainLayout();
    }

    private void setupUI() {
        typeComboBox.setPromptText("Выберите тип ЛКМ");
        productComboBox.setPromptText("Выберите продукт");
        detailsBox.setPadding(new Insets(10));
        searchField.setPromptText("Поиск по названию продукта");
    }

    private VBox createMainLayout() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.setPrefSize(434, 470);

        HBox searchBox = new HBox(10, searchField, searchButton);
        searchBox.setAlignment(Pos.CENTER_LEFT);

        layout.getChildren().addAll(
                searchBox,
                typeComboBox,
                productComboBox,
                detailsBox
        );

        return layout;
    }

    // Геттеры
    public ComboBox<String> getTypeComboBox() { return typeComboBox; }
    public ComboBox<String> getProductComboBox() { return productComboBox; }
    public VBox getDetailsBox() { return detailsBox; }
    public TextField getSearchField() { return searchField; }
    public Button getSearchButton() { return searchButton; }
    public VBox getLayout() { return mainLayout; }

    // Методы для работы с деталями
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

        Label volumeLabel = new Label(volume);

        Button copyButton = new Button("Копировать");
        copyButton.setOnAction(e -> {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(String.valueOf(id));
            clipboard.setContent(content);
        });

        HBox row = new HBox(10, copyButton, idField, volumeLabel);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }
}
