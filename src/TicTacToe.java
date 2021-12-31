import java.util.*;

public class TicTacToe {

    public static void main(String[] args) {

        Boolean[][] test1 = {{true, false, false},
                {true, false, false},
                {true, false, false}};

        Boolean[][] test2 = {{false, true, false},
                {false, true, false},
                {false, true, false}};

        Boolean[][] test3 = {{false, false, true},
                {false, false, true},
                {false, false, true}};

        Boolean[][] test4 = {{true, true, true},
                {false, false, false},
                {false, false, false}};

        Boolean[][] test5 = {{false, false, false},
                {true, true, true},
                {false, false, false}};

        Boolean[][] test6 = {{false, false, false},
                {false, false, false},
                {true, true, true}};

        Boolean[][] test7 = {{true, false, false},
                {false, true, false},
                {false, false, true}};

        Boolean[][] test8 = {{false, false, true},
                {false, true, false},
                {true, false, false}};


        List<Boolean[][]> tests = new ArrayList<>();
        tests.add(test1);
        tests.add(test2);
        tests.add(test3);
        tests.add(test4);
        tests.add(test5);
        tests.add(test6);
        tests.add(test7);
        tests.add(test8);


        System.out.println("Tic Tac Toe!");
        System.out.println("Enter 1 to play against a computer");
        System.out.println("Enter 2 to play with a friend");
        System.out.println("---------------------------------");

        Scanner scan = new Scanner(System.in);
        int userGameModeChoice = scan.nextInt();


        if (gameMode1(tests, scan, userGameModeChoice)) return;


        gameMode2(tests, scan, userGameModeChoice);


    }//end of main

