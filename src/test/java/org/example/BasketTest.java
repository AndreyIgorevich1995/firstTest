package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Arrays;

class BasketTest {

    Basket basket = new Basket();
    Basket basket1 = new Basket();

    @Test
    void addToCart() {
        basket.setSelectedPrices(new int[]{200, 50, 75, 0, 0, 0, 0});
        basket.setNumbersProductsArray(new int[]{3, 2, 2, 0, 0, 0, 0});
        basket.setSelectedProductArray(new String[]{"Колбаса", "Хлеб", "Молоко", null, null, null, null});

        basket1.addToCart(1, 3);
        basket1.addToCart(2, 2);
        basket1.addToCart(3, 2);
        Assertions.assertEquals(basket.getSum(), basket1.getSum());
        Assertions.assertArrayEquals(basket.getPrice(), basket1.getPrice());
        Assertions.assertArrayEquals(basket.getProducts(), basket1.getProducts());
        Assertions.assertArrayEquals(basket.getNumbersProductsArray(), basket1.getNumbersProductsArray());
        Assertions.assertArrayEquals(basket.getSelectedProductArray(), basket1.getSelectedProductArray());
        Assertions.assertArrayEquals(basket.getSelectedPrices(), basket1.getSelectedPrices());
        //        Assertions.assertEquals(basket, basket1);     ЗДЕСЬ ВОПРОС
    }

    @Test
    void loadFromTxtFile() {
        Basket basket_object_result = new Basket();
        File file_basket_expect = new File("test_basket.txt");
        File file_basket_result = new File("basket.txt");

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file_basket_result))) {
            String selectedProductArrayStr = bufferedReader.readLine();
            String priceStr = bufferedReader.readLine();
            String numbersProductsArrayStr = bufferedReader.readLine();

            basket_object_result.setSelectedProductArray(selectedProductArrayStr.split(" "));
            basket_object_result.setSelectedPrices(Arrays.stream(priceStr.split(" "))
                    .map(Integer::parseInt)
                    .mapToInt(Integer::intValue)
                    .toArray());

            basket_object_result.setNumbersProductsArray(Arrays.stream(numbersProductsArrayStr.split(" "))
                    .map(Integer::parseInt)
                    .mapToInt(Integer::intValue)
                    .toArray());
        } catch (IOException a) {
            throw new RuntimeException(a);
        }

        Basket basket_object_expect = Basket.loadFromTxtFile(file_basket_expect);

        Assertions.assertEquals(basket_object_result.getSum(), basket_object_expect.getSum());
        Assertions.assertArrayEquals(basket_object_result.getPrice(), basket_object_expect.getPrice());
        Assertions.assertArrayEquals(basket_object_result.getProducts(), basket_object_expect.getProducts());
        Assertions.assertArrayEquals(basket_object_result.getNumbersProductsArray(), basket_object_expect.getNumbersProductsArray());
        Assertions.assertArrayEquals(basket_object_result.getSelectedProductArray(), basket_object_expect.getSelectedProductArray());
        Assertions.assertArrayEquals(basket_object_result.getSelectedPrices(), basket_object_expect.getSelectedPrices());
    }

    @Test
    void printCart() {
        Basket q = new Basket();
        int sum = 0;
        q.setNumbersProductsArray(new int[]{4, 2, 8, 0, 0, 0, 1});
        for (int i = 0; i < q.getNumbersProductsArray().length; i++) {
            sum = sum + q.getPrice()[i] * q.getNumbersProductsArray()[i];
        }
        q.setSelectedProductArray(new String[]{"Колбаса", "Хлеб", "Молоко", null, null, null, "Шоколад"});
        StringBuilder stringBuilder = new StringBuilder("Ваша корзина: \r\n");
        for (int i = 0; i < q.getSelectedProductArray().length; i++) {
            if (q.getSelectedProductArray()[i] != null) {
                stringBuilder.append(q.getSelectedProductArray()[i] + " "
                        + q.getNumbersProductsArray()[i] + " шт " + q.getPrice()[i] + " руб/шт " +
                        q.getNumbersProductsArray()[i] * q.getPrice()[i] + " руб в сумме\r\n");
            }
        }
        stringBuilder.append("Итого " + sum + " руб\r\n");

        String consoleOutput = null;
        PrintStream originalOut = System.out;
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1000);
            PrintStream capture = new PrintStream(outputStream);
            System.setOut(capture);
            q.printCart();
            capture.flush();
            consoleOutput = outputStream.toString();
            System.setOut(originalOut);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(stringBuilder, consoleOutput);
    }
}
