import java.net.*;
import java.io.*;

import java.util.Date;
import java.text.SimpleDateFormat;

public class ServerTCP
{
	public static void main(String[] args)   
	{
		/*
		try
		{
			//ServerSocket server = new ServerSocket(12345);
			//Socket client = server.accept();

			byte[] b = InetAddress.getByName("localhost").getAddress();
			System.out.println(b[0] + "." + b[1] + "." + b[2] + "." + b[3]);
			System.out.println("Endereço: " + InetAddress.getByName("www.ufms.br").getHostAddress() );
		}
		catch (Exception err) 
		{
			System.err.println(err);
		}
		*/

		try 
		{
	      	// Instancia o ServerSocket ouvindo a porta 12345
	      	ServerSocket servidor = new ServerSocket(12345);
	      	System.out.println("Servidor ouvindo a porta 12345");
	      	while(true) 
	      	{
		        // o método accept() bloqueia a execução até que
		        // o servidor receba um pedido de conexão
		        Socket cliente = servidor.accept();
		        System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());
		        ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());
		        saida.flush();
		        saida.writeObject(new Date());
		        saida.close();
		        cliente.close();
	      	}  
	    }

	    catch(Exception e) 
	    {
	       	System.out.println("Erro: " + e.getMessage());
	    }

	}
}



/*

import java.net.*;
import java.io.*;

public class ServerTCP
{
	public static void main(String[] args)   
	{
		try 
		{
			ServerSocket server = new ServerSocket(12345);
			Socket s = new Socket("127.0.0.1", 9999);
			InputStream i = s.getInputStream();
			OutputStream o = s.getOutputStream();
			String str;
			do 
			{
				byte[] line = new byte[100];
				System.in.read(line);
				o.write(line);
				i.read(line);
				str = new String(line);
				System.out.println(str.trim());
			} while ( !str.trim().equals("bye") );
			s.close();
		}
		catch (Exception err) 
		{
			System.err.println(err);
		}
	}
}

*/