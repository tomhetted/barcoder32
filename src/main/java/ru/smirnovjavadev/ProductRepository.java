package ru.smirnovjavadev;

import org.w3c.dom.*;  // Импорт классов для работы с XML DOM
import javax.xml.parsers.*;  // Импорт XML парсеров
import java.io.*;  // Импорт для работы с файлами
import java.util.*;  // Импорт коллекций

public class ProductRepository {
    // Константа с именем XML файла
    private static final String XML_FILE = "src/main/resources/products.xml";

    // Основной метод для получения данных о продуктах
    public static Map<String, Map<String, Product>> getProducts() {
        // Создаем основную структуру данных: Категория -> (Продукт -> Объект Product)
        Map<String, Map<String, Product>> productData = new LinkedHashMap<>();

        try {
            // 1. Настройка XML парсера
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // 2. Парсинг XML файла
            Document doc = builder.parse(new File(XML_FILE));

            // 3. Получаем все элементы <category> из XML
            NodeList types = doc.getElementsByTagName("category");

            // 4. Обработка каждой категории
            for (int i = 0; i < types.getLength(); i++) {
                Node typeNode = types.item(i);

                // Проверяем, что это именно элемент (а не текст или комментарий)
                if (typeNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element typeElement = (Element) typeNode;

                    // 5. Получаем название категории
                    String typeName = typeElement.getAttribute("name");

                    // 6. Создаем карту для продуктов этой категории
                    Map<String, Product> products = new LinkedHashMap<>();

                    // 7. Получаем все продукты в категории
                    NodeList productNodes = typeElement.getElementsByTagName("product");

                    // 8. Обработка каждого продукта
                    for (int j = 0; j < productNodes.getLength(); j++) {
                        Node productNode = productNodes.item(j);

                        if (productNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element productElement = (Element) productNode;

                            // 9. Получаем название продукта
                            String productName = productElement.getAttribute("name");

                            // 10. Создаем карту для фасовок продукта (ID -> Объем)
                            Map<Integer, String> cans = new LinkedHashMap<>();

                            // 11. Получаем все фасовки продукта
                            NodeList canNodes = productElement.getElementsByTagName("item");

                            // 12. Обработка каждой фасовки
                            for (int k = 0; k < canNodes.getLength(); k++) {
                                Node itemNode = canNodes.item(k);

                                if (itemNode.getNodeType() == Node.ELEMENT_NODE) {
                                    Element itemElement = (Element) itemNode;

                                    // 13. Получаем ID и объем фасовки
                                    int id = Integer.parseInt(itemElement.getAttribute("id"));
                                    String volume = itemElement.getTextContent();

                                    // 14. Добавляем в карту фасовок
                                    cans.put(id, volume);
                                }
                            }

                            // 15. Создаем объект Product и добавляем в карту продуктов
                            products.put(productName, new Product(cans));
                        }
                    }

                    // 16. Добавляем категорию с продуктами в основную карту
                    productData.put(typeName, products);
                }
            }
        } catch (Exception e) {
            // 17. Обработка ошибок парсинга
            throw new RuntimeException("Failed to parse XML file", e);
        }

        // 18. Возвращаем заполненную структуру данных
        return productData;
    }
}