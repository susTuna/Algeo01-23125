import java.util.Scanner;

public class RegQuadratic{

    // Function to perform multiple quadratic regression
    public static double[] regQuad(Matrix m, Scanner in) {
        double[] err = {-405};

        // Extend the matrix X for quadratic and interaction terms
        int originalCols = m.col - 1; // Number of original x columns
        int newCols = 1 + 2 * originalCols + (originalCols * (originalCols - 1)) / 2; // 1 for intercept, 2*linear terms, and interaction terms

        Matrix x = new Matrix(m.row, newCols);  // Including quadratic and interaction terms

        for (int i = 0; i < x.row; i++) {
            int colIndex = 0;
            x.set(i, colIndex++, 1);  // First column is intercept term (1)
            
            // Add linear terms (x1, x2, ...)
            for (int j = 1; j < m.col; j++) {
                x.set(i, colIndex++, m.elmt(i, j));
            }
            
            // Add quadratic terms (x1^2, x2^2, ...)
            for (int j = 1; j < m.col; j++) {
                x.set(i, colIndex++, Math.pow(m.elmt(i, j), 2));
            }
            
            // Add interaction terms (x1*x2, x1*x3, ...)
            for (int j = 1; j < m.col; j++) {
                for (int k = j + 1; k < m.col; k++) {
                    x.set(i, colIndex++, m.elmt(i, j) * m.elmt(i, k));
                }
            }
        }

        // Extract the y values (first column)
        double[] y = new double[m.row];
        for (int i = 0; i < m.row; i++) {
            y[i] = m.elmt(i, 0);
        }

        // Compute X^T * X (normal equation)
        Matrix xt = Matrix.transposeMatrix(x);
        Matrix xtx = Matrix.multMatrix(xt, x);

        // Compute X^T * y
        Matrix yMatrix = new Matrix(m.row, 1);
        for (int i = 0; i < m.row; i++) {
            yMatrix.set(i, 0, y[i]);
        }
        Matrix xty = Matrix.multMatrix(xt, yMatrix);

        // Create an augmented matrix (X^T * X | X^T * y)
        Matrix augmentedMatrix = new Matrix(xtx.row, xtx.col + 1);
        for (int i = 0; i < xtx.row; i++) {
            for (int j = 0; j < xtx.col; j++) {
                augmentedMatrix.set(i, j, xtx.elmt(i, j));
            }
            augmentedMatrix.set(i, xtx.col, xty.elmt(i, 0));  // Last column is X^T * y
        }

        // Solve the system using Gaussian elimination
        double[] solution = Gauss.msolution(Gauss.mgauss(augmentedMatrix, in));

        // Check if there's no solution
        if (solution == err) {
            System.out.println("Tidak dapat mencari solusi regresi.");
            return err;
        }

        return solution;
    }

    public static void f(double[] solusi, Scanner in, double[] taksiran) {
        double hasil = 0;
        System.out.println("Hasil perhitungan regresi kuadratik berganda :");
        String outPut = "f(x) = ";
        String outPutH = "Dengan taksiran untuk f(";
        
        // Intercept
        hasil += solusi[0];
        outPut += Double.toString(solusi[0]);

        int colIndex = 1;
        // Linear terms (x1, x2, ...)
        for (int i = 0; i < taksiran.length; i++) {
            hasil += solusi[colIndex] * taksiran[i];
            outPut += (solusi[colIndex] >= 0 ? " + " : " - ") + Math.abs(solusi[colIndex]) + "*x_" + (i+1);
            colIndex++;
        }

        // Quadratic terms (x1^2, x2^2, ...)
        for (int i = 0; i < taksiran.length; i++) {
            hasil += solusi[colIndex] * Math.pow(taksiran[i], 2);
            outPut += (solusi[colIndex] >= 0 ? " + " : " - ") + Math.abs(solusi[colIndex]) + "*x_" + (i+1) + "^2";
            colIndex++;
        }

        // Interaction terms (x1*x2, x1*x3, ...)
        for (int i = 0; i < taksiran.length; i++) {
            for (int j = i + 1; j < taksiran.length; j++) {
                hasil += solusi[colIndex] * taksiran[i] * taksiran[j];
                outPut += (solusi[colIndex] >= 0 ? " + " : " - ") + Math.abs(solusi[colIndex]) + "*x_" + (i+1) + "*x_" + (j+1);
                colIndex++;
            }
        }

        // Print the result
        outPutH += "(";
        for (int i = 0; i < taksiran.length; i++) {
            outPutH += (i > 0 ? ", " : "") + taksiran[i];
        }
        outPutH += ") adalah " + hasil;
        
        System.out.println(outPut);
        System.out.println(outPutH);

        // Option to write the result to a file
        System.out.print("Tulis hasil dalam file .txt? (y/n): ");
        String txt = in.next();
        while (!txt.equalsIgnoreCase("y") && !txt.equalsIgnoreCase("n")) {
            System.out.print("Input tidak valid, silahkan input kembali: ");
            txt = in.next();
        }

        if (txt.equalsIgnoreCase("y")) {
            StringBuilder output = new StringBuilder();
            output.append(outPut).append("\n").append(outPutH).append("\n");
            ReadWrite.txtWrite(in, output.toString());
        }
    }

    public static void main(String[] args) {
        double[] ans;
        Scanner in = new Scanner(System.in);

        // Read the input matrix from file or input
        Matrix mat = ReadWrite.txtRead(in);

        System.out.println("Masukkan taksiran (x1, x2, ...):");
        double[] taksiran = new double[mat.col - 1];
        for (int i = 0; i < taksiran.length; i++) {
            System.out.print("x_" + (i + 1) + ": ");
            taksiran[i] = in.nextDouble();
        }

        System.out.println("Matriks sebelum regresi kuadratik:");
        mat.printMatrix();  // Assuming Matrix class has a display method

        // Perform quadratic regression
        ans = regQuad(mat, in);
        f(ans, in, taksiran);
    }
}
