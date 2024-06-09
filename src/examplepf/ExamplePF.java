package examplepf;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author josephcruz
 */
public class ExamplePF {

    private static final String csv_archivo_Inicio = "dataLogin.csv";
    private static final String csv_archivo_Mesa = "dataMesaPedido.csv";
    private static final String salto_de_linea = "\n";

    public static final String negro = "\033[30m";
    public static final String rojo = "\033[31m";
    public static final String verde = "\033[32m";
    public static final String amarillo = "\033[33m";
    public static final String azul = "\033[34m";
    public static final String morado = "\033[35m";
    public static final String cyan = "\033[36m";
    public static final String blanco = "\033[37m";

    public static void main(String[] args) throws AWTException, IOException {

        boolean[] totalMesas = {false, false, false, false, false, false, false, false, false, false};

        Robot r = new Robot();

        int eleccionMenuInicio;

        do {

            menuInicio();
            mensaje("Seleccione una Opción:");
            eleccionMenuInicio = capturarNumero();

            switch (eleccionMenuInicio) {

                case 1:
                    int contador = 0;

                    do {

                        mensaje(verde + "-----------------------------------------------\n");
                        mensaje(verde + "                 LOGIN                         \n");
                        mensaje(verde + "-----------------------------------------------\n");

                        mensaje("Ingrese su Usuario:");
                        String usuarioCliente = capturarTexto();

                        mensaje("Ingrese su Contraseña: ");
                        String contrasenaCliente = capturarTexto();

                        boolean estado = login(usuarioCliente, contrasenaCliente);

                        if (estado == true) {

                            mensaje("\n \n");
                            mensaje("Bienvenido \n \n \n \n");
                            contador = 4;
                            eleccionMenuInicio = 0;

                        } else {

                            mensaje("\n \n");
                            mensaje("el usuario NO existe\n \n \n \n");
                            contador++;

                        }

                        if (contador == 3) {

                            mensaje("regresemos en 15 segundos.... \n \n \n \n");
                            r.delay(15000);
                            contador = 0;

                        }

                    } while (contador < 4);

                    break;

                case 2:
                    mensaje(verde + "-----------------------------------------------\n");
                    mensaje(verde + "                 REGISTRARSE                   \n");
                    mensaje(verde + "-----------------------------------------------\n");

                    mensaje("Ingrese su Nombre:");
                    String nombre = capturarTexto();

                    mensaje("Ingrese su DNI:");
                    String dni = capturarTexto();

                    mensaje("Ingrese su Usuario:");
                    String usuario = capturarTexto();

                    mensaje("Ingrese su Contraseña:");
                    String contrasena = capturarTexto();

                    guardarRegistroLogin(nombre, dni, usuario, contrasena);

                    break;

                case 3:

                    mensaje("Ingrese el  DNI del Usuario a Eliminar :");
                    String usuarioDNIEliminar = capturarTexto();

                    eliminarRegistroUsuario(usuarioDNIEliminar);

                    break;

                case 4:

                    listarDatosRegistro();

                    break;

                case 0:

                    mensaje("GRACIAS POR USAR EL  SISTEMA :");

                    break;

                default:
                    mensaje(verde + "Opción  no valida\n");
            }

        } while (eleccionMenuInicio != 0);

        int eleccionMenuPrincipal;
        do {

            menuPrincipal();
            
            mensaje("Seleccione una Opción:");
            eleccionMenuPrincipal = capturarNumero();

            switch (eleccionMenuPrincipal) {
                case 1:
                    mensaje(verde + "-----------------------------------------------\n");
                    mensaje("         *MENU DE INICIO ---> Antender Mesa            \n");
                    mensaje(verde + "-----------------------------------------------\n");

                    mostrarMesas(totalMesas);

                    mensaje("Ingrese el número de mesa a atender del 1 al 10:");
                    int eleccionMesa = capturarNumero();
                    if (reservarMesa(totalMesas, eleccionMesa)) {

                        mensaje("Mesa reservado con exito");

                        int p = 1;
                        String cartaMesaPedido = eleccionMesa + "";
                        String continuar;
                        do {

                            mensaje("\n \n");
                            mensaje("Tomar pedido:" + p + ":\n");
                            mensaje("\n");
                            mostrarCarta();
                            System.out.print("Seleccione el número de plato para cliente:");
                            int eleccionCarta = capturarNumero();

                            switch (eleccionCarta) {
                                case 1:
                                    cartaMesaPedido += "," + "12";
                                    break;
                                case 2:
                                    cartaMesaPedido += "," + "15";
                                    break;
                                case 3:
                                    cartaMesaPedido += "," + "20";
                                    break;
                            }
                            mensaje("¿Desea agregar más platos a la mesa?: si o no:");
                            p++;
                            continuar = capturarTexto();

                        } while ("si".equals(continuar));

                        guardarRegistroMesa(cartaMesaPedido);
                        mensaje("Pedido Guardado....\n");

                    } else {
                        System.out.println("Mesa fuera del alcance");

                    }

                    break;
                case 2:

                    mensaje(verde + "-----------------------------------------------\n");
                    mensaje("         *MENU DE INICIO ---> Cobrar Mesa              \n");
                    mensaje(verde + "-----------------------------------------------\n");

                    mostrarMesas(totalMesas);

                    mensaje("Ingrese el número de mesa a cobrar del 1 al 10:");
                    int eleccionMesaCobrar = capturarNumero();
                    int cobrarTotal = consumoMesa(eleccionMesaCobrar);
                    mensaje("El total a cobrar de la mesa " + eleccionMesaCobrar + ", es: " + cobrarTotal+"\n");

                    boolean estadoLibeerarMesa = liberarMesa(totalMesas, eleccionMesaCobrar);
                    if (estadoLibeerarMesa) {

                        mensaje("mesa liberada....\n");
                        mostrarMesas(totalMesas);
                    } else {

                        mensaje("no  se pudo  liberar mesa....\n");
                        mostrarMesas(totalMesas);
                    }

                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 0:
                    mensaje("gracias por usar el  sistema\n");
                    break;
                default:
                    mensaje("no es una opción valida\n");
            }

        } while (eleccionMenuPrincipal != 0);
    }

