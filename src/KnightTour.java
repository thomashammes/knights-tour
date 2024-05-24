import java.util.*;
import java.util.concurrent.TimeUnit;

public class KnightTour {

    private static final int SIZE = 8;
    private static final int TOTAL_SIZE = SIZE * SIZE;
    private static final int[] MOVE_X = {1, 2, 2, 1, -1, -2, -2, -1};
    private static final int[] MOVE_Y = {-2, -1, 1, 2, 2, 1, -1, -2};

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        String startingPosition;

        while (true) {
            System.out.println();

            printEmptyBoard();

            System.out.print("Posicao inicial do cavalo (linha, coluna): ");
            startingPosition = scanner.nextLine();

            if (validateInput(startingPosition)) {
                break;
            } else {
                System.out.println("Posicao invalida.");
                TimeUnit.SECONDS.sleep(1);
            }
        }

        System.out.println();

        String[] parts = startingPosition.split(",");
        int startX = Integer.parseInt(parts[0]);
        int startY = Integer.parseInt(parts[1]);
        int[][] board = new int[SIZE][SIZE];

        warnsdorffAlgorithm(startX, startY, board);
    }

    private static boolean validateInput(String p) {
        try {
            String[] parts = p.split(",");
            int row = Integer.parseInt(parts[0]);
            int col = Integer.parseInt(parts[1]);

            if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
                throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private static void printEmptyBoard() {
        int j = 0;

        for (int i = 0; i < 9; i++) {
            if (i != 0) {
                System.out.println(j + "   |   |   |   |   |   |   |   |   |");
                j++;
            } else {
                System.out.println("     0   1   2   3   4   5   6   7\n");
            }
        }
        System.out.println("\n");
    }

    private static void printBoard(int[][] board) {
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 1) {
                    System.out.printf("%4d", board[i][j]);
                } else if (board[i][j] == TOTAL_SIZE) {
                    System.out.printf("%4d", board[i][j]);
                } else {
                    System.out.printf("%4d", board[i][j]);
                }
            }
            System.out.println();
        }
    }

    private static String getProgress(int[][] board) {
        int visitedCell = 0;
        int totalCell = TOTAL_SIZE;

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] != 0) {
                    visitedCell++;
                }
            }
        }

        return visitedCell + " / " + totalCell;
    }

    private static boolean isSafe(int x, int y, int[][] board) {
        return x >= 0 && x < SIZE && y >= 0 && y < SIZE && board[x][y] == 0;
    }

    private static int getDegree(int x, int y, int[][] board) {
        int c = 0;
        for (int i = 0; i < SIZE; i++) {
            if (isSafe(x + MOVE_X[i], y + MOVE_Y[i], board)) {
                c++;
            }
        }
        return c;
    }

    private static void warnsdorffAlgorithm(int x, int y, int[][] board) throws InterruptedException {
        int moveNumber = 1;
        board[x][y] = moveNumber;

        for (int step = 0; step < TOTAL_SIZE; step++) {
            PriorityQueue<int[]> accessiblePositions = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));

            for (int i = 0; i < 8; i++) {
                int newX = x + MOVE_X[i];
                int newY = y + MOVE_Y[i];

                if (isSafe(newX, newY, board)) {
                    int degree = getDegree(newX, newY, board);
                    accessiblePositions.add(new int[]{degree, i});
                }
            }

            if (!accessiblePositions.isEmpty()) {
                int[] minPosition = accessiblePositions.poll();
                int minIndex = minPosition[1];
                x += MOVE_X[minIndex];
                y += MOVE_Y[minIndex];
                moveNumber++;
                board[x][y] = moveNumber;

                System.out.println("Casas viajadas: " + getProgress(board));
                printBoard(board);

                if (moveNumber != TOTAL_SIZE) {
                    TimeUnit.MILLISECONDS.sleep(500);
                    System.out.println();
                }
            }
        }
    }
}


