import java.util.Scanner;

public class Human extends Player {
    public void makeMove(String[][] board, String token) {
        printBoard(board);
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Move: ");
            int input = scanner.nextInt();
            if (input < 1 || input > 9) {
                System.out.println("Invalid input\n\n\n");
                makeMove(board, token);
            }
            String move = Integer.toString(input);
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (move.equals(board[i][j])){
                        board[i][j] = token;
                        return;
                    }
                }
            }
        }
    }
}
