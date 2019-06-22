package tictactoe;

import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static TictactoeValidator tictactoeValidator = new TictactoeValidator();

    public static void main(String[] args) {

        System.out.println("Submit data in format: \"_________\"");
        String input = scanner.nextLine();

        char[][] board = prepareBoard(input);
        String state = tictactoeValidator.validate(board);
        printState(board, state);
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

    private static void printState(char[][] board, String state) {

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
}

class TictactoeValidator {

    public TictactoeValidator() {
    }

    public String validate(char[][] board) {

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
        } else {
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

        for (int j = 0; j < 3; j++){
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
                } else {

                }
            }
        }
        return Math.abs(xCount - oCount);
    }
}



