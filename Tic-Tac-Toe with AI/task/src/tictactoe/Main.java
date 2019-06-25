package tictactoe;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

enum DifficultyLevel {
    EASY, MEDIUM, hard
}

interface Difficulty {

    void makeComputerMove(Player player);
}

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TicTacToeStateEvaluator ticTacToeStateEvaluator = new TicTacToeStateEvaluator();
        UserInputLoader userInputLoader = new UserInputLoader();
        Game game = new Game(scanner, ticTacToeStateEvaluator, userInputLoader);
        userInputLoader.setScanner(scanner);
        while (true) {
            System.out.println("Input command:");
            String input = scanner.nextLine();
            if (input.equals("exit")) {
                break;
            }
            String[] arguments = input.split(" ");
            if (arguments.length == 3) {
                if (arguments[0].equals("start")) {
                    switch (arguments[1]) {
                        default:
                            System.out.println("Bad parameters!");
                        case "user":
                        case "easy":
                        case "medium":
                            break;
                    }
                    switch (arguments[2]) {
                        case "user":
                        case "easy":
                        case "medium":
                            break;
                        default:
                            System.out.println("Bad parameters!");
                    }
                }
                game.start(arguments);
            } else {
                System.out.println("Bad parameters!");
            }
        }
    }
}

class Game {

    private char[][] board;
    private Scanner scanner;
    private TicTacToeStateEvaluator ticTacToeStateEvaluator;
    private UserInputLoader userInputLoader;

    private Player playerOne;
    private Player playerTwo;

    public Game(Scanner scanner, TicTacToeStateEvaluator ticTacToeStateEvaluator, UserInputLoader userInputLoader) {
        this.scanner = scanner;
        this.ticTacToeStateEvaluator = ticTacToeStateEvaluator;
        this.userInputLoader = userInputLoader;
    }

    public Game() {

    }

    public void start(String[] args) {

        String state;
        prepareNewRound(args);
        state = ticTacToeStateEvaluator.getState(board);
        printState(state);

        while (true) {
            playerOne.makeMove();
            state = ticTacToeStateEvaluator.getState(board);
            printState(state);
            if (ticTacToeStateEvaluator.isGameFinished(state)) {
                break;
            }
            playerTwo.makeMove();
            state = ticTacToeStateEvaluator.getState(board);
            printState(state);
            if (ticTacToeStateEvaluator.isGameFinished(state)) {
                break;
            }
        }
    }

    private void prepareNewRound(String[] params) {

        board = prepareEmptyBoard();
        userInputLoader.setBoard(board);

        String playerOneType = params[1];
        String playerTwoType = params[2];

        switch (playerOneType) {
            case "user":
                playerOne = new HumanPlayer('X', board, userInputLoader);
                break;
            case "easy":
                playerOne = new ComputerPlayer('X', board, new EasyDifficulty(board, new Random(957135)));
                break;
            case "medium":
                playerOne = new ComputerPlayer('X', board, new MediumDifficulty(new Random(1564899), board));
                break;
        }
        switch (playerTwoType) {
            case "user":
                playerTwo = new HumanPlayer('O', board, userInputLoader);
                break;
            case "easy":
                playerTwo = new ComputerPlayer('O', board, new EasyDifficulty(board, new Random(427878)));
                break;
            case "medium":
                playerTwo = new ComputerPlayer('O', board, new MediumDifficulty(new Random(7786778), board));
                break;
        }
    }

    private void printState(String state) {
        System.out.println("---------");
        for (char[] boardRow : board) {
            System.out.print("| ");
            for (char boardElement : boardRow) {
                System.out.print(boardElement + " ");
            }
            System.out.print("| \n");
        }
        System.out.println("---------");
        System.out.println(state);
    }

    private char[][] prepareEmptyBoard() {
        char[][] board = new char[3][3];
        for (char[] boardRow : board) {
            Arrays.fill(boardRow, ' ');
        }
        return board;
    }

