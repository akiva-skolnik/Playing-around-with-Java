/**
 * https://en.wikipedia.org/wiki/Knight's_tour
 * @author akiva
 */

public class KnightTour {
    final int ROWS = 5;
    final int COLS = ROWS;
    
    private int[][] board;
    private int solutionsFound;

    public KnightTour() {
        this(0, 0);
    }
    
    // Different starting position sometimes leads to different number of solutions.
    public KnightTour(int startingRow, int startingCol) {
        board = new int[ROWS][COLS];
        solutionsFound = 0;
        solve(startingRow, startingCol, 1);
        System.out.println("Done");
    }
        
    private void solve (int i, int j, int num) {
        if (i >= ROWS || i < 0 || j >= COLS || j < 0 || board[i][j] != 0)
            return;
        board[i][j] = num;
        if (num == ROWS * COLS) {
            solutionsFound++;
            print();
        }

        // Try in all the possible knight directions.
        solve(i-1, j-2, num+1);
        solve(i-2, j-1, num+1);
        solve(i-2, j+1, num+1);
        solve(i-1, j+2, num+1);
        solve(i+1, j+2, num+1);
        solve(i+2, j+1, num+1);
        solve(i+2, j-1, num+1);
        solve(i+1, j-2, num+1);

        board[i][j] = 0;
    }

    private void print() {
        System.out.println("***************************************************");
        System.out.println("Solution number " + solutionsFound);
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++)
                System.out.print(board[i][j] + "\t");
            System.out.println();
        }
    }
}
