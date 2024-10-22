import java.util.Scanner;

public class InverseOBE {
    public static Matrix inverseOBE(Matrix m, Scanner in){
        boolean unsolv = false;
        Matrix solusi = new Matrix(m.row,m.col);
        Matrix temp = new Matrix(m.row,m.col+m.col);
        if (Determinant.det(m)!=0 && m.row==m.col){

            // Perbesar matriks
            for (int i=0; i<temp.row; i++){
                // Isi setengah matriks temp (bagian kiri) dengan matriks input
                for (int j=0; j<m.col; j++){
                    temp.set(i, j, m.elmt(i, j));
                }
                // Isi setengah matriks temp (bagian kanan) dengan matriks identitas
                for (int k=m.col; k<m.col+m.col; k++){
                    if (k==i+m.col){
                        temp.set(i, k, 1);
                    }
                    else{
                        temp.set(i, k, 0);
                    }
                }
            }

            // OBE dengan Gauss-Jordan
            int N=Math.min(temp.row, temp.col);
            for (int i = 0; i < N; i++) {
                // Step 1: Find pivot (leading non-zero element in current column)
                if (temp.elmt(i, i) == 0) {
                    for (int j = i + 1; j < N; j++) {
                        if (temp.elmt(j, i) != 0) {
                            temp.swapRow(i, j);
                            break;
                        }
                    }
                }

                // Step 2: Normalize the row so that pivot becomes 1
                double pivot = temp.elmt(i, i);
                if (pivot != 0) {
                    temp.multRowByK(i, 1 / pivot);
                }

                // Step 3: Eliminate all elements below the pivot in the current column
                for (int j = i + 1; j < N; j++) {
                    if(!temp.isZeroRow(j)){
                        double factor = -temp.elmt(j, i);
                        temp.addRow(j, i, factor);
                    }
                    
                }
            }

            /* Eliminasi Gauss-Jordan */
            for(int i=N-1;i>=0;i--){
                double pivot = temp.elmt(i,i);

                for(int j=i-1;j>=0;j--){
                    if(!temp.isZeroRow(j)){
                        double factor = -temp.elmt(j, i);
                        temp.addRow(j, i, factor);
                    }
                }
            }

            for (int i=0; i<temp.row; i++){
                // Cek setengah matriks temp (bagian kiri) apakah sudah menjadi matriks identitas
                for (int j=0; j<m.col; j++){
                    if (i==j && temp.elmt(i, j)!=1 || i!=j && temp.elmt(i, j)!=0){
                        unsolv = true;
                    }
                }
            }

            if(unsolv){
                System.out.println("Tidak dapat mencari inverse dengan metode OBE.");
                return null;
            } else {
                // Isi matriks solusi dengan setengah matriks temp (bagian kanan)
                for (int i=0; i<temp.row; i++){
                    for (int k=m.col; k<temp.col; k++){
                        solusi.set(i, k-m.col, temp.elmt(i, k));
                    } 
                }
            }
            return solusi;
            
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        Matrix ans;
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the number of rows: ");
        int rows = in.nextInt();
        System.out.print("Enter the number of columns: ");
        int cols = in.nextInt();
        
        Matrix mat = new Matrix(rows, cols);
        System.out.println("Enter matrix elements:");
        mat.readMatrix(in);
        ans = inverseOBE(mat, in);

        if (ans != null) {
            for (int i=0; i < ans.row; i++) {
                for (int j=0; j < ans.col; j++) {
                    System.out.print(ans.elmt(i, j) + " ");
                }
                System.out.println();
            }
        }
        
    }
}
