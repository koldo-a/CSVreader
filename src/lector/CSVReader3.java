package lector;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CSVReader3 {
    public static void main(String[] args) {
        String inputFilePath = "C:\\Users\\JAVA\\Downloads\\archivo.csv";
        String outputFilePath = "C:\\Users\\JAVA\\Downloads\\archivo2.csv";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));

            String line;
            while ((line = reader.readLine()) != null) {
            	System.out.println(line);
                if (line.contains("\"")) {
                	System.out.println("contiene ");
                	// Eliminar las comillas al principio y al final
                    line = line.substring(1, line.length() - 1);
                    // Reemplazar comillas dobles con comas y agregar comas al inicio y al final
                    line = "," + line.replaceAll("\"", "") + ",";
                    writer.write(line);
                }
            }

            reader.close();
            writer.close();

            System.out.println("Archivo procesado correctamente.");
        } catch (IOException e) {
            System.err.println("Error al procesar el archivo: " + e.getMessage());
        }
    }
}