    public String[] getInputCommand() {

        boolean isCorrect = false;
        String input = null;
        String[] result = null;

        do {
            System.out.println("Input command:");
            input = scanner.nextLine();

            if (input.equals("")) {
                System.out.println("Bad Parameters!");
                continue;
            }
            result = input.split(" ");
            if (result.length != 3) {
                System.out.println("Bad Parameters!");
                continue;
            } else if (!result[0].equals("start")) {
                System.out.println("Bad Parameters!");
                continue;
            }

            switch (result[1]) {
                case "user":
                case "easy":
                    break;
                default:
                    System.out.println("Bad parameters!");
                    continue;
            }

            switch (result[2]) {
                case "user":
                case "easy":
                    break;
                default:
                    System.out.println("Bad parameters!");
                    continue;
            }
            isCorrect = true;
        } while (!isCorrect);
        return result;
    }

    private String getIntialDashboard() {
        System.out.println("Submit data in format: \"_________\"");
        return scanner.nextLine();
    }
}

class TicTacToeStateEvaluator {

    String getState(char[][] board) {

        boolean emptyFields = hasEmptyFields(board);
        boolean isXinRow = isSignInRow(board, 'X');
        boolean isOinRow = isSignInRow(board, 'O');
        int diff = countDifference(board);

        if ((isXinRow && isOinRow) || diff > 1) {
            return "Impossible";
        } else if (emptyFields && !isXinRow && !isOinRow) {
            return "Game not finished";
        } else if (!emptyFields && (!isXinRow && !isOinRow)) {
            return "Draw";
        } else if (isXinRow) {
            return "X wins";
        } else if (isOinRow) {
            return "O wins";
        }
        return " ";
    }

