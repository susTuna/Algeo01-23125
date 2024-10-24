import java.util.Scanner;

public class Main {
    public static int cinCheck(int a,int b,Scanner in){
        int input;
        do {input=in.nextInt();
        }while (input<a||input>b);
        return input;
    }
    
    public static int cinMinCheck(int a,Scanner in){
        int input;
        do {input=in.nextInt();
        }while (input<a);
        return input;
    }

    // MAIN
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        boolean run = true;
        int input = 0;
        String line;
        String[] row = new String[100];

        while (run){
            System.out.print("\033[H\033[2J");
            System.out.print("\nMATRIX CALCULATOR\n");
            System.out.println("\nMENU");
            System.out.println("1. Sistem Persamaan Linier");
            System.out.println("2. Determinan");
            System.out.println("3. Invers");
            System.out.println("4. Interpolasi Polinomial");
            System.out.println("5. Interpolasi Bicubic Spline");
            System.out.println("6. Regresi Berganda");
            System.out.println("7. Keluar");
            
            do {
                // Terima input pilihan menu dari pengguna
                System.out.print(">>");
                line = in.nextLine();
                row = line.split(" ");
                // error handling
                try{
                    input = Integer.parseInt(row[0]);

                }catch (NumberFormatException e){
                    input = 0;
                }
                if (input <= 0 || input > 7){
                    System.out.println("Input tidak valid. Coba lagi.");
                }
            }while(input <= 0 || input > 7);

            switch(input){
                // SISTEM PERSAMAAN LINIER
                case 1:
                System.out.print("\033[H\033[2J"); // clear screen
                System.out.println("SISTEM PERSAMAAN LINIER!");
                System.out.println("\nPILIH METODE");
                System.out.println("1. Metode Eliminasi Gauss");
                System.out.println("2. Metode Eliminasi Gauss-Jordan");
                System.out.println("3. Metode Invers");
                System.out.println("4. Kaidah Cramer");
                System.out.println("5. BACK");
                do{
                    // Terima input pilihan metode SPL dari pengguna
                    System.out.print(">>");
                    line = in.nextLine();
                    row = line.split(" ");
                    try{
                        input = Integer.parseInt(row[0]);
    
                    }catch(NumberFormatException e){
                        input = 0;
                    }
                    if (input <= 0 || input > 5){
                        System.out.println("Input tidak valid. Coba lagi.");
                    }
                }while(input <= 0 || input > 5);

                switch(input){
                    case 1:
                    System.out.print("\033[H\033[2J");
                    System.out.println("SPL METODE GAUSS!\n");
                    Gauss.call();
                    break;
                    
                    case 2:
                    System.out.print("\033[H\033[2J");
                    System.out.println("SPL METODE GAUSS-JORDAN!\n");
                    GaussJordan.call();
                    break;
                    
                    case 3:
                    System.out.print("\033[H\033[2J");
                    System.out.println("SPL METODE INVERS!\n");
                    InverseSPL.call();
                    break;

                    case 4:
                    System.out.print("\033[H\033[2J");
                    System.out.println("SPL DENGAN KAIDAH CRAMER!\n");
                    Cramer.call();
                    break;

                    case 5:
                    System.out.println("\n...BACK TO MAIN MENU...");
                    break;
                }
                break;

                case 2:
                System.out.print("\033[H\033[2J");
                System.out.println("DETERMINAN!");
                System.out.println("\nPILIH METODE");
                System.out.println("1. Metode Reduksi Baris");
                System.out.println("2. Metode Kofaktor");
                System.out.println("3. BACK");
                do{
                    System.out.print(">>");
                    line = in.nextLine();
                    row = line.split(" ");
                    try{
                        input = Integer.parseInt(row[0]);
    
                    }catch(NumberFormatException e){
                        input = 0;
                    }
                    if (input <= 0 || input > 3){
                        System.out.println("Input tidak valid. Coba lagi.");
                    }
                }while(input <= 0 || input > 3);

                switch(input){
                    case 1:
                    System.out.print("\033[H\033[2J");
                    System.out.println("DETERMINAN METODE REDUKSI BARIS!\n");
                    Determinant.call();
                    break;
                    
                    case 2:
                    System.out.print("\033[H\033[2J");
                    System.out.println("DETERMINAN METODE KOFAKTOR!\n");
                    DeterminanKofaktor.call();
                    break;

                    case 3:
                    System.out.println("\n...BACK TO MAIN MENU...");
                    break;
                }
                break;

                case 3:
                System.out.print("\033[H\033[2J");
                System.out.println("INVERS!");
                System.out.println("\nPILIH METODE");
                System.out.println("1. Metode OBE");
                System.out.println("2. Metode Adjoin");
                System.out.println("3. BACK");
                do{
                    System.out.print(">>");
                    line = in.nextLine();
                    row = line.split(" ");
                    try{
                        input = Integer.parseInt(row[0]);
    
                    }catch(NumberFormatException e){
                        input = 0;
                    }
                    if (input <= 0 || input > 3){
                        System.out.println("Input tidak valid. Coba lagi.");
                    }
                }while(input <= 0 || input > 3);

                switch(input){
                    case 1:
                    System.out.print("\033[H\033[2J");
                    System.out.println("INVERS METODE OBE!\n");
                    InverseOBE.call();
                    break;
                    
                    case 2:
                    System.out.print("\033[H\033[2J");
                    System.out.println("INVERS METODE ADJOIN!\n");
                    InverseAdj.call();
                    break;

                    case 3:
                    System.out.println("\n...BACK TO MAIN MENU...");
                    break;
                }
                break;

                case 4:
                System.out.print("\033[H\033[2J");
                System.out.println("INTERPOLASI POLINOMIAL!\n");
                Interpolation.interpolF(in);
                break;

                case 5:
                System.out.print("\033[H\033[2J");
                System.out.println("BICUBIC SPLINE INTERPOLATION!\n");
                BicSplineInterpolation.BicInterpol();
                break;

                case 6:
                System.out.print("\033[H\033[2J");
                System.out.println("REGRESI BERGANDA!");
                System.out.println("\nPILIH METODE");
                System.out.println("1. Linier");
                System.out.println("2. Kuadratik");
                System.out.println("3. BACK");
                do{
                    System.out.print(">>");
                    line = in.nextLine();
                    row = line.split(" ");
                    try{
                        input = Integer.parseInt(row[0]);
    
                    }catch(NumberFormatException e){
                        input = 0;
                    }
                    if (input <= 0 || input > 3){
                        System.out.println("Input tidak valid. Coba lagi.");
                    }
                }while(input <= 0 || input > 3);

                switch(input){
                    case 1:
                    System.out.print("\033[H\033[2J");
                    System.out.println("REGRESI LINIER BERGANDA!\n");
                    RegLinier.call();
                    break;
                    
                    case 2:
                    System.out.print("\033[H\033[2J");
                    System.out.println("REGRESI KUADRATIK BERGANDA!\n");
                    RegQuadratic.call();
                    break;

                    case 3:
                    System.out.println("\n...BACK TO MAIN MENU...");
                    break;
                }
                break;

                case 7:
                System.out.print("\033[H\033[2J");
                System.out.print("THANKS FOR USING THIS CALCULATOR!\n");
                System.out.print("BYE BYEEE!");
                run = false;
                break;

            }
        }

    }
}
