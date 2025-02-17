package org.example;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Ejercicio1 {
    public static void main(String[] args) throws IOException {
        FTPClient cliente = new FTPClient();
        String servFTP = "localhost"; // servidor FTP
        int port = 21;
        String user = "usuario 1";
        String password = "123";
        String directorio="/usuario1/carpeta A";
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
            JFileChooser fileChooser=new JFileChooser();
            fileChooser.setDialogTitle("Selecciona archivo a subir");
            int result=fileChooser.showOpenDialog(null);
            if (result==JFileChooser.APPROVE_OPTION){
                File selectedFile = fileChooser.getSelectedFile();
                System.out.println(selectedFile.getAbsolutePath());


                cliente.changeWorkingDirectory(directorio);
                // Subir el archivo al servidor FTP
                FileInputStream fis = new FileInputStream(selectedFile);
                cliente.setFileType(FTP.BINARY_FILE_TYPE);
                boolean uploaded = cliente.storeFile(selectedFile.getName(), fis);
                fis.close();
                if (uploaded){
                    System.out.println("ARCHIVO SUBIDO");
                    String archivoRemoto=selectedFile.getName();
                    File archivoLocal = new File("descargado_" + archivoRemoto); // Nombre local para la descarga
                    FileOutputStream fos = new FileOutputStream(archivoLocal);

                    boolean descargado = cliente.retrieveFile(archivoRemoto, fos);
                    fos.close();

                    if (descargado) {
                        System.out.println("Archivo descargado exitosamente como: " + archivoLocal.getAbsolutePath());
                    } else {
                        System.out.println("Error al descargar el archivo.");
                    }
                }
            }
            System.out.println("Autenticación exitosa");
        }else{
            System.out.println("Error");
        }

        cliente.disconnect();
        System.out.println("Conexión finalizada.");
    }
}
