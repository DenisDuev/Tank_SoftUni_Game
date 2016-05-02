package output;

import constants.Constants;

import java.io.Serializable;

public class MapLevel implements Serializable {
    private String name;
    private int[][] matrix;
    private int firstPlayerCol;
    private int firstPlayerRow;
    private int secondPlayerCol;
    private int secondPlayerRow;
    private int entryPointRow;
    private int entryPointCol;

    public MapLevel(int[][] givenMatrix, String name) {
        this.matrix = new int[Constants.MATRIX_ROWS][Constants.MATRIX_COLS];
        this.name = name;
        this.entryPointRow = -1;
        this.entryPointCol = -1;
        fillLevelMatrix(givenMatrix);
        markFreeSells();
        findEntryPoints();
    }

    public String getName() {
        return name;
    }

    public int getEntryPointRow() {
        return entryPointRow;
    }

    public int getEntryPointCol() {
        return entryPointCol;
    }

    public int getFirstPlayerCol() {
        return firstPlayerCol;
    }

    public int getFirstPlayerRow() {
        return firstPlayerRow;
    }

    public int getSecondPlayerCol() {
        return secondPlayerCol;
    }

    public int getSecondPlayerRow() {
        return secondPlayerRow;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    private void markFreeSells() {
        boolean firstPlayerFound = false;
        boolean secondPlayerFound = false;
        for (int row = Constants.MATRIX_ROWS - 1; row >= 0; row--) {
            for (int index1 = Constants.MATRIX_COLS / 2 - 2, index2 = Constants.MATRIX_COLS / 2 + 1; index1 >= 0; index1--, index2++) {
                if (!firstPlayerFound && matrix[row][index1] == 0) {
                    firstPlayerFound = true;
                    this.firstPlayerCol = index1;
                    this.firstPlayerRow = row;
                }
                if (!secondPlayerFound && matrix[row][index2] == 0) {
                    secondPlayerFound = true;
                    this.secondPlayerCol = index2;
                    this.secondPlayerRow = row;
                }
            }
            if (firstPlayerFound && secondPlayerFound) {
                break;
            }
        }
    }

    private void fillLevelMatrix(int[][] givenMatrix) {
        for (int row = 0; row < Constants.MATRIX_ROWS; row++) {
            for (int col = 0; col < Constants.MATRIX_COLS; col++) {
                switch (givenMatrix[row][col]) {
                    case Constants.WALL_ORDINARY:
                        //ordinary
                        this.matrix[row][col] = Constants.WALL_ORDINARY_INDEX;
                        break;
                    case Constants.WALL_METAL:
                        this.matrix[row][col] = Constants.WALL_METAL_INDEX;
                        break;
                    case Constants.WALL_WATER:
                        this.matrix[row][col] = Constants.WALL_WATER_INDEX;
                        break;
                    case Constants.WALL_GREEN:
                        this.matrix[row][col] = Constants.WALL_GREEN_INDEX;
                        break;
                }
            }
        }
    }

    private void findEntryPoints() {
        for (int row = 0; row < Constants.MATRIX_ROWS; row++) {
            for (int col = 0; col < Constants.MATRIX_COLS; col++) {
                if (this.matrix[row][col] == 0) {
                    this.entryPointRow = row;
                    this.entryPointCol = col;
                    break;
                }
            }
            if (entryPointCol != -1){
                break;
            }
        }
    }
}
