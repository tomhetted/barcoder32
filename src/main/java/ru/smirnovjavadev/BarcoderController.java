package ru.smirnovjavadev;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
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
        List<String> categoryNames = categories.stream()
                .map(Category::getName)
                .collect(Collectors.toList());
        view.getTypeComboBox().setItems(FXCollections.observableArrayList(categoryNames));
    }

    private void setupEventHandlers() {
        // Обработчики комбобоксов
        view.getTypeComboBox().setOnAction(e -> handleCategorySelection());
        view.getProductComboBox().setOnAction(e -> handleProductSelection());

        // Обработчики поиска
        view.getSearchButton().setOnAction(e -> handleSearch());
        view.getSearchField().setOnAction(e -> handleSearch());

        // Сброс поиска при изменении текста
        view.getSearchField().textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.isEmpty()) {
                resetToNormalMode();
            }
        });
    }

    private void handleCategorySelection() {
        String selectedCategory = view.getTypeComboBox().getValue();
        if (selectedCategory != null) {
            resetToNormalMode();
            updateProductList(selectedCategory);
        }
    }

    private void updateProductList(String categoryName) {
        Category category = findCategoryByName(categoryName);
        if (category != null) {
            ObservableList<String> products = FXCollections.observableArrayList(
                    category.getProducts().stream()
                            .map(Product::getName)
                            .collect(Collectors.toList())
            );
            view.getProductComboBox().setItems(products);
            view.getProductComboBox().getSelectionModel().clearSelection();
        }
    }

    private void handleProductSelection() {
        String category = view.getTypeComboBox().getValue();
        String product = view.getProductComboBox().getValue();

        if (category != null && product != null) {
            resetToNormalMode();
            showProductDetails(category, product);
        }
    }

    private void showProductDetails(String categoryName, String productName) {
        view.clearDetails();
        Product product = findProductByName(categoryName, productName);
        if (product != null) {
            view.addProductHeader(productName);
            product.getItems().forEach(item -> {
                HBox row = BarcoderView.createDetailRow(item.getId(), item.getVolume());
                view.addDetailRow(row);
            });
        }
    }

    private void handleSearch() {
        String query = view.getSearchField().getText().trim().toLowerCase();
        view.clearDetails();

        if (query.isEmpty()) {
            resetToNormalMode();
            return;
        }

        List<Product> foundProducts = categories.stream()
                .flatMap(c -> c.getProducts().stream())
                .filter(p -> p.getName().toLowerCase().contains(query))
                .collect(Collectors.toList());

        if (foundProducts.isEmpty()) {
            Label noResults = new Label("Ничего не найдено");
            view.getDetailsBox().getChildren().add(noResults);
        } else {
            foundProducts.forEach(product -> {
                view.addProductHeader(product.getName());
                product.getItems().forEach(item -> {
                    HBox row = BarcoderView.createDetailRow(item.getId(), item.getVolume());
                    view.addDetailRow(row);
                });
            });
        }
    }

    private void resetToNormalMode() {
        view.clearDetails();
        view.getSearchField().clear();
    }

    private Category findCategoryByName(String name) {
        return categories.stream()
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    private Product findProductByName(String categoryName, String productName) {
        Category category = findCategoryByName(categoryName);
        if (category != null) {
            return category.getProducts().stream()
                    .filter(p -> p.getName().equals(productName))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }
}