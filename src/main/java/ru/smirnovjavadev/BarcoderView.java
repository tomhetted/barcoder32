package ru.smirnovjavadev;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Map;

public class BarcoderView {
    private final ComboBox<String> typeComboBox = new ComboBox<>();
    private final ComboBox<String> productComboBox = new ComboBox<>();
    private final VBox detailsBox = new VBox(5);

    public BarcoderView() {
        setupUI();
    }

    private void setupUI() {
        typeComboBox.setPromptText("Выберите тип ЛКМ");
        productComboBox.setPromptText("Выберите продукт");
        detailsBox.setPadding(new Insets(10));
    }

    public ComboBox<String> getTypeComboBox() {
        return typeComboBox;
    }

    public ComboBox<String> getProductComboBox() {
        return productComboBox;
    }

    public VBox getDetailsBox() {
        return detailsBox;
    }

    public VBox getLayout() {
        VBox layout = new VBox(10, typeComboBox, productComboBox, detailsBox);
        layout.setPadding(new Insets(10));
        layout.setPrefSize(434, 370);
        return layout;
    }

    public void updateProductList(Map<String, Product> products) {
        productComboBox.setItems(FXCollections.observableArrayList(products.keySet()));
    }

    public void clearDetails() {
        detailsBox.getChildren().clear();
    }

    public void addDetailRow(HBox row) {
        detailsBox.getChildren().add(row);
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
