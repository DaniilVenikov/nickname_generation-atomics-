import java.util.Random;

import static utils.Utils.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread palindrome = new Thread(() -> {
            for (String text : texts){
                if(isPalindrome(text) && !isSimilarChars(text)) incrementCounter(text.length());
            }
        });
        palindrome.start();

        Thread sameChar = new Thread(() -> {
            for (String text : texts){
                if(isSimilarChars(text)) incrementCounter(text.length());
            }
        });
        sameChar.start();

        Thread ascendingOrder = new Thread(() -> {
            for (String text : texts){
                if(isCorrectOrder(text) && !isSimilarChars(text)) incrementCounter(text.length());
            }
        });
        ascendingOrder.start();


        palindrome.join();
        sameChar.join();
        ascendingOrder.join();

        System.out.println("Красивых слов с длиной 3: " + counterPalindrome + " штук");
        System.out.println("Красивых слов с длиной 4: " + counterSimilarChars + " штук");
        System.out.println("Красивых слов с длиной 5: " + counterCorrectOrder + " штук");

    }
}
