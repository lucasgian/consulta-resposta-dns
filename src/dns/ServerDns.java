/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consulta.resposta.dns;

import java.net.*;
import java.io.IOException;

/**
 *
 * @author Gian Fonseca
 * @author Jonathan H
 */
public class ServerDns {
    /**
    * serverName variavel estatica com o valor do server.
    */
    private static String serverName = "localhost";
    /**
    * address variavel estatica com o valor do endereço do server.
    */
    private static String address;
    /**
    * ip variavel estatica com o valor do ipv-4 do servido.
    */
    private static String ip;
    /**
    * result variavel estatica com o valor do resultado após o processamento das inforamções e resposta.
    */
    private static String[] result;
    /**
    * index do vetor.
    */
    private static int index = 0;
    
    /**
     * inicia a execulção do programa, respondendo ao valores passados por parametro.
     * 
     * @param args, um vertor de string como entrada.
     * @throws java.io.IOException
     */
    public void run(String[] args) throws IOException {
        try {
            add("----------------------------");
            config(args);

            ServerSocket servidor = new ServerSocket(12345);

            InetAddress[] addressReturn = Inet6Address.getAllByName(address);

            inputA(addressReturn[0]);

            if (addressReturn.length == 1) {
                inputAAAA(null);
            } else {
                for (int i = 1; i < addressReturn.length; i++) {
                    if (addressReturn[i].getHostAddress().length() > 14) {
                        inputAAAA(addressReturn[i]);
                    } else {
                        inputA(addressReturn[i]);
                    }
                }
            }

            inputMX();
            
            servidor.close();
        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
            //result = result.concat("Erro: ").concat(e.getMessage()).concat("\n"); 
        }

    }

    private boolean inputA(InetAddress address) throws IOException {
        //System.out.println("Entrada A: ");
        //System.out.println("IPV4: " + address.getHostAddress());
        add("Entrada A: ");
        add(("IPV4: ").concat(address.getHostAddress()));
        
        return true;
    }

    private boolean inputAAAA(InetAddress address) throws IOException {

        //System.out.println("Entrada AAAA: ");
        if (address == null) {
            //System.out.println("<none>");
            //result = result.concat("Entrada AAAA: \n").concat("<none>").concat("\n");
            add("Entrada AAAA: ");
            add("<none>");
            return false;
        }

        //System.out.println("IPV6: " + address.getHostAddress());
       
        add("Entrada AAAA: ");
        add(("IPV6: ").concat(address.getHostAddress()));
        return true;
    }

    private boolean inputMX() throws IOException {
        //System.out.println("Entrada MX: ");

        //System.out.println("name server: " + serverName);
        //result = result.concat("Entrada MX: \n").concat("name server: ").concat(serverName).concat("\n");
        add("Entrada MX: ");
        add(("name server: ").concat(serverName));
        return true;
    }

    private void config(String[] args) throws IOException {
        if (args.length == 2) {
            addressIp(args[1]);
        }

        address = args[0];

        if (address.charAt(address.length() - 1) == '.') {
            address = address.substring(0, address.length() - 1);
        }
    }

    private boolean addressIp(String ip) throws IOException {
        InetAddress addr = InetAddress.getByName(ip);
        serverName = addr.getHostName();

        return true;
    }
    
     /**
     * get da varivel result
     * @return String com o valor do resultado final.
     */
    private void add(String value) {
        if (index == 0) 
           result = new String[64];
          
        result[index++] = value;
    }
    
    /**
     * get da varivel result
     * @return String com o valor do resultado final.
     */
    public String[] getResult() {
        return result;
    }
   
}