    private static boolean gameMode1(List<Boolean[][]> tests, Scanner scan, int userGameModeChoice) {
        //game mode 1
        if (userGameModeChoice == 1) {

            System.out.println("Alright computer is ready");
            System.out.println("Enter a number 1-9 to make your move");
            System.out.println("------------------------------------");

            String[][] positions = {{"1", "2", "3"},
                    {"4", "5", "6"},
                    {"7", "8", "9"}};

            boolean randMoveDone = false;

            while (true) {

                printBoard(positions);

                boolean validChoice = false;
                String[] xo = {"X", "O"};

                while (!validChoice) {

                    if (checkIfTie(positions)) return true;

                    System.out.println("Player make your move");
                    int userPositionChoice = scan.nextInt();
                    boolean isValidPosition = isValidPosition(positions, userPositionChoice, "Already taken, Try Again");


                    if (isValidPosition) {
                        validChoice = true;
                        setPositionValue(positions, userPositionChoice, "X");
                    }
                }
                if (testForWinner(tests, positions, xo)) return true;
                if (checkIfTie(positions)) return true;

                validChoice = false;

                while (!validChoice) {

                    //computer smart move logic
                    System.out.println("Computer is making there move");
                    int computerPositionChoice;
                    if(!randMoveDone){
                        computerPositionChoice = makeRandomCompMove();
                        randMoveDone = true;
                    }else{
                        computerPositionChoice = makeSmartComputerMove(positions);
                    }


                    //test for if the position is alreay taking
                    boolean isValidPosition = true;
                    int pos = 0;
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            pos++;
                            if (computerPositionChoice == pos && ("X".equals(positions[i][j]) || "O".equals(positions[i][j]))) {
                                isValidPosition = false;

                            }
                        }
                    }

                    if (isValidPosition) {
                        setPositionValue(positions, computerPositionChoice, "O");
                        validChoice = true;
                    }
                }
                // how to make a test for winner
                if (testForWinner(tests, positions, xo)) return true;

                if (checkIfTie(positions)) return true;
            }
        }
        return false;
    }

    private static boolean isValidPosition(String[][] positions, int userPositionChoice, String s) {
        //how to make a test for if the position is already taking
        boolean isValidPosition = true;
        int pos = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                pos++;
                if (userPositionChoice == pos && ("X".equals(positions[i][j]) || "O".equals(positions[i][j]))) {
                    isValidPosition = false;
                    System.out.println(s);

                }
            }
        }
        return isValidPosition;
    }

    private static void setPositionValue(String[][] positions, int userPositionChoice, String x) {
        if (userPositionChoice == 1) {
            positions[0][0] = x;
        }
        if (userPositionChoice == 2) {
            positions[0][1] = x;
        }
        if (userPositionChoice == 3) {
            positions[0][2] = x;
        }
        if (userPositionChoice == 4) {
            positions[1][0] = x;
        }
        if (userPositionChoice == 5) {
            positions[1][1] = x;
        }
        if (userPositionChoice == 6) {
            positions[1][2] = x;
        }
        if (userPositionChoice == 7) {
            positions[2][0] = x;
        }
        if (userPositionChoice == 8) {
            positions[2][1] = x;
        }
        if (userPositionChoice == 9) {
            positions[2][2] = x;
        }
    }

    private static void gameMode2(List<Boolean[][]> tests, Scanner scan, int userGameModeChoice) {
        //game mode 2
        if (userGameModeChoice == 2) {

            System.out.println("Alright player X make your move!");
            System.out.println("Enter a number 1-9 to make your move");
            System.out.println("------------------------------------");

            String[] xo = {"X", "O"};

            String[][] positions = {{"1", "2", "3"},
                    {"4", "5", "6"},
                    {"7", "8", "9"}};
            boolean validChoice = false;

            while (true) {

                printBoard(positions);

                //Player X goes now
                while (!validChoice) {

                    if (checkIfTie(positions)) return;

                    System.out.println("Player X: ");
                    int user1PositionChoice = scan.nextInt();

                    //test for if positon is taken
                    validChoice = testForIsPositionAlreadyTaken(positions, user1PositionChoice);

                    setPositionValue(positions, user1PositionChoice, "X");
                }

                // Test for winner
                if (testForWinner(tests, positions, xo)) return;

                //Test for Tie
                if (checkIfTie(positions)) return;
                validChoice = false;


                //player O goes now
                while (!validChoice) {

                    printBoard(positions);

                    System.out.println("Player O: ");
                    int user2PositionChoice = scan.nextInt();

                    //test for if positon is taken
                    validChoice = testForIsPositionAlreadyTaken(positions, user2PositionChoice);

                    setPositionValue(positions, user2PositionChoice, "O");
                }
                //Test for Winner
                if (testForWinner(tests, positions, xo)) return;

                //Test for Tie
                if (checkIfTie(positions)) return;

                validChoice = false;
            }
        }
    }

    private static boolean testForIsPositionAlreadyTaken(String[][] positions, int user1PositionChoice) {
        boolean isValidPosition = isValidPosition(positions, user1PositionChoice, "Invalid choice, Try Again");
        return isValidPosition;
    }

    //Talking machine code
    public enum Voice {Alex}
    public void say(String msg) {
        say(msg, Voice.Alex);
    }
    public void say(String msg, Voice voice) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        // The shell command to run
        processBuilder.command("bash", "-c", "say -v " + voice.name() + " \"" + msg + "\"");

        try {
            // Execute the command
            Process process = processBuilder.start();
            // Wait for the command to finish
            process.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean testForWinner(List<Boolean[][]> tests, String[][] positions, String[] xo) {
        // how to test for winner
        for (int x = 0; x < xo.length; x++) {
            String v = xo[x];
            for (Boolean[][] test : tests) {
                int score = 0;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        String boardValueAtPosition = positions[i][j];
                        if (test[i][j] && boardValueAtPosition.equals(v)) {
                            score++;
                        }
                    }
                }
                if (score == 3) {
                    System.out.println("We have a winner: " + v);

                    TicTacToe computerVoice = new TicTacToe();

                    if(v.equals("O")){
                        computerVoice.say("hahaha you suck");
                    }

                    return true;
                }
            }
        }
        return false;
    }

    private static boolean checkIfTie(String[][] positions) {
        //check if game is a TIE
        int counter = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; i < 3; i++) {
                if (positions[i][j].equals("X") || positions[i][j].equals("O")) {
                    counter++;
                    if (counter == 9) {
                        System.out.println("Tie, Game over");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static void printBoard(String[][] positions) {
        //when called it prints the board

        System.out.println("\n \n \n \n \n \n \n \n \n \n \n \n");
        System.out.println(positions[0][0] + "|" + positions[0][1] + "|" + positions[0][2] +
                "\n" + "-" + " " + "-" + " " + "-" +
                "\n" + positions[1][0] + "|" + positions[1][1] + "|" + positions[1][2] +
                "\n" + "-" + " " + "-" + " " + "-" +
                "\n" + positions[2][0] + "|" + positions[2][1] + "|" + positions[2][2]);
    }

    private static int makeSmartComputerMove(String[][] positions){
        //computer makes a smart desition about were to go

        int computerSmartChoice = 0;

        Integer positionToWin = winCheck(positions);
        if (positionToWin != null) {
            return positionToWin;
        }

        Integer positionToBlock = blockCheck(positions);
        if (positionToBlock != null) {
            return positionToBlock;
        }


        return makeRandomCompMove();

    }

    private static Integer blockCheck(String[][] positions) {
        //check if comp needs to block player

        //check rows
        for(int r = 0; r < 3; r++){
            int count = 0;
            for(int c = 0; c < 3; c++){
                if(positions[r][c].equals("X") && !positions[r][c].equals("O")){
                    count++;
                }
            }
            if(count == 2){
                for(int c = 0; c < 3; c++){
                    if(!positions[r][c].equals("X") && !positions[r][c].equals("O")){
                        return (r * 3) + c + 1;
                    }
                }
            }
        }

        //check calums
        for(int c = 0; c < 3; c++){
            int count = 0;
            for(int r = 0; r < 3; r++){
                if(positions[r][c].equals("X") && !positions[r][c].equals("O")){
                    count++;
                }
            }
            if(count == 2){
                for(int r = 0; r < 3; r++){
                    if(!positions[r][c].equals("X") && !positions[r][c].equals("O")){
                        return (r * 3) + c + 1;
                    }
                }
            }
        }

        //check diagonal
        int count = 0;
        if(positions[0][0].equals("X")){
            count++;
        }
        if(positions[1][1].equals("X")){
            count++;
        }
        if(positions[2][2].equals("X")){
            count++;
        }
        if(count == 2){
            if(!positions[0][0].equals("X") && !positions[0][0].equals("O")){
                return 1;
            }
            if(!positions[1][1].equals("X") && !positions[1][1].equals("O")){
                return 5;
            }
            if(!positions[2][2].equals("X") && !positions[2][2].equals("O")){
                return 9;
            }
        }
        count = 0;
        if(positions[0][2].equals("X")){
            count++;
        }
        if(positions[1][1].equals("X")){
            count++;
        }
        if(positions[2][0].equals("X")){
            count++;
        }
        if(count == 2){
            if(!positions[0][2].equals("X") && !positions[0][2].equals("O")){
                return 3;
            }
            if(!positions[1][1].equals("X") && !positions[1][1].equals("O")){
                return 5;
            }
            if(!positions[2][0].equals("X") && !positions[2][0].equals("O")){
                return 7;
            }
        }
        return null;
    }

    //check what move will win for comp
    private static Integer winCheck(String[][] positions) {
        //check rows
        for(int r = 0; r < 3; r++){
            int count = 0;
            for(int c = 0; c < 3; c++){
                if(positions[r][c].equals("O")){
                    count++;
                }
            }
            if(count == 2){
                for(int c = 0; c < 3; c++){
                    if(!positions[r][c].equals("O") && !positions[r][c].equals("X")){
                        return (r * 3) + c + 1;
                    }
                }
            }
        }

        //check calums
        for(int c = 0; c < 3; c++){
            int count = 0;
            for(int r = 0; r < 3; r++){
                if(positions[r][c].equals("O")){
                    count++;
                }
            }
            if(count == 2){
                for(int r = 0; r < 3; r++){
                    if(!positions[r][c].equals("O") && !positions[r][c].equals("X")){
                        return (r * 3) + c + 1;
                    }
                }
            }
        }

        //check diagonal
        int count = 0;
        if(positions[0][0].equals("O")){
            count++;
        }
        if(positions[1][1].equals("O")){
            count++;
        }
        if(positions[2][2].equals("O")){
            count++;
        }
        if(count == 2){
            if(!positions[0][0].equals("O") && !positions[0][0].equals("X")){
                return 1;
            }
            if(!positions[1][1].equals("O") && !positions[1][1].equals("X")){
                return 5;
            }
            if(!positions[2][2].equals("O") && !positions[2][2].equals("X")){
                return 9;
            }
        }
        count = 0;
        if(positions[0][2].equals("O")){
            count++;
        }
        if(positions[1][1].equals("O")){
            count++;
        }
        if(positions[2][0].equals("O")){
            count++;
        }
        if(count == 2){
            if(!positions[0][2].equals("O") && !positions[0][2].equals("X")){
                return 3;
            }
            if(!positions[1][1].equals("O") && !positions[1][1].equals("X")){
                return 5;
            }
            if(!positions[2][0].equals("O") && !positions[2][0].equals("X")){
                return 7;
            }
        }
        return null;
    }

    private static int makeRandomCompMove(){
        //computer makes a random desition about were to go

        Random rand = new Random();
        int computerPositionChoice = rand.nextInt(9) + 1;

        return computerPositionChoice;

    }

}
