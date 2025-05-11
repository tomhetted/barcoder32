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
    private static Map<String, Map<String, Product>> dataCache = null;
    // Время последнего изменения файла
    private static long lastModifiedTime = 0;

    /**
     * Возвращает данные о продуктах, загружая их из XML при первом вызове
     * или при изменении файла
     */
    public static Map<String, Map<String, Product>> getProducts() {
        File xmlFile = getXmlFile();
        checkFileExists(xmlFile);

        // Если файл изменился или данные еще не загружены
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
    private static Map<String, Map<String, Product>> loadDataFromXml(File xmlFile) throws RuntimeException {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            Map<String, Map<String, Product>> result = new LinkedHashMap<>();
            NodeList categories = doc.getElementsByTagName("category");

            for (int i = 0; i < categories.getLength(); i++) {
                Node categoryNode = categories.item(i);
                if (categoryNode.getNodeType() == Node.ELEMENT_NODE) {
                    processCategory((Element) categoryNode, result);
                }
            }
            return result;

        } catch (Exception e) {
            throw new RuntimeException("Ошибка чтения XML-файла: " + xmlFile.getAbsolutePath(), e);
        }
    }

    /**
     * Обрабатывает категорию продуктов
     */
    private static void processCategory(Element categoryElement,
                                        Map<String, Map<String, Product>> result) {

        String categoryName = categoryElement.getAttribute("name");
        Map<String, Product> products = new LinkedHashMap<>();
        NodeList productNodes = categoryElement.getElementsByTagName("product");

        for (int j = 0; j < productNodes.getLength(); j++) {
            Node productNode = productNodes.item(j);
            if (productNode.getNodeType() == Node.ELEMENT_NODE) {
                processProduct((Element) productNode, products);
            }
        }
        result.put(categoryName, products);
    }

    /**
     * Обрабатывает конкретный продукт
     */
    private static void processProduct(Element productElement,
                                       Map<String, Product> products) {

        String productName = productElement.getAttribute("name");
        Map<Integer, String> items = new LinkedHashMap<>();
        NodeList itemNodes = productElement.getElementsByTagName("item");

        for (int k = 0; k < itemNodes.getLength(); k++) {
            Node itemNode = itemNodes.item(k);
            if (itemNode.getNodeType() == Node.ELEMENT_NODE) {
                processItem((Element) itemNode, items);
            }
        }
        products.put(productName, new Product(items));
    }

    /**
     * Обрабатывает отдельную фасовку продукта
     */
    private static void processItem(Element itemElement,
                                    Map<Integer, String> items) {

        int id = Integer.parseInt(itemElement.getAttribute("id"));
        String volume = itemElement.getTextContent().trim();
        items.put(id, volume);
    }

    /**
     * Вспомогательный метод для получения абсолютного пути к файлу
     * (полезно для логов и сообщений об ошибках)
     */
    public static String getXmlFilePath() {
        return getXmlFile().getAbsolutePath();
    }
}