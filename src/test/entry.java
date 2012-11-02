package test;

//package columbia.edu.adb;

//import java.io.IOException;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

import javax.xml.parsers.ParserConfigurationException;

//Download and add this library to the build path.
import org.apache.commons.codec.binary.Base64;
import org.xml.sax.SAXException;

public class entry {
	
	public static TreeMap<String,Integer> items=new TreeMap<String,Integer> ();
	public static ArrayList<String> level1=new ArrayList<String>();
	public static ArrayList<String> level2=new ArrayList<String>();
	public static TreeMap<String,TreeSet<String>> table=new TreeMap<String,TreeSet<String>>();
	
	//TreeMap<String,String> Computers=new TreeMap<String,String>();
	//TreeMap<String,String> Health=new TreeMap<String,String>();
	//TreeMap<String,String> Sports=new TreeMap<String,String>();
	//public static TreeSet<String> Root=new TreeSet<String>();
	//public static TreeSet<String> Computers=new TreeSet<String>();
	//public static TreeSet<String> Health=new TreeSet<String>();
	//public static TreeSet<String> Sports=new TreeSet<String>();
	
	public static String queryTransfer(String query){
		String outputs="";
		for(int i=0;i<query.length();i++){
			
			if(query.charAt(i)==' ')
				outputs=outputs+"%20";
			else
				outputs=outputs+query.charAt(i);
		}
		return outputs;
	}
	
	public static double tes=0.6;
	public static double tec=100;
	public static String accountKey = "rOmf0BVgre5o9F/6xOU7wYIsPkTwRSPqVMv83o3Atgo=";
	public static String target="";
	public static ArrayList<String>  getCate(String file) throws IOException, ParserConfigurationException, SAXException{
		
		ArrayList<String> level=new ArrayList<String>();
		readClassif croot=new readClassif();
		//System.out.println(file);
		croot.readFile(file);
		for(String ite:croot.classci.keySet()){items.put(ite, 0);}
		for(String ite:croot.classci.keySet()){
			for(String itt:croot.classci.get(ite)){
				
				String bingUrl = "https://api.datamarket.azure.com/Data.ashx/Bing/SearchWeb/v1/Composite?Query=%27site%3a"+target+"%20"+queryTransfer(itt)+"%27&$top=4&$format=Atom";
				//bingUrl="https://api.datamarket.azure.com/Data.ashx/Bing/SearchWeb/v1/Composite?Query=%27site%3afifa.com%20premiership%27&$top=10&$format=Atom";
				//System.out.println(bingUrl);
				//Provide your account key here. 
				
				
				byte[] accountKeyBytes = Base64.encodeBase64((accountKey + ":" + accountKey).getBytes());
				String accountKeyEnc = new String(accountKeyBytes);
	
				URL url = new URL(bingUrl);
				URLConnection urlConnection = url.openConnection();
				urlConnection.setRequestProperty("Authorization", "Basic " + accountKeyEnc);
						
				InputStream inputStream = (InputStream) urlConnection.getContent();		
				byte[] contentRaw = new byte[urlConnection.getContentLength()];
				inputStream.read(contentRaw);
				String content = new String(contentRaw);
	
				//The content string is the xml/json output from Bing.
				//System.out.println(content);
				outPutxml tofiles=new outPutxml();
				tofiles.toFile(content);
				xmlReader parse=new xmlReader();
				//int transcript;
				intString scr=new intString();
				scr=parse.viewDocument("test.xml");
				//transcript=parse.viewDocument("test.xml");
				items.put(ite, items.get(ite)+scr.totalnumber);
			//System.out.println(ite+": "+items.get(ite));
				//System.out.println("Access table: "+file);
				for(String s:scr.urls)
				table.get(file).add(s);
			}
			
		}/*
		for(String s:items.keySet()){
			System.out.println("Category:"+s+" has "+items.get(s));
		}*/
		int temp=0;
		int total=0;
		String cate="";
		for(String s:items.keySet())
		{
			//if(items.get(s)>temp) {cate=s;temp=items.get(s);}
			total=total+items.get(s);
		}
		for(String s:items.keySet()){
		if((double)items.get(s)/total>tes && items.get(s)>tec){level.add(s);}}
	
		return level;
	}
	public static void output(String filename){
		getWordsLynx getword=new getWordsLynx();
		ArrayList<TreeSet<String>> Rootall=new ArrayList<TreeSet<String>>();
		TreeSet<String> allTerms=new TreeSet<String>();
		TreeMap<String,Integer> finalValue=new TreeMap<String,Integer> ();
		for(String s:table.get(filename)){
			System.out.println("I am analyzing: "+s);
			Rootall.add(getword.runLynx(s));
		}
		System.out.println("Analyzing Finished!");
		//System.out.println(Rootall);
		for(TreeSet p:Rootall)
		{
			allTerms.addAll(p);
		}
		for(String s:allTerms){
			finalValue.put(s, 0);
		}
		for(String s:allTerms){
			for(TreeSet t:Rootall){
				if(t.contains(s)){
					finalValue.put(s, finalValue.get(s)+1);
				}
			}
		}
		
		
		
		
		try{ 
            BufferedWriter out=new BufferedWriter(new FileWriter( filename+"-"+target+".txt")); 
            for(String s:allTerms) {
            	out.write(s+" frequency: "+finalValue.get(s)+"\n"); 
            }; 
            out.close(); 
		}catch(IOException e){ 
		}
	}
	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
		if(args.length<4){System.out.println("Please give three arguments!");return;}
		accountKey=args[0];
		//accountKey="HPfW+LaXFpZi1C3ZAL+6C9uGc3Xz3UqQt4GtYguFW5A=";
		tes=Double.parseDouble(args[1]);
		tec=Double.parseDouble(args[2]);
		target=args[3];
		table.put("Root", new TreeSet<String>());
		table.put("Computers", new TreeSet<String>());
		table.put("Health", new TreeSet<String>());
		table.put("Sports", new TreeSet<String>());
		String cate="Root";
		level1=getCate(cate);
		if(level1.size()==0) {System.out.println("Root/");}
		for(String le1:level1){
			items=new TreeMap<String,Integer> ();
			System.out.println("level1 has"+le1);
			level2=getCate(le1);
			if(level2.size()==0) {System.out.println("Root/"+level1);}
			for(String le2:level2){
				System.out.println("level2 has"+le2);
				System.out.println("Root/"+le1+"/"+le2);
			}
		}
		
	
		for(String s:level1){
			table.get("Root").addAll(table.get(s));
		}
		output("Root");
		for(String s:level1){
			output(s);
		}
		
	}
		
	
	
}


