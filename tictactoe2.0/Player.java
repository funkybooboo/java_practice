
public abstract class Player {
    public abstract void makeMove(String[][] board, String token);

    public void printBoard(String[][] board) {
        int numRow = 0;
        for (String[] row : board) {
            int numCol = 0;
            for (String s : row) {
                if (numCol == 2) System.out.print(s);
                else System.out.print(s + " | ");
                numCol++;
            }
            if (numRow != 2) System.out.println("\n---------");
            numRow++;
        }
        System.out.println();
    }
}
