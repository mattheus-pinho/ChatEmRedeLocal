import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.swing.*;
import javax.swing.border.Border;
import ComunicadorEmRede.ComunicadorEmRede;
import java.util.*;

public class Principal implements Observer
{
	//elementos visuais
	JFrame tela = new JFrame();
	JTextArea visor = new JTextArea();
	JTextField mensagem = new JTextField();
	JButton enviar = new JButton();
	JTextField campoPorta = new JTextField();
	JTextField campoPorta2 = new JTextField();
	JTextField campoIp = new JTextField();
	JLabel tituloServidor = new JLabel();
	JScrollPane scroll = new JScrollPane(visor);
	JLabel tituloCliente = new JLabel();
	JLabel tip = new JLabel();
	JLabel meuIp = new JLabel();
	JLabel portaPadrao = new JLabel();
	JLabel tport = new JLabel();
	JLabel tport2 = new JLabel();
	JButton conexao = new JButton();
	JButton conexao1 = new JButton();
	JButton F1 = new JButton();
	
	//variaveis globais
	String porta;
	String infoIP;
	String msg;
	ComunicadorEmRede comunicador = new ComunicadorEmRede();
	String quemEnvia = "";
	String recebido;
	String ip;
	
	public Principal() throws IOException 
	{	
		//configura cor da borda
		Border borda = BorderFactory.createLineBorder(Color.black);

		//adicionando classe comunicador como observavel
		comunicador.addObserver(this);
		
		//visor das mensagens e scroll
		visor.setEditable(false);
		visor.setWrapStyleWord(true);
		visor.setBorder(borda);
		scroll.setSize(320, 370);
		scroll.setLocation(10, 10);
        
		try (Socket socket = new Socket()) {
		    socket.connect(new InetSocketAddress("google.com", 80));
		    ip = socket.getLocalAddress().getHostAddress();
		}
			
		//pega ip local da maquina
		infoIP = "Meu IP: " +  ip;
		
		//label que exibe meu IP
		meuIp.setText(infoIP);
		meuIp.setSize(200, 100);
		meuIp.setLocation(380, 270);
		
		//label que exibe porta padr�o
		portaPadrao.setText("Porta Padr�o: 12345");
		portaPadrao.setSize(200, 100);
		portaPadrao.setLocation(380, 300);
		
		//Campo da 'PORTA'
		campoPorta.setSize(90, 20);
		campoPorta.setLocation(415, 60);
		
		//Label 'PORTA'
		tport.setText("Porta");
		tport.setSize(50, 20);
		tport.setLocation(380, 60);
		
		//fecha a conex�o
		F1.setText("Desconectar");
		F1.setSize(120, 25);
		F1.setLocation(400, 400);
		F1.addActionListener
		(	
				new ActionListener() 
				{
					public void actionPerformed(ActionEvent e)
					{
						try {
							comunicador.fecharConexao();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						
					}	
				}
			);
		
		//abetura do Servidor
		conexao1.setSize(100, 25);
		conexao1.setText("Conectar S");
		conexao1.setLocation(410, 100);
		conexao1.addActionListener
		(	
			new ActionListener() 
			{
				public void actionPerformed(ActionEvent e)
				{
						porta = campoPorta.getText();
						try {
							comunicador.iniciaServidor(porta);
							comunicador.receberDados();
							quemEnvia = "servidor";
						} catch (IOException e1) {
							e1.printStackTrace();
						}
				}	
			}
		);

		//label 'SER SERVIDOR'
		tituloServidor.setText("Ser Servidor");
		tituloServidor.setSize(80, 20);
		tituloServidor.setLocation(420, 10);
		
		//Label 'SER CLIENTE'
		tituloCliente.setText("Ser Cliente");
		tituloCliente.setSize(80, 20);
		tituloCliente.setLocation(420, 155);
		
		//Label do 'IP'
		tip.setText("IP");
		tip.setSize(30, 20);
		tip.setLocation(390, 190);
	
		//Campo do 'IP'
		campoIp.setSize(90, 20);
		campoIp.setLocation(415, 190);
		
		//campo da 'PORTA'
		campoPorta2.setSize(90, 20);
		campoPorta2.setLocation(415, 220);
		
		//Label 'PORTA'
		tport2.setText("Porta");
		tport2.setSize(50, 20);
		tport2.setLocation(380, 220);
		
		//Bot�o de conex�o do cliente
		conexao.setSize(100, 25);
		conexao.setText("Conectar C");
		conexao.setLocation(410, 260);
		conexao.addActionListener
		(
				new ActionListener() 
				{
					public void actionPerformed(ActionEvent e)
					{
						
							try 
							{
								comunicador.iniciaCliente(campoIp.getText(), campoPorta2.getText() );
								quemEnvia = "cliente";
								comunicador.receberDados();
							} 
							catch (Exception e1) 
							{
								e1.printStackTrace();
							}	
					}
				}
				
		);		
		
		//JFrame 
		tela.setSize(600, 500);
		tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tela.setLocation(200, 200);
		tela.setLayout(null);
		tela.setResizable(false);
		tela.getContentPane().setBackground(Color.orange);
		tela.setTitle("Chat Java Repositorio");
		
		//Campo de texto "mensagem"
		mensagem.setSize(250, 50);
		mensagem.setLocation(10, 390);
		
		//Bot�o de envio
		enviar.setSize(60, 50);
		enviar.setLocation(270, 390);
		enviar.setText(">>");
		enviar.addActionListener
		(
				new ActionListener() 
				{
					public void actionPerformed(ActionEvent e)
					{	
						try 
						{
							comunicador.enviarDados(mensagem.getText());
							visor.setText(visor.getText() + mensagem.getText() + "\n\r");
							mensagem.setText(" ");
						} 
						catch (IOException e1) 
						{
							e1.printStackTrace();
						}
					}
				}
		);
		
		
		//Envio com ENTER
		
		mensagem.addActionListener
		(
				new ActionListener() 
				{
					public void actionPerformed(ActionEvent e)
					{						
						try 
						{
							comunicador.enviarDados(mensagem.getText());
							visor.setText(visor.getText() + mensagem.getText() + "\n\r");
							mensagem.setText(" ");
						} 
						catch (IOException e1) 
						{
							e1.printStackTrace();
						}					
					}
				}
		);
		
		
		
	//Adiciona os componentes a tela	
		//tela.add(scroll);
		tela.add(tport);
		tela.add(tport2);
		tela.add(conexao1);
		tela.add(conexao);
		tela.add(tip);
		tela.add(tituloServidor);
		tela.add(tituloCliente);
		tela.add(campoIp);
		tela.add(campoPorta);
		tela.add(campoPorta2);
		tela.add(enviar);
		tela.add(mensagem);
		tela.add(scroll);
		tela.add(F1);
		tela.add(meuIp);
		tela.add(portaPadrao);
		tela.setVisible(true);
		
		
	}
	
	//main
	public static void main(String[] args) throws IOException 
	{
		new Principal();
	}

	//metodo responsavel por atualizar mensagens do visor
	public void atualizarVisor()
	{
		recebido = comunicador.getMensagem();
		visor.setText(visor.getText() + recebido + "\n\r");
	}
	
	//metodo update da classe observer
	public void update(Observable arg0, Object arg1) 
	{
		System.out.println("notificado com sucesso");
		if (arg0 instanceof ComunicadorEmRede)
		{
			atualizarVisor();
		}
	}
	
}