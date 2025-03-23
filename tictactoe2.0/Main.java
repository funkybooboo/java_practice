import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("(S)ingle player");
        System.out.println("(M)ulti player");
        System.out.print("Input: ");
        Scanner scanner = new Scanner(System.in);
        Player player1 = null;
        Player player2 = null;
        if (Objects.equals(scanner.nextLine().trim(), "s") || Objects.equals(scanner.nextLine().trim(), "S")) {
            player1 = new Human();
            player2 = new Computer();
            System.out.println("player1 = x");
            System.out.println("computer = o");
        } else if (Objects.equals(scanner.nextLine().trim(), "m") || Objects.equals(scanner.nextLine().trim(), "M")) {
            player1 = new Human();
            player2 = new Human();
            System.out.println("player1 = x");
            System.out.println("player2 = 0");
        } else {
            System.out.println("Invalid input\n\n\n");
            main(args);
        }
        String[][] board = {
                {"1", "2", "3"},
                {"4", "5", "6"},
                {"7", "8", "9"}
        };
        assert player1 != null;
        Random random = new Random();
        int low = 0;
        int high = 2;
        int result = random.nextInt(high-low) + low;
        int count = 0;
        while(true) {
            if (result == 0) {
                if (count == 0) System.out.println("player1 go first");
                player1.makeMove(board, "x");
                player2.makeMove(board, "o");
            } else {
                if (count == 0) System.out.println("player2 or computer go first");
                player2.makeMove(board, "o");
                player1.makeMove(board, "x");
            }
            if (isWinner(board, "x") || isWinner(board, "o")) break;
            count++;
        }
        System.out.println("End board");
        player1.printBoard(board);

    }

    private static Boolean isWinner(String[][] board, String token) {
        // row
        if (Objects.equals(board[0][0], token)
                && Objects.equals(board[0][1], token)
                && Objects.equals(board[0][2], token)) {
            return true;
        } else if (Objects.equals(board[1][0], token)
                && Objects.equals(board[1][1], token)
                && Objects.equals(board[1][2], token)) {
            return true;
        } else if (Objects.equals(board[2][0], token)
                && Objects.equals(board[2][1], token)
                && Objects.equals(board[2][2], token)) {
            return true;
        }
        // col
        if (Objects.equals(board[0][0], token)
                && Objects.equals(board[1][0], token)
                && Objects.equals(board[2][0], token)) {
            return true;
        } else if (Objects.equals(board[0][1], token)
                && Objects.equals(board[1][1], token)
                && Objects.equals(board[2][1], token)) {
            return true;
        } else if (Objects.equals(board[0][2], token)
                && Objects.equals(board[1][2], token)
                && Objects.equals(board[2][2], token)) {
            return true;
        }
        // sideways
        if (Objects.equals(board[0][0], token)
                && Objects.equals(board[1][1], token)
                && Objects.equals(board[2][2], token)) {
            return true;
        } else if (Objects.equals(board[0][2], token)
                && Objects.equals(board[1][1], token)
                && Objects.equals(board[2][0], token)) {
            return true;
        }
        return false;
    }

}