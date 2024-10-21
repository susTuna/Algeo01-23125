import java.util.Scanner;

public class InverseOBE {
    public static Matrix inverseOBE(Matrix m){
        Matrix err = new Matrix(1,1);
        err.set(0, 0, -405);
        boolean unsolv = false;
        Matrix solusi = new Matrix(m.row,m.col);
        Matrix temp = new Matrix(m.row,m.col+m.col);

        // Prekondisi awal: determinan matriks tidak nol dan matriks persegi
        if (Determinant.det(m)!=0 && m.isSquare()){
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

            // OBE dengan Gauss-Jordan //
            /* Ubah Ke Matrix Eselon */
            double tolerance = 1e-9;
            int N=Math.min(temp.row, temp.col); 
            for (int i = 0; i < N; i++) {
                // Step 1: Find pivot (leading non-zero element in current column)
                if (Math.abs(temp.elmt(i, i)) < tolerance) {
                    for (int j = i + 1; j < N; j++) {
                        if (Math.abs(temp.elmt(j, i)) > Math.abs(temp.elmt(i, i))) {
                            temp.swapRow(i, j);
                            break;
                        }
                    }
                }

                // Step 2: Normalize the row so that pivot becomes 1
                double pivot = temp.elmt(i, i);
                if (Math.abs(pivot) > tolerance) {
                    temp.multRowByK(i, 1 / pivot);
                } else {
                    unsolv = true;
                    break;
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
                for(int j=i-1;j>=0;j--){
                    if(!temp.isZeroRow(j)){
                        double factor = -temp.elmt(j, i);
                        temp.addRow(j, i, factor);
                    }
                }
            }
            // Gauss-Jordan DONE //

            for (int i=0; i<temp.row; i++){
                // Cek setengah matriks temp (bagian kiri) apakah sudah menjadi matriks identitas
                for (int j=0; j<m.col; j++){
                    if ((i == j && Math.abs(temp.elmt(i, j) - 1) > tolerance) || 
                        (i != j && Math.abs(temp.elmt(i, j)) > tolerance)) {
                        unsolv = true;
                    }
                }
            }

            // Jika tidak terbentuk matriks identitas di bagian kiri, return error
            if(unsolv){
                System.out.println("Tidak dapat mencari inverse dengan metode OBE.");
                return err;
            } else {
                // Isi matriks solusi dengan setengah matriks temp (bagian kanan)
                for (int i=0; i<temp.row; i++){
                    for (int k=m.col; k<temp.col; k++){
                        solusi.set(i, k-m.col, temp.elmt(i, k));
                    } 
                }
                return solusi;
            }
            
        } else {
            return err;
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

        ans = inverseOBE(mat);

        for (int i=0; i < ans.row; i++) {
            for (int j=0; j < ans.col; j++) {
                 System.out.print(ans.elmt(i, j) + " ");
            }
            System.out.println();
        }
    }
}
