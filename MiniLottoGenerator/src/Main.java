import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {

    static final int LOTTO_SIZE = 6;
    static final int MIN_NUMBER = 1;
    static final int MAX_NUMBER = 45;

    static Random random = new Random();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("================================");
        System.out.println("       LOTTO GENERATOR V2       ");
        System.out.println("================================");

        System.out.print("자동 게임 수를 입력하세요: ");
        int autoCount = scanner.nextInt();

        System.out.print("수동 게임 수를 입력하세요: ");
        int manualCount = scanner.nextInt();

        int[][] autoGames = new int[autoCount][LOTTO_SIZE];
        int[][] manualGames = new int[manualCount][LOTTO_SIZE];

        // 자동 번호 생성
        for (int i = 0; i < autoCount; i++) {
            autoGames[i] = createAutoLotto();
        }

        // 수동 번호 입력
        for (int i = 0; i < manualCount; i++) {

            System.out.println();
            System.out.println("[수동 " + (i + 1) + "게임]");

            manualGames[i] = createManualLotto();
        }

        // 결과 출력
        System.out.println();
        System.out.println("================================");
        System.out.println("          LOTTO RESULT          ");
        System.out.println("================================");

        for (int i = 0; i < autoCount; i++) {
            System.out.println(
                    "[자동 " + (i + 1) + "] "
                            + Arrays.toString(autoGames[i])
            );
        }

        for (int i = 0; i < manualCount; i++) {
            System.out.println(
                    "[수동 " + (i + 1) + "] "
                            + Arrays.toString(manualGames[i])
            );
        }

        scanner.close();
    }

    // 자동 로또 번호 생성
    public static int[] createAutoLotto() {

        int[] lotto = new int[LOTTO_SIZE];

        for (int i = 0; i < lotto.length; i++) {

            int number = random.nextInt(MAX_NUMBER) + 1;

            for (int j = 0; j < i; j++) {

                if (lotto[j] == number) {
                    number = random.nextInt(MAX_NUMBER) + 1;
                    j = -1;
                }
            }

            lotto[i] = number;
        }

        Arrays.sort(lotto);

        return lotto;
    }

    // 수동 로또 번호 입력
    public static int[] createManualLotto() {

        int[] lotto = new int[LOTTO_SIZE];

        for (int i = 0; i < lotto.length; i++) {

            while (true) {

                System.out.print(
                        (i + 1) + "번째 번호 입력 (1~45): "
                );

                int number = scanner.nextInt();

                // 범위 검사
                if (number < MIN_NUMBER || number > MAX_NUMBER) {

                    System.out.println(
                            "1부터 45 사이의 숫자를 입력해주세요."
                    );

                    continue;
                }

                // 중복 검사
                boolean duplicate = false;

                for (int j = 0; j < i; j++) {

                    if (lotto[j] == number) {
                        duplicate = true;
                        break;
                    }
                }

                if (duplicate) {

                    System.out.println(
                            "이미 입력한 번호입니다."
                    );

                    continue;
                }

                lotto[i] = number;
                break;
            }
        }

        Arrays.sort(lotto);

        return lotto;
    }
}