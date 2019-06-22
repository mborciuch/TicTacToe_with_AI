package tictactoe;

public class Main {
    public static void main(String[] args) {
        char[][] tictactoeArray = new char[3][3];
        tictactoeArray[0][0] = 'X';
        tictactoeArray[0][1] = 'X';
        tictactoeArray[0][2] = 'X';
        tictactoeArray[1][0] = 'X';
        tictactoeArray[1][1] = 'X';
        tictactoeArray[1][2] = 'X';
        tictactoeArray[2][0] = 'X';
        tictactoeArray[2][1] = 'X';
        tictactoeArray[2][2] = 'X';

        for(char tictacRow[] :  tictactoeArray){
            for (char tictacElement : tictacRow){
                System.out.print(tictacElement);
            }
            System.out.println();
        }

    }
}
