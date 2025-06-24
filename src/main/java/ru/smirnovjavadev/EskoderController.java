package ru.smirnovjavadev;

import javafx.collections.FXCollections;
import javafx.scene.control.Label;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер, управляющий логикой приложения:
 * - Обрабатывает действия пользователя (выбор, поиск)
 * - Взаимодействует с View (EskoderView)
 * - Получает данные из модели (список Producer, Category и Product)
 */
public class EskoderController {

    private final EskoderView view;               // UI-компоненты
    private final Catalog catalog;      // // Модель данных: список производителей с категориями и продуктами

    /**
     * Конструктор инициализирует контроллер:
     * - Запускает установку обработчиков
     * - Заполняет ComboBox типов
     */
    public EskoderController(EskoderView view, Catalog catalog) {
        this.view = view;
        this.catalog = catalog;
        setupEventHandlers();
        initProducerComboBox();
    }

    /**
     * Инициализирует ComboBox с производителями (поставщиками)
     */
    private void initProducerComboBox() {
        List<String> producerNames = catalog.getProducers().stream()
                .map(Producer::getName)
                .collect(Collectors.toList());

        view.getProducerComboBox().setItems(FXCollections.observableArrayList(producerNames));
    }

    /**
     * Назначает обработчики событий для всех интерактивных элементов UI
     */
    private void setupEventHandlers() {
        view.getProducerComboBox().setOnAction(e -> handleProducerSelection());
        view.getCategoryComboBox().setOnAction(e -> handleCategorySelection());
        view.getProductComboBox().setOnAction(e -> handleProductSelection());

        view.getSearchButton().setOnAction(e -> handleSearch());
        view.getSearchField().setOnAction(e -> handleSearch());

        view.getSearchField().textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.isEmpty()) {
                resetToNormalMode();
            }
        });
    }

    private void handleProducerSelection() {
        String selectedProducer = view.getProducerComboBox().getValue();
        if (selectedProducer != null) {
            resetToNormalMode();
            updateCategoryList(selectedProducer);
        }
    }

    /**
     * Обрабатывает выбор категории:
     * - сбрасывает состояние
     * - загружает список продуктов для выбранной категории
     */
    private void handleCategorySelection() {
        String selectedProducer = view.getProducerComboBox().getValue();
        String selectedCategory = view.getCategoryComboBox().getValue();
        if (selectedProducer != null && selectedCategory != null) {
            resetToNormalMode();
            updateProductList(selectedProducer, selectedCategory);
        }
    }

    /**
     * Заполняет ComboBox продуктов для указанной категории
     */
    private void updateProductList(String producerName, String categoryName) {
        Category category = findCategoryByName(producerName, categoryName);
        if (category != null) {
            List<String> productNames = category.getProducts().stream()
                    .map(Product::getName)
                    .collect(Collectors.toList());

            view.getProductComboBox().setItems(FXCollections.observableArrayList(productNames));
            view.getProductComboBox().getSelectionModel().clearSelection();
        }
    }

    /**
     * Заполняет ComboBox категорий для поставщика
     */
    private void updateCategoryList(String producerName) {
        Producer producer = findProducerByName(producerName);
        if (producer != null) {
            List<String> categoryNames = producer.getCategories().stream()
                    .map(Category::getName)
                    .collect(Collectors.toList());

            view.getCategoryComboBox().setItems(FXCollections.observableArrayList(categoryNames));
            view.getCategoryComboBox().getSelectionModel().clearSelection();
            view.getProductComboBox().getItems().clear();
            view.getProductComboBox().getSelectionModel().clearSelection();
        }
    }

    /**
     * Обрабатывает выбор продукта:
     * - сбрасывает состояние
     * - отображает детали выбранного продукта
     */
    private void handleProductSelection() {
        String producer = view.getProducerComboBox().getValue();
        String category = view.getCategoryComboBox().getValue();
        String product = view.getProductComboBox().getValue();

        if (producer != null && category != null && product != null) {
            resetToNormalMode();
            showProductDetails(producer, category, product);
        }
    }

    /**
     * Показывает фасовки и детали продукта
     */
    private void showProductDetails(String producerName, String categoryName, String productName) {
        view.clearDetails();
        Product product = findProductByName(producerName, categoryName, productName);
        if (product != null) {
            view.addProductDetails(product);
            view.adjustWindowHeight(product.getItems().size() + 1);
        }
    }

    /**
     * Поиск продуктов по имени (без учёта регистра)
     */
    private void handleSearch() {
        view.getProducerComboBox().getSelectionModel().clearSelection();
        view.getCategoryComboBox().getSelectionModel().clearSelection();
        view.getCategoryComboBox().getItems().clear();
        view.getProductComboBox().getSelectionModel().clearSelection();
        view.getProductComboBox().getItems().clear();

        String query = view.getSearchField().getText().trim().toLowerCase();
        view.clearDetails();

        if (query.isEmpty()) {
            resetToNormalMode();
            return;
        }

        List<Product> foundProducts = catalog.getProducers().stream()
                .flatMap(p -> p.getCategories().stream())
                .flatMap(c -> c.getProducts().stream())
                .filter(p -> p.getName().toLowerCase().contains(query))
                .collect(Collectors.toList());

        if (foundProducts.isEmpty()) {
            view.getDetailsBox().getChildren().add(new Label("Ничего не найдено"));
            view.adjustWindowHeight(1);
        } else {
            int totalItems = 0;
            for (Product product : foundProducts) {
                view.addProductDetails(product);
                totalItems += product.getItems().size() + 1;
            }
            view.adjustWindowHeight(totalItems);
        }
    }

    /**
     * Сброс интерфейса в "чистое" состояние:
     * - очищаем детали
     * - сбрасываем поле поиска
     * - возвращаем высоту окна
     */
    private void resetToNormalMode() {
        view.clearDetails();
        view.getSearchField().clear();
        view.adjustWindowHeight(0);
    }

    private Producer findProducerByName(String name) {
        return catalog.getProducers().stream()
                .filter(p -> p.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Поиск категории по имени (точное совпадение)
     */
    private Category findCategoryByName(String producerName, String categoryName) {
        Producer producer = findProducerByName(producerName);
        if (producer != null) {
            return producer.getCategories().stream()
                    .filter(c -> c.getName().equals(categoryName))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    /**
     * Поиск продукта по имени в пределах конкретной категории
     */
    private Product findProductByName(String producerName, String categoryName, String productName) {
        Category category = findCategoryByName(producerName, categoryName);
        if (category != null) {
            return category.getProducts().stream()
                    .filter(p -> p.getName().equals(productName))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }
}
