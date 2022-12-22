import java.util.Random;

public class Computer extends Player {
    public void makeMove(String[][] board, String token) {
        Random random = new Random();
        int low = 1;
        int high = 10;
        while (true) {
            String move = Integer.toString(random.nextInt(high-low) + low);
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
