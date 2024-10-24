import java.util.Scanner;

public class InverseAdj {
    public static Matrix f(Matrix m, Scanner in){
        Matrix A = AdjointCofactor.adjoint(m, m.row);
        double det=DeterminanKofaktor.determinan(m, m.row);
        for(int i=0;i<A.row;i++){
            A.multRowByK(i, det);
        }
        return A;
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

        Matrix inversed = f(matrix, scanner);

        System.out.println("Matriks Inverse: ");
        inversed.printMatrix();

        scanner.close();
    }
}
