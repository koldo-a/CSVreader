package lector;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CSVReader {

    public static void main(String[] args) throws IOException {
        String csvFile = "C:\\Users\\JAVA\\Downloads\\archivo.csv";
        String csvFileOut = "C:\\Users\\JAVA\\Downloads\\archivo2.csv";
        String line = "";
        String csvSplitBy = ",";
        StringBuilder field = new StringBuilder();
        boolean inQuotation = false;

        BufferedWriter writer = new BufferedWriter(new FileWriter(csvFileOut));
        
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                for (char c : line.toCharArray()) {
                    if (c == '"') {
                        inQuotation = !inQuotation;
                    } else if (c == '\n' || c == '\r' && !inQuotation) {
                        // Ignorar el salto de línea dentro de las comillas
                        continue;
                    } else if (c == ',' && !inQuotation) {
                        // Se imprime el campo cuando no estamos dentro de una cadena
                        String trimmedField = field.toString().replaceAll("^[^:]+:", "");
                        System.out.print(trimmedField + ",");
                        writer.write(trimmedField + ",");
                        field.setLength(0); // Se limpia el StringBuilder para el próximo campo
                    } else {
                        field.append(c); // Se añade el carácter al campo
                    }
                }
                // Se imprime el último campo de la línea
                String trimmedField = field.toString().replaceAll("^[^:]+:", "").trim();
                System.out.println("," + trimmedField+",");
                writer.write("," + trimmedField+",");
                writer.newLine(); // Agregar nueva línea al final de cada línea
                field.setLength(0); // Se limpia el StringBuilder para la próxima línea
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer.close(); // Se asegura de cerrar el BufferedWriter al finalizar
        }
    }
}
