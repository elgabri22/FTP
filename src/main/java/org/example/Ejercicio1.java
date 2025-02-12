package org.example;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;

public class Ejercicio1 {
    public static void main(String[] args) throws IOException {
        FTPClient cliente = new FTPClient();
        String servFTP = "localhost"; // servidor FTP
        int port = 21;
        String user = "usuario 1";
        String password = "123";
        System.out.println("Nos conectamos a: " + servFTP);
        cliente.connect(servFTP,port);

        // respuesta del servidor FTP
        System.out.print(cliente.getReplyString());
        // código de respuesta
        int respuesta = cliente.getReplyCode();

        System.out.println("Respuesta: "+respuesta);

        if (!FTPReply.isPositiveCompletion(respuesta)) {
            cliente.disconnect();
            System.out.println("Conexión rechazada: " + respuesta);
            System.exit(0);
        }

        if (cliente.login(user,password)){
            System.out.println("Autenticación exitosa");
        }else{
            System.out.println("Error");
        }

        cliente.disconnect();
        System.out.println("Conexión finalizada.");
    }
}
