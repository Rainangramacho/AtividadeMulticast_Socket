import java.net.*;
import java.util.*;
import java.net.InetAddress;
import java.util.Scanner;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.net.SocketTimeoutException;

public class MulticastIP{
	
	public static void main(String args[]) throws Exception{
		
		//int porta = 6789;
		
		Scanner lerTeclado = new Scanner(System.in);
		String ipDestino = null;
		InetAddress ipGrupo = null;
		int portaDestino = 6789;
		String digitado = new String("entrou...");
		String respostaDaPorta = null;
		String respostaDoIP = null;
		
		byte[] ipGrupoByte = new byte [1024];
		String ipGrupoString = null;
		byte[] portaByte = new byte [1024];
		String portaString = null;
		
		//InetAddress ipGrupo = InetAddress.getByName("224.225.226.227");
		//ms.joinGroup(ipGrupo);
		
		
		System.out.print("Digite seu nome: ");
		String nomeUsuario = lerTeclado.nextLine();
		
		if(args.length == 0){// se nao tiver arg, ele pergunta se quer informar o ip
		System.out.print("Deseja informar IP do grupo de destino (S/N)? ");
		respostaDoIP = lerTeclado.nextLine();
		
		
			if(respostaDoIP.equalsIgnoreCase("s")){
					System.out.print("Digite o IP do grupo de destino:");
					ipDestino = lerTeclado.nextLine();
					ipGrupo = InetAddress.getByName(ipDestino);
		
			}else{
					ipGrupo = InetAddress.getByName("224.225.226.227");
			}
			
			
		}else if(args.length >=1 ){ // se tiver de um arg ou mais, ele usa o valor que o usuario digitar no arg
			ipGrupoByte = args[0].getBytes();
			ipGrupoString = new String(ipGrupoByte); // transformar byte em string
			ipGrupo = InetAddress.getByName(ipGrupoString); // transformar string em tipo InnetAddres
		}
		
		
		
		if(args.length <=1){ // se tiver até um arg que é o ip, ele pergunta se quer informar a porta
			System.out.print("Deseja informar porta de destino? (S/N)");
			respostaDaPorta = lerTeclado.nextLine();
		
				if(respostaDaPorta.equalsIgnoreCase("s")){
						System.out.print("Digite o numero da porta de destino:");
						portaDestino = lerTeclado.nextInt();
					
					
				}else{
						portaDestino = 6789; //como argumento
				}
				
		}else if(args.length >1){ // se tiver mais de um arg, no caso o ip e a porta de destino, ele usa o valor do arg
			portaByte = args[1].getBytes();
			portaString = new String(portaByte); //transformar byte em string
			portaDestino = Integer.parseInt(portaString); // transformar string em int
		}
		
		
		
		MulticastSocket ms = new MulticastSocket(portaDestino);
		ms.joinGroup(ipGrupo);
		
		new MulticastIPThread(ms).start();
		ms.setSoTimeout( 10000 );
		
		boolean flag = false;
		while (true){
			try{
				digitado = nomeUsuario + " > " + digitado;
				byte[] m = digitado.getBytes();
				DatagramPacket pctVai = new DatagramPacket(m,m.length,ipGrupo,portaDestino);
				ms.send(pctVai);
				if (flag) // se flag for true ele da um break e fecha a conexão
					break; 
				digitado = lerTeclado.nextLine(); // o que o usuario digitar vai para digitado
				if (digitado.equals("sair")){
					digitado = "saiu...";
					flag = true;
				}
			
			} catch(SocketTimeoutException e) {
				
				
			}
			
		}
			ms.leaveGroup(ipGrupo);
			ms.close();	
		
	
		
}
}