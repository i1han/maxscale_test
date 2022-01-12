package test;

import java.util.ArrayList;

public class test{

  public static void main(String[] args) throws Exception
  {
	  TestDAO tdao = new TestDAO();

	// tdao.setDB("oracle");
	// tdao.insertDB("dd",1242564);
	
	   ArrayList<testbean> list =  tdao.selectDB();
	   System.out.println(list);
  }
}