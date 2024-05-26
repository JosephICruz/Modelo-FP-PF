
package examplepf;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author josephcruz
 */
public class ExampleMenuCSV {

    private static final String csv_archivo_Inicio = "data.csv";

    public static void main(String[] args) {
        boolean estado;

        do {
            mensaje("-----------------------------------------------\n");
            mensaje("                 LOGIN                         \n");
            mensaje("-----------------------------------------------\n");

            mensaje("Ingrese su usuario:\n");
            String usuarioCliente = capturartexto();

            mensaje("Ingrese su contrasena:\n");
            String contrasenaCliente = capturartexto();

            estado = login(usuarioCliente, contrasenaCliente);

            if (estado == true) {
                mensaje("el usuario  existe\n");

            } else {
                mensaje("el usuario NO existe\n");

            }

        } while (estado == false);

        menuPrincipal();

    }

// función que comprieba si existe el usuario y contrseña 
    private static boolean login(String usuarioCliente, String contrasenaCliente) {

        boolean resultado = true;
        boolean encontrado = false;
        try (BufferedReader br = new BufferedReader(new FileReader(csv_archivo_Inicio))) {
            String line;

            while ((line = br.readLine()) != null) {
                // Utiliza split para separar los datos por comas
                String[] datos = line.split(",");

                // Verifica si ambos valores están en la línea actual
                if (contieneValor(datos, usuarioCliente) && contieneValor(datos, contrasenaCliente)) {
                    encontrado = true;
                    break; // Si ambos valores están, no es necesario continuar buscando
                }
            }

            if (encontrado) {
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

    // Función para verificar si un valor está presente en un array de Strings
    private static boolean contieneValor(String[] array, String value) {
        for (String elemento : array) {
            if (elemento.trim().equals(value)) {
                return true;
            }
        }
        return false;
    }

    //Funcion para mostrar el menu principal
    public static void menuPrincipal() {

        System.out.println("----------------Menu de Opciones-------------------");
        System.out.println("1.-Buscar:");
        System.out.println("2.-Registrar:");
        System.out.println("3.-Vender:");
        System.out.println("4.-Eliminar venta:");
        System.out.println("4.-Reportar");
        System.out.println("0.-Salir");
        System.out.println("---------------------------------------------------");

    }

    public static void mensaje(String mensaje) {

        System.out.print(mensaje);
    }

    public static String capturartexto() {

        Scanner sc = new Scanner(System.in);
        String texto = sc.nextLine();
        return texto;
    }

}
