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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Masukkan ukuran baris matriks: ");
        int row = scanner.nextInt();
        System.out.print("Masukkan ukuran kolom matriks: ");
        int col = scanner.nextInt();

        Matrix matrix = new Matrix(row, col);

        matrix.readMatrix(scanner);

        // Periksa apakah matriks adalah NxN (row harus sama dengan col untuk menghitung determinan)
        if (row == col) {
            double hasil = determinan(matrix, col);

            // Cetak hasil determinan
            System.out.println("Determinan matriks adalah: " + hasil);
        } else {
            System.out.println("Matriks bukan persegi (NxN), sehingga determinan tidak dapat dihitung.");
        }
    }
}
