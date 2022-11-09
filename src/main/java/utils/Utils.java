package utils;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Utils {
    public static AtomicInteger counterPalindrome = new AtomicInteger();
    public static AtomicInteger counterSimilarChars = new AtomicInteger();
    public static AtomicInteger counterCorrectOrder = new AtomicInteger();


    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPalindrome(String text){
        return text.equals(new StringBuilder(text).reverse().toString());
    }

    public static boolean isSimilarChars(String text){
        for (int i = 1; i < text.length(); i++) {
            if(text.charAt(i) != text.charAt(i - 1)) return false;
        }
        return true;
    }

    public static boolean isCorrectOrder(String text){
        for (int i = 1; i < text.length(); i++) {
            if(text.charAt(i) < text.charAt(i -1)) return false;
        }
        return true;
    }


    public static void incrementCounter(int stringLength){
        if (stringLength == 3) {
            counterPalindrome.getAndIncrement();
        } else if (stringLength == 4){
            counterSimilarChars.getAndIncrement();
        } else if (stringLength == 5){
            counterCorrectOrder.getAndIncrement();
        }
    }
}
