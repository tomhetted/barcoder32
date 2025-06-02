package ru.smirnovjavadev;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Получение данных через ProductService
        List<Category> productData = ProductRepository.getProducts();

        // Создание интерфейса
        BarcoderView view = new BarcoderView();

        // Создание контроллера
        BarcoderController controller = new BarcoderController(view, productData);

        // Создание сцены и отображение
        Scene scene = new Scene(view.getLayout());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
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