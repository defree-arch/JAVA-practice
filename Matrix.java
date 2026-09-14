import java.util.Scanner;

public class Matrix {
    
    //fields of class
    private final int rows;
    private final int cols;
    private final int[][] nums;

    //constructor of matrix
    public Matrix (int rows, int cols) {
        if(rows <= 0 || cols <= 0)
            throw new IllegalArgumentException("Invalid data size", null);
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
    public Matrix sum(Matrix B) {
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
    public Matrix multiply(Matrix B) {
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

    //transposition matrix
    public Matrix t() {
        Matrix result = new Matrix(cols, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                result.set(j, i, this.get(i, j));
            }
        }
        return result;
    }

    //determinant
    public double determinant() {
        if (cols != rows) 
            throw new IllegalArgumentException("Invalid size.", null);
        int n = rows; 

        double matrix[][] = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = this.get(i, j);
            }
        }
        double result = 1;

        for (int k = 0; k < n; k++) {
            if (matrix[k][k] == 0) {
                int swap_row = -1;
                for (int i = k + 1; i < n; i++) {
                    if (matrix[i][k] != 0) {
                        swap_row = i;
                        break;
                    }
                }
                if (swap_row == -1) return 0;
                
                //swaping rows
                double[] temp = matrix[k];
                matrix[k] = matrix[swap_row];
                matrix[swap_row] = temp;
                result = -result;
                //
            }
        //changing to 0 
        for (int i = k + 1; i < n; i++) {
            double multiplier = matrix[i][k] / matrix[k][k];
            for (int j = 0; j < n; j++) {
                matrix[i][j] -= multiplier * matrix[k][j];
            }
        }
        //
        result *= matrix[k][k];
        }
        return result;
    }

    //inverse matrix
    public double[][] inverse() {
        if (rows != cols)
            throw new IllegalArgumentException("Invalid size.", null);
        if (this.determinant() == 0) 
            throw new IllegalArgumentException("Invalid determinant (inverse is not able).", null);

        int n = rows;

        //advanced matrix = this matrix + E
        double[][] matrix_E = new double[n][n * 2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix_E[i][j] = this.get(i, j);
                matrix_E[i][n + j] = (i == j) ? 1.0: 0.0;
            }
        }

        //Gauss-Jordan method
        for (int k = 0; k < n; k++) {
            if (matrix_E[k][k] == 0) {
                int swap_row = -1;
                for(int i = k + 1; i < n; i++) {
                    if (matrix_E[i][k] != 0) {
                        swap_row = i;
                        break;
                    }
                }
            
                if (swap_row == -1)
                    throw new IllegalStateException("Matrix is singular.");
                
                //swaping
                double[]temp = matrix_E[k];
                matrix_E[k] = matrix_E[swap_row];
                matrix_E[swap_row] = temp;
                //
            }

            double pivot = matrix_E[k][k];

            for (int j = 0; j < n * 2; j++) {
                matrix_E[k][j] /= pivot;
            }

            //change to 0 (up/down)
            for (int i = 0; i < n; i++) {
                if (i != k) {
                    double multiplier = matrix_E[i][k];
                    for (int j = 0; j < n * 2; j++) {
                        matrix_E[i][j] -= multiplier * matrix_E[k][j];

                    }
                }
            }
        }
        double[][] result = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = matrix_E[i][n + j];
            }
        }
        return result;
    }

    //division
    public Matrix division(Matrix B) {
        if (this.cols != B.rows)
            throw new IllegalArgumentException("Incompatible sizes for division.");

        double[][] B_inverse = B.inverse();

        Matrix Matrix_B_inverse = new Matrix(B.rows, B.cols);
        for (int i = 0; i < B.rows; i++) {
            for (int j = 0; j < B.cols; j++) {
                Matrix_B_inverse.set(i, j, (int) Math.round(B_inverse[i][j]));
            }
        }
        return this.multiply(Matrix_B_inverse);
    }

    //clearning output
    private static void clearConsole() {
    String os = System.getProperty("os.name");
    if (os.contains("Windows")) {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
        }
    } else {
        try {
            new ProcessBuilder("/bin/sh", "-c", "clear").inheritIO().start().waitFor();
        } catch (Exception e) {
        }
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

        clearConsole();
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

        clearConsole();
        //output
        System.out.println("Matrix 'A': ");
        A.print_matrix();

        System.out.println("Matrix 'B': ");
        B.print_matrix();
        
        System.out.println("Enter the operation from this list: +, *, /, t (transposition), d (determinant)");
        String operation = input.next();
        
        clearConsole();
        System.out.println("Result: ");
        //add
        if (operation.equals("+")) {
            Matrix result = A.sum(B);
            result.print_matrix();
        }

        //multiply
        if (operation.equals("*")) {
            Matrix result = A.multiply(B);
            result.print_matrix();
        }

        if (operation.equals("/")) {
            Matrix result = A.division(B);
            result.print_matrix();
        }

        //transposition
        if (operation.equals("t")) {
            System.out.println("Matrix is A or B (enter a high letter): ");
            String matrix = input.next();
           if (matrix.equals("A")) {
                Matrix result = A.t();
                result.print_matrix();
           }
           else {
                Matrix result = B.t();
                result.print_matrix(); 
           }
        }

        //determinant
        if (operation.equals("d")) {
           System.out.println("Matrix is A or B (enter a high letter): ");
           String matrix = input.next();
           if (matrix.equals("A")) {
                double result = A.determinant();
                System.out.print("The determinant of matrix A: " + result);
           }
           else {
                double result = B.determinant();
                System.out.print("The determinant of matrix B: " + result);

           }
        }
        input.close();
        }
    }