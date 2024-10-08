import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

public class Gauss {
    public static Matrix f(Matrix m){
        float[] failRet = {-999999999};
        /* Ubah Ke Matrix Eselon */
        int N=m.row;
        for(int i=0;i<N;i++){
            int max = i;
            for(int j=i+1;j<N;j++){
                if(Math.abs(m.elmt(j,i))>Math.abs(m.elmt(max,i))){
                    max = j;
                }
            }
            m.swapRow(i,max);

            for(int j=i+1;i<N;i++){
                float factor = -(m.elmt(j,i)/m.elmt(i,i));
                m.addRow(j,i,factor);
            }
            // /* Cari pivot (menukar hingga didapat baris pertama) */
            // if(m.elmt(i,i)==0){
            //     for(int j=i+1;j<m.row;j++){
            //         if(m.elmt(j,i)!=0){
            //             m.swapRow(i,j);
            //             break;
            //         }
            //     }
            // }

            // /* Ubah elemen tidak nol pada baris pertama menjadi 1 */
            // float pivot = m.elmt(i,i);
            // if(pivot!=0){
            //     m.multRowByK(i, 1/pivot);
            // }

            // /*  */
            // for(int j=i+1;j<m.row;j++){
            //     float factor = -m.elmt(j,i);
            //     m.addRow(j,i,factor);
            // }
        }

        // for(int i = m.row-1;i>=0;i--){
        //     for(int j=i-1;j>=0;j--){
        //         float factor = -m.elmt(j,i);
        //         m.addRow(j,i,factor);
        //     }
        // }
        
        return m;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the number of rows: ");
        int rows = in.nextInt();
        System.out.print("Enter the number of columns: ");
        int cols = in.nextInt();
        
        Matrix mat = new Matrix(rows, cols);
        System.out.println("Enter matrix elements:");
        mat.readMatrix(in);
        
        System.out.println("Matrix before Gaussian elimination:");
        mat.printMatrix();
        
        mat=f(mat);
        
        System.out.println("Matrix after Gaussian elimination:");
        mat.printMatrix();
    }
}