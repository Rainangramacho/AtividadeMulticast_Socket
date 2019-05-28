import java.net.*;
import java.util.*;

public class MulticastIPThread extends Thread{
	
	MulticastSocket ms;
	
	
	public MulticastIPThread(MulticastSocket ms){
		
		this.ms = ms;
		
	}
	
	public void run(){
		
		while(true){
			byte[] buffer = new byte[1024];
			DatagramPacket pctVem = new DatagramPacket(buffer, buffer.length);
			try{
				ms.receive(pctVem);
				System.out.println(new String(pctVem.getData()).trim());
			}catch(Exception e){
				System.out.println("saiu...");
				break;
			}
		}
			
	}
	
}