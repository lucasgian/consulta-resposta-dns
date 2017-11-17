import java.net.*;
import java.io.*;

import java.util.Date;
import java.text.SimpleDateFormat;


public class DNS
{

	private static String serverName = "localhost";
	private static String address;
	private static String ip;

	public static void main(String[] args)   
	{
		try 
		{
			if(args == null)
			{
				System.out.println("Deve ser informado o parametro: \n  Nome do dominio");
				return;
			}

			config(args);

	      	ServerSocket servidor = new ServerSocket(12345);
	    	

	      	InetAddress[] addressReturn = Inet6Address.getAllByName(address);
	      	
		    inputA(addressReturn[0]);
		    
		    if(addressReturn.length == 1)
		    	inputAAAA(null);

		    else for(int i=1; i < addressReturn.length; i++){
		    	if(addressReturn[i].getHostAddress().length() > 14)
		    		inputAAAA(addressReturn[i]);	
		    	else
		    		inputA(addressReturn[i]);
		    }

		    	
		    


		    inputMX();

	    }

	    catch(Exception e) 
	    {
	       	System.out.println("Erro: " + e.getMessage());
	    }

	}

	private static boolean inputA(InetAddress address) throws IOException	
	{
	    System.out.println("Entrada A: ");
      	System.out.println("IPV4: " + address.getHostAddress());

	    return true;
	}

	private static boolean inputAAAA(InetAddress address) throws IOException	
	{

	    System.out.println("Entrada AAAA: ");
	    if(address == null)
	    {
			System.out.println("<none>");	    	
			return false;
	    }


      	System.out.println("IPV6: " + address.getHostAddress());

	    return true;
	}

	private static boolean inputMX() throws IOException	
	{
		System.out.println("Entrada MX: ");

		System.out.println("name server: " + serverName);

	    return true;
	}

	private static void config(String[] args) throws IOException
	{
		if(args.length == 2)
			addressIp(args[1]);

		address = args[0];

		if(address.charAt(address.length() - 1) == '.')
			address = address.substring(0, address.length() - 1);
	}

	private static boolean addressIp(String ip) throws IOException
	{
	    InetAddress addr = InetAddress.getByName(ip);
		serverName = addr.getHostName();

		return true;
	}
}