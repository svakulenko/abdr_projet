
public class Main {
	public static void main(String args[]){
		Client1 c1 = new Client1(1);
		Client2 c2 = new Client2(2);
		c1.start();
		c2.start();
		try {
			c1.join();
			c2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
