import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Determinant {
    public static double det(Matrix m){
        double err = -405;
        double det = 1;
        Matrix copy = m.copyMatrix(m);
        /* Ubah Ke Matrix Eselon */
        int N=copy.row;
        for (int i = 0; i < N; i++) {
            // Step 1: Find pivot (leading non-zero element in current column)
            if (copy.elmt(i, i) == 0) {
                for (int j = i + 1; j < N; j++) {
                    if (copy.elmt(j, i) != 0) {
                        copy.swapRow(i, j);
                        break;
                    }
                }
            }
            // Step 3: Eliminate all elements below the pivot in the current column
            for (int j = i + 1; j < N; j++) {
                if(!copy.isZeroRow(j)){
                    double factor = -copy.elmt(j, i)/copy.elmt(i,i);
                    copy.addRow(j, i, factor);
                }
                
            }
        }

        for(int i=0;i<N;i++){
            det*=copy.elmt(i,i);
        }
        return det;
    }

    

    public static void main(String[] args) {
        double ans;
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the number of rows: ");
        int rows = in.nextInt();
        System.out.print("Enter the number of columns: ");
        int cols = in.nextInt();
        
        Matrix mat = new Matrix(rows, cols);
        System.out.println("Enter matrix elements:");
        mat.readMatrix(in);
        
        ans=det(mat);

        System.out.println("Matrix after Gaussian elimination:");
        mat.printMatrix();

        System.out.println("Determinant : "+ans);
    }
}