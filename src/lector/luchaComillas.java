package lector;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class luchaComillas {

	 public static void main(String[] args) {
	        String filePath = "C:\\Users\\JAVA\\Downloads\\archivo.csv"; // Ruta de tu archivo de texto
	        extractTextBetweenQuotes(filePath);
	    }

	    public static void extractTextBetweenQuotes(String filePath) {
	        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
	            StringBuilder textBetweenQuotes = new StringBuilder();
	            boolean inQuotes = false;

	            // Expresión regular para buscar comillas
	            Pattern pattern = Pattern.compile("\"(.*?)\"", Pattern.DOTALL);

	            String line;
	            while ((line = reader.readLine()) != null) {
	                Matcher matcher = pattern.matcher(line);
	                while (matcher.find()) {
	                    String text = matcher.group(1); // Texto entre las comillas
	                    textBetweenQuotes.append(text).append("\n"); // Añadir texto al resultado con nueva línea
	                }
	            }

	            System.out.println("Texto entre comillas:");
	            System.out.println(textBetweenQuotes.toString().trim()); // Mostrar resultado sin espacios adicionales
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}