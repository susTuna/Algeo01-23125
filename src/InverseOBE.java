import java.util.Scanner;

public class InverseOBE {
    public static Matrix inverseOBE(Matrix m, Scanner in){
        boolean unsolv = false;
        Matrix solusi = new Matrix(m.row,m.col);
        Matrix temp = new Matrix(m.row,m.col+m.col);
        if (Determinant.det(m, in)!=0 && m.row==m.col){

            // Perbesar matriks
            for (int i=0; i<temp.row; i++){
                // Isi setengah matriks temp (bagian kiri) dengan matriks input
                for (int j=0; j<m.col; j++){
                    temp.set(i, j, m.elmt(i, j));
                }
                // Isi setengah matriks temp (bagian kanan) dengan matriks identitas
                for (int k=m.col; k<m.col+m.col; k++){
                    if (k==i+m.col){
                        temp.set(i, k, 1);
                    }
                    else{
                        temp.set(i, k, 0);
                    }
                }
            }

            // OBE dengan Gauss-Jordan
            int N=Math.min(temp.row, temp.col);
            for (int i = 0; i < N; i++) {
                // Step 1: Find pivot (leading non-zero element in current column)
                if (temp.elmt(i, i) == 0) {
                    for (int j = i + 1; j < N; j++) {
                        if (temp.elmt(j, i) != 0) {
                            temp.swapRow(i, j);
                            break;
                        }
                    }
                }

                // Step 2: Normalize the row so that pivot becomes 1
                double pivot = temp.elmt(i, i);
                if (pivot != 0) {
                    temp.multRowByK(i, 1 / pivot);
                }

                // Step 3: Eliminate all elements below the pivot in the current column
                for (int j = i + 1; j < N; j++) {
                    if(!temp.isZeroRow(j)){
                        double factor = -temp.elmt(j, i);
                        temp.addRow(j, i, factor);
                    }
                    
                }
            }

            /* Eliminasi Gauss-Jordan */
            GaussJordan.mgaussJordan(temp, in);

            for (int i=0; i<temp.row; i++){
                // Cek setengah matriks temp (bagian kiri) apakah sudah menjadi matriks identitas
                for (int j=0; j<m.col; j++){
                    if (i==j && temp.elmt(i, j)!=1 || i!=j && temp.elmt(i, j)!=0){
                        unsolv = true;
                    }
                }
            }

            if(unsolv){
                System.out.println("Tidak dapat mencari inverse dengan metode OBE.");
                return null;
            } else {
                // Isi matriks solusi dengan setengah matriks temp (bagian kanan)
                for (int i=0; i<temp.row; i++){
                    for (int k=m.col; k<temp.col; k++){
                        solusi.set(i, k-m.col, temp.elmt(i, k));
                    } 
                }
            }
            return solusi;
            
        } else {
            return null;
        }
    }

    public static void save2file(Matrix m, Scanner in){ // save to file buat matrix ya
        String output;
        output="";
        for(int i=0;i<m.row;i++){
            for(int j=0;j<m.col;j++){
                if(j>0){
                    output+=(" "+Double.toString(m.elmt(i,j)));
                }else{
                    output+=(Double.toString(m.elmt(i,j)));
                }
            }
            if(i<m.row-1){
                output+=("\n");
            }
        }
        System.out.print("Tulis hasil dalam file .txt? (y/n): ");
            String txt = in.next();
            while (!txt.equalsIgnoreCase("y") && !txt.equalsIgnoreCase("n")) {
                System.out.print("Input tidak valid, silahkan input kembali: ");
                txt = in.next();
            }

            if (txt.equalsIgnoreCase("y")) {
                ReadWrite.txtWrite(in, output);
            }
    }

    public static void main(String[] args) {
        Matrix ans;
        Scanner in = new Scanner(System.in);

        Matrix mat;
        mat=ReadWrite.txtRead(in);

        System.out.println("Matriks sebelum operasi invers:");
        mat.printMatrix();
        
        ans=inverseOBE(mat, in);

        System.out.println("Matriks setelah operasi invers dengan OBE:");
        ans.printMatrix();

        save2file(mat, in);
    }
}
