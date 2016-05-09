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
	
	private static String[] filterTags = {
			"Obama", "Clinton", "Donald Trump", "Hillary", "Ted Cruz", "George Bush",
			"reddit", "Reddit", "subreddit", "upvote", "downvote", "upmod", "up mod",
			"downmod", "down mod",
			"Wikipedia",
			"Nokia",
			"Samsung",
			"Linux",
			" Asus ",
			" Ubuntu ",
			" Debian ",
			"unsubscribe",
			" OS ",
			" Mac ", 
			"IP adress",
			/*"www.",
			".net",
			".org",
			".com",*/
			"Photoshop",
			"Bittorrent",
			//"government", "Government",
			"politics",
			"shitpost",
			"terrorist",
			"terrorism",
			"Ron Paul",
			"John Cena",
			//"property rights",
			"trolling",
			"IMO",
			"IMHO",
			"Windows",
			"programmer",
			"youtube",
			"c++",
			"C++",
			"PHP",
			"SQL",
			"ISP",
			"Mathematica",
			"Matlab",
			"lorem ipsum",
			"Lorem ipsum",
			"GlaxoSmithKline",
			"Dick Cheney",
			"Al Gore"
			/*,

			" you ",
			" your ",
			" you’re ",
			"You ",
			"Your ",
			"You’re "*/
	};
	
	
	private static boolean filterByWords(String s){
		for(String w: filterTags){
			if(s.contains(w)){
				return false;
			}
		}
		return true;
	}
	
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
					String story = jObject.getString("text");
					String subreddit = jObject.has("subreddit") ? jObject.getString("subreddit") : "";
					String author = jObject.getString("author");
					//if(story.length() >= MIN_STORY_SIZE ){
					if(filterByWords(story)){
						bw.write(line);
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

		File data = new File("data");
		File[] files = data.listFiles();
		String[] fil = data.list();
		
		for(String s: fil){
			if(!s.equals(".DS_Store")){
				System.out.println("processing " + s);
				processFile(s);
			}
		}
		
		/*File fout400to700 = new File("RS_400_to_800.txt");
		
		FileOutputStream fos = null;
		
		try {
			fos = new FileOutputStream(fout);
		} catch (FileNotFoundException e) { e.printStackTrace();}
		
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		*/
		
	}
	
	
}
