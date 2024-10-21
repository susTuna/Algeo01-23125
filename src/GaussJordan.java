import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GaussJordan {
    public static double[] gaussJ(Matrix m, Scanner in){
        double[] err = {-405};
        double[] solusi = new double[m.col-1];
        boolean[] isFree = new boolean[m.col-1];
        boolean unsolv=false;
        boolean hasFreeVar = false;
        List<Integer> freeVar = new ArrayList<>();
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
        /* Menentukan adanya variabel bebas */
        // for(int i=0;i<N;i++){
        //     boolean foundPivot = false;
        //     for(int j=0;j<m.col-1;j++){
        //         if(m.elmt(i,j)!=0){
        //             foundPivot=true;
        //             break;
        //         }
        //     }
        //     if(!foundPivot){
        //         hasFreeVar=true; //variabel bebas ditemukan
        //         isFree[i]=true;
        //     }
        // }

        /* Keluaran */
        if(unsolv){
            System.out.println("Tidak dapat mencari solusi SPL.");
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
            System.out.println("Hasil perhitungan menggunakan metode Gauss-Jordan:");
            for(int i=0;i<solusi.length;i++){
                System.out.println("x"+(i+1)+" : "+solusi[i]);
            }

            System.out.print("Tulis hasil dalam file .txt? (y/n): ");
            String txt = in.next();
            while (!txt.equalsIgnoreCase("y") && !txt.equalsIgnoreCase("n")) {
                System.out.print("Input tidak valid, silahkan input kembali: ");
                txt = in.next();
            }

            if (txt.equalsIgnoreCase("y")) {
                StringBuilder output = new StringBuilder();
                for (int i = 0; i < solusi.length; i++) {
                    output.append("x")
                        .append(i + 1)
                        .append(" : ")
                        .append(solusi[i])
                        .append("\n");
                }
                ReadWrite.txtWrite(in, output.toString());
            }

            return solusi;
        }
    }

    

    public static void main(String[] args) {
        double[] ans;
        Scanner in = new Scanner(System.in);
        
        Matrix mat = null;
        mat=ReadWrite.txtRead(in);
        
        System.out.println("Matrix before Gauss-Jordan elimination:");
        mat.printMatrix();
        
        ans=gaussJ(mat,in);

        System.out.println("Matrix after Gaussian-Jordan elimination:");
        mat.printMatrix();
    }
}
    