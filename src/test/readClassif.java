package test;

import java.io.*;
import java.util.*;
public class readClassif {
	public HashMap<String,ArrayList<String>> classci=new HashMap<String,ArrayList<String>>();
	public void readFile(String filename) throws IOException{
		File file=new File(filename);
		
		BufferedReader reader=null;
		try {
			reader=new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String temp;
		while((temp=reader.readLine())!=null)
		{
			int index=temp.indexOf(" ");
			String name=temp.substring(0,index);
			String st=temp.substring(index+1,temp.length());
			if(classci.containsKey(name)){classci.get(name).add(st);}
			else
				classci.put(name, new ArrayList<String>());
			//System.out.println(st);
		}
	}
	public static void main(String[] args) throws IOException{
		
	}
}
