package tictactoe;

import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("Submit data in format: \"_________\"");
        String input = scanner.nextLine();

        char[][] board = prepareBoard(input);
        printState(board);
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

    private static void printState(char[][] board) {

        System.out.println("---------");
        for (char[] boardRow : board) {
            System.out.print("| ");
            for (char boardElement : boardRow) {
                System.out.print(boardElement + " ");
            }
            System.out.print("| \n");
        }
        System.out.println("---------");
    }
}
