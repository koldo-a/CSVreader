package lector;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class CSVReaderWithUI2 {
    private static String inputFilePath;
    private static String outputFilePath;
    private static JTextArea consoleTextArea; // Componente para mostrar los mensajes de consola
    private static JTable table;

    public static void main(String[] args) {
        // Crear y configurar la ventana de selección de archivo
        JFrame frame = new JFrame("Seleccionar Archivo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(404, 350);
        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setVgap(12);
        borderLayout.setHgap(12);
        frame.getContentPane().setLayout(borderLayout);

        // Panel para el campo de texto y el botón de selección de archivo
        BorderLayout bl_fileSelectionPanel = new BorderLayout();
        bl_fileSelectionPanel.setVgap(12);
        bl_fileSelectionPanel.setHgap(12);
        JPanel fileSelectionPanel = new JPanel(bl_fileSelectionPanel);

        // Campo de texto para mostrar la ruta del archivo seleccionado
        JTextField textField = new JTextField(20);
        textField.setForeground(new Color(0, 0, 255));
        fileSelectionPanel.add(textField, BorderLayout.CENTER);

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
        
        table = new JTable();
        table.setForeground(new Color(0, 0, 255));
        fileSelectionPanel.add(table, BorderLayout.NORTH);
        fileSelectionPanel.add(selectButton, BorderLayout.EAST);

        // Agregar el panel de selección de archivo a la región norte de la ventana
        frame.getContentPane().add(fileSelectionPanel, BorderLayout.NORTH);

        // Panel para botones
        JPanel buttonPanel = new JPanel(new FlowLayout());

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
        buttonPanel.add(processButton);

        // Botón para leer el archivo procesado
        JButton readButton = new JButton("Leer Archivo");
        readButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Llamar al método para abrir el archivo procesado
                openFile();
            }
        });
        buttonPanel.add(readButton);

        // Agregar el panel de botones a la región sur de la ventana
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // JTextArea para mostrar los mensajes de consola
        consoleTextArea = new JTextArea();
        consoleTextArea.setForeground(new Color(255, 0, 0));
        consoleTextArea.setBackground(new Color(236, 231, 221));
        consoleTextArea.setEditable(false); // Hacer que el JTextArea no sea editable
        JScrollPane scrollPane = new JScrollPane(consoleTextArea);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Redirigir la salida estándar y la salida de error estándar a JTextArea
        PrintStream printStream = new PrintStream(new CustomOutputStream(consoleTextArea));
        System.setOut(printStream);
        System.setErr(printStream);

        // Mostrar la ventana
        frame.setVisible(true);
    }

    // Método para procesar el archivo
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
    }

    // Método para abrir el archivo procesado
    private static void openFile() {
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
