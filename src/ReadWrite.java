import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadWrite {
    public static Matrix txtRead(Scanner in){
        Matrix A = null;

        // Input nama file
        String namaFile = "";
        while (namaFile.isEmpty()) {
            System.out.print("Masukkan nama file (<namafile>.txt): ");
            namaFile = in.next();
        }
        System.out.println("Nama file yang dipilih: " + namaFile);

        // Akses file
        File file = new File("test\\" + namaFile);
        try (Scanner reader = new Scanner(file)) {
            int rowSize = 0, colSize = 0;
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                if (rowSize == 0) {
                    colSize = line.split(" ").length;
                }
                rowSize++;
            }

            A = new Matrix(rowSize, colSize);

            reader.reset();
            try (Scanner dataReader = new Scanner(file)) {
                int i = 0;
                while (dataReader.hasNextLine()) {
                    String line = dataReader.nextLine();
                    String[] row = line.split(" ");
                    for (int j = 0; j < colSize; j++) {
                        A.set(i, j, Double.parseDouble(row[j]));
                    }
                    i++;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File tidak ditemukan. Pesan error: " + e.getMessage());
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
}
