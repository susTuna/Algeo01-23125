import java.util.Scanner;

public class Cramer {
    public static double[] cramer(Matrix m, Scanner in){
        double[] err = {-405};
        double[] solusi = new double[m.row];

        // Create the coefficient matrix (n x n) from the original matrix (n x (n+1))
        Matrix coeffMatrix = new Matrix(m.row, m.col - 1);
        // Fill the coefficient matrix
        for (int i = 0; i < m.row; i++) {
            for (int j = 0; j < m.col - 1; j++) {
                coeffMatrix.set(i, j, m.elmt(i, j));
            }
        }

        

        double dete = Determinant.det(coeffMatrix, in);
        
        if (dete != 0 && m.row == (m.col-1)) {
            Matrix temp = new Matrix(m.row, m.col-1);

            for (int i = 0; i < m.row; i++) { 
                for (int j = 0; j < m.row; j++) {
                    for (int k = 0; k < m.col-1; k++) {
                        temp.set(j, k, coeffMatrix.elmt(j, k));
                    }
                }
                for (int j = 0; j < m.row; j++) {
                    temp.set(j, i, m.elmt(j, m.col - 1));
                }
                double detTemp = Determinant.det(temp, in);
                solusi[i] = detTemp/dete;
            }

            System.out.println("\nSolusi SPL:");
            for (int i = 0; i < solusi.length; i++) {
                System.out.println("x" + (i+1) + " : " + solusi[i]);
            }

            return solusi;
        } else {
            System.out.println("\nTidak dapat mencari solusi kaidah cramer karena determinan matriks = 0.");
            return err;
        }
    }

    public static void call() {
        double[] solusi;
        Scanner in = new Scanner(System.in);

        int choice;
        choice = ReadWrite.fileOrKeys(in);

        if (choice == 1) {
            System.out.print("Masukkan jumlah baris: ");
            int rows = in.nextInt();
            System.out.print("Masukkan jumlah kolom: ");
            int cols = in.nextInt();
            
            Matrix m1 = new Matrix(rows, cols);
            System.out.println("\nMasukkan matriks:");
            m1.readMatrix(in);
            
            System.out.println("\nMatriks sebelum operasi Cramer:");
            m1.printMatrix();
            
            // Print solusi
            solusi=cramer(m1, in);

            // Write to file
            ReadWrite.arrToFile(solusi, in);
        } else if (choice == 2) {
            Matrix m2;
            m2=ReadWrite.txtRead(in);

            System.out.println("\nMatriks sebelum operasi Cramer:");
            m2.printMatrix();

            // Print solusi
            solusi=cramer(m2, in);

            // Write to file
            ReadWrite.arrToFile(solusi, in);
        }
    }
}
