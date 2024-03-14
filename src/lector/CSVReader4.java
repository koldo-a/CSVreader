package lector;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CSVReader4 {
	public static final String inputFilePath = "C:\\Users\\JAVA\\Downloads\\archivo.txt"; // Ruta del archivo de entrada
	public static final String outputFilePath = "C:\\Users\\JAVA\\Downloads\\archivo_sin_saltos.txt"; // Ruta del
																										// archivo de
																										// salida

	public static void main(String[] args) {

		try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
				BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				// Eliminar saltos de línea de la línea actual
				line = line.replaceAll("\\r|\\n", "");
				// Escribir la línea sin saltos de línea en el archivo de salida
				writer.write(line);
			}
			System.out.println(outputFilePath);
			System.out.println("Saltos de línea eliminados correctamente.");
		} catch (IOException e) {
			System.err.println("Error al procesar el archivo: " + e.getMessage());
		}
		
		ReadFile();
	}
	
	

	private static void ReadFile(){

	try(
	BufferedReader reader = new BufferedReader(new FileReader(outputFilePath)))
	{
		String line;
		while ((line = reader.readLine()) != null) {
			System.out.println(line); // Imprime cada línea en la consola
		}
	}catch(
	IOException e)
	{
		System.err.println("Error al leer el archivo: " + e.getMessage());
	}
}}
