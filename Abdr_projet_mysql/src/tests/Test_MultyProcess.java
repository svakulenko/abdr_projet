package tests;

import common.Conf;

public class Test_MultyProcess {

	public void runMultyTache(Integer threadsNum){
		System.out.println("runMulty");
		Test_SingleProcess[] threads = new Test_SingleProcess[threadsNum];
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Test_SingleProcess();
			threads[i].start();
		}

		for (int i = 0; i < threads.length; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}


		
	}

	public static void main(String[] args) {

		
		new Test_MultyProcess().runMultyTache(Conf.threadsNum);

	}

}
