package tictactoe;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static TictactoeValidator tictactoeValidator = new TictactoeValidator();
    private static char[][] board;
    private static Random random = new Random(1);

    public static void main(String[] args) {

        boolean isFinished = false;
        String state;
        board = prepareEmptyBoard();
        state = tictactoeValidator.validate(board);
        printState(state);
        do {
            makeUserMove();
            state = tictactoeValidator.validate(board);
            printState(state);
            if(state == "X wins" || state == "O wins" || state == "Draw"){
                isFinished = true;
                break;
            }
            makeComputerMove();
            state = tictactoeValidator.validate(board);
            printState(state);
            if(state == "X wins" || state == "O wins" || state == "Draw"){
                isFinished = true;
                break;
            }
        } while (!isFinished);
    }

    private static void makeUserMove() {
        int[] userInput = getUserCoordinates();
        updateBoard(userInput);
    }

    private static String getIntialDashboard() {
        System.out.println("Submit data in format: \"_________\"");
        return scanner.nextLine();
    }
    private static char[][] prepareEmptyBoard(){
        char[][] board = new char[3][3];
        for (char[] boardRow : board){
            Arrays.fill(boardRow, ' ');
        }
        return board;
    }
    private static char[][] prepareBoard(String input) {
        char[][] board = new char[3][3];

        String inputData = input.substring(1, input.length() - 1);

        int resultFromDivision;
        int restFromDivision;

        for (int i = 0; i < inputData.length(); i++) {
            resultFromDivision = i / 3;
            restFromDivision = i % 3;
            board[resultFromDivision][restFromDivision] = inputData.charAt(i);
        }
        return board;
    }

    private static void printState(String state) {
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

    private static int[] getUserCoordinates() {

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
        int[] result = convertCoordinates(coordinatesToConvert);
        return result;
    }

    private static void makeComputerMove() {
        System.out.println("Making move level \"easy\"");
        boolean isMoved = false;
        int i;
        int j;
        do {
            i = random.nextInt( 3);
            j = random.nextInt(3);

            if (board[i][j] == ' ') {
                board[i][j] = 'O';
                isMoved = true;
            }
        } while (!isMoved);
    }

    private static int[] convertCoordinates(int[] coordinates) {
        int[] newCoordinates = new int[2];
        newCoordinates[0] = convertY(coordinates[1]);
        newCoordinates[1] = convertX(coordinates[0]);
        return newCoordinates;
    }

    private static void updateBoard(int[] coordinates) {
        board[coordinates[0]][coordinates[1]] = 'X';
    }

    private static int convertX(int x) {
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

    private static int convertY(int y) {
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

    private static boolean isInputValid(String input) {

        String x = String.valueOf(input.charAt(0));
        String y = String.valueOf(input.charAt(2));

        if (!isNumeric(x)) {
            System.out.println("You should enter numbers!");
            return false;
        }
        if (!isNumeric(y)) {
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

    private static boolean isBoardCellEmpty(int[] coordinatesToCheck) {

        int newX = convertY(coordinatesToCheck[1]);
        int newY = convertX(coordinatesToCheck[0]);

        if (board[newX][newY] == ' ') {
            return true;
        } else {
            System.out.println("This cell is occupied! Choose another one!");
            return false;
        }
    }

    private static boolean isNumeric(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

class TictactoeValidator {
    String validate(char[][] board) {

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
}