    private boolean hasEmptyFields(char[][] board) {
        for (char[] boardRow : board) {
            for (char boardElement : boardRow) {
                if (boardElement == ' ') {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isSignInRow(char[][] board, char sign) {

        for (char[] boardRow : board) {
            if (boardRow[0] == sign && boardRow[0] == boardRow[1] && boardRow[1] == boardRow[2]) {
                return true;
            }
        }

        for (int j = 0; j < 3; j++) {
            if (board[0][j] == sign && board[0][j] == board[1][j] && board[1][j] == board[2][j]) {
                return true;
            }
        }

        if (board[0][0] == sign && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return true;
        }

        if (board[0][2] == sign && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return true;
        }
        return false;
    }

    private int countDifference(char[][] board) {
        int xCount = 0;
        int oCount = 0;

        for (char[] boardRow : board) {
            for (char boardElement : boardRow) {
                if (boardElement == 'X') {
                    xCount++;
                } else if (boardElement == 'O') {
                    oCount++;
                }
            }
        }
        return Math.abs(xCount - oCount);
    }

    boolean isGameFinished(String state) {
        return state.equals("X wins") || state.equals("O wins") || state.equals("Draw") || state.equals("Impossible");
    }
}

abstract class Player {

    char[][] board;
    private char sign;

    Player(char sign, char[][] board) {
        this.sign = sign;
        this.board = board;
    }

    char getSign() {
        return sign;
    }

    abstract void makeMove();

    abstract void set(int x, int y, char sign);
}

class HumanPlayer extends Player {

    UserInputLoader userInputLoader;

    HumanPlayer(char sign, char[][] board, UserInputLoader userInputLoader) {
        super(sign, board);
        this.userInputLoader = userInputLoader;
    }

    @Override
    void makeMove() {
        int[] coordinates = userInputLoader.getUserCoordinates();
        set(coordinates[0], coordinates[1], getSign());
    }

    @Override
    void set(int x, int y, char sign) {
        board[x][y] = sign;
    }
}

class ComputerPlayer extends Player {

    Difficulty difficulty;

    public ComputerPlayer(char sign, char[][] board, Difficulty difficulty) {
        super(sign, board);
        this.difficulty = difficulty;
    }

    void makeMove() {
        difficulty.makeComputerMove(this);
    }

    @Override
    void set(int x, int y, char sign) {
        board[x][y] = sign;
    }
}

class UserInputLoader {

    Scanner scanner;
    private char[][] board;

    public void setBoard(char[][] board) {
        this.board = board;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    int[] getUserCoordinates() {

        boolean isReady = false;
        String input;
        int x = 0;
        int y = 0;
        do {
            System.out.println("Enter the coordinates:");
            input = scanner.nextLine();
            if (isInputValid(input)) {
                x = Character.getNumericValue(input.charAt(0));
                y = Character.getNumericValue(input.charAt(2));
                int[] coordinatesToCheck = {x, y};
                if (isBoardCellEmpty(coordinatesToCheck)) {
                    isReady = true;
                }
            }
        } while (!isReady);

        int[] coordinatesToConvert = {x, y};
        return convertCoordinates(coordinatesToConvert);
    }

    private int[] convertCoordinates(int[] coordinates) {
        int[] newCoordinates = new int[2];
        newCoordinates[0] = convertToX(coordinates[1]);
        newCoordinates[1] = convertToY(coordinates[0]);
        return newCoordinates;
    }

    private int convertToY(int x) {
        int j = 0;
        switch (x) {
            case 1:
                j = 0;
                break;
            case 2:
                j = 1;
                break;
            case 3:
                j = 2;
                break;
        }
        return j;
    }

    private int convertToX(int y) {
        int i = 0;
        switch (y) {
            case 1:
                i = 2;
                break;
            case 2:
                i = 1;
                break;
            case 3:
                i = 0;
                break;
        }
        return i;
    }

    private boolean isInputValid(String input) {

        String x = String.valueOf(input.charAt(0));
        String y = String.valueOf(input.charAt(2));

        if (isNumeric(x)) {
            System.out.println("You should enter numbers!");
            return false;
        }
        if (isNumeric(y)) {
            System.out.println("You should enter numbers!");
            return false;
        }

        int xNumber = Integer.valueOf(x);
        int yNumber = Integer.valueOf(y);

        if (xNumber < 1 || xNumber > 3) {
            System.out.println("Coordinates should be from 1 to 3!");
            return false;
        }

        if (yNumber < 1 || yNumber > 3) {
            System.out.println("Coordinates should be from 1 to 3!");
            return false;
        }
        return true;
    }

    private boolean isBoardCellEmpty(int[] coordinatesToCheck) {

        int newX = convertToX(coordinatesToCheck[1]);
        int newY = convertToY(coordinatesToCheck[0]);

        if (board[newX][newY] == ' ') {
            return true;
        } else {
            System.out.println("This cell is occupied! Choose another one!");
            return false;
        }
    }

    private boolean isNumeric(String input) {
        try {
            Integer.parseInt(input);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }
}

class EasyDifficulty implements Difficulty {

    char[][] board;
    Random random;

    public EasyDifficulty(char[][] board, Random random) {
        this.board = board;
        this.random = random;
    }

    @Override
    public void makeComputerMove(Player player) {
        System.out.println("Making move level \"easy\"");
        boolean isMoved = false;
        int i;
        int j;
        do {
            j = random.nextInt(3);
            i = random.nextInt(3);

            if (board[i][j] == ' ') {
                player.set(i, j, player.getSign());
                isMoved = true;
            }
        } while (!isMoved);
    }
}

class MediumDifficulty implements Difficulty {

    Random random;
    char[][] board;

    public MediumDifficulty(Random random, char[][] board) {
        this.random = random;
        this.board = board;
    }



    @Override
    public void makeComputerMove(Player player) {

        RowAnalyzer rowAnalyzer;
        char sign = player.getSign();
        char oppositeSign = sign == 'X' ? 'O' : 'X';

        for(int x = 0; x < 3; x++){
            rowAnalyzer = RowAnalyzer.row(board, x);
            if(requestLast(rowAnalyzer,player, sign, oppositeSign)){
                return;
            }

        }
        for (int y = 0; y < 3; y++){
            rowAnalyzer = RowAnalyzer.col(board, y);
            if(requestLast(rowAnalyzer,player, sign, oppositeSign)){
                return;
            }
        }

        rowAnalyzer = RowAnalyzer.diagonalLeftTop(board);
        if (requestLast(rowAnalyzer, player, sign, oppositeSign)){
            return;
        }

        rowAnalyzer = RowAnalyzer.diagonalRightTop(board);
        if (requestLast(rowAnalyzer, player, sign, oppositeSign)){
            return;
        }

        boolean isMoved = false;
        int i;
        int j;
        do {
            i = random.nextInt(3);
            j = random.nextInt(3);

            if (board[i][j] == ' ') {
                player.set(i, j, player.getSign());
                isMoved = true;
            }
        } while (!isMoved);
        return;
    }



    private boolean requestLast(RowAnalyzer rowAnalyzer, Player player, char sign, char oppositeSign) {
        int[] coordinates = rowAnalyzer.getOneLeftOrNull(sign);
        if (coordinates != null) {
            player.set(coordinates[0], coordinates[1], sign);
            return true;
        }
        coordinates = rowAnalyzer.getOneLeftOrNull(oppositeSign);
        if (coordinates != null) {
            player.set(coordinates[0], coordinates[1], sign);
            return true;
        }
        return false;
    }
}

class RowAnalyzer {

    static final String TYPE_COL = "col";
    static final String TYPE_ROW = "row";
    static final String TYPE_DIAG_TOP_LEFT = "diagtopleft";
    static final String TYPE_DIAG_TOP_RIGHT = "diagtopright";
    char[] row;
    String type;
    int matrixRowPointer;

    RowAnalyzer(char[] row, String type, int matrixRowPointer) {
        this.row = row;
        this.type = type;
        this.matrixRowPointer = matrixRowPointer;
    }

    public RowAnalyzer(char[] row, String type) {
        this.row = row;
        this.type = type;
    }

    static public RowAnalyzer col(char[][] board, int x) {

        char[] row = new char[board.length];
        for (int y = 0; y < board.length; y++) {
            row[y] = board[x][y];
        }
        return new RowAnalyzer(row, TYPE_COL, x);
    }

    static public RowAnalyzer row(char[][] board, int y) {
        return new RowAnalyzer(board[y], TYPE_ROW, y);
    }

    static public RowAnalyzer diagonalLeftTop(char[][] board) {
        char[] row = new char[board.length];
        for (int x = 0; x < board.length; x++) {
            row[x] = board[x][x];
        }
        return new RowAnalyzer(row, TYPE_DIAG_TOP_LEFT);
    }

    static public RowAnalyzer diagonalRightTop(char[][] board) {
        char[] row = new char[board.length];
        for (int x = board.length - 1; x >= 0; x--) {
            row[x] = board[x][board.length - x - 1];
        }
        return new RowAnalyzer(row, TYPE_DIAG_TOP_RIGHT);
    }

    public int[] getOneLeftOrNull(char symbol) {
        int symbolCounter = 0;
        int emptyCounter = 0;
        int[] position = new int[2];
        for (int i = 0; i < row.length; i++) {
            if (row[i] == symbol) {
                symbolCounter++;
            } else if (row[i] == ' ') {
                emptyCounter++;
                switch (type) {
                    case TYPE_COL:
                        position[0] = i;
                        position[1] = matrixRowPointer;
                        break;
                    case TYPE_ROW:
                        position[0] = matrixRowPointer;
                        position[1] = i;
                        break;
                    case TYPE_DIAG_TOP_LEFT:
                        position[0] = i;
                        position[1] = position[0];
                        break;
                    case TYPE_DIAG_TOP_RIGHT:
                        position[0] = i;
                        position[1] = row.length - i - 1;
                }
            }
        }
        if ((row.length - symbolCounter) == 1 && emptyCounter == 1) {
            return position;
        }
        return null;
    }
}




