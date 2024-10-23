import java.util.Scanner;
import java.lang.Math;

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

    public static void BicInterpol(Scanner in){
        double a,b;
        Matrix readMat=ReadWrite.txtRead(in);        
        Matrix fVals=readF(in, readMat);
        a=readMat.elmt(readMat.col, 0);
        b=readMat.elmt(readMat.col, 1);
        Matrix X=xMatrix();
        Matrix inverseX=InverseOBE.inverseOBE(X,in);
        Matrix aVals=Matrix.multMatrix(inverseX, fVals);
        double result = countResult(aVals, a, b);
        String outString = "f(" + Double.toString(a) + "," + Double.toString(b) + ") = " + Double.toString(result);
        System.out.println(outString);
        System.out.print("Tulis hasil dalam file .txt? (y/n): ");
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