import java.sql.Array;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    static AtomicInteger atomicIntegerWordsLength3 = new AtomicInteger(0);
    static AtomicInteger atomicIntegerWordsLength4 = new AtomicInteger(0);
    static AtomicInteger atomicIntegerWordsLength5 = new AtomicInteger(0);
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        int[] result = new int[3];
        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (String letter : texts){
            if (letter.length() == 3){
                Callable<Integer> callable1 = () -> {
                    int counter = 0;
                    if (checkPalindrome(letter) || checkOneChar(letter) || checkOrder(letter)){
                        counter = atomicIntegerWordsLength3.getAndIncrement();
                    }
                    return counter;
                };
                Future<Integer> future = executor.submit(callable1);
                result[0] = future.get();
            } else if (letter.length() == 4){
                Callable<Integer> callable2 = () -> {
                    int counter = 0;
                    if (checkPalindrome(letter) || checkOneChar(letter) || checkOrder(letter)){
                        counter = atomicIntegerWordsLength4.getAndIncrement();
                    }
                    return counter;
                };
                Future<Integer> future = executor.submit(callable2);
                result[1] = future.get();
            } else if (letter.length() == 5){
                Callable<Integer> callable3 = () -> {
                    int counter = 0;
                    if (checkPalindrome(letter) || checkOneChar(letter) || checkOrder(letter)){
                        counter = atomicIntegerWordsLength5.getAndIncrement();
                    }
                    return counter;
                };
                Future<Integer> future = executor.submit(callable3);
                result[2] = future.get();
            }
        }
        executor.shutdown();
        System.out.printf("Красивых слов с длиной 3: %d штук%nКрасивых слов с длиной 4: %d штук%n" +
                "Красивых слов с длиной 5: %d штук%n", result[0], result[1], result[2]);

    }

    public static boolean checkPalindrome(String  original){
        StringBuilder reverse = new StringBuilder();
        for (int i = original.length() - 1; i >= 0 ; i--) {
            reverse.append(original.charAt(i));
        }
        return original.equals(reverse.toString());
    }

    public static boolean checkOneChar(String letters){
        if (letters.isEmpty()) return false;
        int count = 0;
        char c = letters.charAt(0);
        for (int i = letters.length() - 1; i > 0; i--) {
            if(c == letters.charAt(i)) count++;
        }
        return count == letters.length() - 1;
    }

    public static boolean checkOrder(String letters){
        int max = 0;
        int count;
        boolean flag = true;
        int i = 0;
        while (flag && i < letters.length()){
            char c = letters.charAt(i);
            count = (int) letters.chars()
                    .filter(x -> x == c).count();
            if( count > 1){
                if (max == 0) {
                    max = count;
                } else if (max > count){
                    flag = false;
                    break;
                }
                for (int j = i + 1; j < count; j++) {
                    char temp = letters.charAt(j);
                    if (c != temp) {
                        flag = false;
                        break;
                    }
                }
            } else i++;
            i++;
        }
        return flag;
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}
