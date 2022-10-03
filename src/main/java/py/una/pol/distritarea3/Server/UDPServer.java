package py.una.pol.distritarea3.Server;

import java.io.*;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import py.una.pol.distritarea3.Models.Mensaje;
import py.una.pol.distritarea3.Models.NIS;

/**
 *
 * @author Alejandra Lezcano https://github.com/lezcanoale
 */
public class UDPServer {

    static DB db = new DB();

    static String MyIp = "";
    static String ipClient = "";

    public static void main(String[] args) {
        int puertoServidor = 4242;
        if (args.length == 1) {
            puertoServidor = Integer.parseInt(args[0]);
        } else if (args.length > 1) {
            System.err.println("ERROR en args");
            System.exit(1);
        }

        db.datosPrecargados();

        System.out.println("Datos en la DB: ");
        System.out.println(db.datos.toString());

        try {
            //1) Creamos el socket Servidor de Datagramas (UDP)
            DatagramSocket serverSocket = new DatagramSocket(puertoServidor, InetAddress.getByName("127.0.0.1"));
            System.out.println("Servidor Sistemas Distribuidos - UDP");

            //2) buffer de datos a enviar y recibir
            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];

            //3) Servidor siempre esperando
            while (true) {

                receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

                // 4) Receive
                serverSocket.receive(receivePacket);
                MyIp = serverSocket.getLocalSocketAddress().toString().substring(1);
                ipClient = receivePacket.getAddress().toString().substring(1) + ":" + receivePacket.getPort();

                // Datos recibidos e Identificamos quien nos envio
                String datoRecibido = new String(receivePacket.getData());
                datoRecibido = datoRecibido.trim();

                Mensaje recibidoCliente = new Mensaje(datoRecibido);
                Mensaje enviarCliente = procesarMensaje(recibidoCliente);
                loger(MyIp, ipClient, enviarCliente.tipo_operacion, enviarCliente.cuerpo);

                sendData = enviarCliente.toJSON().getBytes();

                // Enviando response
                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                serverSocket.send(sendPacket);
            }
        } catch (Exception ex) {
            Logger.getLogger(UDPServer.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
    }

    public static Mensaje procesarMensaje(Mensaje datoRecibido) throws ParseException {

        loger(ipClient, MyIp, datoRecibido.tipo_operacion, datoRecibido.cuerpo);

        int tipo_operacion = datoRecibido.tipo_operacion;
        String cuerpo = datoRecibido.cuerpo;

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(cuerpo.trim());
        JSONObject jsonObject = (JSONObject) obj;

        int nis_id = (int) (long) jsonObject.get("nis_id");
        NIS nis = db.obtener(nis_id);

        switch (tipo_operacion) {
            case 1:
                if (nis == null) {
                    return new Mensaje(1, "El NIS-" + nis_id + " aun no existe, debe registrar su conexion primero");
                }
                nis.setConsumo((int) (long) jsonObject.get("consumo"));
                return new Mensaje(0, "El consumo de NIS-" + nis_id + " se registro exitosamente a " + nis.getConsumo());
            case 2:
                if (nis == null) {
                    NIS nuevo = db.crear((int) (long) jsonObject.get("nis_id"));
                    nuevo.setActivo(true);
                    return new Mensaje(0, "El NIS-" + nis_id + " se creo y se pone en conexion");
                }
                if (nis.isActivo()) {
                    return new Mensaje(0, "El NIS-" + nis_id + " ya se encuentra conectado");
                }
                nis.setActivo(true);
                return new Mensaje(0, "El NIS-" + nis_id + " se pone en conexion");
            case 3:
                if (nis == null) {
                    return new Mensaje(3, "El NIS-" + nis_id + " aun no existe, debe registrar su conexion primero");
                }
                if (!nis.isActivo()) {
                    return new Mensaje(0, "El NIS-" + nis_id + " ya se encuentra desconectado");
                }
                nis.setActivo(false);
                return new Mensaje(0, "El NIS-" + nis_id + " se pone en desconectado");
            case 4:
                return new Mensaje(0, db.getActivos().toString());
            case 5:
                return new Mensaje(0, db.getInActivos().toString());
            case 6:
                if (nis == null) {
                    return new Mensaje(6, "El NIS-" + nis_id + " aun no existe, debe registrar su conexion primero");
                }
                if (nis.isActivo()) {
                    return new Mensaje(0, "El NIS-" + nis_id + " esta actualmente conectado");
                }
                return new Mensaje(0, "El NIS-" + nis_id + " esta actualmente desconectado");
            default:
                break;
        }
        return null;
    }

    public static void loger(String ip, String ip2, int tipo_operacion, String error) {
        FileWriter fw = null;
        try {
            if (!error.equals("")) {
                error = ", " + error;
            }
            String log = LocalDateTime.now() + ", origen(" + ip + "), destino(" + ip2 + "), tipo_operacion: " + tipo_operacion + error;
            System.out.println(log);
            fw = new FileWriter("log.txt", true); //the true will append the new data
            fw.write(log + "\n");//appends the string to the file
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(UDPServer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(UDPServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