    //----------funciones-------------
    public static void menuInicio() {

        System.out.println(verde + "-----------------------------------------------");
        System.out.println("                    *MENU DE INICIO                  ");
        System.out.println(verde + "-----------------------------------------------");
        System.out.println("Opciones:");
        System.out.println("         1.Login");
        System.out.println("         2.Registrarse");
        System.out.println("         3.Eliminar Usuario");
        System.out.println("         4.Listar Usuario");
        System.out.println("");
        System.out.println("         0.-Salir");
        System.out.println(verde + "-----------------------------------------------");

    }

    public static void menuPrincipal() {

        System.out.println(verde + "-----------------------------------------------");
        System.out.println("                    *MENU PRINCIPAL*                 ");
        System.out.println(verde + "-----------------------------------------------");
        System.out.println("Opciones:");
        System.out.println("         1.Atender Mesa");
        System.out.println("         2.Cobrar Mesa");
        System.out.println("         3.xxxxxxxxxxxxx");
        System.out.println("");
        System.out.println("         4.xxxxxxxxxx");
        System.out.println("         5.xxxxxxxxxxxxxx");
        System.out.println("");
        System.out.println("         0.-Salir");
        System.out.println(verde + "-----------------------------------------------");

    }

    public static void mostrarCarta() {

        System.out.println("1.- Lomo saltado s/12.00");
        System.out.println("2.- Cebiche  s/15.00");
        System.out.println("3.- Escabeche s/20.00");

    }

    public static void mensaje(String mensaje) {

        System.out.print(mensaje);
    }

    public static int capturarNumero() {

        Scanner sc = new Scanner(System.in);
        int numero = sc.nextInt();
        return numero;
    }

    public static String capturarTexto() {

        Scanner sc = new Scanner(System.in);
        String texto = sc.nextLine();
        return texto;
    }

