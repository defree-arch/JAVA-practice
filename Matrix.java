import java.util.Scanner;

public class Matrix {
    
    //fields of class
    private final int rows;
    private final int cols;
    private final int[][] nums;

    //constructor of matrix
    public Matrix (int rows, int cols) {
        if(rows <= 0 || cols <= 0)
            throw new IllegalArgumentException("Unavaliable date's size", null);
        this.rows = rows;
        this.cols = cols;
        this.nums = new int[rows][cols];
    }

    //set nums
    public void set(int i, int j, int value) {
        nums[i][j] = value;
    }

    //output
    public void print_matrix() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(nums[i][j] + " ");
            }
            System.out.println();
        }
    }

    //test
    public static void main (String[] args) {
        Scanner input = new Scanner(System.in);

        //matrix A
        System.out.println("Enter the quantity of ROWS for matrix 'A': ");
        int rows_a = input.nextInt();

        System.out.println("Enter the quantity of COLUMNS for matrix 'A': ");
        int cols_a = input.nextInt();

        Matrix A = new Matrix(rows_a, cols_a);
        
        System.out.println("Matrix (example) :\n 1 2 \n 3 4 \n Enter: 1 2 3 4");
        System.out.println("For enter data seen to example:");
        System.out.println("Enter a data for matrix (using Space between elements): ");

        for (int i = 0; i < rows_a; i++) {
            for (int j = 0; j < cols_a; j++)
            {
                A.set(i, j, input.nextInt());
            }
        }
        //matrix B
        System.out.println("Enter the quantity of ROWS for matrix 'B': ");
        int rows_b = input.nextInt();

        System.out.println("Enter the quantity of COLUMNS for matrix 'B': ");
        int cols_b = input.nextInt();

        System.out.println("Matrix (example) :\n 1 2 \n 3 4 \n Enter: 1 2 3 4");
        System.out.println("For enter data seen to example:");
        System.out.println("Enter a data for matrix (using Space between elements): ");

        Matrix B = new Matrix(rows_b, cols_b);
        for (int i = 0; i < rows_b; i++) {
            for (int j = 0; j < cols_b; j++)
            {
                B.set(i, j, input.nextInt());
            }
        }
        input.close();
        //output
        System.out.println("Matrix 'A': ");
        A.print_matrix();

        System.out.println("Matrix 'B': ");
        B.print_matrix();

        }
}