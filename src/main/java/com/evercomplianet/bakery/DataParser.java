package com.evercomplianet.bakery;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class DataParser {

    public static Logger logger = LogManager.getLogger("FilesParser");

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static  List<Order> parseOrderFile(Path path) throws Exception {

        List<Order> orders =new ArrayList<>();
        String currentLine;

        BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
        while ((currentLine = reader.readLine()) != null) {//while there is content on the current line
            if (!currentLine.startsWith(";")) {

                Order order = objectMapper.readValue(currentLine, Order.class);
                orders.add(order);
            }
        }
        logger.debug("start:{}", System.currentTimeMillis());
        return orders;
    }


    public static Map<String,Product> parseProductFile(Path path) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Product> map =new ConcurrentHashMap<>();
        String currentLine;
        BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
        while ((currentLine = reader.readLine()) != null) {//while there is content on the current line
            if (!currentLine.startsWith(";")) {

                Product product = objectMapper.readValue(currentLine, Product.class);
                map.put(product.getName(),product);

            }
        }
        logger.debug("start:{}", System.currentTimeMillis());
        return map;
    }



}


