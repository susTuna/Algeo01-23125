import java.util.Scanner;

public class InverseSPL {
    public static Matrix inverseSPL(Matrix m) {
        Matrix err = new Matrix(1,1);
        err.set(0, 0, -405);
        Matrix solusi;
        Matrix inv = new Matrix(m.row, m.col-1);
        Matrix b = new Matrix(m.row, 1);

        if (m.row==m.col-1) {
            // Simpan matriks peubah untuk di-inverse
            for (int i = 0; i < m.row; i++) {
                for (int j = 0; j < m.col-1; j++) {
                    inv.set(i, j, m.elmt(i, j));
                }
            }

            // Simpan matriks konstanta
            for (int i = 0; i < m.row; i++) {
                b.set(i, 0, m.elmt(i, m.col-1));
            }

            // Inverse-kan matriks peubah
            InverseOBE.inverseOBE(inv);

            
            // Cek apakah matriks tidak dapat di-inverse
            if (inv.row == 1 && inv.col == 1 && inv.elmt(0, 0) == -405) {
                System.out.println("Tidak dapat mencari solusi SPL karena matriks tidak dapat di-inverse.");
                return err;
            // Perkalian matriks peubah yang sudah di-inverse dengan matriks konstanta
            } else {
                solusi = Matrix.multMatrix(inv, b);
            }
            return solusi;
            
        } else {
            System.out.println("Tidak dapat mencari solusi SPL karena matriks bukan matriks persegi.");
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

        ans = inverseSPL(mat);

        for (int i = 0; i < ans.row; i++) {
            System.out.print("x" + (i+1) + " = " + ans.elmt(i, 0) + "\n");
        }
    }
}
