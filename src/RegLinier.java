import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RegLinier {
    public static double[] regLin(Matrix m, Scanner in) {
        double[] err = {-405};
        
        // store x values (other than first column)
        Matrix x = new Matrix(m.row, m.col-1);
        for (int i=0; i<m.row; i++) {
            for (int j=1; j<m.col; j++) {
                x.set(i, j-1, m.elmt(i, j));
            }
        }
        
        // store y values (first column)
        Matrix y = new Matrix(m.row, 1);
        for (int i=0; i<m.row; i++) {
            y.set(i, 0, m.elmt(i, 0));
        }
        
        // hitung x^T * x
        Matrix xt = Matrix.transposeMatrix(x);
        Matrix xtx = Matrix.multMatrix(xt, x); // row = xt.row ; col = x.col

        // hitung x^T * y
        Matrix xty = Matrix.multMatrix(xt, y); // row = xt.row ; col = y.col

        Matrix temp = new Matrix(xt.row, x.col+y.col);
        // gabungkan xtx dan xty
        for (int i=0; i<temp.row; i++) {
            for (int j=0; j<xtx.col; j++) {
                temp.set(i, j, xtx.elmt(i, j));
                }
            for (int j=0; j<xty.col; i++) {
                temp.set(i, j+xty.col, xty.elmt(i, 0));
            }
        }
        
        
        Matrix solusiM = Gauss.mgauss(temp, in);
        // Penyelesaian gauss
        double[] solusi2 = new double[m.col-1];
        boolean[] isFree = new boolean[m.col-1];
        boolean unsolv=false;
        boolean hasFreeVar = false;
        List<Integer> freeVar = new ArrayList<>();
        /* Lakukan Substitusi Balik */
        for(int i=solusiM.row-1;i>=0;i--){
            if(solusiM.isZeroRow(i)&&solusiM.elmt(i,solusiM.col-1)!=0){
                return err;
            }
            else if(solusiM.isZeroRow(i)){
                freeVar.add(i); //variabel bebas
            }
            double sum=0;
            for(int j=i+1;j<solusiM.col-1;j++){
                sum+=m.elmt(i,j)*solusi2[j];
            }
            solusi2[i]=(solusiM.elmt(i,solusiM.col-1)-sum)/solusiM.elmt(i,i);
        }
        /* Menentukan adanya variabel bebas */
        for(int i=0;i<solusiM.row;i++){
            boolean foundPivot = false;
            for(int j=0;j<solusiM.col-1;j++){
                if(solusiM.elmt(i,j)!=0){
                    foundPivot=true;
                    break;
                }
            }
            if(!foundPivot){
                hasFreeVar=true; //variabel bebas ditemukan
                isFree[i]=true;
            }
        }

        /* Keluaran */
        if(unsolv){
            System.out.println("Tidak dapat mencari solusi regresi.");
            return err;
        }
        else if(hasFreeVar){
            System.out.println("Hasil perhitungan regresi menggunakan metode Gauss :");
            for(int i=0;i<solusiM.col-1;i++){
                if(isFree[i]){
                    System.out.println("beta"+(i+1)+" : variabel bebas");
                }
                else{
                    System.out.print("beta"+(i+1)+ " : ");
                    for(int j=i+1;j<solusiM.col-1;j++){
                        if(solusiM.elmt(i,j)!=0){
                            System.out.print(solusiM.elmt(i,j)+" * variabel bebas ");
                        }  
                    }
                    System.out.println();
                }
            }
            return err;
        }
        else{
            System.out.println("Hasil perhitungan regresi menggunakan metode Gauss :");
            for(int i=0;i<solusi2.length;i++){
                System.out.println("x"+(i+1)+" : "+solusi2[i]);
            }
            return solusi2;
        }
    }

    public static void call() {
        double[] ans;
        Scanner in = new Scanner(System.in);

        int choose;
        choose = ReadWrite.fileOrKeys(in);

        if (choose == 1) {
            System.out.print("Masukkan jumlah baris: ");
            int rows = in.nextInt();
            System.out.print("Masukkan jumlah kolom: ");
            int cols = in.nextInt();
            
            Matrix m1 = new Matrix(rows, cols);
            System.out.println("Masukkan matriks:");
            m1.readMatrix(in);
            
            System.out.println("Matriks sebelum regresi linier:");
            m1.printMatrix();

            ans = regLin(m1, in);
        } else { // if (choose == 2)
            Matrix mat;
            mat=ReadWrite.txtRead(in);

            System.out.println("Matriks sebelum regresi linier:");
            mat.readMatrix(in);

            ans = regLin(mat, in);
        }
        
        System.out.println("Solusi regresi linier:");
        for (int i=0; i < ans.length; i++) {
            System.out.print("beta" + (i+1) + " = " + ans[i]);
        }

        // Write to file
        ReadWrite.arrToFile(ans, in);
    }
}
