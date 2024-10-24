import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

public class ReadWrite {
    public static int fileOrKeys(Scanner in) {
        int choice;
        String line;
        String[] row = new String[100];

        System.out.println("\nPILIH CARA INPUT MATRIKS!");
        System.out.println("1. Dari keyboard");
        System.out.println("2. Dari file");
        System.out.print("Pilihan: ");
        do{
            System.out.print(">>");
            line = in.nextLine();
            row = line.split(" ");
            try{
                choice = Integer.parseInt(row[0]);

            }catch(NumberFormatException e){
                choice = 0;
            }
            if (choice <= 0 || choice > 2){
                System.out.println("\nInput tidak valid. Coba lagi.");
            }
        }while(choice <= 0 || choice > 2);
        
        return choice;
    }

    public static Matrix txtRead(Scanner in) {
    Matrix A = new Matrix(0, 0);

    // Input file name
    String namaFile = "";
    while (namaFile.isEmpty()) {
        System.out.print("\nMasukkan nama file (<namafile>.txt): ");
        namaFile = in.next();
    }
    System.out.println("Nama file yang dipilih: " + namaFile);

    // Try accessing the file
    try {
        // Declare file variable
        File txt = new File("test\\" + namaFile);

        // Determine matrix size by counting lines and columns
        int rowSize = 0, colSize = 0;
        try (Scanner reader = new Scanner(txt)) {
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                if (rowSize == 0) {
                    colSize = line.split(" ").length; // Get column size from first row
                }
                rowSize++;
            }
        }

        // Initialize matrix with determined size
        A = new Matrix(rowSize, colSize);

        // Populate matrix with values
        try (Scanner reader = new Scanner(txt)) {
            int i = 0;
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] row = line.split(" ");
                for (int j = 0; j < row.length; j++) {
                    A.set(i, j, Double.parseDouble(row[j]));
                }
                i++;
            }
        }
    } catch (FileNotFoundException e) {
        System.out.println("\nError: File tidak ditemukan. Pesan error: " + e.getMessage());
    } catch (Exception e) {
        System.out.println("\nError saat parsing file: " + e.getMessage());
    }

    return A;
}


    public static void txtWrite(Scanner in, String str){
        // Input nama file
        String namaFile = "";
        while (namaFile.isEmpty()) {
            System.out.print("Masukkan nama file (<namafile>.txt): ");
            namaFile = in.next();
        }
        System.out.println("Nama file yang dipilih: " + namaFile);

        try  {// Akses file
            File file = new File("test\\" + namaFile);
            FileWriter writer = new FileWriter(file, true);
            writer.write(str);
            writer.write("\n");
            writer.close();
            System.out.println("Berhasil menulis ke file " + namaFile);
        } catch (FileNotFoundException e) {
            System.out.println("Error: File tidak ditemukan. Pesan error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void doubleToFile(double value, Scanner in) {
        System.out.print("\nTulis hasil dalam file .txt? (y/n): ");
        String txt = in.next();
        while (!txt.equalsIgnoreCase("y") && !txt.equalsIgnoreCase("n")) {
            System.out.print("Input tidak valid, silahkan input kembali: ");
            txt = in.next();
        }
    
        if (txt.equalsIgnoreCase("y")) {
            String output = "Nilai : " + value + "\n";
            txtWrite(in, output);
        }
    }

    public static void arrToFile(double[] solusi, Scanner in) {
        System.out.print("\nTulis hasil dalam file .txt? (y/n): ");
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
            txtWrite(in, output.toString());
        }
    }
    
    public static void matrixToFile(Matrix solusi, Scanner in) {
        System.out.print("\nTulis hasil dalam file .txt? (y/n): ");
        String txt = in.next();
        while (!txt.equalsIgnoreCase("y") && !txt.equalsIgnoreCase("n")) {
            System.out.print("Input tidak valid, silahkan input kembali: ");
            txt = in.next();
        }
    
        if (txt.equalsIgnoreCase("y")) {
            StringBuilder output = new StringBuilder();
            for (int i = 0; i < solusi.row; i++) {
                for (int j = 0; j < solusi.col; j++) {
                    output.append(solusi.elmt(i, j)).append(" ");
                }
                output.append("\n");  // Move to the next line after each row
            }
            txtWrite(in, output.toString());
        }
    }
    

    
}
