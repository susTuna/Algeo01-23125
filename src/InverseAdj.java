import java.util.Scanner;

public class InverseAdj {
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

    public static Matrix inversAdj(Matrix m, Scanner in){
        Matrix A = adjoint(m, m.row);
        double det=DeterminanKofaktor.determinan(m, m.row);
        for(int i=0;i<A.row;i++){
            A.multRowByK(i, det);
        }
        return A;
    }

    public static void call() {
        Scanner in = new Scanner(System.in);

        int choice;
        choice = ReadWrite.fileOrKeys(in);

        if (choice == 1) {
            System.out.print("Masukkan jumlah baris: ");
            int rows = in.nextInt();
            System.out.print("Masukkan jumlah kolom: ");
            int cols = in.nextInt();

            Matrix m1 = new Matrix(rows, cols);
            System.out.println("Masukkan matriks:");
            m1.readMatrix(in);

            System.out.println("Matriks sebelum operasi Invers Adjoint:");
            m1.printMatrix();

            if (rows != cols) {
                System.out.println("Matriks tidak memiliki invers Adjoint karena bukan matriks persegi.");
            } else {
                Matrix solusi = inversAdj(m1, in);
                System.out.println("Matriks setelah operasi Invers Adjoint:");
                solusi.printMatrix();

                // Write to file
                ReadWrite.matrixToFile(solusi, in);
            }            

        } else { // if choice == 2
            Matrix m2;
            m2 = ReadWrite.txtRead(in);

            System.out.println("Matriks sebelum operasi Invers Adjoint:");
            m2.printMatrix();

            if (m2.row != m2.col) {
                System.out.println("Matriks tidak memiliki invers Adjoint karena bukan matriks persegi.");
            } else {
                Matrix solusi = inversAdj(m2, in);
                System.out.println("Matriks setelah operasi Invers Adjoint:");
                solusi.printMatrix();

                // Write to file
                ReadWrite.matrixToFile(solusi, in);
            }

            

        }

        

        
    }
}