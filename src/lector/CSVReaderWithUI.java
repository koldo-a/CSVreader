package lector;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CSVReaderWithUI {
    private static String inputFilePath;
    private static String outputFilePath;
    private static JTextArea consoleTextArea; // Componente para mostrar los mensajes de consola

    public static void main(String[] args) {
        // Crear y configurar la ventana de selección de archivo
        JFrame frame = new JFrame("Seleccionar Archivo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 150);
        frame.setLayout(new FlowLayout());

        // Campo de texto para mostrar la ruta del archivo seleccionado
        JTextField textField = new JTextField(20);
        frame.add(textField);

        // Botón para seleccionar el archivo
        JButton selectButton = new JButton("Seleccionar Archivo");
        selectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    textField.setText(selectedFile.getAbsolutePath());
                }
            }
        });
        frame.add(selectButton);

        // Botón para procesar el archivo seleccionado
        JButton processButton = new JButton("Procesar Archivo");
        processButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Obtener la ruta del archivo seleccionado del campo de texto
                inputFilePath = textField.getText();
                outputFilePath = inputFilePath.replace(".txt", "_sin_saltos.txt");

                // Procesar el archivo seleccionado
                processFile();
            }
        });
        frame.add(processButton);
        
        // Botón para leer el archivo procesado
        JButton readButton = new JButton("Leer Archivo");
        readButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		// Obtener la ruta del archivo seleccionado del campo de texto
        		System.out.println("ejecutando lectura");
        		abrirArchivo();
        	}
        });
        frame.add(readButton);
        
        // JTextArea para mostrar los mensajes de consola
        consoleTextArea = new JTextArea();
        consoleTextArea.setEditable(false); // Hacer que el JTextArea no sea editable
        JScrollPane scrollPane = new JScrollPane(consoleTextArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Redirigir la salida estándar y la salida de error estándar a JTextArea
        PrintStream printStream = new PrintStream(new CustomOutputStream(consoleTextArea));
        System.setOut(printStream);
        System.setErr(printStream);
        // Mostrar la ventana
        frame.setVisible(true);
    }

    protected static void abrirArchivo() {

    	// Método para leer y abrir el archivo procesado
    	    try {
    	        File file = new File(outputFilePath);
    	        if (file.exists()) {
    	            Desktop.getDesktop().open(file);
    	            System.out.println("Archivo abierto correctamente.");
    	        } else {
    	            System.err.println("El archivo no existe.");
    	        }
    	    } catch (IOException e) {
    	        System.err.println("Error al abrir el archivo: " + e.getMessage());
    	    }
    	}

	private static void processFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Eliminar saltos de línea de la línea actual
                line = line.replaceAll("\\r|\\n", "");
                // Escribir la línea sin saltos de línea en el archivo de salida
                writer.write(line);
            }
            System.out.println("Saltos de línea eliminados correctamente.");
        } catch (IOException e) {
            System.err.println("Error al procesar el archivo: " + e.getMessage());
        }

        // Leer y mostrar el contenido del archivo procesado
        try (BufferedReader reader = new BufferedReader(new FileReader(outputFilePath))) {
            String line;
            System.out.println("Contenido del archivo procesado:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo procesado: " + e.getMessage());
        }
    }
    // Clase personalizada para redirigir la salida a un JTextArea
    private static class CustomOutputStream2 extends OutputStream {
        private JTextArea textArea;

        public CustomOutputStream2(JTextArea textArea) {
            this.textArea = textArea;
        }

        @Override
        public void write(int b) throws IOException {
            // Redirige datos al JTextArea
            textArea.append(String.valueOf((char) b));
            // Hace que el texto se muestre al instante
            textArea.setCaretPosition(textArea.getDocument().getLength());
        }
    }
    
 // Clase personalizada para redirigir la salida a un JTextArea
    private static class CustomOutputStream extends OutputStream {
        private JTextArea textArea;

        public CustomOutputStream(JTextArea textArea) {
            this.textArea = textArea;
        }

        @Override
        public void write(int b) throws IOException {
            // Redirige datos al JTextArea
            textArea.append(String.valueOf((char) b));
            // Hace que el texto se muestre al instante
            textArea.setCaretPosition(textArea.getDocument().getLength());
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            String s = new String(b, off, len);
            textArea.append(s);
            // Hace que el texto se muestre al instante
            textArea.setCaretPosition(textArea.getDocument().getLength());
        }
    }

}
