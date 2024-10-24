import java.util.Scanner;

public class DeterminanKofaktor {

    public static double determinan(Matrix matrix, int n) {
        double det = 0.0;

        if (n == 1) {
            return matrix.elmt(0,0);
        }

        if (n == 2) {
            return matrix.elmt(0,0) * matrix.elmt(1,1) - matrix.elmt(0,1) * matrix.elmt(1,0);
        }


        Matrix submatrix = new Matrix(n,n);

        for (int x = 0; x < n; x++) {
            getSubmatrix(matrix, submatrix, 0, x, n);
            det += matrix.elmt(0,x) * determinan(submatrix, n - 1) * (x % 2 == 0 ? 1 : -1);
        }

        return det;
    }

    public static void getSubmatrix(Matrix matrix, Matrix submatrix, int row, int col, int n) {
        int subi = 0;
        for (int i = 1; i < n; i++) {
            int subj = 0;
            for (int j = 0; j < n; j++) {
                if (j == col) {
                    continue;
                }
                submatrix.set(subi, subj, matrix.elmt(i,j));
                subj++;
            }
            subi++;
        }
    }

    public static void call() {
        Scanner in = new Scanner(System.in);

        int choice;
        choice = ReadWrite.fileOrKeys(in);

        if (choice == 1) {
            System.out.print("\nMasukkan jumlah baris: ");
            int row = in.nextInt();
            System.out.print("Masukkan jumlah kolom: ");
            int col = in.nextInt();

            Matrix m1 = new Matrix(row, col);
            System.out.println("\nMasukkan matriks:");
            m1.readMatrix(in);

            System.out.println("\nMatriks sebelum operasi determinan kofaktor:");
            m1.printMatrix();

            // Periksa apakah matriks adalah NxN (row harus sama dengan col untuk menghitung determinan)
            if (row == col) {
                double hasil = determinan(m1, col);
                // Cetak hasil determinan
                System.out.println("\nDeterminan matriks adalah: " + hasil);

                // Write to file
                ReadWrite.doubleToFile(hasil, in);
            } else {
                System.out.println("\nMatriks bukan persegi (NxN), sehingga determinan tidak dapat dihitung.");
            }

        } else { // if (choice == 2)
            Matrix m2;
            m2 = ReadWrite.txtRead(in);
            int row2 = m2.row;
            int col2 = m2.col;

            System.out.println("\nMatriks sebelum operasi determinan kofaktor:");
            m2.printMatrix();

            // Periksa apakah matriks adalah NxN (row harus sama dengan col untuk menghitung determinan)
            if (row2 == col2) {
                double hasil = determinan(m2, col2);
                // Cetak hasil determinan
                System.out.println("\nDeterminan matriks adalah: " + hasil);

                // Write to file
                ReadWrite.doubleToFile(hasil, in);
            } else {
                System.out.println("\nMatriks bukan persegi (NxN), sehingga determinan tidak dapat dihitung.");
            }
        }

        
        
    }
}