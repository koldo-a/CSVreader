package lector;

import java.io.*;

public class CSVReader2 {	//archivo para eliminar saltos de linea
    public static void main(String[] args) {
        String inputFilePath = "C:\\Users\\JAVA\\Downloads\\archivo.csv";
        String outputFilePath = "C:\\Users\\JAVA\\Downloads\\archivo2.csv";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));

            String line;
            while ((line = reader.readLine()) != null) {
                // Reemplazar el salto de línea por un punto y coma
                line = line.replace("\n", ";");
                writer.write(line);
                writer.newLine();
            }

            reader.close();
            writer.close();

            System.out.println("Archivo preprocesado correctamente.");
        } catch (IOException e) {
            System.err.println("Error al procesar el archivo: " + e.getMessage());
        }
    }
}
