/**
 * https://en.wikipedia.org/wiki/Sudoku
 * 
 * There are 2 implementations for this problem below: pure recursion, and mostly loops.
 * You can use whatever you like.
 * 
 * @author akiva
 */

// Read sudokus from file:
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Sudoku {
    private int[][] board;
    final int root;

    public Sudoku() {
        int[][] board = {
                {5,3,0,0,7,0,0,0,0},
                {6,0,0,1,9,5,0,0,0},
                {0,9,8,0,0,0,0,6,0},
                {8,0,0,0,6,0,0,0,3},
                {4,0,0,8,0,3,0,0,1},
                {7,0,0,0,2,0,0,0,6},
                {0,6,0,0,0,0,2,8,0},
                {0,0,0,4,1,9,0,0,5},
                {0,0,0,0,8,0,0,7,9}};

        this.board = board;
        root = (int) Math.sqrt(board.length);
        go();
    }

    public Sudoku(int[][] board) {
        this.board = board;
        root = (int) Math.sqrt(board.length);
        go();
    }

    public Sudoku (String str) {
        int length = (int) Math.sqrt(str.length());
        root = (int) Math.sqrt(length);
        board = new int[length][length];
        for (int i = 0; i < str.length(); i++)
            board[i/board.length][i%board.length] = (str.charAt(i) == '.' ? 0 : (str.charAt(i) - '0'));
        go();
    }

    private void go() {
        System.out.println("\nBefore:");
        print();
        System.out.println("\nAfter:");
        // solveWithLoops();
        solveWithRecursion(0,0,1);
        print();
    }

    private boolean solveWithLoops() {
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board.length; j++)
                if (board[i][j] == 0) {
                    for (int num = 1; num <= board.length; num++)
                        if (validLoops(i, j, num)) {
                            board[i][j] = num;
                            if (solveWithLoops())
                                return true;
                            board[i][j] = 0;
                        }
                    return false;
                }
        return true;
    }

    private boolean validLoops (int i, int j, int num) {
        for (int i1 = 0; i1 < board.length; i1++) // Check column
            if (board[i1][j] == num)
                return false;

        for (int j1 = 0; j1 < board.length; j1++) // Check row
            if (board[i][j1] == num)
                return false;

        // subbox
        int additionI = i/root * root, additionJ = j/root * root;
        for (int i1 = 0; i1 < root; i1++)
            for (int j1 = 0; j1 < root; j1++)
                if (board[i1 + additionI][j1 + additionJ] == num)
                    return false;

        return true;
    }

    
    private boolean solveWithRecursion(int i, int j, int num) {
        if (j == board.length) {
            j = 0;
            i++;
        }
        if (i == board.length)
            return true;
        if (num == board.length+1)
            return false;

        if (board[i][j] != 0)
            return solveWithRecursion(i, j+1, 1);

        if (validRecursion(i, j, num)) {
            board[i][j] = num;    
            if (solveWithRecursion(0, 0, 1)) 
                return true;
            board[i][j] = 0;
        }
        return solveWithRecursion(i, j, num+1);
    }

    private boolean validRecursion (int i, int j, int num) {
        int additionI = i/root * root, additionJ = j/root * root;
        return validCol(0,j,num) && validRow(i,0,num) && validBox(additionI, 0, additionJ, 0, num);

    }

    private boolean validRow (int i, int j, int num) {
        if (j == board.length)
            return true;
        if (board[i][j] == num)
            return false;
        return validRow(i, j+1, num);
    }

    private boolean validCol (int i, int j, int num) {
        if (i == board.length)
            return true;
        if (board[i][j] == num)
            return false;
        return validCol(i+1, j, num);
    }

    private boolean validBox (int i, int additionI, int j, int additionJ, int num) {
        if (additionJ == root) {
            additionJ = 0;
            additionI++;
        }
        if (additionI == root)
            return true;
        if (board[i+additionI][j+additionJ] == num)
            return false;
        return validBox(i, additionI, j, additionJ+1, num);
    }

    private void print() {
        int len = board.length;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                System.out.print(board[i][j] + " ");
                if ((j+1) % root == 0 && j != len-1)
                    System.out.print("| ");
            }
            System.out.println();
            if ((i+1) % root == 0 && i != len-1) {
                for (int j = 0; j < board.length+2; j++)
                    System.out.print("- ");
                System.out.println();
            }

        }
    }

    public static void main(String[] args) {
        try {
            File myObj = new File("random100.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine())
                new Sudoku(myReader.nextLine());
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
}
