package test;

import java.io.*;
import java.io.InputStreamReader;
import java.util.*;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class getWordsLynx {

    public static TreeSet runLynx(String url) {

        int buffersize = 40000;
        StringBuffer buffer = new StringBuffer(buffersize);

        try {
        	
            String cmdline[] = {"/usr/bin/lynx", "--dump", url };
            Process p = Runtime.getRuntime().exec(cmdline);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            char[] cbuf = new char[1];

            while (stdInput.read(cbuf, 0, 1) != -1 || stdError.read(cbuf, 0, 1) != -1) {
                buffer.append(cbuf);
            }
            p.waitFor();
            stdInput.close();
            stdError.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        // Remove the References at the end of the dump
        int end = buffer.indexOf("\nReferences\n");

        if (end == -1) {
            end = buffer.length();
        }
        // Remove everything inside [   ] and do not write more than two consecutive spaces
        boolean recording = true;
        boolean wrotespace = false;
        StringBuffer output = new StringBuffer(end);

        for (int i = 0; i < end; i++) {
            if (recording) {
                if (buffer.charAt(i) == '[') {
                    recording = false;
                    if (!wrotespace) {
                        output.append(' ');
                        wrotespace = true;
                    }
                    continue;
                } else {
                    if (Character.isLetter(buffer.charAt(i)) && buffer.charAt(i)<128) {
                        output.append(Character.toLowerCase(buffer.charAt(i)));
                        wrotespace = false;
                    } else {
                        if (!wrotespace) {
                            output.append(' ');
                            wrotespace = true;
                        }
                    }
                }
            } else {
                if (buffer.charAt(i) == ']') {
                    recording = true;
                    continue;
                }
            }
        }
        Set document = new TreeSet();
        StringTokenizer st = new StringTokenizer(output.toString());

        while (st.hasMoreTokens()) {
            String tok = st.nextToken();
            //System.out.println(tok);
            document.add(tok);
        }
        //System.out.println("returened successfully!");
        return (TreeSet)document;
    }

    public static void main(String args[]) {
    	TreeSet p=new TreeSet();
    	p=runLynx("http://hoopedia.nba.com/index.php?title=User:Z51e224ha2");
    	System.out.println(p);
    	/*
    	ArrayList<String> a=new ArrayList<String>();
    	a.add("e");
    	TreeSet<String> dd=new TreeSet<String>();
    	HashSet<String> b=new HashSet<String>();
    	b.add("s");
    	HashSet<String> c=new HashSet<String>();
    	TreeSet<String> ab=new TreeSet<String>();
    	
    	b.add("d");
    	Set f=new TreeSet();
    	f.add("dd");
    	Set e=new HashSet();
    	e.add("asd");
    	b.add("s");
    	e.addAll(b);
    	f.addAll(e);
    	b.addAll(f);
    	System.out.println((HashSet<String>)f);
    	/*String a="0    1 2   4 ";
    	String[] p=a.split("[' ']+");
    	System.out.println(p.length);
    	for(String s:p){System.out.println(s);};
        Set<String> stri=runLynx(args[0]);
        for(String s:stri){
        	System.out.println(s);*/
        
    }
}