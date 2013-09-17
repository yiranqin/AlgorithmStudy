package edu.upenn.yiranqin.stringrelated;

public class StringHandler {
	public String exampleAllWords = "verygoodboy";
	public String exampleAllWordsWithDiffParse = "Thisisawesome";
	public String exampleNotAllWords = "thit";
	public String exampleNoWords = "thumgry";
	
	public static void main(String[] argv){
		new StringHandler().start();
	}
	
	public void start(){
		parseSimple(0, 0, exampleNotAllWords);		
	}
	
	public int parseSimple(int wordStart, int wordEnd, String sentence){
		if(wordEnd >= sentence.length())
			return wordEnd - wordStart;
		
		String word = sentence.substring(wordStart, wordEnd + 1);
		
		int bestExact = parseSimple(wordEnd + 1, wordEnd + 1, sentence);
		int bestExtend = parseSimple(wordStart, wordEnd + 1, sentence);
		
		System.out.println(word + " " + bestExact + " " + bestExtend);
		return Math.min(bestExact, bestExtend);
	
	}
}
