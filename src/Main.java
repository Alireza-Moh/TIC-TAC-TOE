import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * Project name: TIC TAC TOE
 * @author Alireza Mohammadi
 * */

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();

    // -----------------------------------FOR THE PLAYGROUND-----------------------------------
    public static final int ROW = 3;
    public static final int COLUMN = 3;
    public static char[][] board = new char[ROW][COLUMN];

    // -----------------------------------COLORS-----------------------------------
    public static final String RESET_COLOR = "\033[0m";    // Text Reset
    public static final String RED = "\033[0;31m";    // RED
    public static final String BLUE = "\033[0;34m";    //    BLUE

    //-----------------------------------PLAYERS-----------------------------------
    public static String player1Name;
    public static String player2Name;
    public static int player1Move;
    public static int player2Move;

    //-----------------------------------MODE-----------------------------------
    public static int mode;

    public static String message;

    //-----------------------------------AI--------------------------------------
    public static int aiMove;

    public static void main(String[] args) {
        startGame();
    }

    /**
     * This method will contain all the other methods. By calling this method in the main-method it will run the other
     * methods
     * */
    public static void startGame() {
        printInfo();
        getMode();
        startMode(mode);
    }

    /**
     * In this mode two human players play against each other
     * */
    public static void hvsH() {
        initializeBoard("mode 1 (human-vs-human)");
        drawBoard(board);
        getPlayer1Name();
        getPlayer2Name();
        printInformation(player1Name, player2Name);
        whoStarts();
    }

    /**
     * This method ask the players for the game mode
     * */
    public static void getMode() {
        boolean isValid = false;
        while (!isValid) {
            try {
                do {
                    System.out.println("----------------------------------------------------------------" + BLUE);
                    System.out.println("Enter 1 if you want to play again a human player otherwise 2");
                    System.out.println("[Mode 1]: human vs human,");
                    System.out.println("[Mode 2]: AI vs human");
                    System.out.println(RESET_COLOR + "----------------------------------------------------------------");
                    System.out.println("Choose your mode:");
                    mode = scanner.nextInt();
                    isValid = true;
                } while (mode < 1 || mode > 2);
            }catch (InputMismatchException e) {
                scanner.nextLine();    // Clears the buffer
                System.err.println("Enter a number please!");
            }
        }
    }

    /**
     * This method starts the mode that the player has chosen
     * @param mode the game mode
     * */
    public static void startMode(int mode) {
        switch (mode) {
            case 1 -> {
                hvsH();
            }
            case 2 -> {
                aiVsHuman();
            }
            default -> {
                throw new IllegalStateException("Unexpected value: " + mode);
            }
        }
    }

    /**
     * It just prints a welcome message
     * */
    public static void printInfo() {
        boolean failed = false;
        while (!failed) {
            try {
                System.out.println("Welcome to TIC TAC TOE");
                //System.out.println("Enter your name and the character with which you won to play.");
                Thread.sleep(1000);
                failed = true;
            }catch (Exception e) {
                System.out.println("An error has occurred. The game is restarted");
            }
        }
    }

    /**
     * It initializes the board
     * @param message mode message
     * */
    public static void initializeBoard(String message) {
        try {
            System.out.println("Starting " + message + "...");
            System.out.println("Initializing board...");
            Thread.sleep(2000);    // Wait 2 second
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    board[i][j] = '-';
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * It prints the board in the console
     * @param board the playground
     * */
    public static void drawBoard(char[][] board) {
        System.out.println("----------------" + RED);
        for (char[] chars : board) {
            System.out.print(" | ");
            for (int j = 0; j < board.length; j++) {
                System.out.print(chars[j] + " | ");
            }
            System.out.println();
        }
        System.out.println(RESET_COLOR + "----------------");
    }

    /**
     * It gets the name of player-1
     * */
    public static void getPlayer1Name() {
        do {
            System.out.println("[Player-1] Enter your name:");
            player1Name = scanner.next();
        }while (!isAlphabetic(player1Name));
    }

    /**
     * It gets the name of player-2
     * */
    public static void getPlayer2Name() {
        do {
            System.out.println("[Player-2] Enter your name:");
            player2Name = scanner.next();
        }while (!isAlphabetic(player2Name));
    }

    /**
     * It checks if the entered name contains chars
     * @param playerName the entered name
     * */
    public static boolean isAlphabetic(String playerName) {
        for(char character : playerName.toCharArray())
            if (!Character.isLetter(character)) {
                return false;    // If the String contains numbers or other characters then [A-z] then return false
            }
        return true;
    }

    /**
     * Just print the inputs to remind players
     * @param player1Name player-1
     * @param player2Name player-2
     * */
    public static void printInformation(String player1Name, String player2Name) {
        System.out.println("-------------------------------------------------------------------");
        System.out.println(BLUE + "[" + player1Name + "]" + " your are X!");
        System.out.println(BLUE + "[" + player2Name + "]" + " your are O (Not 0-ZERO)!" + RESET_COLOR);
        System.out.println("-------------------------------------------------------------------");
    }

    /**
     * Get the placement of player-1
     * @param playerName player name
     * */
    public static void getPlayer1Move(String playerName) {
        boolean isValid = false;
        while (!isValid) {
            try {
                do {
                    System.out.println("[" + playerName + "] " + "Enter your placement (1-9):");
                    player1Move = scanner.nextInt();
                    isValid = true;
                } while (player1Move < 1 || player1Move > 9);
                if (!isSpaceAvailable(board, player1Move)) {
                    System.out.println(BLUE + "This field is already taken by " + RED + "Code" + RESET_COLOR);
                    do {
                        System.out.println("[" + playerName + "] " + "Enter your placement (1-9):");
                        player1Move = scanner.nextInt();
                    } while (player1Move < 1 || player1Move > 9);
                }
            }catch (InputMismatchException e) {
                String invalidInput = scanner.nextLine();    // This line clears the buffer when the player is entering an invalid number
                System.out.println("Invalid input. Please enter a number between 1-9");
                System.out.println("You entered " + invalidInput);
            }
        }
    }

    /**
     * Get the placement of player-2
     * @param playerName player name
     * */
    public static void getPlayer2Move(String playerName) {
        boolean isValid = false;   // Flag to get out of while loop
        while (!isValid) {
            try {
                do {
                    System.out.println("[" + playerName + "] " + "Enter your placement (1-9):");
                    player2Move = scanner.nextInt();
                    isValid = true;
                } while (player2Move < 1 || player2Move > 9);
                if (!isSpaceAvailable(board, player2Move)) {
                    System.out.println(BLUE + "This field is already taken by " + RED + player1Name + RESET_COLOR);
                    do {
                        System.out.println("[" + playerName + "] " + "Enter your placement (1-9):");
                        player2Move = scanner.nextInt();
                    } while (player2Move < 1 || player2Move > 9);
                }
            }catch (InputMismatchException e) {
                String invalidInput = scanner.next();    // This line clears the buffer when the player is entering an invalid number
                System.out.println("Invalid input. Please enter a number between 1-9");
                System.out.println("You entered " + invalidInput);
            }
        }
    }

    /**
     * Choose starter randomly
     * X represents player-1 and O represents player-2
     * Choose randomly between 1 and 2
     * 1 stands for player-1
     * 2 stands for player-2
     * */
    public static void whoStarts() {
        int randomNum = 1 + random.nextInt(2);

        switch (randomNum) {
            case 1 -> {
                while (true) {
                    getPlayer1Move(player1Name);
                    updateBoard(board, player1Move, 'X');
                    if (isGameOver(board, player1Name, player2Name)) {
                        break;
                    }
                    getPlayer2Move(player2Name);
                    updateBoard(board, player2Move, 'O');
                    if (isGameOver(board, player1Name, player2Name)) {
                        break;
                    }
                }
                restartGame();
            }
            case 2 -> {
                while (true) {
                    getPlayer2Move(player2Name);
                    updateBoard(board, player2Move, 'O');
                    if (isGameOver(board, player1Name, player2Name)) {
                        break;
                    }
                    getPlayer1Move(player1Name);
                    updateBoard(board, player1Move, 'X');
                    if (isGameOver(board, player1Name, player2Name)) {
                        break;
                    }
                }
                restartGame();
            }
        }
        //restartGame();
    }

    /**
     * After the player move input, check whether the selected field is still free or not yet occupied.
     * @param board the playground
     * @param playerMove player move
     * @return true or false (true when the field is not taken and false when it is already selected)
     * */
    public static boolean isSpaceAvailable(char[][] board, int playerMove) {
        switch (playerMove) {
            case 1 -> {
                return board[0][0] == '-';
            }
            case 2 -> {
                return board[0][1] == '-';
            }
            case 3 -> {
                return board[0][2] == '-';
            }
            case 4 -> {
                return  board[1][0] == '-';
            }
            case 5 -> {
                return board[1][1] == '-';
            }
            case 6 -> {
                return board[1][2] == '-';
            }
            case 7 -> {
                return board[2][0] == '-';
            }
            case 8 -> {
                return board[2][1] == '-';
            }
            case 9 -> {
                return board[2][2] == '-';
            }
            default -> {
                return false;
            }
        }
    }

    /**
     * Set the players move on the board and then print the board
     * @param board the playground
     * @param playerMove player placement
     * @param playerCharacter player character
     * */
    public static void updateBoard(char[][] board, int playerMove, char playerCharacter) {
        switch (playerMove) {
            case 1 -> {
                board[0][0] = playerCharacter;
                drawBoard(board);
            }
            case 2 -> {
                board[0][1] = playerCharacter;
                drawBoard(board);
            }
            case 3 -> {
                board[0][2] = playerCharacter;
                drawBoard(board);
            }
            case 4 -> {
                board[1][0] = playerCharacter;
                drawBoard(board);
            }
            case 5 -> {
                board[1][1] = playerCharacter;
                drawBoard(board);
            }
            case 6 -> {
                board[1][2] = playerCharacter;
                drawBoard(board);
            }
            case 7 -> {
                board[2][0] = playerCharacter;
                drawBoard(board);
            }
            case 8 -> {
                board[2][1] = playerCharacter;
                drawBoard(board);
            }
            case 9 -> {
                board[2][2] = playerCharacter;
                drawBoard(board);
            }
        }
    }

    /**
     * Check if the game is finished
     * @param board playground
     * @param player1Name name of player-1
     * @param player2Name name of player-2
     * */
    public static boolean isGameOver(char[][] board, String player1Name, String player2Name) {
        if (checkWinner(board, 'X')) {    // If the X matches to 3 field then return true, is means that player-1 has won
            System.out.println("----------------------------");
            System.out.println(BLUE + player1Name + " has won the game!" + RESET_COLOR);
            System.out.println("----------------------------");
            return true;
        }
        else if (checkWinner(board, 'O')) {    // If the X matches to 3 field then return true, is means that player-2 has won
            System.out.println("----------------------------");
            System.out.println(BLUE + player2Name + " has won the game!" + RESET_COLOR);
            System.out.println("----------------------------");
            return true;
        }
        for (char[] chars : board) {
            for (int k = 0; k < board.length; k++) {
                if (chars[k] == '-') {
                    return false;
                }
            }
        }
        System.out.println("----------------------------------------");
        System.out.println( BLUE + "No winner, the game ended in a tie!" + RESET_COLOR);
        System.out.println("----------------------------------------");
        return true;
    }

    /**
     * Check if there is a winner (check if one of the player has 3 fields in a row with their own character)
     * @param board playground
     * @param playerCharacter the player char
     * */
    public static boolean checkWinner(char[][] board, char playerCharacter) {
        for (int i = 0; i < board.length; i++) {
            if (board[i][0] == playerCharacter && board[i][1] == playerCharacter && board[i][2] == playerCharacter) {
                return true;
            }
        }
        for (int j = 0; j < board.length; j++) {
            if (board[0][j] == playerCharacter && board[1][j] == playerCharacter && board[2][j] == playerCharacter) {
                return true;
            }
        }

        if (board[0][0] == playerCharacter && board[1][1] == playerCharacter && board[2][2] == playerCharacter) {
            return true;
        }
        else if (board[0][2] == playerCharacter && board[1][1] == playerCharacter && board[2][0] == playerCharacter) {
            return true;
        }
        return false;
    }

    /**
     * Ask the players if the want to play again
     * */
    public static void restartGame() {
        String playAgain;    // To store player input

        System.out.println("Do you want ot play another round? (yes or no)");
        playAgain = scanner.next().toUpperCase();

        if (playAgain.equals("YES")) {
            hvsH();
        }
        else {
            System.out.println("Ok, see you soon");
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------AI MODE-------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------

    /**
     * In this mode play an AI against a human player
     * */
    public static void aiVsHuman() {
        initializeBoard("mode 2 (AI-vs-human)");
        drawBoard(board);
        getPlayer1Name();
        printInformation(player1Name, "Code");    //AI name: Code
        whoStarts(player1Name, "Code");
    }

    /**
     * Choose starter randomly
     * Choose between 1 and 2
     * X represents player-1 and O represents AI
     * 1 stands for player-1
     * 2 stands for AI named Code
     * */
    public static void whoStarts(String player1Name, String player2Name) {
        int randomNum = 1 + random.nextInt(2);

        switch (randomNum) {
            case 1 -> {
                while (true) {
                    getPlayer1Move(player1Name);
                    updateBoard(board, player1Move, 'X');
                    if (isGameOver(board, player1Name, player2Name)) {
                        break;
                    }
                    do {
                        aiMove();
                    }while (aiMove == player1Move);
                    updateBoard(board, aiMove, 'O');    // O represents the AI
                    if (isGameOver(board, player1Name, "Code")) {
                        break;
                    }
                }
                restartGame();
            }
            case 2 -> {
                while (true) {
                    do {
                        aiMove();
                    }while (aiMove == player1Move);
                    updateBoard(board, aiMove, 'O');    // O represents the AI
                    if (isGameOver(board, player1Name, "Code")) {
                        break;
                    }
                    getPlayer1Move(player1Name);
                    updateBoard(board, player1Move, 'X');
                    if (isGameOver(board, player1Name, "Code")) {
                        break;
                    }
                }
                restartGame();
            }
        }
    }

    /**
     * This method generates a random number between 1-9 for the (dump)
     * AI placement
     * */
    public static void aiMove() {
        aiMove = 1 + random.nextInt(9);
    }
}
