package tests;



import common.Conf;

public class Test_Batch  {

	public void runBatch(){
		Integer step = 0;
		Integer threadsNum = 0;
		//Conf.MyS3Time = 1;
		try {
			
			while(step++ < 4){
				
				
				switch (step) {
				case 1:
					threadsNum = 1;
					
					break;
				case 2:
					threadsNum = 2;
					break;
				case 3:
					threadsNum = 5;
					break;
				case 4:
					threadsNum = 10;
					break;
				case 5:
					threadsNum = 20;
					break;
				default:
					break;
				}
				
			
			 
			System.out.println("run Test_MultyProcess tache");
			Test_MultyProcess tm = new Test_MultyProcess();
			tm.runMultyTache(threadsNum);
			Thread.sleep(1001);
			System.out.println("next Test_MultyProcess tache");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		Test_Batch tb =new Test_Batch();
		tb.runBatch();
	}
	
	
}
