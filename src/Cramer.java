import java.util.Scanner;

public class Cramer {
    public static double[] cramer(Matrix m, Scanner in){
        double[] err = {-405};
        double[] solusi = new double[m.row];
        Matrix temp = new Matrix(m.row, m.col-1);
        double dete = Determinant.det(m, in);
        if (Determinant.det(m, in) != 0 && m.row == (m.col-1)) {
            for (int i = 0; i < m.row; i++) { 
                // Swap column i with the last column
                m.swapCol(i, m.col-1); 
                // Copy the matrix to temporary matrix
                for (int j = 0; j < m.row; j++) {
                    for (int k = 0; k < m.col-1; k++) {
                        temp.set(j, k, m.elmt(j, k));
                    }
                }
                // Put the SPL solution in a temporary array
                solusi[i] = Determinant.det(temp, in)/dete;
                // Swap back the column
                m.swapCol(i, m.col-1);
            }

            System.out.println("Solusi SPL:");
            for (int i = 0; i < solusi.length; i++) {
                System.out.println("x" + (i+1) + " : " + solusi[i]);
            }

            return solusi;
        } else {
            System.out.println("Tidak dapat mencari solusi kaidah cramer karena determinan matriks = 0.");
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
            System.out.println("Masukkan matriks:");
            m1.readMatrix(in);
            
            System.out.println("Matriks sebelum operasi Cramer:");
            m1.printMatrix();
            
            // Print solusi
            solusi=cramer(m1, in);

            // Write to file
            ReadWrite.arrToFile(solusi, in);
        } else if (choice == 2) {
            Matrix m2;
            m2=ReadWrite.txtRead(in);

            System.out.println("Matriks sebelum operasi Cramer:");
            m2.printMatrix();

            // Print solusi
            solusi=cramer(m2, in);

            // Write to file
            ReadWrite.arrToFile(solusi, in);
        }
    }
}
