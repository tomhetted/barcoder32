package ru.smirnovjavadev;

import java.util.LinkedHashMap;
import java.util.Map;

public class ProductService {

    public static Map<String, Map<String, Product>> getProducts() {
        // Основная структура: тип -> продукт -> фасовки
        Map<String, Map<String, Product>> productData = new LinkedHashMap<>();

        // Данные для "Интерьерные краски Aura"
        Map<String, Product> auraInteriorPaints = new LinkedHashMap<>();

        Map<Integer, String> nordMap = new LinkedHashMap<>(); // Nord
        nordMap.put(17113, "0,9л");
        nordMap.put(17114, "2,7л");
        nordMap.put(17115, "9л");
        auraInteriorPaints.put("Nord", new Product(nordMap));

        Map<Integer, String> fjordMap = new LinkedHashMap<>(); // Fjord
        fjordMap.put(17116, "0,9л");
        fjordMap.put(17117, "2,7л");
        fjordMap.put(17118, "9л");
        auraInteriorPaints.put("Fjord", new Product(fjordMap));

        Map<Integer, String> mattlatexMap = new LinkedHashMap<>(); // Mattlatex
        mattlatexMap.put(22885, "0,9л А");
        mattlatexMap.put(22886, "2,7л А");
        mattlatexMap.put(22887, "9л А");
        mattlatexMap.put(22888, "0,9 TR");
        mattlatexMap.put(22889, "2,7л TR");
        mattlatexMap.put(22890, "9л TR");
        auraInteriorPaints.put("Mattlatex", new Product(mattlatexMap));

        Map<Integer, String> golfMap = new LinkedHashMap<>(); // Golfstrom
        golfMap.put(17119, "0,9л");
        golfMap.put(17121, "2,7л");
        golfMap.put(17122, "9л");
        auraInteriorPaints.put("Golfström", new Product(golfMap));

        Map<Integer, String> satinMap = new LinkedHashMap<>(); // Satin
        satinMap.put(17124, "0,9л");
        satinMap.put(15968, "2,7л");
        satinMap.put(15969, "9л");
        auraInteriorPaints.put("Satin", new Product(satinMap));

        productData.put("Интерьерные краски Aura", auraInteriorPaints);

        // Данные для "Интерьерные краски Eskaro"
        Map<String, Product> eskaroInteriorPaints = new LinkedHashMap<>();

        Map<Integer, String> moda3Map = new LinkedHashMap<>();
        moda3Map.put(27022, "0,9л A");
        moda3Map.put(27023, "2,7л A");
        moda3Map.put(27077, "9л A");
        moda3Map.put(27206, "0,9л TR");
        moda3Map.put(27207, "2,7л TR");
        moda3Map.put(27208, "9л TR");
        eskaroInteriorPaints.put("Moda 3", new Product(moda3Map));

        Map<Integer, String> moda7Map = new LinkedHashMap<>();
        moda7Map.put(27024, "0,9л A");
        moda7Map.put(27025, "2,7л A");
        moda7Map.put(27078, "9л A");
        moda7Map.put(27209, "0,9л TR");
        moda7Map.put(27210, "2,7л TR");
        moda7Map.put(27211, "9л TR");
        eskaroInteriorPaints.put("Moda 7", new Product(moda7Map));

        Map<Integer, String> modaAmMap = new LinkedHashMap<>();
        modaAmMap.put(27200, "0,9л A");
        modaAmMap.put(27201, "2,7л A");
        modaAmMap.put(27202, "9л A");
        modaAmMap.put(27203, "0,9л TR");
        moda7Map.put(27204, "2,7л TR");
        moda7Map.put(27205, "9л TR");
        eskaroInteriorPaints.put("Moda Absolut Matt", new Product(modaAmMap));

        Map<Integer, String> akritMap = new LinkedHashMap<>();
        akritMap.put(38682, "0,9л A");
        akritMap.put(38683, "2,7л A");
        akritMap.put(38684, "9л A");
        akritMap.put(38685, "0,9л TR");
        akritMap.put(38686, "2,7л TR");
        eskaroInteriorPaints.put("Akrit", new Product(akritMap));

        productData.put("Интерьерные краски Eskaro",eskaroInteriorPaints);

        // Данные для "Фасадные краски Aura и Eskaro"
        Map<String, Product> facadePaints = new LinkedHashMap<>();

        Map<Integer, String> expoMap = new LinkedHashMap<>();
        expoMap.put(19498, "2,7л A");
        expoMap.put(19499, "9л A");
        expoMap.put(19579, "2,7л TR");
        expoMap.put(19497, "9л TR");
        facadePaints.put("Aura Expo", new Product(expoMap));

        Map<Integer, String> mansardaMap = new LinkedHashMap<>();
        mansardaMap.put(40516, "0,9л A");
        mansardaMap.put(40517, "2,5л A");
        mansardaMap.put(40518, "9л A");
        mansardaMap.put(40519, "0,9л TR");
        mansardaMap.put(40520, "2,5л TR");
        mansardaMap.put(40521, "9л TR");
        facadePaints.put("Aura Mansarda", new Product(mansardaMap));

        Map<Integer, String> verandaMap = new LinkedHashMap<>();
        verandaMap.put(40533, "0,95л A");
        verandaMap.put(40534, "2,85л A");
        verandaMap.put(22555, "9,5л A");
        verandaMap.put(40535, "0,9л TR");
        verandaMap.put(40536, "2,7л TR");
        verandaMap.put(40537, "9л TR");
        facadePaints.put("Eskaro Veranda", new Product(verandaMap));

        productData.put("Фасадные краски Aura и Eskaro", facadePaints);

        // Данные для "Эмали Aura и Eskaro"
        Map<String, Product> enamels = new LinkedHashMap<>();

        Map<Integer, String> remixMap = new LinkedHashMap<>();
        remixMap.put(19825, "0,9л A");
        remixMap.put(19826, "2,4л A");
        remixMap.put(23292, "0,9л TR");
        remixMap.put(23293, "2,4л TR");
        enamels.put("Aura Remix", new Product(remixMap));

        Map<Integer, String> thermoMap = new LinkedHashMap<>();
        thermoMap.put(22952, "0,45л");
        thermoMap.put(17127, "0,9л");
        enamels.put("Aura Thermo", new Product(thermoMap));

        Map<Integer, String> moobliMap = new LinkedHashMap<>();
        moobliMap.put(40538, "0,45л A");
        moobliMap.put(25045, "0,9л A");
        moobliMap.put(40539, "0,45л TR");
        moobliMap.put(40540, "0,9л TR");
        enamels.put("Eskaro Mööblivärv", new Product(moobliMap));

        productData.put("Эмали Aura и Eskaro", enamels);

        // Данные для "Лаки, масла и лазури"
        Map<String, Product> lacquersOilsLazurs = new LinkedHashMap<>();

        Map<Integer, String> auraInt = new LinkedHashMap<>();
        auraInt.put(25048, "1л");
        auraInt.put(25049, "2,5л");
        lacquersOilsLazurs.put("Aura Interior Lack", new Product(auraInt));

        Map<Integer, String> marineMap = new LinkedHashMap<>();
        marineMap.put(22064, "0,95л");
        marineMap.put(40531, "2,4л");
        lacquersOilsLazurs.put("Eskaro Marine Lakk 40", new Product(marineMap));

        Map<Integer, String> terraceMap = new LinkedHashMap<>();
        terraceMap.put(22608, "0,9л");
        terraceMap.put(40529, "2,7л");
        terraceMap.put(40530, "9л");
        lacquersOilsLazurs.put("Eskaro Terrace", new Product(terraceMap));

        Map<Integer, String> lazurMap = new LinkedHashMap<>();
        lazurMap.put(38345, "0,9л");
        lazurMap.put(38346, "2,5л");
        lazurMap.put(38347, "9л");
        lacquersOilsLazurs.put("Aura Fasad Lazur", new Product(lazurMap));

        productData.put("Лаки, масла и лазури Aura и Eskaro", lacquersOilsLazurs);

        // Данные для "Декоративные покрытия Aura Dekor"
        Map<String, Product> dekor = new LinkedHashMap<>();

        Map<Integer, String> grundMap = new LinkedHashMap<>();
        grundMap.put(22883, "3,5кг");
        grundMap.put(22884, "14кг");
        dekor.put("Aura Dekor Grund", new Product(grundMap));

        Map<Integer, String> map15 = new LinkedHashMap<>();
        map15.put(16272, "8кг");
        map15.put(16044, "25кг");
        dekor.put("Aura Dekor Putz Шуба 1,5мм", new Product(map15));

        Map<Integer, String> map25 = new LinkedHashMap<>();
        map25.put(16273, "8кг");
        map25.put(16045, "25кг");
        dekor.put("Aura Dekor Putz Шуба 2,5мм", new Product(map25));

        Map<Integer, String> putzKor2mm = new LinkedHashMap<>();
        putzKor2mm.put(16270, "8кг");
        putzKor2mm.put(16965, "25кг");
        dekor.put("Aura Dekor Putz Короед 2мм", new Product(putzKor2mm));

        Map<Integer, String> putzKor3mm = new LinkedHashMap<>();
        putzKor3mm.put(16271, "8кг");
        putzKor3mm.put(16043, "25кг");
        dekor.put("Aura Dekor Putz Короед 3мм", new Product(putzKor3mm));

        Map<Integer, String> structur = new LinkedHashMap<>();
        structur.put(22881, "2,5л");
        structur.put(22882, "10л");
        dekor.put("Aura Dekor Structur", new Product(structur));

        Map<Integer, String> primer = new LinkedHashMap<>();
        primer.put(39174, "1,4кг");
        primer.put(39175, "3,65кг");
        dekor.put("Aura Dekor Primer", new Product(primer));

        Map<Integer, String> mattLack = new LinkedHashMap<>();
        mattLack.put(39184, "1кг");
        dekor.put("Aura Dekor Matt Lack", new Product(mattLack));

        Map<Integer, String> atlas = new LinkedHashMap<>();
        atlas.put(39176, "1кг");
        atlas.put(39177, "2,5кг");
        dekor.put("Aura Dekor Atlas", new Product(atlas));

        Map<Integer, String> kristall = new LinkedHashMap<>();
        kristall.put(39178, "1кг");
        kristall.put(39179, "2,5кг");
        dekor.put("Aura Dekor Kristall", new Product(kristall));

        Map<Integer, String> nubuck = new LinkedHashMap<>();
        nubuck.put(39183, "1кг");
        dekor.put("Aura Dekor Nubuck", new Product(nubuck));

        Map<Integer, String> universum = new LinkedHashMap<>();
        universum.put(39182, "8кг");
        dekor.put("Aura Dekor Universum", new Product(universum));

        Map<Integer, String> grotto = new LinkedHashMap<>();
        grotto.put(39180, "15кг");
        dekor.put("Aura Dekor Grotto", new Product(grotto));

        Map<Integer, String> loft = new LinkedHashMap<>();
        loft.put(39181, "15кг");
        dekor.put("Aura Dekor Loft", new Product(loft));

        productData.put("Декоративные покрытия Aura Dekor", dekor);

        return productData;
    }
}


