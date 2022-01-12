package test;

import java.util.ArrayList;

public class test{

  public static void main(String[] args) throws Exception
  {
	  TestDAO tdao = new TestDAO();
	  
	  
	// tdao.setDB("oracle");
	// tdao.insertDB("dd",1242564);
	
	  
	  for (int i = 0 ; i <= 3 ; i++) {
		  
	  tdao.selectDB();
	  
	  Thread.sleep(2000);
	  
	  }
	  
	  
  }
}