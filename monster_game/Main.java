import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class Main {
    // P = player
    // * = wall
    // M = monsters
    // K = key
    // D = door
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        File file = new File("monsterGame/High Scores");
        boolean overAllDone = false;
        do {
            boolean playerWon;
            boolean playerLost = false;
            int timesPlayed = 0;
            int[] data = getStartingDifficultyLevel(scanner, file);
            int sizeOfMap = data[0];
            int numberOfMonsters = data[1];
            int score = data[2];
            do {
                timesPlayed++;
                if (timesPlayed > 1) {
                    sizeOfMap += 2;
                    numberOfMonsters += 1;
                    score += 50;
                }
                char[][] map = generateLevel(sizeOfMap, numberOfMonsters);
                boolean hasKey = false;
                boolean isAlive = true;
                playerWon = false;
                while(isAlive) {
                    displayMap(map);
                    System.out.print("Enter a command: ");
                    score -= 1;
                    String input = scanner.next();
                    if (input.equals("done")) {
                        System.exit(-1);
                    }
                    int result = movePlayer(map, input);
                    if (result == 1) {
                        hasKey = true;
                    }
                    if (result == 2 && hasKey) {
                        playerWon = true;
                        break;
                    }
                    isAlive = !moveMonsters(map);
                }
                if (playerWon) {
                    System.out.println("You Win!");
                    System.out.println("Score: " + score);
                    handleSavingScore(file, score);
                    overAllDone = anotherGameMenu(scanner, overAllDone);
                    if (overAllDone) {
                        break;
                    }
                } else {
                    System.out.println("You Loose!");
                    System.out.println("Game Over!");
                    System.out.println("Score: " + score);
                    overAllDone = anotherGameMenu(scanner, overAllDone);
                    playerLost = true;
                    timesPlayed = 0;
                }
            } while (!playerLost);
        } while (!overAllDone);
    }

    private static void handleSavingScore(File file, int score) {
        saveScore(file, score);
        sortSavedScores(file);
    }

    private static void quicksort(ArrayList<Integer> array) {
        quicksort(array, 0, array.size() - 1);
    }

    private static void quicksort(ArrayList<Integer> array, int lowIndex, int highIndex) {
        if (lowIndex >= highIndex) {
            return;
        }
        int pivotIndex = new Random().nextInt(highIndex - lowIndex) + lowIndex;
        int pivot = array.get(pivotIndex);
        swap(array, pivotIndex, highIndex);
        int leftPointer = partition(array, lowIndex, highIndex, pivot);
        quicksort(array, lowIndex, leftPointer - 1);
        quicksort(array, leftPointer + 1, highIndex);
    }

    private static int partition(ArrayList<Integer> array, int lowIndex, int highIndex, int pivot) {
        int leftPointer = lowIndex;
        int rightPointer = highIndex;
        while (leftPointer < rightPointer) {
            while (array.get(leftPointer) <= pivot && leftPointer < rightPointer) {
                leftPointer++;
            }
            while (array.get(rightPointer) >= pivot && leftPointer < rightPointer) {
                rightPointer--;
            }
            swap(array, leftPointer, rightPointer);
        }
        swap(array, leftPointer, highIndex);
        return leftPointer;
    }

    private static void swap(ArrayList<Integer> array, int index1, int index2) {
        int temp = array.get(index1);
        array.set(index1, array.get(index2));
        array.set(index2, temp);
    }

    private static void sortSavedScores(File file) {
        try {
            boolean done = false;
            ArrayList<Integer> scores = new ArrayList<>();
            Scanner scanner = new Scanner(file);
            while (!done) {
                if (scanner.hasNextLine()) {
                    String stringScore = scanner.nextLine().strip();
                    if (stringScore.isBlank()) {
                        done = true;
                    } else {
                        int score = Integer.parseInt(stringScore);
                        scores.add(score);
                    }
                } else {
                    break;
                }
            }
            new FileWriter("monsterGame/High Scores", false).close();
            quicksort(scores);
            Collections.reverse(scores);
            for (int data : scores) {
                saveScore(file, data);
            }
        } catch (Exception e) {
            System.out.println("Error while sorting saved scores");
            e.printStackTrace();
        }
    }

    private static void saveScore(File file, int score) {
        try {
            FileWriter fileWriter = new FileWriter(file.getName(), true);
            fileWriter.write(score + "\n");
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("Error while saving score");
        }
    }

    private static void getTopThreeHighScores(File file) {
        try {
            Scanner scanner = new Scanner(file);
            System.out.println("Top 3 high scores");
            for (int i = 0; i < 3; i++) {
                if (scanner.hasNextLine()) {
                    String score = scanner.nextLine();
                    System.out.println("score " + (i + 1) + ": " + score);
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Error while getting Scores");
            e.printStackTrace();
        }
    }

    private static boolean anotherGameMenu(Scanner scanner, boolean overAllDone) {
        boolean done = false;
        System.out.println("Another Game? y or n");
        do {
            String userChoice = scanner.next();
            if (Objects.equals(userChoice, "y")) {
                done = true;
                overAllDone = false;
            } else if (Objects.equals(userChoice, "n")) {
                done = true;
                overAllDone = true;
            } else {
                System.out.println("Invalid input");
            }
        } while (!done);
        return overAllDone;
    }

    private static int[] getStartingDifficultyLevel(Scanner scanner, File file) {
        System.out.println("Pick starting difficulty or see scores");
        System.out.println("See top 3 scores (0)");
        System.out.println("Easy             (1)");
        System.out.println("Normal           (2)");
        System.out.println("Hard             (3)");
        System.out.println("Expert           (4)");
        System.out.print("Enter: ");
        boolean done = false;
        int sizeOfMap = 0;
        int numberOfMonsters = 0;
        int score = 100;
        while (!done) {
            int userChoice = scanner.nextInt();
            if (userChoice == 0) {
                getTopThreeHighScores(file);
            } else if (userChoice == 1) {
                done = true;
                sizeOfMap = 10;
                numberOfMonsters = 2;
            } else if (userChoice == 2) {
                done = true;
                sizeOfMap = 20;
                numberOfMonsters = 4;
                score += 50;
            } else if (userChoice == 3) {
                done = true;
                sizeOfMap = 30;
                numberOfMonsters = 6;
                score += 100;
            } else if (userChoice == 4) {
                done = true;
                sizeOfMap = 40;
                numberOfMonsters = 8;
                score += 150;
            } else {
                System.out.println("Invalid Input");
            }
        }
        int[] data = new int[3];
        data[0] = sizeOfMap;
        data[1] = numberOfMonsters;
        data[2] = score;
        return data;
    }

    private static int movePlayer(char[][] map, String input) {
        int nextRow = 0;
        int nextCol = 0;
        for (int row = 0; row < map.length; row ++) {
            for (int col = 0; col < map.length; col ++) {
                if (map[row][col] == 'P') {
                    if (input.equals("w")) {
                        nextRow = row - 1;
                        nextCol = col;
                    }
                    if (input.equals("a")) {
                        nextRow = row;
                        nextCol = col - 1;
                    }
                    if (input.equals("s")) {
                        nextRow =  row + 1;
                        nextCol = col;
                    }
                    if (input.equals("d")) {
                        nextRow = row;
                        nextCol = col + 1;
                    }
                    // key = 1
                    // door = 2
                    // everything else = 0
                    if (map[nextRow][nextCol] == ' ') {
                        map[row][col] = ' ';
                        map[nextRow][nextCol] = 'P';
                        return 0;
                    }
                    if (map[nextRow][nextCol] == 'K') {
                        map[row][col] = ' ';
                        map[nextRow][nextCol] = 'P';
                        return 1;
                    }
                    if (map[nextRow][nextCol] == 'D') {
                        return 2;
                    }
                    break;
                }
            }
        }
        return 0;
    }

    private static boolean moveMonsters(char[][] map) {
        boolean foundPlayer = false;
        for (int row = 0; row < map.length; row ++) {
            for (int col = 0; col < map.length; col ++) {
                if (map[row][col] == 'M') {
                    int nextRow = row;
                    int nextCol = col;
                    if (map[row][col + 1] == 'P') {
                        foundPlayer = true;
                        nextCol = col + 1;
                    } else if(map[row][col - 1] == 'P') {
                        foundPlayer = true;
                        nextCol = col - 1;
                    } else if (map[row + 1][col] == 'P') {
                        foundPlayer = true;
                        nextRow = row + 1;
                    } else if (map[row - 1][col] == 'P') {
                        foundPlayer = true;
                        nextRow = row - 1;
                    } else {
                        Random random = new Random();

                        if (random.nextDouble() < .5) {
                            if (random.nextDouble() < .5) {
                                nextRow = row - 1;
                            } else {
                                nextRow = row + 1;
                            }
                        } else {
                            if (random.nextDouble() < .5) {
                                nextCol = col - 1;
                            } else {
                                nextCol = col + 1;
                            }
                        }
                    }
                    if (map[nextRow][nextCol] == ' ' || map[nextRow][nextCol] == 'P') {
                        map[row][col] = ' ';
                        map[nextRow][nextCol] = 'm';
                    }
                }
            }
        }
        for (int row = 0; row < map.length; row ++) {
            for (int col = 0; col < map.length; col ++) {
                if (map[row][col] == 'm') {
                    map[row][col] = 'M';
                }
            }
        }
        return foundPlayer;
    }

    private static char[][] generateLevel(int mapSize, int numOfMonsters) {
        char[][] map = new char[mapSize][];
        fillMap(map, mapSize);
        createWalls(map);
        generateInnerWalls(map);
        map = doCellularAutomata(map);
        addStaticElements(map);
        randomlyPlaceElement(map, 'K');
        for (int i = 0; i < numOfMonsters; i++) {
            randomlyPlaceElement(map, 'M');
        }

        return map;
    }

    private static void fillMap(char[][] map, int mapSize) {
        for (int i = 0; i < map.length; i++) {
            map[i] = new char[mapSize];
            Arrays.fill(map[i], ' ');
        }
    }

    private static void createWalls(char[][] map) {
        Arrays.fill(map[0], '*');
        Arrays.fill(map[map.length - 1], '*');
        for (int i = 0; i < map.length; i++) {
            map[i][0] = '*';
            map[i][map[i].length - 1] =  '*';
        }
    }

    private static void generateInnerWalls(char[][] map) {
        Random random = new Random();
        for (int i = 2; i < map.length - 2; i++) {
            for (int j = 2; j < map[i].length - 2; j ++) {
                if (random.nextDouble() < .4) {
                    map[i][j] = '*';
                }
            }
        }
    }

    private static char[][] copyMap(char[][] map) {
        char[][] newMap = new char[map.length][];
        for (int i = 0; i < map.length; i++) {
            newMap[i] = new char[map[i].length];
            System.arraycopy(map[i], 0, newMap[i], 0, map[i].length);
        }
        return newMap;
    }

    private static char[][] doCellularAutomata(char[][] map) {
        char[][] mapCopy = null;
        for (int k = 0; k < 40; k ++) {
            mapCopy = copyMap(map);
            for (int i = 2; i < map.length - 2; i++) {
                for (int j = 2; j < map[i].length - 2; j ++) {
                    int count = countNumberOfNeighbors(map, i, j);
                    if (map[i][j] == '*' && count < 3) {
                        mapCopy[i][j] = ' ';
                    } else if(map[i][j] == ' ' && count > 4) {
                        mapCopy[i][j] = '*';
                    }
                }
            }
            map = mapCopy;
        }
        return mapCopy;
    }

    private static int countNumberOfNeighbors(char[][] map, int row, int col) {
        int count = 0;
        if (map[row - 1][col - 1] == '*') count ++;
        if (map[row - 1][col] == '*') count ++;
        if (map[row - 1][col + 1] == '*') count ++;
        if (map[row][col - 1] == '*') count ++;
        if (map[row][col + 1] == '*') count ++;
        if (map[row + 1][col - 1] == '*') count ++;
        if (map[row + 1][col] == '*') count ++;
        if (map[row + 1][col + 1] == '*') count ++;
        return count;
    }

    private static void addStaticElements(char[][] map) {
        map[map.length - 2][1] = 'P';
        map[1][map[1].length - 2] = 'D';
    }

    private static void randomlyPlaceElement(char[][] map, char element) {
        Random random = new Random();
        int row = random.nextInt(map.length - 3) + 1;
        int col = random.nextInt(map[0].length - 3) + 1;
        while(map[row][col] != ' ') {
            row ++;
        }
        map[row][col] = element;
    }

    private static void displayMap(char[][] map) {
        for (char[] chars : map) {
            for (char aChar : chars) {
                System.out.print(aChar + " ");
            }
            System.out.println();
        }
    }
}
