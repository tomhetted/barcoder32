package ru.smirnovjavadev;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Класс для работы с данными о продуктах из внешнего XML-файла
 * (файл должен находиться в той же папке, что и исполняемый JAR)
 */
public class ProductRepository {
    // Имя XML-файла с данными (располагается рядом с программой)
    private static final String XML_FILE_NAME = "products.xml";
    // Кэш для хранения данных после первой загрузки
    private static List<Category> dataCache = null;
    // Время последнего изменения файла
    private static long lastModifiedTime = 0;

    /**
     * Возвращает данные о продуктах, загружая их из XML при первом вызове
     * или при изменении файла
     */
    public static List<Category> getProducts() {
        File xmlFile = getXmlFile();
        checkFileExists(xmlFile);

        if (dataCache == null || xmlFile.lastModified() > lastModifiedTime) {
            dataCache = loadDataFromXml(xmlFile);
            lastModifiedTime = xmlFile.lastModified();
        }
        return dataCache;
    }

    /**
     * Получает файл XML из рабочей директории программы
     */
    private static File getXmlFile() {
        try {
            String jarPath = ProductRepository.class.getProtectionDomain()
                    .getCodeSource().getLocation().getPath();
            String decodedPath = URLDecoder.decode(jarPath, StandardCharsets.UTF_8.name());
            File jarFile = new File(decodedPath);
            return new File(jarFile.getParent(), XML_FILE_NAME);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Ошибка декодирования пути к JAR-файлу", e);
        }
    }

    /**
     * Проверяет существование файла, создает шаблон при необходимости
     */
    private static void checkFileExists(File xmlFile) throws RuntimeException {
        if (!xmlFile.exists()) {
            try {
                createDefaultXmlFile(xmlFile);
                System.out.println("Создан новый файл конфигурации: "
                        + xmlFile.getAbsolutePath());
            } catch (IOException e) {
                throw new RuntimeException("Не удалось создать файл конфигурации", e);
            }
        }
    }

    /**
     * Создает файл с шаблоном данных при первом запуске
     */
    private static void createDefaultXmlFile(File targetFile) throws IOException {
        try (InputStream is = ProductRepository.class.getResourceAsStream("/default_products.xml");
             OutputStream os = new FileOutputStream(targetFile)) {
            if (is == null) {
                throw new IOException("Шаблон default_products.xml не найден в ресурсах");
            }
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        }
    }

    /**
     * Основной метод загрузки данных из XML
     */
    private static List<Category> loadDataFromXml(File xmlFile) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            List<Category> categories = new ArrayList<>();
            NodeList categoryNodes = doc.getElementsByTagName("category");

            for (int i = 0; i < categoryNodes.getLength(); i++) {
                Node categoryNode = categoryNodes.item(i);
                if (categoryNode.getNodeType() == Node.ELEMENT_NODE) {
                    categories.add(processCategory((Element) categoryNode));
                }
            }
            return categories;

        } catch (Exception e) {
            throw new RuntimeException("Ошибка чтения XML", e);
        }
    }

    /**
     * Обрабатывает категорию продуктов
     */
    private static Category processCategory(Element categoryElement) {
        String categoryName = categoryElement.getAttribute("name");
        List<Product> products = new ArrayList<>();
        NodeList productNodes = categoryElement.getElementsByTagName("product");

        for (int j = 0; j < productNodes.getLength(); j++) {
            Node productNode = productNodes.item(j);
            if (productNode.getNodeType() == Node.ELEMENT_NODE) {
                products.add(processProduct((Element) productNode));
            }
        }
        return new Category(categoryName, products);
    }

    /**
     * Обрабатывает конкретный продукт
     */
    private static Product processProduct(Element productElement) {
        String productName = productElement.getAttribute("name");
        List<Item> items = new ArrayList<>();
        NodeList itemNodes = productElement.getElementsByTagName("item");

        for (int k = 0; k < itemNodes.getLength(); k++) {
            Node itemNode = itemNodes.item(k);
            if (itemNode.getNodeType() == Node.ELEMENT_NODE) {
                items.add(processItem((Element) itemNode));
            }
        }
        return new Product(productName, items);
    }

    /**
     * Обрабатывает отдельную фасовку продукта
     */
    private static Item processItem(Element itemElement) {
        int id = Integer.parseInt(itemElement.getAttribute("id"));
        String volume = itemElement.getTextContent().trim();
        return new Item(id, volume);
    }

    /**
     * Вспомогательный метод для получения абсолютного пути к файлу
     * (полезно для логов и сообщений об ошибках)
     */
    public static String getXmlFilePath() {
        return getXmlFile().getAbsolutePath();
    }
}