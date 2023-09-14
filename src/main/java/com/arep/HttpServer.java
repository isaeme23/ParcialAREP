package com.arep;

import java.lang.reflect.InvocationTargetException;
import java.net.*;
import java.io.*;

public class HttpServer {
    public static void main(String[] args) throws IOException, InvocationTargetException, IllegalAccessException {
        ServerSocket serverSocket = null;
        while (true){

        try {
            serverSocket = new ServerSocket(36000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 36000.");
            System.exit(1);
        }
        String path = null;
        Socket clientSocket = null;
        try {
            System.out.println("Listo para recibir ...");
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String inputLine, outputLine;
        while ((inputLine = in.readLine()) != null) {
            System.out.println("Recibí: " + inputLine);
            if (inputLine.contains("GET")) {
                path = inputLine.split(" ")[1];
                System.out.println("Path " + path);
            }
            if (!in.ready()) {
                break;
            }
        }

        outputLine = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n";
        LoadClasses.load();
        if (path.length() > 2 && LoadClasses.getActions().containsKey(path.split("/")[1].split("\\Q?\\E")[0])) {
            outputLine = outputLine + LoadClasses.execute(path.split("/")[1].split("\\Q?\\E")[0], Double.valueOf(path.split("=")[1]));
        } else if (path.startsWith("/calculadora")){
            outputLine = outputLine +
                    "<!DOCTYPE html>\n"
                    + "<html>\n"
                    + "<head>\n"
                    + "<meta charset=\"UTF-8\">\n"
                    + "<title>Title of the document</title>\n"
                    + "</head>\n"
                    + "<body>\n"
                    + "<h1>Inserte la operacion con los numeros deseados</h1>\n" +
                    "<form action=\"/hello\">\n" +
                    "    <label for=\"number\">Operacion:</label><br>\n" +
                    "    <input type=\"text\" id=\"number\" name=\"number\" value=\"Escriba Aqui\"><br><br>\n" +
                    "    <input type=\"button\" value=\"Submit\" onclick=\"loadGetMsg()\">\n" +
                    "</form>\n" +
                    "<div id=\"getrespmsg\"></div>\n" +
                    "\n" +
                    "<script>\n" +
                    "            function loadGetMsg() {\n" +
                    "                let nameVar = document.getElementById(\"number\").value;\n" +
                    "                let numberVar = nameVar.split(\"(\")[1].split(\")\")[0];\n"+
                    "                let operationVar = nameVar.split(\"(\")[0];\n"+
                    "                const xhttp = new XMLHttpRequest();\n" +
                    "                xhttp.onload = function() {\n" +
                    "                    document.getElementById(\"getrespmsg\").innerHTML =\n" +
                    "                    this.responseText;\n" +
                    "                }\n" +
                    "                xhttp.open(\"GET\", \"/\"+operationVar+\"?number=\"+numberVar);\n" +
                    "                xhttp.send();\n" +
                    "            }\n" +
                    "        </script>"
                    + "</body>\n"
                    + "</html>\n";
        }
        out.println(outputLine);
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();

    }
    }
}
