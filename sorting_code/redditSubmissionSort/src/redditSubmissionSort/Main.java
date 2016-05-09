package redditSubmissionSort;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

import org.json.JSONException;
import org.json.JSONObject;

public class Main {
	
	/*public static List<String> readFile(File file)
	{
List<String> output = new List<String>();
BufferedReader br = null;
try
		{
			br = new BufferedReader(new FileReader(file));
		}
catch (FileNotFoundException e)
		{
// FIXME : Consider throwing an exception that can be caught.
			e.printStackTrace();
		}
try
		{
String line = br.readLine();
while (line != null)
			{
				output.add(line);
				line = br.readLine();
			}
			br.close();
		}
catch (IOException e){e.printStackTrace();}
return output;	
	}*/
	
	private static int MIN_STORY_SIZE = 400;
	private static String OUT_PATH = "out.txt";
	private static int NUM_LINES = 196531736;
	
	public static void main(String[] args){
		
		int count = 0;
		
		File file = new File("data/reddit/RS_full_corpus.txt");
		BufferedReader br = null;
		
		
		File fout = new File(OUT_PATH);
		
		FileOutputStream fos = null;
		
		try {
			fos = new FileOutputStream(fout);
		} catch (FileNotFoundException e) { e.printStackTrace();}
		
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// FIXME : Consider throwing an exception that can be caught.
			e.printStackTrace();
		}
		
		try {
			String line = br.readLine();
			while (line != null) {
				
				if (count % 100000 == 0){
					System.out.println("processed " + count + "/" + NUM_LINES + "lines");
				}
				
				//output.add(line);
				line = br.readLine();
				
				try {
					JSONObject jObject = new JSONObject(line);
					String story = jObject.getString("selftext");
					String subreddit = jObject.has("subreddit") ? jObject.getString("subreddit") : "";
					String author = jObject.getString("author");
					if(story.length() >= MIN_STORY_SIZE ){
						JSONObject outJSON = new JSONObject();
						outJSON = outJSON.put("author", author);
						outJSON = outJSON.put("subeddit", subreddit);
						outJSON = outJSON.put("text", story);
						bw.write(outJSON.toString());
						bw.newLine();
					}
				} catch (JSONException e){ e.printStackTrace(); }
				
				count += 1;
					
			}
			br.close();
			bw.close();
		} catch (IOException e){e.printStackTrace();}
	}
	
	/*File fout = new File("out.txt");
	FileOutputStream fos = new FileOutputStream(fout);
 
	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
 
	for (int i = 0; i < 10; i++) {
		bw.write("something");
		bw.newLine();
	}
 
	bw.close();*/
}
