import java.util.Scanner;

public class BicSplineInterpolation {
    public static Matrix xMatrix(){
        Matrix X = new Matrix(16, 16);
        int i,j,k,l,x,y;
        double val;
        for (i=0;i<X.row;i++){
            x=i%2;
            y=(i/2)%2;
            k=0;
            l=0;
            for (j=0;j<X.col;j++){
                val=0;
                if (i<4){
                    val=Math.pow(x, k)*Math.pow(y, l);
                }
                else if (i<8){
                    if (k!=0) val=k*Math.pow(x, k-1)*Math.pow(y, l);
                    
                } 
                else if (i<12){
                    if (l!=0) val=l*Math.pow(x, k)*Math.pow(y, l-1);
                }
                else if (i<16){
                    if (k!=0 && l!=0)val=k*l*Math.pow(x, k-1)*Math.pow(y, l-1);
                }
                X.set(i,j,(double)val);
                if (k==3) {
                     k=0;
                    l+=1;
                }
                else k+=1;
            }
        }
        return X;
    }

    public static Matrix readF(Scanner sc, Matrix m){
        int i,j,k=0;
        Matrix retMat = new Matrix (16,1);
        for (i=0;i<m.row-1;i++){
            for (j=0;j<m.col;j++){
                retMat.set(k,0,m.elmt(i,j));
                k+=1;
            }
        }
        return retMat;
    }

    public static double countResult(Matrix m,double a,double b){
        int i,j,k=0;
        double r=0;
        for (j=0;j<=3;j++){
            for (i=0;i<=3;i++){
                r+=m.elmt(k,0)*Math.pow(a, i)*Math.pow(b, j);
                k+=1;
            }
        }
        return r;
    }

    public static void BicInterpol(){
        double a, b, result;

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

            Matrix fVals=readF(in, m1);
            a=m1.elmt(m1.col, 0);
            b=m1.elmt(m1.col, 1);
            Matrix X=xMatrix();
            Matrix inverseX=InverseOBE.inverseOBE(X,in);
            Matrix aVals=Matrix.multMatrix(inverseX, fVals);

            result = countResult(aVals, a, b);
        } else { // if (choose == 2) 
            Matrix m2=ReadWrite.txtRead(in);
            Matrix fVals=readF(in, m2);
            a=m2.elmt(m2.col, 0);
            b=m2.elmt(m2.col, 1);
            Matrix X=xMatrix();
            Matrix inverseX=InverseOBE.inverseOBE(X,in);
            Matrix aVals=Matrix.multMatrix(inverseX, fVals);

            result = countResult(aVals, a, b);
        }
        String outString = "f(" + Double.toString(a) + "," + Double.toString(b) + ") = " + Double.toString(result);
        System.out.println(outString);
        
        System.out.print("\nTulis hasil dalam file .txt? (y/n): ");
        String txt = in.next();
        while (!txt.equalsIgnoreCase("y") && !txt.equalsIgnoreCase("n")) {
            System.out.print("Input tidak valid, silahkan input kembali: ");
            txt = in.next();
        }

        if (txt.equalsIgnoreCase("y")) {
            ReadWrite.txtWrite(in, outString);
        }
    }
}