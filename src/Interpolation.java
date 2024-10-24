import java.util.Scanner;

public class Interpolation {
    
    public static double findX0(Matrix m){
        double X0=m.elmt(0, 1); 
        
        //traversal hanya dilakukan di col 1 karena col 1 berisi x^1
        for (int i=0;i<m.row;i++){
            if (m.elmt(i, 1)<X0){
                X0=m.elmt(i, 1);
            }
        }
        return X0;
    }

    public static double findX0c1(Matrix m){
        double X0=m.elmt(0, 0); 
        
        //traversal hanya dilakukan di col 1 karena col 1 berisi x^1
        for (int i=0;i<m.row;i++){
            if (m.elmt(i, 0)<X0){
                X0=m.elmt(i, 0);
            }
        }
        return X0;
    }

    public static double findXn(Matrix m){
        double Xn=m.elmt(0, 1); 
        
        //traversal hanya dilakukan di col 1 karena col 1 berisi x^1
        for (int i=0;i<m.row;i++){
            if (m.elmt(i, 1)>Xn){
                Xn=m.elmt(i, 1);
            }
        }
        return Xn;
    }

    public static double findXnc1(Matrix m){
        double Xn=m.elmt(0, 0); 
        
        //traversal hanya dilakukan di col 1 karena col 1 berisi x^1
        for (int i=0;i<m.row;i++){
            if (m.elmt(i, 0)>Xn){
                Xn=m.elmt(i, 0);
            }
        }
        return Xn;
    }

    public static boolean arePointsUnique(Matrix m){
        boolean unique=true;
        int i,j;
        i=0;
        while ((i<m.row-1)&&(unique)){
            j=i+1;
            while ((j<m.row)&&(unique)){
                if ((m.elmt(i, 0)==m.elmt(j, 0))&&(m.elmt(i, 1)==m.elmt(j, 1))){
                    unique=false;
                }
                j+=1;
            }
            i+=1;
        }
        return unique;
    }

    public static double[] interpolMatrix(Matrix m){
        double[] err = {-405};
        double[] solusi = new double[m.col-1];
        /* Ubah Ke Matrix Eselon */
        int N=m.row;
        for (int i = 0; i < N; i++) {
            if (m.elmt(i, i) == 0) {
                for (int j = i + 1; j < N; j++) {
                    if (m.elmt(j, i) != 0) {
                        m.swapRow(i, j);
                        break;
                    }
                }
            }
            double pivot = m.elmt(i, i);
            if (pivot != 0) {
                m.multRowByK(i, 1 / pivot);
            }
            for (int j = i + 1; j < N; j++) {
                if(!m.isZeroRow(j)){
                    double factor = -m.elmt(j, i);
                    m.addRow(j, i, factor);
                }
                
            }
        }
        /* Lakukan Substitusi Balik */
        for(int i=m.row-1;i>=0;i--){
            if(m.isZeroRow(i)&&m.elmt(i,m.col-1)!=0){
                break; //tidak ada solusi
            }
            else if(m.isZeroRow(i)){
                break; //variabel bebas
            }
            double sum=0;
            for(int j=i+1;j<m.col-1;j++){
                sum+=m.elmt(i,j)*solusi[j];
            }
            solusi[i]=(m.elmt(i,m.col-1)-sum)/m.elmt(i,i);
        }
            return solusi;
    }

    public static double[] polynomialInterpolation(double[] x, double[] y) {
        int n = x.length; // number of data points
        Matrix m = new Matrix(n, n + 1);

        // Construct the Vandermonde matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                m.set(i, j, Math.pow(x[i], j)); // x[i]^j
            }
            m.set(i, n, y[i]); // Add y[i] as the augmented column
        }

        // Solve the system using Gaussian elimination (function provided)
        double[] coefficients = interpolMatrix(m);

        return coefficients; // Return the polynomial coefficients
    }

    public static void interpolF(Scanner in){
        Matrix m = new Matrix(0, 0);
        double taksiran = 0;
        double hasil = 0;
        double[] solusi = null;

        int choice;
        choice = ReadWrite.fileOrKeys(in);

        if (choice == 1) {
            System.out.println("\nMasukkan jumlah titik yang akan diinterpolasi (minimal 2): ");
            int n = Main.cinMinCheck(2,in);
            m = new Matrix(n,2);
            double[] x = new double[n];
            double[] y = new double[n];
            while (true){
                System.out.println("\nMasukkan "+n+" titik (Format: xn yn lalu diakhiri enter)");
                m.readMatrix(in);
                if (arePointsUnique(m)){
                    break;
                }
                System.out.println("\nData tidak valid! Setiap titik harus berbeda");
            }
            System.out.println("\nMasukkan nilai x yang akan ditaksir nilai fungsinya: ");
            taksiran=in.nextDouble();
            while (taksiran<findX0c1(m) || taksiran>findXnc1(m)){
                System.out.println("\nInput tidak valid, silahkan input kembali");
                System.out.println("Nilai x harus berada di antara x0 sampai xn");
                taksiran=in.nextDouble();
            }
            for(int i=0;i<m.row;i++){
                x[i] = m.elmt(i,0);
                y[i] = m.elmt(i,1);
            }
            solusi = polynomialInterpolation(x,y);
        } else {
            Matrix mat = ReadWrite.txtRead(in);
            Matrix interpolmat = new Matrix(mat.row-1,mat.row);
            for (int i=0;i<interpolmat.row;i++){
                double ex=mat.element[i][0];
                for (int j=0;j<interpolmat.row;j++){
                    interpolmat.set(i,j,Math.pow(ex,j));
                }
                interpolmat.set(i,interpolmat.col-1,mat.element[i][1]);
            }
            taksiran=mat.elmt(mat.row-1, 0);
            if (taksiran<findX0(interpolmat) || taksiran>findXn(interpolmat)){
                System.out.println("\nData tidak valid! Nilai x harus berada di antara x0 sampai xn");
                return;
            }
            if (!arePointsUnique(mat)){
                System.out.println("\nData tidak valid! Setiap titik harus berbeda");
                return;
            }
            solusi = interpolMatrix(interpolmat);
        }

        System.out.println("\nHasil perhitungan interpolasi polinomial :");
        String outPut;
        String outPutH;
        outPutH = "Dengan taksiran untuk f(";
        outPut = "f(x) = ";
            for(int i=0;i<solusi.length;i++){
                hasil+=solusi[i]*Math.pow(taksiran,i);
                if(i!=0){
                    if(solusi[i]<0){
                        outPut+=(" - "+Double.toString(-solusi[i])+"x^"+(i));
                    }
                    else{
                        outPut+=(" + "+Double.toString(solusi[i])+"x^"+(i));
                    }
                }else{
                    outPut+=(Double.toString(solusi[i]));
                }
            }
            outPutH+=(Double.toString(taksiran)+") adalah " + Double.toString(hasil));
            System.out.println(outPut);
            System.out.println(outPutH);
            

            System.out.print("\nTulis hasil dalam file .txt? (y/n): ");
            String txt = in.next();
            while (!txt.equalsIgnoreCase("y") && !txt.equalsIgnoreCase("n")) {
                System.out.print("Input tidak valid, silahkan input kembali: ");
                txt = in.next();
            }

            if (txt.equalsIgnoreCase("y")) {
                StringBuilder output = new StringBuilder();
                output.append(outPut).append("\n").append(outPutH).append("\n");
                ReadWrite.txtWrite(in, output.toString());
            }
    }
}