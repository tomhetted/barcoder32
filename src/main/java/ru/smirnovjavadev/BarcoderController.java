package ru.smirnovjavadev;

import javafx.collections.FXCollections;
import javafx.scene.layout.HBox;


import java.util.List;
import java.util.stream.Collectors;

public class BarcoderController {
    private final BarcoderView view;
    private final List<Category> categories;

    public BarcoderController(BarcoderView view, List<Category> categories) {
        this.view = view;
        this.categories = categories;
        setupEventHandlers();
        initTypeComboBox();
    }

    private void initTypeComboBox() {
        // Заполняем комбобокс названиями категорий
        List<String> categoryNames = categories.stream()
                .map(Category::getName)
                .collect(Collectors.toList());

        view.getTypeComboBox().setItems(FXCollections.observableArrayList(categoryNames));
    }

    private void setupEventHandlers() {
        // Обработчик выбора категории
        view.getTypeComboBox().setOnAction(e -> {
            String selectedCategoryName = view.getTypeComboBox().getValue();
            if (selectedCategoryName != null) {
                updateProductList(selectedCategoryName);
            }
        });

        // Обработчик выбора продукта
        view.getProductComboBox().setOnAction(e -> {
            String selectedCategoryName = view.getTypeComboBox().getValue();
            String selectedProductName = view.getProductComboBox().getValue();

            view.clearDetails();

            if (selectedCategoryName != null && selectedProductName != null) {
                showProductDetails(selectedCategoryName, selectedProductName);
            }
        });
    }

    private void updateProductList(String categoryName) {
        // Находим выбранную категорию
        Category selectedCategory = categories.stream()
                .filter(c -> c.getName().equals(categoryName))
                .findFirst()
                .orElse(null);

        if (selectedCategory != null) {
            // Получаем названия продуктов для этой категории
            List<String> productNames = selectedCategory.getProducts().stream()
                    .map(Product::getName)
                    .collect(Collectors.toList());

            view.updateProductList(FXCollections.observableArrayList(productNames));
        }
    }

    private void showProductDetails(String categoryName, String productName) {
        // Находим выбранный продукт
        categories.stream()
                .filter(c -> c.getName().equals(categoryName))
                .flatMap(c -> c.getProducts().stream())
                .filter(p -> p.getName().equals(productName))
                .findFirst()
                .ifPresent(product -> {
                    // Добавляем все варианты фасовок
                    product.getItems().forEach(item -> {
                        HBox row = BarcoderView.createDetailRow(item.getId(), item.getVolume());
                        view.addDetailRow(row);
                    });
                });
    }
}