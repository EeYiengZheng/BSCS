import com.BSCS.BS;
import java.util.Scanner;

/**
 * CS146 Section 7 Project
 *
 * CS BS Game
 *
 * By: Ee Yieng Zheng
 *     Paul Nguyen
 *     Joshua Salom
 * Presentation date: Monday, May 16, 2016
 */
public class _MAIN_ {
    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            System.out.println("Welcome to the CS BS Game");
            System.out.println("Choose number of players, 2-4: ");
            int input = in.nextInt();

            BS BSG = new BS(input);
            BSG.play();
        }
    }
}
