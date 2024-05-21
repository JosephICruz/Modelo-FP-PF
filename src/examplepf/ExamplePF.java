package examplepf;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author josephcruz
 */
public class ExamplePF {

    private static final String csv_archivo_Inicio = "data.csv";
    private static final String delimitador = ",";
    private static final String salto_de_linea = "\n";

    public static final String negro = "\033[30m";
    public static final String rojo = "\033[31m";
    public static final String verde = "\033[32m";
    public static final String amarillo = "\033[33m";
    public static final String azul = "\033[34m";
    public static final String morado = "\033[35m";
    public static final String cyan = "\033[36m";
    public static final String blanco = "\033[37m";

    public static void main(String[] args) throws AWTException {
        Robot r = new Robot();

        int eleccion;

        do {

            menuInicio();
            mensaje("Seleccione una Opcion:");
            eleccion = capturarNumero();

            switch (eleccion) {

                case 1:
                    int contador = 0;

                    do {
                        mensaje(verde + "-----------------------------------------------\n");
                        mensaje(verde + "                 LOGIN                         \n");
                        mensaje(verde + "-----------------------------------------------\n");

                        mensaje("Ingrese su usuario:\n");
                        String usuarioCliente = capturartexto();

                        mensaje("Ingrese su contrasena:\n");
                        String contrasenaCliente = capturartexto();

                        boolean estado = login(usuarioCliente, contrasenaCliente);

                        if (estado == true) {
                            mensaje("el usuario  existe\n");
                            contador = 4;
                            eleccion = 0;

                        } else {
                            mensaje("el usuario NO existe\n");
                            contador++;

                        }

                        if (contador == 3) {

                            r.delay(15000);
                            contador = 0;

                        }

                    } while (contador < 4);

                    break;
                case 2:
                    mensaje(verde + "-----------------------------------------------\n");
                    mensaje(verde + "                 REGISTRARSE                   \n");
                    mensaje(verde + "-----------------------------------------------\n");

                    mensaje("Ingrese su Nombre:\n");
                    String nombre = capturartexto();

                    mensaje("Ingrese su DNI\n");
                    String dni = capturartexto();

                    mensaje("Ingrese su usuario:\n");
                    String usuario = capturartexto();

                    mensaje("Ingrese su contrasena:\n");
                    String contrasena = capturartexto();

                    guardarRegistro(nombre, dni, usuario, contrasena);

                    break;
                default:
                    mensaje(verde + "GRACIAS POR USAR EL SISTEMA\n");
            }

        } while (eleccion != 0);

        menuPrincipal();

    }

    //----------funciones-------------
    public static void menuInicio() {

        System.out.println(verde + "-----------------------------------------------");
        System.out.println("                    *MENU DE INICIO                  ");
        System.out.println(verde + "-----------------------------------------------");
        System.out.println("Opciones:");
        System.out.println("         1.Login");
        System.out.println("         2.Registrarse");
        System.out.println("");
        System.out.println("         0.-Salir");
        System.out.println(verde + "-----------------------------------------------");

    }

    public static void menuPrincipal() {

        System.out.println(verde + "-----------------------------------------------");
        System.out.println("                    *MENU PRINCIPAL*                 ");
        System.out.println(verde + "-----------------------------------------------");
        System.out.println("Opciones:");
        System.out.println("         1.Buscar cliente");
        System.out.println("         2.Registrar cliente");
        System.out.println("         3.xxxxxxxxxxxxx");
        System.out.println("");
        System.out.println("         4.xxxxxxxxxx");
        System.out.println("         5.xxxxxxxxxxxxxx");
        System.out.println("");
        System.out.println("         0.-Salir");
        System.out.println(verde + "-----------------------------------------------");

    }

    public static void mensaje(String mensaje) {

        System.out.print(mensaje);
    }

    public static int capturarNumero() {

        Scanner sc = new Scanner(System.in);
        int numero = sc.nextInt();
        return numero;
    }

    public static String capturartexto() {

        Scanner sc = new Scanner(System.in);
        String texto = sc.nextLine();
        return texto;
    }

    public static void guardarRegistro(String nombre, String dni, String usuario, String contrasena) {

        String datos = nombre + "," + dni + "," + usuario + "," + contrasena;
        try (FileWriter fileWriter = new FileWriter(csv_archivo_Inicio, true); BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter.write(datos);
            bufferedWriter.write(salto_de_linea);
            System.out.println(amarillo + "Registro creado exitosamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static boolean login(String usuarioCliente, String contrasenaCliente) {

        boolean resultado = true;
        boolean found = false;
        try (BufferedReader br = new BufferedReader(new FileReader(csv_archivo_Inicio))) {
            String line;

            while ((line = br.readLine()) != null) {
                // Utiliza split para separar los datos por comas
                String[] datos = line.split(",");

                // Verifica si ambos valores están en la línea actual
                if (contieneValor(datos, usuarioCliente) && contieneValor(datos, contrasenaCliente)) {
                    found = true;
                    break; // Si ambos valores están, no es necesario continuar buscando
                }
            }

            if (found) {
                return resultado = true;
            } else {
                resultado = false;
                return resultado;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    // Método para verificar si un valor está presente en un array de Strings
    private static boolean contieneValor(String[] array, String value) {
        for (String element : array) {
            if (element.trim().equals(value)) {
                return true;
            }
        }
        return false;
    }

}
