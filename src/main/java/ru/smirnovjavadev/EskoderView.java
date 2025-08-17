package ru.smirnovjavadev;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.List;

public class EskoderView {
    // Элементы интерфейса
    private final ComboBox<String> producerComboBox = new ComboBox<>(); // Выбор поставщика
    private final ComboBox<String> categoryComboBox = new ComboBox<>(); // Выбор типа ЛКМ
    private final ComboBox<String> productComboBox = new ComboBox<>(); // Выбор продукта
    private final VBox detailsBox = new VBox(5); // Контейнер для отображения деталей
    private final TextField searchField = new TextField(); // Поле поиска
    private final Button searchButton = new Button("Найти"); // Кнопка поиска
    private final ScrollPane scrollPane = new ScrollPane(); // Прокрутка для detailsBox
    private final VBox mainLayout; // Основной контейнер компоновки

    // Константы размеров окна
    private final double BASE_WINDOW_HEIGHT = 400; // Базовая высота окна
    private final double BASE_WINDOW_WIDTH = 434; // Базовая ширина окна
    private final double ITEM_HEIGHT = 30; // Высота одной строки с деталями
    private final double MAX_WINDOW_HEIGHT = 700; // Максимальная высота окна

    public EskoderView() {
        setupUI(); // Инициализация UI компонентов
        mainLayout = createMainLayout(); // Создание основной компоновки
        detailsBox.setId("detailsBox"); // Установка ID для CSS стилизации
    }

    /**
     * Настройка базовых параметров UI компонентов
     */
    private void setupUI() {
        // Установка текста-подсказки
        producerComboBox.setPromptText("Выберите поставщика");
        categoryComboBox.setPromptText("Выберите тип ЛКМ");
        productComboBox.setPromptText("Выберите продукт");
        searchField.setPromptText("Поиск по названию продукта");

        // Настройка ScrollPane
        scrollPane.setContent(detailsBox); // Помещаем VBox в ScrollPane
        scrollPane.setFitToWidth(true); // Автоподгон по ширине
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Отключаем горизонтальную прокрутку
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Вертикальная прокрутка при необходимости
        scrollPane.setMinHeight(150); // Минимальная высота области

        // Настройка detailsBox
        detailsBox.setPadding(new Insets(10)); // Отступы внутри контейнера
        VBox.setVgrow(scrollPane, Priority.ALWAYS); // Растягиваем ScrollPane по вертикали
    }

    /**
     * Создание основной компоновки интерфейса
     */
    private VBox createMainLayout() {
        VBox layout = new VBox(10); // Основной контейнер с отступами 10px
        layout.setPadding(new Insets(10)); // Внутренние отступы
        layout.setPrefSize(BASE_WINDOW_WIDTH, BASE_WINDOW_HEIGHT); // Установка базовых размеров

        // Панель поиска
        HBox searchBox = new HBox(10, searchField, searchButton); // Горизонтальное расположение
        searchBox.setAlignment(Pos.CENTER_LEFT); // Выравнивание по левому краю
        HBox.setHgrow(searchField, Priority.ALWAYS); // Поле поиска растягивается по ширине

        // Добавление компонентов в основной контейнер
        layout.getChildren().addAll(
                searchBox, // Панель поиска
                producerComboBox, // Выбор поставщика
                categoryComboBox, // Выбор типа
                productComboBox, // Выбор продукта
                scrollPane // Прокручиваемая область с деталями
        );

        return layout;
    }

    /**
     * Подгон высоты окна под количество элементов
     * @param itemCount - количество отображаемых элементов
     */
    public void adjustWindowHeight(int itemCount) {
        // Расчет новой высоты
        double calculatedHeight = BASE_WINDOW_HEIGHT + (itemCount * ITEM_HEIGHT);
        // Ограничение минимальной и максимальной высоты
        double newHeight = Math.min(Math.max(calculatedHeight, BASE_WINDOW_HEIGHT), MAX_WINDOW_HEIGHT);

        // Применение новой высоты
        Scene scene = mainLayout.getScene();
        if (scene != null && scene.getWindow() != null) {
            scene.getWindow().setHeight(newHeight);
            scrollPane.setVvalue(0); // Прокрутка в начало
        }
    }

    /**
     * Сброс высоты окна к базовому значению
     */
    public void resetWindowHeight() {
        adjustWindowHeight(0);
    }

    /**
     * Очистка области с деталями
     */
    public void clearDetails() {
        detailsBox.getChildren().clear();
    }

