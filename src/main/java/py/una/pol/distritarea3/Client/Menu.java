package py.una.pol.distritarea3.Client;

import java.util.Scanner;
import org.json.simple.JSONObject;
import py.una.pol.distritarea3.Models.Mensaje;

/**
 *
 * @author Alejandra Lezcano https://github.com/lezcanoale
 */
public class Menu {

    public static void menuIntro() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Por favor, introduzca el ID del NIS donde se encuentra: ");
        UDPClient.nisId = sc.nextInt();
    }

    public static void menuError(String error) {
        System.err.println(error);
    }

    public static Mensaje MenuCLI() {
        System.out.println("Menu Principal");
        System.out.println("1 - Registrar consumo NIS-" + UDPClient.nisId);
        System.out.println("2 - Enviar orden de conexión");
        System.out.println("3 - Enviar orden de desconexión");
        System.out.println("4 - Listar activos");
        System.out.println("5 - Listar inactivos");
        System.out.println("6 - Informar conectividad NIS-X");
        System.out.println("Cualquier otro numero para salir");
        Scanner sc = new Scanner(System.in);
        int tipoOperacion = sc.nextInt();

        JSONObject obj = new JSONObject();
        obj.put("nis_id", UDPClient.nisId);

        switch (tipoOperacion) {
            case 1:
                System.out.println("Registrar consumo NIS-" + UDPClient.nisId);
                System.out.println("Introduzca el consumo: ");
                int consumo = sc.nextInt();

                obj.put("consumo", consumo);

                break;
            case 2:
                System.out.println("Enviar orden de conexión");
                break;
            case 3:
                System.out.println("Enviar orden de desconexión");
                break;
            case 4:
                System.out.println("Listar activos");
                break;
            case 5:
                System.out.println("Listar inactivos");
                break;
            case 6:
                System.out.println("Informar conectividad NIS-X");
                System.out.println("Introduzca el id del NIS que desea conocer su conectividad: ");
                int nisId = sc.nextInt();

                obj.put("nis_id", nisId);

                break;
            default:
                UDPClient.salir = true;
        }
        return new Mensaje(tipoOperacion, obj.toJSONString());
    }

    public static void menuRespuesta(Mensaje respuesta) {
        System.out.println();
        System.out.println(respuesta.cuerpo);
        System.out.println();
    }

}
