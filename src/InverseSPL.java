import java.util.Scanner;

public class InverseSPL {
    public static Matrix inverseSPL(Matrix m, Scanner in) {
        // x = a^-1 * b
        
        Matrix err = new Matrix(1,1);
        err.set(0, 0, -405);
        
        Matrix a = new Matrix(m.row, m.col-1);
        Matrix b = new Matrix(m.row, 1);

        Matrix solusi = new Matrix(m.row, 1);

        if (m.row==m.col-1) { // matriks persegi
            // Simpan matriks peubah untuk di-inverse
            for (int i = 0; i < m.row; i++) {
                for (int j = 0; j < m.col-1; j++) {
                    a.set(i, j, m.elmt(i, j));
                }
            }

            // Simpan matriks konstanta
            for (int i = 0; i < m.row; i++) {
                b.set(i, 0, m.elmt(i, m.col-1));
            }

            // Inverse-kan matriks peubah
            Matrix invA = InverseOBE.inverseOBE(a, in);
            
            // Cek apakah matriks tidak dapat di-inverse
            if (invA.elmt(0, 0) == -405) {
                System.out.println("\nTidak dapat mencari solusi SPL karena matriks tidak dapat di-inverse.");
                return err;
            // Perkalian matriks peubah yang sudah di-inverse dengan matriks konstanta
            } else {
                solusi = Matrix.multMatrix(invA, b);
                for (int i = 0; i < solusi.row; i++) {
                    System.out.print("x" + (i+1) + " = " + solusi.elmt(i, 0) + "\n");
                }
            }
            
            return solusi;
        } else {
            System.out.println("\nTidak dapat mencari solusi SPL karena matriks bukan matriks persegi.");
            return err;
        }
    }

    public static void call() {
        Matrix ans;
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
            
            System.out.println("\nMatriks sebelum operasi Inverse untuk mencari SPL:");
            m1.printMatrix();
            
            // Print solusi
            ans=inverseSPL(m1, in);

            // Write to file
            ReadWrite.matrixToFile(ans, in);

        } else if (choice == 2) {
            Matrix m2;
            m2=ReadWrite.txtRead(in);
        
            System.out.println("\nMatriks sebelum operasi Inverse untuk mencari SPL:");
            m2.printMatrix();
            
            // Print solusi
            ans=inverseSPL(m2, in);

            // Write to file
            ReadWrite.matrixToFile(ans, in);
        }
    }
}