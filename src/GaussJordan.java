import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GaussJordan {
    public static double[] gaussJordan(Matrix m, Scanner in){
        double[] err = {-405};
        double[] solusi = new double[m.col-1];
        boolean[] isFree = new boolean[m.col-1];
        boolean unsolv=false;
        boolean hasFreeVar = false;
        List<Integer> freeVar = new ArrayList<>();
        
        m=mgaussJordan(m, in);

        /* Lakukan Substitusi Balik */
        for(int i=m.row-1;i>=0;i--){
            if(m.isZeroRow(i)&&m.elmt(i,m.col-1)!=0){
                unsolv=true;
                break; //tidak ada solusi
            }
            else if(m.isZeroRow(i)){
                freeVar.add(i); //variabel bebas
            }
            double sum=0;
            for(int j=i+1;j<m.col-1;j++){
                sum+=m.elmt(i,j)*solusi[j];
            }
            solusi[i]=(m.elmt(i,m.col-1)-sum)/m.elmt(i,i);
        }

        /* Keluaran */
        if(unsolv){
            System.out.println("\nTidak dapat mencari solusi SPL.");
            return err;
        }
        else if(hasFreeVar){
            for(int i=0;i<m.col-1;i++){
                if(isFree[i]){
                    System.out.println("x"+(i+1)+" : variabel bebas");
                }
                else{
                    System.out.print("x"+(i+1)+ " : ");
                    for(int j=i+1;j<m.col-1;j++){
                        if(m.elmt(i,j)!=0){
                            System.out.print(m.elmt(i,j)+" * variabel bebas ");
                        }  
                    }
                    System.out.println();
                }
            }
            return err;
        }
        else{
            System.out.println("\nHasil perhitungan menggunakan metode Gauss-Jordan:");
            for(int i=0;i<solusi.length;i++){
                System.out.println("x"+(i+1)+" : "+solusi[i]);
            }
            return solusi;
        }
    }

    public static Matrix mgaussJordan(Matrix m, Scanner in){
        /* Ubah Ke Matrix Eselon */
        int N=Math.min(m.row, m.col);
        for (int i = 0; i < N; i++) {
            // Step 1: Find pivot (leading non-zero element in current column)
            if (m.elmt(i, i) == 0) {
                for (int j = i + 1; j < N; j++) {
                    if (m.elmt(j, i) != 0) {
                        m.swapRow(i, j);
                        break;
                    }
                }
            }

            // Step 2: Normalize the row so that pivot becomes 1
            double pivot = m.elmt(i, i);
            if (pivot != 0) {
                m.multRowByK(i, 1 / pivot);
            }

            // Step 3: Eliminate all elements below the pivot in the current column
            for (int j = i + 1; j < N; j++) {
                if(!m.isZeroRow(j)){
                    double factor = -m.elmt(j, i);
                    m.addRow(j, i, factor);
                }
                
            }
        }

        /* Eliminasi Gauss-Jordan */
        for(int i=N-1;i>=0;i--){
            double pivot = m.elmt(i,i);

            for(int j=i-1;j>=0;j--){
                if(!m.isZeroRow(j)){
                    double factor = -m.elmt(j, i);
                    m.addRow(j, i, factor);
                }
            }
        }
        return m;
    }

    public static void call() {
        Matrix ans;
        double[] solusi;
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
            
            System.out.println("\nMatriks sebelum Eliminasi Gauss-Jordan:");
            m1.printMatrix();
            
            ans=mgaussJordan(m1, in);

            System.out.println("\nMatriks setelah Eliminasi Gauss-Jordan:");
            ans.printMatrix();

            // Print solusi
            solusi = gaussJordan(ans, in);

            // Write to file
            ReadWrite.arrToFile(solusi, in);

        } else if (choice == 2) {
            Matrix m2;
            m2=ReadWrite.txtRead(in);
        
            System.out.println("\nMatriks sebelum Eliminasi Gauss-Jordan:");
            m2.printMatrix();
            
            ans=mgaussJordan(m2, in);

            System.out.println("\nMatriks setelah Eliminasi Gauss-Jordan:");
            ans.printMatrix();

            // Print solusi
            solusi = gaussJordan(ans, in);

            // Write to file
            ReadWrite.arrToFile(solusi, in);
        }
    }
}
    