package org.example;


import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        /*Basket basket = new Basket();
        System.out.println(basket);*/

        File basket = new File("basket.txt");
        basket.createNewFile();
        Scanner scanner = new Scanner(System.in);
        Basket myBasket = null;
//        Basket myBasket = new Basket();

        if (basket.exists()) {
            myBasket = Basket.loadFromTxtFile(basket);
        } else {
            myBasket = new Basket();
        }
        while (true) {

            System.out.println("Список возможных товаров для покупки");
            for (int i = 1; i < myBasket.getProducts().length + 1; i++) {
                System.out.println(i + ". " + myBasket.getProducts()[i - 1] + " " + myBasket.getPrice()[i - 1] + " руб/шт");
            }
            System.out.println("Выберите товар и количество или введите `end`");

            String input = scanner.nextLine();
            if (input.equals("end")) {
                break;
            }
            String[] purchases = input.split(" ");
            int selectedProduct = Integer.parseInt(purchases[0]);  //выбранный продукт
            int numberProducts = Integer.parseInt(purchases[1]);//выбранное количество продукта
            myBasket.addToCart(selectedProduct, numberProducts);
        }

        myBasket.printCart();
        myBasket.saveTxt(basket);

        Basket.loadFromTxtFile(basket);
        System.out.println(Basket.loadFromTxtFile(basket));
    }
}