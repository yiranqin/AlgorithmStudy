package edu.upenn.yiranqin.stringrelated;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import edu.upenn.yiranqin.datastructures.MyTrie;

public class DictionaryUtil {
	public static MyTrie buildUpDictionaryTrie(File file){
		if(file == null || !file.exists())
			return null;
		
		MyTrie dicTrie = new MyTrie();
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(new FileReader(file));
			String curWord = null;
			while((curWord = reader.readLine()) != null){
				dicTrie.addWord(curWord.toLowerCase());
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
			if(reader != null)
				reader.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return dicTrie;
	}
}
