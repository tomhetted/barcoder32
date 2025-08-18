package ru.smirnovjavadev;

import javafx.scene.control.Label;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Контроллер, управляющий логикой приложения:
 * - Обрабатывает действия пользователя (выбор, поиск)
 * - Взаимодействует с View (EskoderView)
 * - Получает данные из модели (список Category и Product)
 */
public class EskoderController {

    private final EskoderView view;               // UI-компоненты
    private final List<Category> categories;      // Модель данных: список категорий с продуктами

    /**
     * Конструктор инициализирует контроллер:
     * - Запускает установку обработчиков
     * - Заполняет ComboBox типов
     */
    public EskoderController(EskoderView view, List<Category> categories) {
        this.view = view;
        this.categories = categories;
        setupEventHandlers();
        initTypeComboBox();
    }

    /**
     * Заполняет ComboBox типов (категорий)
     */
    private void initTypeComboBox() {
        List<String> names = categories.stream()
                .map(Category::getName)
                .collect(Collectors.toList());
        view.setTypes(names);
    }

    /**
     * Назначает обработчики событий для всех интерактивных элементов UI
     */
    private void setupEventHandlers() {
        // Обработка выбора типа (категории)
        view.getTypeComboBox().setOnAction(e -> handleCategorySelection());

        // Обработка выбора продукта
        view.getProductComboBox().setOnAction(e -> handleProductSelection());

        // Обработка поиска по нажатию кнопки или Enter в поле
        view.getSearchButton().setOnAction(e -> handleSearch());
        view.getSearchField().setOnAction(e -> handleSearch());

        // Сброс в обычный режим при очистке поля поиска
        view.getSearchField().textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.isEmpty()) {
                resetToNormalMode();
            }
        });
    }

    /**
     * Обрабатывает выбор категории:
     * - сбрасывает состояние
     * - загружает список продуктов для выбранной категории
     */
    private void handleCategorySelection() {
        String selectedCategory = view.getTypeComboBox().getValue();
        if (selectedCategory != null) {
            resetToNormalMode();
            updateProductList(selectedCategory);
        }
    }

    /**
     * Заполняет ComboBox продуктов для указанной категории
     */
    private void updateProductList(String categoryName) {
        Category c = findCategoryByName(categoryName);
        if (c != null) {
            List<String> productNames = c.getProducts().stream()
                    .map(Product::getName)
                    .collect(Collectors.toList());

            view.setProducts(productNames);
        } else {
            view.clearProductBox();
        }
    }

    /**
     * Обрабатывает выбор продукта:
     * - сбрасывает состояние
     * - отображает детали выбранного продукта
     */
    private void handleProductSelection() {
        String category = view.getTypeComboBox().getValue();
        String product = view.getProductComboBox().getValue();

        if (category != null && product != null) {
            resetToNormalMode();
            showProductDetails(category, product);
        }
    }

    /**
     * Показывает фасовки и детали продукта
     */
    private void showProductDetails(String categoryName, String productName) {
        view.clearDetails();

        Product product = findProductByName(categoryName, productName);
        if (product != null) {
            view.addProductDetails(product); // один метод, добавляющий заголовок и фасовки
            view.adjustWindowHeight(product.getItems().size() + 1); // +1 — заголовок
        }
    }

    /**
     * Поиск продуктов по имени (без учёта регистра)
     */
    private void handleSearch() {
        // При поиске мы хотим очистить type/product списки (но оставить возможность выбрать поставщика в другом варианте)
        view.clearProductBox();
        // не трогаем список types (только снимаем выбор) — или используем view.clearTypeAndProductBoxes()
        view.clearSelectionsOnly();

        // Получаем строку поиска и очищаем старые детали
        String rawQuery = view.getSearchField().getText();
        final String query = (rawQuery == null ? "" : rawQuery.trim().toLowerCase(Locale.ROOT));
        view.clearDetails();

        // Если строка пуста — сбрасываем в нормальный режим
        if (query.isEmpty()) {
            resetToNormalMode();
            return;
        }

        // Фильтруем продукты по имени
        List<Product> foundProducts = categories.stream()
                .flatMap(cat -> cat.getProducts().stream())
                .filter(prod -> prod.getName().toLowerCase(Locale.ROOT).contains(query))
                .collect(Collectors.toList());

        // Если ничего не найдено — показать сообщение
        if (foundProducts.isEmpty()) {
            Label noResults = new Label("Ничего не найдено");
            view.getDetailsBox().getChildren().add(noResults);
            view.adjustWindowHeight(1);
        } else {
            // Иначе — отобразить все найденные продукты и их фасовки
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

    /**
     * Поиск категории по имени (точное совпадение)
     */
    private Category findCategoryByName(String name) {
        return categories.stream()
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Поиск продукта по имени в пределах конкретной категории
     */
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
