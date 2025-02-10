package ru.smirnovjavadev;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Map;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Получение данных через ProductService
        Map<String, Map<String, Product>> productData = ProductService.getProducts();

        // Создание компонентов интерфейса
        ComboBox<String> typeComboBox = new ComboBox<>();
        ComboBox<String> productComboBox = new ComboBox<>();
        VBox detailsBox = new VBox(5); // Отображение информации о фасовках

        // Заполнение первого ComboBox (типы ЛКМ)
        typeComboBox.setItems(FXCollections.observableArrayList(productData.keySet()));

        // Обработка выбора типа ЛКМ
        typeComboBox.setOnAction(e -> {
            String selectedType = typeComboBox.getValue();
            if (selectedType != null) {
                productComboBox.setItems(FXCollections.observableArrayList(productData.get(selectedType).keySet()));
            }
        });

        // Обработка выбора конкретного продукта
        productComboBox.setOnAction(e -> {
            String selectedType = typeComboBox.getValue();
            String selectedProduct = productComboBox.getValue();
            detailsBox.getChildren().clear(); // Очистка предыдущих данных

            if (selectedType != null && selectedProduct != null) {
                Product product = productData.get(selectedType).get(selectedProduct);
                if (product != null) {
                    // Заполнение detailsBox: строки из ID, фасовки и кнопки "Копировать"
                    for (Map.Entry<Integer, String> entry : product.getProductMap().entrySet()) {
                        HBox row = getRow(entry);
                        row.setAlignment(Pos.CENTER_LEFT); // Выравниваем элементы по вертикали
                        detailsBox.getChildren().add(row);
                    }
                }
            }
        });

        // Компоновка интерфейса
        VBox layout = new VBox(10, typeComboBox, productComboBox, detailsBox);
        layout.setPadding(new Insets(10));
        layout.setPrefSize(434, 352);

        // Создание сцены и отображение
        Scene scene = new Scene(layout);
        scene.getStylesheets().add(getClass().getResource("/greystyle.css").toExternalForm());
        primaryStage.setTitle("Колеровочные баркоды Eskaro");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private static HBox getRow(Map.Entry<Integer, String> entry) {
        Integer id = entry.getKey();
        String volume = entry.getValue();

        // TextField для ID
        TextField idField = new TextField(id.toString());
        idField.setEditable(false); // Запрещаем редактирование
        idField.setPrefWidth(80);

        // Label для фасовки
        Label volumeLabel = new Label(volume);

        // Кнопка "Копировать"
        Button copyButton = new Button("Копировать");
        copyButton.setOnAction(copyEvent -> {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(id.toString()); // Копируем ID в буфер обмена
            clipboard.setContent(content);
        });

        // Объединяем элементы в строку
        HBox row = new HBox(10, copyButton, idField, volumeLabel);
        return row;
    }

    public static void main(String[] args) {
        launch(args);
    }
}