package test;

import java.io.*;
//the following class simply output the string into xml file.
public class outPutxml {
	public void toFile(String s){
		try{ 
             BufferedWriter out=new BufferedWriter(new FileWriter( "test.xml")); 
             out.write(s); 
             out.close(); 
		}catch(IOException e){ 
     }
	}

}