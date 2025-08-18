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
    public static synchronized List<Category> getProducts() {
        File xmlFile = getXmlFile();
        checkFileExists(xmlFile);

        long currentLastModified = xmlFile.lastModified();
        if (dataCache == null || currentLastModified > lastModifiedTime) {
            List<Category> loaded = loadDataFromXml(xmlFile);

            dataCache = Collections.unmodifiableList(loaded);
            lastModifiedTime = currentLastModified;
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
        String categoryName = categoryElement.getAttribute("name").trim();
        List<Product> products = new ArrayList<>();
        NodeList children = categoryElement.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE && "product".equals(node.getNodeName())) {
                products.add(processProduct((Element) node));
            }
        }
        return new Category(categoryName, Collections.unmodifiableList(new ArrayList<>(products)));
    }

    /**
     * Обрабатывает конкретный продукт
     */
    private static Product processProduct(Element productElement) {
        String productName = productElement.getAttribute("name");
        List<Item> items = new ArrayList<>();
        NodeList children = productElement.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE && "item".equals(node.getNodeName())) {
                items.add(processItem((Element) node));
            }
        }
        return new Product(productName, Collections.unmodifiableList(items));
    }

    /**
     * Обрабатывает отдельную фасовку продукта
     */
    private static Item processItem(Element itemElement) {
        String idAttr = itemElement.getAttribute("id");
        try {
            int id = Integer.parseInt(idAttr);
            String volume = itemElement.getTextContent().trim();
            return new Item(id, volume);
        } catch (NumberFormatException ex) {
            String ctx = itemElement.getTextContent();
            throw new RuntimeException("Неверный id у <item>: '" + idAttr + "'. Текст элемента: '" + ctx + "'.", ex);
        }
    }

    /**
     * Вспомогательный метод для получения абсолютного пути к файлу
     * (полезно для логов и сообщений об ошибках)
     */
    public static String getXmlFilePath() {
        return getXmlFile().getAbsolutePath();
    }
}