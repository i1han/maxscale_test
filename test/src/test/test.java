package test;

import java.util.Random;

public class test{

  public static void main(String[] args) throws Exception
  {
	TestDAO tdao = new TestDAO();
	Random random = new Random();
	    
//	tdao.setDB("oracle");
//	tdao.insertDB(tdao.rand_str(), random.nextInt(100000));

//	tdao.selectDB();
	  
	for (int i = 1 ; i < 100 ; i++) {
		System.out.println("============================================================================================================================================================================");
		System.out.println("Num : " + i);
		tdao.selectDB();  
		Thread.sleep(500);
		System.out.println("============================================================================================================================================================================");
	}
 
	  
  }
}