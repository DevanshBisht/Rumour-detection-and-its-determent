package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.TreeSet;

import databaseConnection.DbOperation;

public class NegativeWords {
	
	TreeSet <String> tokens=new TreeSet<String>();
	int i=0;

	/**
	 * @param tokens the tokens to set
	 */
	public void setTokens(TreeSet<String> tokens) {
		this.tokens = tokens;
	} 
	public String Checkstatus() throws ClassNotFoundException, SQLException
	{
		
		String pword="";
		Iterator<String> itr=this.tokens.iterator();
		while(itr.hasNext()){
			String word=itr.next(); 
			ResultSet rs=DbOperation.selectOperation("SELECT * FROM  negativewords");
			while(rs.next())
			{
				String keyword=rs.getString("words");
				word=word.toLowerCase();
				keyword=keyword.toLowerCase();
				if(keyword.equals(word))
				{
				pword=word;	
				}
				
			}
		}
		
		return pword;
		
	}
}
