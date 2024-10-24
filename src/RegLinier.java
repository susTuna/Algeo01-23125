import java.util.Scanner;

public class RegLinier {

    // Function to perform multiple linear regression
    public static double[] regLin(Matrix m, Scanner in) {
        double[] err = {-405};

        // Extract the x values (columns other than the first)
        Matrix x = new Matrix(m.row, m.col);  // Including column of ones for intercept
        for (int i = 0; i < x.row; i++) {
            x.set(i, 0, 1);  // First column set to 1 for intercept term
            for (int j = 1; j < x.col; j++) {
                x.set(i, j, m.elmt(i, j - 1));  // Shift columns by one (exclude first column from original)
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
            System.out.println("\nTidak dapat mencari solusi regresi.");
            return err;
        }

        return solution;
    }

    public static void f(double[] solusi, Scanner in, double[] taksiran) {
        double hasil = 0;
        System.out.println("\nHasil perhitungan regresi linear berganda :");
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

        // Print the result
        outPutH += "(";
        for (int i = 0; i < taksiran.length; i++) {
            outPutH += (i > 0 ? ", " : "") + taksiran[i];
        }
        outPutH += ") adalah " + hasil;
        
        System.out.println(outPut);
        System.out.println(outPutH);

        // Option to write the result to a file
        System.out.print("\nTulis hasil dalam file .txt? (y/n): ");
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

    public static void call() {
        double[] ans;
        Matrix mat;
        Scanner in = new Scanner(System.in);

        int choose;
        choose = ReadWrite.fileOrKeys(in);

        if (choose == 1) {
            System.out.print("\nMasukkan jumlah baris: ");
            int rows = in.nextInt();
            System.out.print("Masukkan jumlah kolom: ");
            int cols = in.nextInt();
            
            mat = new Matrix(rows, cols);
            System.out.println("\nMasukkan matriks:");
            mat.readMatrix(in);
        } else { // if (choose == 2)
            mat=ReadWrite.txtRead(in);

            System.out.println("\nMatriks sebelum regresi linier:");
            mat.printMatrix();
        }

        System.out.println("\nMasukkan taksiran (x1, x2, ...):");
        double[] taksiran = new double[mat.col - 1];
        for (int i = 0; i < taksiran.length; i++) {
            System.out.print("x_" + (i + 1) + ": ");
            taksiran[i] = in.nextDouble();
        }
        
        ans = regLin(mat, in);
        f(ans, in, taksiran);

    }
}
