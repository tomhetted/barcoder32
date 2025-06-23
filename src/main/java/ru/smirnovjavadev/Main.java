package ru.smirnovjavadev;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Получение данных через ProductRepository
        List<Category> productData = ProductRepository.getProducts();

        // Создание интерфейса
        EskoderView view = new EskoderView();

        // Создание контроллера
        EskoderController controller = new EskoderController(view, productData);

        // Создание сцены и отображение
        Scene scene = new Scene(view.getLayout());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/violetstyle.css")).toExternalForm());
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