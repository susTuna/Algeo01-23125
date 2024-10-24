import java.util.Scanner;

public class AdjointCofactor {
    public static double determinant(Matrix matrix, int n) {
        double det = 0.0;

        if (n == 1) {
            return matrix.elmt(0, 0);
        }
        if (n == 2) {
            return matrix.elmt(0, 0) * matrix.elmt(1, 1) - matrix.elmt(0, 1) * matrix.elmt(1, 0);
        }

        Matrix submatrix = new Matrix(n - 1, n - 1);
        for (int x = 0; x < n; x++) {
            getSubmatrix(matrix, submatrix, 0, x, n);
            det += matrix.elmt(0, x) * determinant(submatrix, n - 1) * (x % 2 == 0 ? 1 : -1);
        }
        return det;
    }
    public static void getSubmatrix(Matrix matrix, Matrix submatrix, int row, int col, int n) {
        int subi = 0;
        for (int i = 0; i < n; i++) {
            if (i == row) continue; 
            int subj = 0;
            for (int j = 0; j < n; j++) {
                if (j == col) continue; 
                submatrix.set(subi, subj, matrix.elmt(i, j));
                subj++;
            }
            subi++;
        }
    }

    public static Matrix cofactor(Matrix matrix, int n) {
        Matrix cofactorMatrix = new Matrix(n, n);
        Matrix submatrix = new Matrix(n - 1, n - 1);


        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                getSubmatrix(matrix, submatrix, i, j, n);

                double detSubmatrix = determinant(submatrix, n - 1);

                double cofactorValue = detSubmatrix * ((i + j) % 2 == 0 ? 1 : -1);

                cofactorMatrix.set(i, j, cofactorValue);
            }
        }
        return cofactorMatrix;
    }

    public static Matrix transpose(Matrix matrix, int n) {
        Matrix transposeMatrix = new Matrix(n, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                transposeMatrix.set(j, i, matrix.elmt(i, j));
            }
        }
        return transposeMatrix;
    }

    public static Matrix adjoint(Matrix matrix, int n) {
        Matrix cofactorMatrix = cofactor(matrix, n);
        return transpose(cofactorMatrix, n);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Masukkan jumlah baris dan kolom (NxN matriks): ");
        int n = scanner.nextInt();

        if (n <= 0) {
            System.out.println("Dimensi matriks tidak valid.");
            return;
        }

        Matrix matrix = new Matrix(n, n);

        System.out.println("Masukkan elemen-elemen matriks: ");
        matrix.readMatrix(scanner);

        Matrix adjointMatrix = adjoint(matrix, n);

        System.out.println("Matriks Adjoin (Adjugate): ");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(adjointMatrix.elmt(i, j) + "\t");
            }
            System.out.println();
        }

        scanner.close();
    }
}
