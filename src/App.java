import java.io.File;
import java.util.Scanner;

public class App {

    // Create a function called is_valid that takes grid, r, c and k as arguments
    // and returns a boolean value. This function will return true if the number k
    // can be placed at grid[r][c] without violating the rules, otherwise it will
    // return false.
    public static boolean is_valid(int[][] grid, int r, int c, int k) {
        // Check if the number k is already present in the row r
        for (int i = 0; i < 9; i++) {
            if (grid[r][i] == k) {
                return false;
            }
        }

        // Check if the number k is already present in the column c
        for (int i = 0; i < 9; i++) {
            if (grid[i][c] == k) {
                return false;
            }
        }

        // Check if the number k is already present in the 3x3 subgrid
        int start_row = r - r % 3;
        int start_col = c - c % 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[i + start_row][j + start_col] == k) {
                    return false;
                }
            }
        }

        return true;
    }

    // Print grid
    public static void print_grid(int[][] grid) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                System.out.print(grid[r][c] + " ");
            }
            System.out.println();
        }
    }

    // Create a function called solve that takes grid, r and c as arguments and
    // returns a boolean value. This function will solve the Sudoku puzzle using
    // backtracking and return true if the puzzle is solved, otherwise it will
    // return false.
    public static boolean solve(int[][] grid, int r, int c) {
        // If we have reached the last cell in the grid, return true
        if (r == 9) {
            return true;
        }

        // If we have reached the last column, move to the next row
        else if (c == 9) {
            return solve(grid, r + 1, 0);
        }

        // If the current cell is not empty, move to the next cell
        else if (grid[r][c] != 0) {
            return solve(grid, r, c + 1);
        }

        // Try placing the numbers 1 to 9 in the current cell
        else {
            for (int k = 1; k <= 9; k++) {
                if (is_valid(grid, r, c, k)) {
                    grid[r][c] = k;

                    if (solve(grid, r, c + 1)) {
                        return true;
                    }

                    grid[r][c] = 0;
                }
            }
            return false;
        }
    }

    public static void main(String[] args) throws Exception {

        String filePath = "src\\p096_sudoku.txt";

        File file = new File(filePath);

        Scanner fileScanner = new Scanner(file);

        int[][] grid = new int[9][9];

        while (fileScanner.hasNextLine()){
            String row = fileScanner.nextLine();
            if(row.contains("Grid")){
                System.out.println(row);
                for (int r = 0; r < 9; r++) {
                    row = fileScanner.nextLine();
                    String[] rowArray = row.split("");
                    for (int c = 0; c < 9; c++) {
                        grid[r][c] = Integer.parseInt(rowArray[c]);
                    }
                }
            }
            solve(grid, 0, 0);
            print_grid(grid);
        }
        
        fileScanner.close();
    }
}
