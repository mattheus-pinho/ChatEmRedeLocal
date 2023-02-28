package ComunicadorEmRede;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;

public class ComunicadorEmRede extends Observable
{
	//Variaveis
	ObjectOutputStream output = null;
    ObjectInputStream in = null;
    private BufferedReader i;
	private BufferedWriter o;
	Thread threadReceberMensagem;
	Socket aceito;
	String mensagem;
	private Process process;
	String comandoEnviado = "go movetime 5000";
	String comandoRecebido;
	int portac;
	Socket cliente;
	ServerSocket servidor;
	
	//metodo construtor
	public ComunicadorEmRede() 
	{
       
	}
	
	//metodo inicia servidor
	public void iniciaServidor(String porta) throws IOException
	{
		portac = Integer.parseInt(porta);
		servidor = new ServerSocket(portac);
		System.out.println("Aguardando conexao na porta: " + porta);
		aceito = servidor.accept();
		System.out.println("Cliente conectado!");
		output = new ObjectOutputStream(aceito.getOutputStream());
		in = new ObjectInputStream(aceito.getInputStream());	
	}

	//metodo inicia cliente
	public void iniciaCliente(String ip, String porta ) throws IOException, Exception
	{
		portac = Integer.parseInt(porta);
		aceito = new Socket(ip, portac);
		System.out.println("Conectado ao servidor!");	
		output = new ObjectOutputStream(aceito.getOutputStream());
		in = new ObjectInputStream(aceito.getInputStream());
	}
	
	//metodo encia dados
	public void enviarDados(String dadosParaEnviar) throws IOException
	{
		output.writeObject(dadosParaEnviar);
		output.flush();
	}
	
	//metodo com threado : recebimento de dados
	public void receberDados() throws IOException
	{
		Thread threadReceberMensagem = new Thread
		(
				new Runnable() 
				{
					public void run() 
					{
							while (true)
							{
								try 
								{
									mensagem = (String) in.readObject();
									setChanged();
									notifyObservers();
								} 
								catch (ClassNotFoundException | IOException e) 
								{
									e.printStackTrace();
								}								
							}
					}
				}
		);
		threadReceberMensagem.start();
	}
    
	//metodo que pega mensagem para passar para a interface
	public String getMensagem()
	{
		return mensagem;
	}
	
	//metodo encerra conex�o
	public void fecharConexao() throws IOException
	{
		System.out.println("Conex�o sendo fechada");
		aceito.close();
	}

}