    public static void guardarRegistroLogin(String nombre, String dni, String usuario, String contrasena) {

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
        boolean ecncontrado = false;
        
        try (BufferedReader br = new BufferedReader(new FileReader(csv_archivo_Inicio))) {
        
            String line;

            while ((line = br.readLine()) != null) {
                // Utiliza split para separar los datos por comas
                String[] datos = line.split(",");

                // Verifica si ambos valores están en la línea actual
                if (contieneValor(datos, usuarioCliente) && contieneValor(datos, contrasenaCliente)) {
                    ecncontrado = true;
                    break; // Si ambos valores están, no es necesario continuar buscando
                }
            }

            if (ecncontrado) {
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
    private static boolean contieneValor(String[] array, String valorBuscar) {
       
        for (String elemento : array) {
            if (elemento.trim().equals(valorBuscar)) {
                return true;
            }
        }
        return false;
    }

    // reservar mesa
    public static boolean reservarMesa(boolean[] totalMesa, int numeroMesa) {
        if (numeroMesa < 1 || numeroMesa > totalMesa.length) {
            return false;

        }
        if (totalMesa[numeroMesa - 1] == false) {
            totalMesa[numeroMesa - 1] = true;
            return true;

        }

        return false;
    }

    // liberar mesa
    public static boolean liberarMesa(boolean[] totalMesa, int numeroMesa) {
        if (numeroMesa < 1 || numeroMesa > totalMesa.length) {
            return false;

        }
        if (totalMesa[numeroMesa - 1] == true) {
            totalMesa[numeroMesa - 1] = false;
            return true;

        }

        return false;
    }

    // mostrar estado mesa
    public static void mostrarMesas(boolean[] totalMesa) {
        System.out.println("Estado de la mesa:");

        for (int i = 0; i < totalMesa.length; i++) {

            if (totalMesa[i]) {
                System.out.print(" MesaOcupado:" + i + " "); // ocupado
            } else {
                System.out.print(" MesaLibre:" + i + " "); // libre
            }
        }
        System.out.println();

    }

    // para guardar pedido de la mesa
    private static void guardarRegistroMesa(String cartaMesaPedido) {

        try (FileWriter fileWriter = new FileWriter(csv_archivo_Mesa, true); BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter.write(cartaMesaPedido);
            bufferedWriter.write(salto_de_linea);
            System.out.println(amarillo + "Registro guardado exitosamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // para sumar el  consume de la mesa
    private static int consumoMesa(int numeroMesa) {

        int resultado = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(csv_archivo_Mesa))) {

            String line;

            while ((line = br.readLine()) != null) {
                // Utiliza split para separar los datos por comas
                String[] datos = line.split(",");
                int[] arrayInt = new int[datos.length];

                for (int i = 0; i < datos.length; i++) {
                    arrayInt[i] = Integer.parseInt(datos[i]);
                }

                if (arrayInt[0] == numeroMesa) {

                    for (int num : arrayInt) {
                        //System.out.println(num);
                        resultado += num;
                    }

                    resultado = resultado - arrayInt[0];

                    break;

                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    // eliminar linea
    public static void eliminarRegistroUsuario(String dniEliminar) throws IOException {
        // Lee el archivo CSV y carga los datos en una lista
        List<String> lineas = new ArrayList<>();

        BufferedReader lector = new BufferedReader(new FileReader(csv_archivo_Inicio));
        String linea;

        while ((linea = lector.readLine()) != null) {
            // Si la línea no es la que queremos eliminar, la agregamos a la lista
            String[] datos = linea.split(",");
            if (!(datos[1].equals(dniEliminar))) {
                lineas.add(linea);
            }
        }
        lector.close();

        // Escribe los datos restantes en el archivo CSV
        FileWriter escritor = new FileWriter(csv_archivo_Inicio);
        for (String l : lineas) {
            escritor.write(l + "\n");
        }
        escritor.close();

        System.out.println("Se eliminó la línea del archivo CSV.");
    }

    public static void listarDatosRegistro() throws IOException {
        // Crea un lector de CSV
        BufferedReader lector = new BufferedReader(new FileReader(csv_archivo_Inicio));

        // Lee todas las líneas del archivo CSV
        String linea;
        while ((linea = lector.readLine()) != null) {
            String[] datos = linea.split(",");
            for (String dato : datos) {
                System.out.print(dato + "\t");
            }
            System.out.println(); // Nueva línea para la próxima fila
        }

        // Cierra el lector
        lector.close();
    }

}
