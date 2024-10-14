import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Determinant {
    public static float det(Matrix m){
        float err = -405;
        float det = 1;
        /* Ubah Ke Matrix Eselon */
        int N=m.row;
        for (int i = 0; i < N; i++) {
            // Step 1: Find pivot (leading non-zero element in current column)
            if (m.elmt(i, i) == 0) {
                for (int j = i + 1; j < N; j++) {
                    if (m.elmt(j, i) != 0) {
                        m.swapRow(i, j);
                        break;
                    }
                }
            }
            // Step 3: Eliminate all elements below the pivot in the current column
            for (int j = i + 1; j < N; j++) {
                if(!m.isZeroRow(j)){
                    float factor = -m.elmt(j, i)/m.elmt(i,i);
                    m.addRow(j, i, factor);
                }
                
            }
        }

        for(int i=0;i<N;i++){
            det*=m.elmt(i,i);
        }
        return det;
    }

    

    public static void main(String[] args) {
        float ans;
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