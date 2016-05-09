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
	private static double BIG_SIZE = 224.57;
	private static int NUM_LINES = 54504410;
	
	private static void processFile(String name){
		
		System.out.println("processing " + name);
		int count = 0;
		
		File file = new File("data/" + name);
		BufferedReader br = null;
		
		
		File fout = new File("out-" + name);
		
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
				
				if (count % 1000000 == 0){
					System.out.println("processed " + count + " of about " + NUM_LINES + "lines");
				}
				
				//output.add(line);
				line = br.readLine();
				
				if(line == null){break;}
				
				try {
					JSONObject jObject = new JSONObject(line);
					String story = jObject.getString("body");
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

		} catch (IOException e){e.printStackTrace();}
		
		try{
			br.close();
			bw.close();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args){

		processFile("RC_2008-01.txt");
		processFile("RC_2008-02.txt");
		processFile("RC_2008-03.txt");
		processFile("RC_2008-04.txt");
		processFile("RC_2008-05.txt");
		processFile("RC_2008-06.txt");
		processFile("RC_2008-07.txt");
		processFile("RC_2008-08.txt");
		processFile("RC_2008-09.txt");
		processFile("RC_2008-10.txt");
		processFile("RC_2008-11.txt");
		processFile("RC_2008-12.txt");
		processFile("RC_2007-10.txt");
		processFile("RC_2007-11.txt");
		processFile("RC_2007-12.txt");
		
	}
	
	
}
