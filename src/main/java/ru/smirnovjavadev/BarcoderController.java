package ru.smirnovjavadev;

import javafx.collections.FXCollections;
import javafx.scene.layout.HBox;

import java.util.Map;

public class BarcoderController {
    private final BarcoderView view;
    private final Map<String, Map<String, Product>> productData;

    public BarcoderController(BarcoderView view, Map<String, Map<String, Product>> productData) {
        this.view = view;
        this.productData = productData;
        setupEventHandlers();
    }

    private void setupEventHandlers() {
        view.getTypeComboBox().setItems(FXCollections.observableArrayList(productData.keySet()));

        view.getTypeComboBox().setOnAction(e -> {
            String selectedType = view.getTypeComboBox().getValue();
            if (selectedType != null) {
                view.updateProductList(productData.get(selectedType));
            }
        });

        view.getProductComboBox().setOnAction(e -> {
            String selectedType = view.getTypeComboBox().getValue();
            String selectedProduct = view.getProductComboBox().getValue();
            view.clearDetails();

            if (selectedType != null && selectedProduct != null) {
                Product product = productData.get(selectedType).get(selectedProduct);
                if (product != null) {
                    product.getProductMap().forEach((id, volume) -> {
                        HBox row = BarcoderView.createDetailRow(id, volume);
                        view.addDetailRow(row);
                    });
                }
            }
        });
    }
}