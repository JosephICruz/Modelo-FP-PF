/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examplepf;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVCrudExample {

    private static final String CSV_FILE_PATH = "data.csv";

    public static void main(String[] args) {
        // Crear datos de ejemplo
        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"1", "Juan", "25"});
        data.add(new String[]{"2", "María", "30"});
        data.add(new String[]{"3", "Pedro", "28"});

        // Crear el archivo CSV con los datos de ejemplo
        createCSV(data);

        // Leer el archivo CSV
        List<String[]> readData = readCSV();

        // Imprimir los datos leídos
        System.out.println("Datos leídos del archivo CSV:");
        for (String[] row : readData) {
            for (String cell : row) {
                System.out.print(cell + "\t");
            }
            System.out.println();
        }

        // Actualizar los datos en el archivo CSV
        updateCSV("2", new String[]{"2", "María", "31"});

        // Leer nuevamente el archivo CSV
        List<String[]> updatedData = readCSV();

        // Imprimir los datos actualizados
        System.out.println("\nDatos actualizados en el archivo CSV:");
        for (String[] row : updatedData) {
            for (String cell : row) {
                System.out.print(cell + "\t");
            }
            System.out.println();
        }

        // Eliminar una fila del archivo CSV
        deleteCSVRow("3");

        // Leer nuevamente el archivo CSV después de eliminar la fila
        List<String[]> newData = readCSV();

        // Imprimir los datos después de eliminar la fila
        System.out.println("\nDatos después de eliminar una fila del archivo CSV:");
        for (String[] row : newData) {
            for (String cell : row) {
                System.out.print(cell + "\t");
            }
            System.out.println();
        }
    }

    private static void createCSV(List<String[]> data) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_FILE_PATH))) {
            for (String[] row : data) {
                writer.println(String.join(",", row));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String[]> readCSV() {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                data.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    private static void updateCSV(String idToUpdate, String[] newData) {
        List<String[]> data = readCSV();
        for (int i = 0; i < data.size(); i++) {
            String[] row = data.get(i);
            if (row[0].equals(idToUpdate)) {
                data.set(i, newData);
                break;
            }
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_FILE_PATH))) {
            for (String[] row : data) {
                writer.println(String.join(",", row));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void deleteCSVRow(String idToDelete) {
        List<String[]> data = readCSV();
        data.removeIf(row -> row[0].equals(idToDelete));
        try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_FILE_PATH))) {
            for (String[] row : data) {
                writer.println(String.join(",", row));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
