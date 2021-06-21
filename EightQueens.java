/**
 * https://en.wikipedia.org/wiki/Eight_queens_puzzle
 * @author akiva
 */

public class EightQueens {
    final int BOARD_LENGTH = 8;
    final int TOTAL_QUEENS = BOARD_LENGTH;

    private boolean[][] board;
    private int solutionsFound;
    
    public EightQueens() {
        board = new boolean[BOARD_LENGTH][BOARD_LENGTH];
        solutionsFound = 0;
        solve(0, 0, TOTAL_QUEENS);
    }
    
    private void solve (int i, int j, int queensLeft) {
        if (queensLeft == 0) {
            solutionsFound++;
            print();
            return;
        }
        if (j == BOARD_LENGTH) {
            // next row
            j = 0;
            i++;
        }
        if (i < BOARD_LENGTH) {
            if (isAvailable(i, j)) {
                board[i][j] = true;
                solve(i+1, 0, queensLeft-1); // Search from the next row, as this one is already taken.
            }
            // skip this cell
            board[i][j] = false;
            solve(i, j+1, queensLeft);
        }
    }
    
    private boolean isAvailable (int i, int j) {
        // Down and right is always clear (available)
        for (int i1 = 0; i1 < i; i1++) // up
            if (board[i1][j])
                return false;
        
        for (int j1 = 0; j1 < j; j1++) // left
            if (board[i][j1])
                return false;
        
        // Diagonals
        for (int upLeft = 1; i-upLeft >= 0 && j-upLeft >= 0; upLeft++)
            if (board[i-upLeft][j-upLeft])
                return false;
        for (int upRight = 1; i-upRight >= 0 && j+upRight < BOARD_LENGTH; upRight++)
            if (board[i-upRight][j+upRight])
                return false;
        return true;
    }
    
    private void print() {
        System.out.println("***************************************************");
        System.out.println("Solution number " + solutionsFound);
        for (int i = 0; i < BOARD_LENGTH; i++) {
            for (int j = 0; j < BOARD_LENGTH; j++) {
                System.out.print(board[i][j] ? "*" : "-");
                if (j != BOARD_LENGTH-1)
                    System.out.print(",");
            }
            System.out.println();
        }
    }
}
