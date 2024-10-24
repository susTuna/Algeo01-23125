import java.util.Scanner;

public class RegLinier {
    public static double[] regLin(Matrix m, Scanner in) {
        double[] err = {-405};
        
        // store  x values (other than first column)
        Matrix x = new Matrix(m.row, m.col-1);
        for (int i=0; i<m.row; i++) {
            for (int j=1; j<m.col; j++) {
                x.set(i, j, m.elmt(i, j));
            }
        }
        
        // store y values (first column)
        double[] y = new double[m.row];
        for (int i=0; i<m.row; i++) {
            y[i] = m.elmt(i, 0);
        }
        
        // hitung x^T * x
        Matrix xt = Matrix.transposeMatrix(m);
        Matrix xtx = Matrix.multMatrix(xt, m);

        // hitung x^T * y
        Matrix yMatrix = new Matrix(m.row, 1);
        for (int i=0; i<m.row; i++) {
            yMatrix.set(i, 0, y[i]);
        }

        Matrix xty = Matrix.multMatrix(xt, yMatrix);

        Matrix temp = new Matrix(m.row, m.col);

        // gabungkan xtx dan xty
        for (int i=0; i<m.row; i++) {
            for (int j=0; j<m.col; j++) {
                if (j < m.col-1) {
                    temp.set(i, j, xtx.elmt(i, j));
                } else {
                    temp.set(i, j, xty.elmt(i, 0));
                }
            }
        }

        double[] solusi = Gauss.gauss(temp, in);

        if (solusi == err) {
            System.out.println("Tidak dapat mencari solusi regresi.");
            return err;
        } 

        return solusi;
    }

    public static void main(String[] args) {
        double[] ans;
        Scanner in = new Scanner(System.in);
        
        Matrix mat;
        mat=ReadWrite.txtRead(in);

        System.out.println("Matriks sebelum regresi linier:");
        mat.readMatrix(in);

        ans = regLin(mat, in);

        System.out.println("Solusi regresi linier:");
        for (int i=0; i < ans.length; i++) {
            System.out.print("beta" + i+1 + " = " + ans[i]);
        }
    }
}
