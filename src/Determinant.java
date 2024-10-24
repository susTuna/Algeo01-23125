import java.util.Scanner;

public class Determinant {
    public static double det(Matrix m, Scanner in){
        double err = -405;
        double det = 1;
        Matrix copy = m.copyMatrix(m);

        if (m.row != m.col) {
            return err;
        }

        /* Ubah Ke Matrix Eselon */
        int N=copy.row;
        for (int i = 0; i < N; i++) {
            if (copy.elmt(i, i) == 0) {
                for (int j = i + 1; j < N; j++) {
                    if (copy.elmt(j, i) != 0) {
                        copy.swapRow(i, j);
                        break;
                    }
                }
            }
            for (int j = i + 1; j < N; j++) {
                if(!copy.isZeroRow(j)){
                    double factor = -copy.elmt(j, i)/copy.elmt(i,i);
                    copy.addRow(j, i, factor);
                }
                
            }
        }

        for(int i=0;i<N;i++){
            det*=copy.elmt(i,i);
        }
        return det;
    }

    

    public static void call() {
        double err = -405;
        double solusi;
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
            
            System.out.println("Matriks sebelum operasi determinan:");
            m1.printMatrix();
            
            solusi=det(m1, in);

        } else { // if (choice == 2)
            Matrix m2;
            m2=ReadWrite.txtRead(in);

            System.out.println("Matriks sebelum operasi determinan:");
            m2.printMatrix();

            solusi=det(m2, in);
        }

        if (solusi == err) {
            System.out.println("Determinan tidak bisa dihitung karena bukan matriks persegi.");
        } else {
            System.out.println("Determinan : "+solusi);
        }

        // Write to file
        ReadWrite.doubleToFile(solusi, in);
    }
}