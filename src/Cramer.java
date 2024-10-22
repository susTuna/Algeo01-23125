import java.util.Scanner;

public class Cramer {
    public static double[] cramer(Matrix m){
        double[] err = {-405};
        boolean unsolv=false;
        double[] solusi = new double[m.row];
        Matrix temp = new Matrix(m.row, m.col-1);
        double dete = Determinant.det(m);
        if (Determinant.det(m) != 0 && m.row == (m.col-1)) {
            for (int i = 0; i < m.row; i++) { 
                // Swap column i with the last column
                m.swapCol(i, m.col-1); 
                // Copy the matrix to temporary matrix
                for (int j = 0; j < m.row; j++) {
                    for (int k = 0; k < m.col-1; k++) {
                        temp.set(j, k, m.elmt(j, k));
                    }
                }
                // Put the SPL solution in a temporary array
                solusi[i] = Determinant.det(temp)/dete;
                // Swap back the column
                m.swapCol(i, m.col-1);
            }
        } else {
            unsolv = true;
        }

        if(unsolv){
            System.out.println("Tidak dapat mencari solusi kaidah cramer karena determinan matriks = 0.");
            return err;
        }

        return solusi;
    }

    public static void main(String[] args) {
        double[] ans;
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the number of rows: ");
        int rows = in.nextInt();
        System.out.print("Enter the number of columns: ");
        int cols = in.nextInt();
        
        Matrix mat = new Matrix(rows, cols+1);
        System.out.println("Enter matrix elements:");
        mat.readMatrix(in);
        ans = cramer(mat);
        for (int i = 0; i < ans.length; i++) {
            System.out.println("x" + (i+1) + " : " + ans[i]);
        }
    }
}
