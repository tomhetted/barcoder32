package ru.smirnovjavadev;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Получение данных в формате Catalog (включает производителей, категории, продукты и фасовки)
        Catalog catalog = ProductRepository.getCatalog();

        // Создание интерфейса
        EskoderView view = new EskoderView();

        // Создание контроллера
        EskoderController controller = new EskoderController(view, catalog);

        // Настройка сцены
        Scene scene = new Scene(view.getLayout());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/bluestyle.css")).toExternalForm());

        // Настройка окна
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
        primaryStage.setMinWidth(450);
        primaryStage.setMinHeight(400);
        primaryStage.setMaxHeight(700);
        primaryStage.setTitle("Колеровочные баркоды Eskaro");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
