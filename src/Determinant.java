import java.util.Scanner;

public class Determinant {
    public static double det(Matrix m, Scanner in){
        double err = -405;
        double det = 1;
        Matrix copy = m.copyMatrix(m);

        if (m.row != m.col) {
            System.out.println("Determinan tidak bisa dihitung karena bukan matriks persegi.");
            return err;
        }

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
        
        Matrix mat;
        mat=ReadWrite.txtRead(in);

        System.out.println("Matriks sebelum operasi determinan:");
        mat.printMatrix();
        
        ans=det(mat, in);

        System.out.println("Determinan : "+ans);
    }
}