import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        int[] lotto = new int[6];
        Random random = new Random();

        for (int i = 0; i < lotto.length; i++) {
            int number = random.nextInt(45) + 1;

            for (int j = 0; j < i; j++) {
                if (lotto[j] == number) {
                    number = random.nextInt(45) + 1;
                    j = -1;
                }
            }

            lotto[i] = number;
        }

        Arrays.sort(lotto);

        System.out.println("Lotto Numbers");
        System.out.println(Arrays.toString(lotto));
    }
}
