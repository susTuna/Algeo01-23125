import java.util.Scanner;

public class InverseOBE {
    public static Matrix inverseOBE(Matrix m, Scanner in){
        Matrix err = new Matrix(1,1);
        err.set(0, 0, -405);
        Matrix solusi = new Matrix(m.row,m.col);
        Matrix temp = new Matrix(m.row,m.col+m.col);
        if (Determinant.det(m, in)!=0 && m.row==m.col){

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
            GaussJordan.mgaussJordan(temp, in);

            for (int i=0; i<temp.row; i++){
                // Cek setengah matriks temp (bagian kiri) apakah sudah menjadi matriks identitas
                for (int j=0; j<m.col; j++){
                    if (i==j && temp.elmt(i, j)!=1 || i!=j && temp.elmt(i, j)!=0){
                        return err;
                    }
                }
            }

            // Isi matriks solusi dengan setengah matriks temp (bagian kanan)
            for (int i=0; i<temp.row; i++){
                for (int k=m.col; k<temp.col; k++){
                    solusi.set(i, k-m.col, temp.elmt(i, k));
                } 
            }
            
            return solusi;
        } else {
            return err;
        }
    }

    public static void call() {
        Matrix solusi;
        Matrix err = new Matrix(1,1);
        err.set(0, 0, -405);
        Scanner in = new Scanner(System.in);

        int choice;
        choice = ReadWrite.fileOrKeys(in);

        if (choice == 1) {
            System.out.print("\nMasukkan jumlah baris: ");
            int rows = in.nextInt();
            System.out.print("Masukkan jumlah kolom: ");
            int cols = in.nextInt();
            
            Matrix m1 = new Matrix(rows, cols);
            System.out.println("\nMasukkan matriks:");
            m1.readMatrix(in);
            
            System.out.println("\nMatriks sebelum operasi determinan:");
            m1.printMatrix();
            
            solusi=inverseOBE(m1, in);
        } else { // if (choice == 2)
            Matrix m2;
            m2=ReadWrite.txtRead(in);

            System.out.println("\nMatriks sebelum operasi invers:");
            m2.printMatrix();

            solusi=inverseOBE(m2, in);
        }
        
        if (solusi==err){
            System.out.println("\nTidak dapat mencari inverse dengan metode OBE.");
        } else {
            System.out.println("\nMatriks setelah operasi invers dengan OBE:");
            solusi.printMatrix();
        }

        // Write to file
        ReadWrite.matrixToFile(solusi, in);
    }
}
