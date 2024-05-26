package examplepf;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author josephcruz
 */
public class ExampleMenuBasic {

   

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
                mensaje("el usuario existe\n");

            } else {
                mensaje("el usuario NO existe\n");

            }

        } while (estado == false);

        menuPrincipal();

    }

// función que comprieba si existe el usuario y contrseña 
    private static boolean login(String usuarioCliente, String contrasenaCliente) {
        
        String usuarioSistema = "jiji";
        String contrasenaSistema = "jojo";
        
        boolean estado;
        
        if(usuarioCliente.equals(usuarioSistema) && contrasenaCliente.equals(contrasenaSistema)){
        
            estado = true;
        }else{
            
            estado = false; 
        
        }
        
        return estado;
    }

    
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
