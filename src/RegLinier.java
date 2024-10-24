import java.util.ArrayList;
import java.util.List;
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
            System.out.println("Tidak dapat mencari solusi regresi.");
            return err;
        }

        return solution;
    }

    public static void f(double[] solusi, Scanner in, double taksiran){
        double hasil = 0;
        System.out.println("Hasil perhitungan regresi linear berganda :");
        String outPut;
        String outPutH;
        outPutH = "Dengan taksiran untuk f(";
        outPut = "f(x) = ";
            for(int i=0;i<solusi.length;i++){
                hasil+=solusi[i]*Math.pow(taksiran,i);
                if(i!=0){
                    if(solusi[i]<0){
                        outPut+=(" - "+Double.toString(-solusi[i])+"b_"+(i));
                    }
                    else{
                        outPut+=(" + "+Double.toString(solusi[i])+"b_"+(i));
                    }
                }else{
                    outPut+=(Double.toString(solusi[i])+"b_"+(i));
                }
            }
            outPutH+=(Double.toString(taksiran)+") adalah " + Double.toString(hasil));
            System.out.println(outPut);
            System.out.println(outPutH);
            

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
        double taksiran;
        Scanner in = new Scanner(System.in);

        // Read the input matrix from file or input
        Matrix mat = ReadWrite.txtRead(in);

        System.out.println("Masukkan taksiran");
        taksiran = in.nextDouble();

        System.out.println("Matriks sebelum regresi linier:");
        mat.printMatrix();  // Assuming Matrix class has a display method

        // Perform linear regression
        ans = regLin(mat, in);
        f(ans, in, taksiran);

        // System.out.println("Solusi regresi linier:");
        // for (int i = 0; i < ans.length; i++) {
        //     System.out.println("beta" + (i + 1) + " = " + ans[i]);
        // }
    }
}
