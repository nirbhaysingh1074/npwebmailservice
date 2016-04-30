package hello;

import java.io.*;
import org.apache.commons.codec.binary.Base64;
import java.net.URLEncoder;
import java.util.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.MimeUtility;

public class Temp {
	public static void main(String[] args) {
	
		try {
			String s="\r\n=?utf-8?Q?Informaci=C3=B3n_Financiera_por_[tu_oferta_del_d=C3=ADa]?= <news@comprapremium.es>";
			String frm=MimeUtility.decodeText(s);
			System.out.println(frm);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/* String orig = "nirbhay# pra^..tap!! sinhg>?1/234";
		 System.out.println(orig);
		 String  result = orig.replaceAll("[^\\w\\s]","");
		 System.out.println(result);*/
		/*String data="<SCRIpt type='java'>hiiiiiiiiii ggg</script> hellooo <SCRIpt>hiiiiiiiiii ggg</script> hiii &lt;script&gt;alert(&quot;I am destroyer&quot;);&lt;/script&gt; <SCRIpt type='javascript' src='jjj.js'/> <img oneRROr='javascript:alert(&quot;XSS&quot;)' src='6.jpg'/> helllloooo <img onclick='alert(gfgf)' >tttttttttt";
		StringBuilder regex = new StringBuilder("<script[^>]*>(.*?)</script>");
		int flags = Pattern.MULTILINE | Pattern.DOTALL| Pattern.CASE_INSENSITIVE;
		Pattern pattern = Pattern.compile(regex.toString(), flags);
		Matcher matcher = pattern.matcher(data);
		data=matcher.replaceAll("");
		System.out.println( data);
		
		
		StringBuilder regex1 = new StringBuilder("<script[^>]*>");
		int flags1 = Pattern.MULTILINE | Pattern.DOTALL| Pattern.CASE_INSENSITIVE;
		Pattern pattern1 = Pattern.compile(regex1.toString(), flags1);
		Matcher matcher1 = pattern1.matcher(data);
		data=matcher1.replaceAll("");
		System.out.println( data);
		
		StringBuilder regex2 = new StringBuilder("onerror=[^>]*>");
		int flags2 = Pattern.MULTILINE | Pattern.DOTALL| Pattern.CASE_INSENSITIVE;
		Pattern pattern2 = Pattern.compile(regex2.toString(), flags2);
		Matcher matcher2 = pattern2.matcher(data);
		data=matcher2.replaceAll(">");
		System.out.println( data);
		 
	
	StringBuilder regex3 = new StringBuilder("on[^=](.*?)>");
	int flags3 = Pattern.MULTILINE | Pattern.DOTALL| Pattern.CASE_INSENSITIVE;
	Pattern pattern3 = Pattern.compile(regex3.toString(), flags3);
	Matcher matcher3 = pattern3.matcher(data);
	data=matcher3.replaceAll(">");
	System.out.println( data);*/
		
		/*
		        String orig = "original String before base64 encoding in Java";

		        //encoding  byte array into base 64
		        byte[] encoded = Base64.encodeBase64(orig.getBytes());     
		      
		        System.out.println("Original String: " + orig );
		        System.out.println("Base64 Encoded String : " + new String(encoded));
		      
		        //decoding byte array into base64
		        byte[] decoded = Base64.decodeBase64(encoded);      
		        System.out.println("Base 64 Decoded  String : " + new String(decoded));
*/
	

/*		
		String flname="nirbhay@silvereye.in";
		String name_email="";
		int dolorIndex = flname.indexOf("$");
			if(dolorIndex > 0)
			{
				name_email = flname.substring(0,dolorIndex).trim();
			}
			else
			{
				name_email = flname;
			}
		System.out.println(name_email);*/
		
	//	str=str.replaceAll("[\/]+", "");
	//	System.out.println(str);
		
		/*try {
			String url = "http://localhost/?q=" + URLEncoder.encode ("?blah&", "UTF-8") + "&d=" + URLEncoder.encode ("blah", "UTF-8");
		System.out.println(url);
			
			
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		 /* Scanner in = new Scanner(System.in);
	        int t = in.nextInt();
	       
	        for(int a0 = 0; a0 < t; a0++){
	             long tot=0;
	            int n = in.nextInt();
	          
	            int arr[]={2,1,3,1,2,3};
	           
	            for(int i=3, j=0; i < n;){
	              tot+=i;
	              i=i+arr[j++];
	              if(j>=5)
	            	  j=0;
	            }
	           
	            System.out.println(tot);
	            
	        }*/
		
	}
	/* public static void main(String[] args) {
		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		   try
		   {
			   String s=br.readLine();
			   String arr[]=s.split(" ");
	        int N=Integer.parseInt(arr[0] );
	        int M=Integer.parseInt( arr[1]);
	        String arrstr[]=new String[N];
	        for(int i=0;i<N;i++)
	            {
	        	arrstr[i]= br.readLine();
	            
	        }
	        ?UTF-8?B?4KSq4KS/4KSv4KWC4KS3IOCknOCli+CktuClgA==?= 
	        int no=0;
	        boolean f=true;
	        if(N<M)
	        {
	        	no=N;
	        }
	        else
	        {
	        	no=M;
	        }
	        for(;no>0;no--)
	        {
	        	if(no==1)
		        {
		        	System.out.println(no);	
		        	f=false;
		        	break;
		        }
	        	else
	        	{
	       boolean f1= uppertri(arrstr,N,M, no);
	       if(f1)
	       {
	    	   
	    	   List<String> list = Arrays.asList(arrstr);
               
              
               Collections.reverse(list);
              
              
               arrstr = (String[]) list.toArray();
	    	   
	       boolean f2= lowertri(arrstr,N, M, no);
	        if(f2)
	        {
	        	System.out.println(no);	
	        	f=false;
	        	break;
	        }
	       }
	        
	       
	      
	        }
	        }
	        if(f)
	        {
	        	System.out.println("0");	
	        	
	        }
		   }
		   catch(Exception e)
		   {
			   e.printStackTrace();
		   }
	    }
	 
	 public static boolean  uppertri(String []arrstr,int N, int M, int no)
	 {
		 boolean f=true;
		 String st="";
		 for(int i=0;i<no;i++)
		 {
			 st+="#";
		 }
		 for(int i=0;i<N;i++)
		 {
			 if(arrstr[i].indexOf(st)>-1)		
			 {
				 if(i+no>=N)
				 {
					 f=false;
					 break;
				 }
				 int l=arrstr[i].indexOf(st);
				int n=no--; 
				for (int k=i+1;k<no;k++)
				{
					String stt="";
					for(int p=0;p<n;p++)
					 {
						 stt+="#";
					 }
					if(arrstr[k].substring(l, n).equals(stt))
					{
						
					}
					else
					{
						break;
					}
					n--;
					
				}
			 }
			 else
			 {
				 if(i+no>=N)
				 {
					 f=false;
					 break;
				 }
			 }
			 
		 }
		 return f;
	 }
	 
	 public static boolean  lowertri(String []arrstr,int N, int M, int no)
	 {
		 boolean f=true;
		 String st="";
		 for(int i=0;i<no;i++)
		 {
			 st+="#";
		 }
		 for(int i=0;i<N;i++)
		 {
			 if(arrstr[i].indexOf(st)>-1)		
			 {
				 if(i+no>=N)
				 {
					 f=false;
					 break;
				 }
				 int l=arrstr[i].indexOf(st);
				int n=no--; 
				for (int k=i+1;k<no;k++)
				{
					String stt="";
					for(int p=0;p<n;p++)
					 {
						 stt+="#";
					 }
					if(arrstr[k].substring(l, n).equals(stt))
					{
						
					}
					else
					{
						break;
					}
					n--;
					
				}
			 }
			 else
			 {
				 if(i+no>=N)
				 {
					 f=false;
					 break;
				 }
			 }
			 
		 }
		 return f;
	 }*/
}
