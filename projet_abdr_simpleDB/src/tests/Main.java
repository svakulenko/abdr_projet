package tests;

import common.Conf;

import client.Client1;

public class Main {
	public static void main(String args[]){

		//"D12", "D11"
		Client1 [] clients = new Client1[Conf.threadsNum];
		for (int i = 0; i < clients.length; i++) {
			clients[i] = new Client1(i, "client1" , "D12", "D11");
			clients[i].start();

		}
		
		try {
			for (int i = 0; i < clients.length; i++) {
				clients[i].join();
			}
		
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
