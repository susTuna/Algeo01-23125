import java.util.Scanner;

public class Matrix {
    double element[][];
    int row;
    int col;

    /* Konstruktor */
    public Matrix(int row, int col){
        this.row = row;
        this.col = col;
        this.element = new double[row][col];
    }

    /* Konstruktor */
    public void set(int row, int col, double val){
        this.element[row][col] = val;
    }

    /* Selektor */
    public double elmt(int row, int col){
        return this.element[row][col];
    }

    /* Baca Matrix */
    public void readMatrix(Scanner in){
        for(int i=0;i<this.row;i++){
            for(int j=0;j<this.col;j++){
                this.element[i][j] = in.nextDouble();
            }
        }
    }

    /* Tulis Matrix */
    public void printMatrix(){
        for(int i=0;i<this.row;i++){
            for(int j=0;j<this.col;j++){
                if(elmt(i,j)==-0){
                    set(i,j,0); //handle case nilai -0
                }
                if(j==0){ //handle first col
                    System.out.print(elmt(i,j));
                }else{
                    System.out.print(" "+elmt(i,j));
                }
            }
            System.out.println(); //print new line
        }
    }

    /* OBE */
    /* Tukar 2 Baris */
    public void swapRow(int r1, int r2){
        double tmp[] = this.element[r1];
        this.element[r1] = this.element[r2];
        this.element[r2] = tmp;
    }

    public void swapCol(int c1, int c2){
        for(int i=0;i<this.row;i++){
            double tmp = this.elmt(i,c1);
            this.set(i,c1,this.elmt(i,c2));
            this.set(i,c2,tmp);
        }
    }

    /* Perkalian Baris dengan Konstanta */
    public void multRowByK(int r, double k){
        for(int i=0;i<this.col;i++){
            this.element[r][i] *= k;
        }
    }

    /* Penjumlahan Baris dengan Kelipatan Baris Lain*/
    public void addRow(int r1, int r2, double k){
        for(int i=0;i<this.col;i++){
            this.element[r1][i] += this.element[r2][i]*k;
        }
    }

    /* Perkalian Matrix */
    public static Matrix multMatrix(Matrix m1, Matrix m2){
        Matrix m3 = new Matrix(m1.row,m2.col);
        int val = 0;
        for(int i=0;i<m1.row;i++){
            for(int j=0;j<m2.col;j++){
                for(int k=0;k<m1.col;k++){
                    val += (m1.elmt(i,k)*m2.elmt(k,j));
                }
                m3.set(i,j,val);
            }
        }
        return m3;
    }

    /* Transpose Matrix */
    public static Matrix transposeMatrix(Matrix m){
        Matrix m_tmp = new Matrix(m.col,m.row);
        for(int i=0;i<m_tmp.row;i++){
            for(int j=0;j<m_tmp.col;j++){
                m_tmp.set(i,j,m.elmt(j,i));
            }
        }
        return m_tmp;
    }

    /* Check Zero Row */
    public boolean isZeroRow(int r){
        for(int i=0;i<this.col-1;i++){ //skip augmented matrix
            if(this.element[r][i]!=0){
                return false;
            }
        }
        return true;
    }

    /* Check If Matrix Is Square */
    public boolean isSquare() {
        return this.row == this.col;
    }
}