//COMMENTED OUT LINES FOR FUTURE USE

/*/FOR ROOT FILE GENERATION
getWordsLynx getword=new getWordsLynx();
ArrayList<TreeSet<String>> Rootall=new ArrayList<TreeSet<String>>();
TreeSet<String> allTerms=new TreeSet<String>();
TreeMap<String,Integer> finalValue=new TreeMap<String,Integer> ();


for(String s:table.get("Root")){
	System.out.println("I am Analyzing "+s);
	Rootall.add(getword.runLynx(s));
}
System.out.println("Analyzing Finished!");
System.out.println(Rootall);
for(TreeSet p:Rootall)
{
	allTerms.addAll(p);
}
for(String s:allTerms){
	finalValue.put(s, 0);
}
for(String s:allTerms){
	for(TreeSet t:Rootall){
		if(t.contains(s)){
			finalValue.put(s, finalValue.get(s)+1);
		}
	}
}

for(String s:allTerms){
	System.out.println(s+" frequency: "+finalValue.get(s));
}


try{ 
    BufferedWriter out=new BufferedWriter(new FileWriter( "root.txt")); 
    for(String s:allTerms) {
    	out.write(s+" frequency: "+finalValue.get(s)+"\n"); 
    }; 
    out.close(); 
}catch(IOException e){ 
}
//Finish Writing Root*/



/*
for(String s:level1){
	System.out.println("vist level1:"+s+" "+table.get(s).size());
	try{  
		BufferedWriter out=new BufferedWriter(new FileWriter( s+".txt"));
		for(String t:table.get(s)){
		       System.out.println("level1 inside:"+t);
            	out.write(t); out.write("\n");
            }; 
            out.close(); 
		}catch(IOException e){ 
		}
}

*/