    /**
     * Добавление строки с деталями
     * @param row - готовая строка (HBox)
     */
    public void addDetailRow(HBox row) {
        detailsBox.getChildren().add(row);
    }

    /**
     * Добавление полной информации о продукте
     * @param product - объект продукта
     */
    public void addProductDetails(Product product) {
        addProductHeader(product.getName()); // Заголовок с именем
        for (Item item : product.getItems()) {
            HBox row = createDetailRow(item.getId(), item.getVolume()); // Строка для каждого варианта
            addDetailRow(row);
        }
    }

    /**
     * Добавление заголовка продукта
     * @param productName - название продукта
     */
    public void addProductHeader(String productName) {
        Label header = new Label(productName);
        detailsBox.getChildren().add(header);
    }

    /**
     * Создание строки с деталями фасовки
     * @param id - идентификатор
     * @param volume - объем фасовки
     * @return готовая строка (HBox)
     */
    public static HBox createDetailRow(int id, String volume) {
        // Поле с ID (нередактируемое)
        TextField idField = new TextField(String.valueOf(id));
        idField.setEditable(false);
        idField.setPrefWidth(80);

        // Метка с объемом
        Label volumeLabel = new Label(volume);
        volumeLabel.setPrefWidth(100);

        // Кнопка копирования
        Button copyButton = new Button("Копировать");
        copyButton.setOnAction(event -> {
            String idToCopy = idField.getText();
            if (idToCopy != null && !idToCopy.isEmpty()) {
                Clipboard clipboard = Clipboard.getSystemClipboard();
                ClipboardContent content = new ClipboardContent();
                content.putString(idToCopy);
                clipboard.setContent(content);

                // выделим текст в поле
                idField.requestFocus();
                idField.selectAll();

                // создаём всплывающий тултип
                Tooltip tooltip = new Tooltip("Скопировано!");
                tooltip.setAutoHide(true);
                tooltip.show(copyButton,
                        copyButton.localToScreen(copyButton.getBoundsInLocal()).getMinX(),
                        copyButton.localToScreen(copyButton.getBoundsInLocal()).getMinY() - 30
                );

                // уберём через 1 секунду
                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(e -> tooltip.hide());
                pause.play();
            }
        });

        // Сборка строки
        HBox row = new HBox(10, copyButton, idField, volumeLabel);
        row.setAlignment(Pos.CENTER_LEFT); // Выравнивание по центру-слева
        return row;
    }

    /** Устанавливает список производителей (перезаписывает items, сбрасывает selection) */
    public void setProducers(List<String> names) {
        producerComboBox.setItems(FXCollections.observableArrayList(names));
        producerComboBox.getSelectionModel().clearSelection();
        producerComboBox.setDisable(names == null || names.isEmpty());
    }

    /** Устанавливает список категорий для выбранного производителя */
    public void setCategories(List<String> names) {
        categoryComboBox.setItems(FXCollections.observableArrayList(names));
        categoryComboBox.getSelectionModel().clearSelection();
        categoryComboBox.setDisable(names == null || names.isEmpty());
    }

    /** Устанавливает список продуктов для выбранной категории */
    public void setProducts(List<String> names) {
        productComboBox.setItems(FXCollections.observableArrayList(names));
        productComboBox.getSelectionModel().clearSelection();
        productComboBox.setDisable(names == null || names.isEmpty());
    }

    /** Очистить содержимое и выбор для category и product (применимо при поиске) */
    public void clearCategoryAndProductBoxes() {
        categoryComboBox.getSelectionModel().clearSelection();
        categoryComboBox.getItems().clear();
        categoryComboBox.setDisable(true);

        productComboBox.getSelectionModel().clearSelection();
        productComboBox.getItems().clear();
        productComboBox.setDisable(true);
    }

    /** Очистить только выбор (не стирая сам список) — если нужно */
    public void clearComboSelections() {
        producerComboBox.getSelectionModel().clearSelection();
        categoryComboBox.getSelectionModel().clearSelection();
        productComboBox.getSelectionModel().clearSelection();
    }

    // Геттеры для доступа к компонентам
    public ComboBox<String> getProducerComboBox() { return producerComboBox; }
    public ComboBox<String> getCategoryComboBox() { return categoryComboBox; }
    public ComboBox<String> getProductComboBox() { return productComboBox; }
    public VBox getDetailsBox()                 { return detailsBox; }
    public TextField getSearchField()           { return searchField; }
    public Button getSearchButton()             { return searchButton; }
    public VBox getLayout()                     { return mainLayout; }
}