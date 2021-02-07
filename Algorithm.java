package controller;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class Algorithm {
	
	
	Map<String, Integer> Count = new HashMap<String, Integer>();
	Map<String, Integer> sorted = new TreeMap<String, Integer>();

	
	String []topkeywords=new String[3]; 
	
	
	String []beforeTokens;
	int digit=0;
	int numbercount=0;
	String ListData="";
	ArrayList<String> list = new ArrayList<String>();

	ExtractDataAndCreateTokens extractDataAndCreateTokens=null;
	StopWordsRemoval stopWordsRemoval=null;
	String postmsg=null;
	TreeSet <String> tokens=new TreeSet<String>();
	TreeSet <String> afterStopwords=new TreeSet<String>();
	public Algorithm(String postmsg) 
	{
		this.postmsg=postmsg;
	}
	 
	public String MyAlgorithm() throws ClassNotFoundException, SQLException
	{
		extractDataAndCreateTokens=new ExtractDataAndCreateTokens();
		extractDataAndCreateTokens.setData(postmsg);
		tokens=extractDataAndCreateTokens.getTokens();
		System.out.println(tokens);
		
		stopWordsRemoval=new StopWordsRemoval();
		stopWordsRemoval.setTokens(tokens);
		afterStopwords=stopWordsRemoval.getTokens();
		System.out.println(afterStopwords);
		
		PositiveWords positiveWords=new PositiveWords();
		positiveWords.setTokens(tokens);
		String i="";
		String ii="";
		String iii="";
		i=positiveWords.Checkstatus();
		
		System.out.println(i);
		
		NegativeWords negativeWords=new NegativeWords();
		negativeWords.setTokens(tokens);
		StressWords stressWords=new StressWords();
		stressWords.setTokens(tokens);
		String []all=postmsg.split(" ");
		if(!i.equals(""))
		{
			
			System.out.println("1");
			
			int position=Arrays.asList(all).indexOf(i);
			if(position!=0)
			{
			if(all[position-1].equals("not") || all[position-1].equals("no"))
			{
				System.out.println("2");
				return "Negative";
				
			}
			}
			return "Positive";
		}
		else
		{
			ii=negativeWords.Checkstatus();
			System.out.println("3");
					if(!ii.equals(""))
					{

						//System.out.println(ii);
						
						int position=Arrays.asList(all).indexOf(ii);
						if(position!=0)
						{
						if(all[position-1].equals("not") || all[position-1].equals("no"))
						{
							System.out.println("4");
							return "Positive";
						}
						}
						return "Negative";
						
					}
					else
					{
						iii=stressWords.Checkstatus();
						System.out.println("5");
						if(!iii.equals(""))
						{

							//System.out.println(ii);
							
							int position=Arrays.asList(all).indexOf(iii);
							if(position!=0)
							{
							if(all[position-1].equals("not") || all[position-1].equals("no"))
							{
								System.out.println("6");
								
								return "Unspecificed";
							}
							}
							return "Stressed";
							
						}
						else
						{
							System.out.println("7");
							return "Unspecificed";
						}
					
					//////////////////-------------------------------/////////////////////////////
		}
		}
		
		}

}
