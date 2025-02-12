package ru.smirnovjavadev;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Map;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Получение данных через ProductService
        Map<String, Map<String, Product>> productData = ProductService.getProducts();

        // Создание интерфейса
        BarcoderView view = new BarcoderView();

        // Создание контроллера
        BarcoderController controller = new BarcoderController(view, productData);

        // Создание сцены и отображение
        Scene scene = new Scene(view.getLayout());
        scene.getStylesheets().add(getClass().getResource("/greystyle.css").toExternalForm());
        primaryStage.setTitle("Колеровочные баркоды Eskaro");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}