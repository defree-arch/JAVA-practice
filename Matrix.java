import java.util.Scanner;

public class Matrix {
    
    //fields of class
    private final int rows;
    private final int cols;
    private final int[][] nums;

    //constructor of matrix
    public Matrix (int rows, int cols) {
        if(rows <= 0 || cols <= 0)
            throw new IllegalArgumentException("Invalid date size", null);
        this.rows = rows;
        this.cols = cols;
        this.nums = new int[rows][cols];
    }

    //set nums
    public void set(int i, int j, int value) {
        nums[i][j] = value;
    }
    //get nums
    public int get(int i, int j) {
        return nums[i][j];
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

    //sum
    public Matrix sum_matrix(Matrix B) {
        if (rows != B.rows || cols != B.cols) 
            throw new IllegalArgumentException("Matrixs have different sizes.", null);
        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.set(i, j, this.get(i, j) + B.get(i, j));
            }
        }
        return result;
    }

    //multiply matrixes
    public Matrix multiply_matrix(Matrix B) {
        if (cols != B.rows) 
            throw new IllegalArgumentException("Invalid sizes.", null);
        Matrix result = new Matrix(rows, B.cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < B.cols; j++) {
                int sum = 0;
                for (int k = 0; k < cols; k++) {
                    sum += this.get(i, k) * B.get(k, j);
                }
                result.set(i, j, sum);
            }
        }
        return result;
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
        
        //output
        System.out.println("Matrix 'A': ");
        A.print_matrix();

        System.out.println("Matrix 'B': ");
        B.print_matrix();
        
        System.out.println("Enter the operation from this list: +, *, T, /");
        String operation = input.next();
        if (operation.equals("+")) {
            Matrix result = A.sum_matrix(B);
            result.print_matrix();
        }

        if (operation.equals("*")) {
            Matrix result = A.multiply_matrix(B);
            result.print_matrix();
        }
        input.close();
        }
    }