import java.util.Scanner;

public class Matrix {
    
    //fields of class
    private final int rows;
    private final int cols;
    private final Complex[][] nums;

    //constructor of matrix
    public Matrix (int rows, int cols) {
        if(rows <= 0 || cols <= 0)
            throw new IllegalArgumentException("Invalid data size", null);
        this.rows = rows;
        this.cols = cols;
        this.nums = new Complex[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                nums[i][j] = new Complex(0.0, 0.0);
            }
        }
    }

    //set nums
    public void set(int i, int j, Complex value) {
        nums[i][j] = value;
    }
    //get nums
    public Complex get(int i, int j) {
        return nums[i][j];
    }

    //output
    public void print_matrix() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print((nums[i][j]).to_string() + " ");
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
                result.set(i, j, this.get(i, j).add(B.get(i, j)));
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
                Complex sum = new Complex(0.0, 0.0);
                for (int k = 0; k < cols; k++) {
                    sum =  sum.add(this.get(i, k).multiply(B.get(k, j)));
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
    public Complex determinant() {
        if (cols != rows) 
            throw new IllegalArgumentException("Invalid size.", null);
        int n = rows; 

        Complex matrix[][] = new Complex[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = this.get(i, j);
            }
        }
        Complex result = new Complex(1.0, 0.0);

        for (int k = 0; k < n; k++) {
            if (matrix[k][k].is_zero()) {
                int swap_row = -1;
                for (int i = k + 1; i < n; i++) {
                    if (!matrix[i][k].is_zero()) {
                        swap_row = i;
                        break;
                    }
                }
                if (swap_row == -1) return new Complex(0.0, 0.0);
                
                //swaping rows
                Complex[] temp = matrix[k];
                matrix[k] = matrix[swap_row];
                matrix[swap_row] = temp;
                result = result.multiply(new Complex(-1.0, 0.0));
                //
            }
        //changing to 0 
        for (int i = k + 1; i < n; i++) {
            Complex multiplier = matrix[i][k].div(matrix[k][k]);
            for (int j = 0; j < n; j++) {
                matrix[i][j] = matrix[i][j].sub(multiplier.multiply(matrix[k][j]));
            }
        }
        //
        result = result.multiply(matrix[k][k]);
        }
        return result;
    }

    //inverse matrix
    public Complex[][] inverse() {
        if (rows != cols)
            throw new IllegalArgumentException("Invalid size.", null);
        if (this.determinant() == new Complex(0.0, 0.0)) 
            throw new IllegalArgumentException("Invalid determinant (inverse is not able).", null);

        int n = rows;

        //advanced matrix = this matrix + E
        Complex[][] matrix_E = new Complex[n][n * 2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix_E[i][j] = this.get(i, j);
                matrix_E[i][n + j] = (i == j) ? new Complex(1.0, 0.0): new Complex(0.0, 0.0);
            }
        }

        //Gauss-Jordan method
        for (int k = 0; k < n; k++) {
            if (matrix_E[k][k] == new Complex(0.0, 0.0)) {
                int swap_row = -1;
                for(int i = k + 1; i < n; i++) {
                    if (matrix_E[i][k] != new Complex(0.0, 0.0)) {
                        swap_row = i;
                        break;
                    }
                }
            
                if (swap_row == -1)
                    throw new IllegalStateException("Matrix is singular.");
                
                //swaping
                Complex[]temp = matrix_E[k];
                matrix_E[k] = matrix_E[swap_row];
                matrix_E[swap_row] = temp;
                //
            }

            Complex pivot = matrix_E[k][k];

            for (int j = 0; j < n * 2; j++) {
                matrix_E[k][j] = matrix_E[k][j].div(pivot);
            }

            //change to 0 (up/down)
            for (int i = 0; i < n; i++) {
                if (i != k) {
                    Complex multiplier = matrix_E[i][k];
                    for (int j = 0; j < n * 2; j++) {
                        matrix_E[i][j] = matrix_E[i][j].sub(multiplier.multiply(matrix_E[k][j]));

                    }
                }
            }
        }
        Complex[][] result = new Complex[n][n];
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

        Complex[][] B_inverse = B.inverse();

        Matrix Matrix_B_inverse = new Matrix(B.rows, B.cols);
        for (int i = 0; i < B.rows; i++) {
            for (int j = 0; j < B.cols; j++) {
                Matrix_B_inverse.set(i, j, B_inverse[i][j]);
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
                A.set(i, j, Complex.parse(input.next()));
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
                B.set(i, j, Complex.parse(input.next()));
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
                Complex result = A.determinant();
                System.out.print("The determinant of matrix A: " + result.to_string());
           }
           else {
                Complex result = B.determinant();
                System.out.print("The determinant of matrix B: " + result.to_string());

           }
        }
        input.close();
        }
    }