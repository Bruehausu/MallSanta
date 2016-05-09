package redditCommentQuery;

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
			"reddit", "Reddit", "subreddit", "upvote", "downvote", "upmod", "up mod", "downmod", "down mod", 
			"Wikipedia", 
			"Nokia", "Samsung", "Linux", " Asus ", " Ubuntu ", " Debian ",
			"unsubscribe",
			" OS ",
			" Mac ", 
			"IP adress",
			"www.",
			".net",
			".org",
			".com",
			"Photoshop",
			"Bittorrent",
			"shitpost",
			"Ron Paul",
			"John Cena",
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

	};
	
	private static String[] targetTags1 = {
			/*"my son",
			"my boy",
			*/
			"mall santa"
	};
	
	private static String[] targetTags2 = {
			/*
			"my daughter",
			"my girl"
			*/
			"terrible",
			"worst",
			"awful",
			"drunk",
			"smoking",
			"creepy",
			"fought",
			"vomit",
			"swore",
			"swear",
			"coked",
			"cocaine",
			"heroin",
			"whiskey",
			"beer",
			"cigarette",
			
	};
	
	private static String[] targetTags3 = {
			"christmas "
			/*
			"my baby",
			"the baby",
			"my child",
			"the children",
			"my children",
			"my kid",
			"the kids",
			"my kids",
			"my eldest",
			"my youngest",
			*/
	};
	
	
	private static int filterByWords(String s){
		for(String w: filterTags){
			if(s.contains(w)){
				return 0;
			}
		}
		
		String sLow = s.toLowerCase();
		
		
		for(String w: targetTags2){
			if(sLow.contains(w)) {
				for(String ww: targetTags1){
					if(sLow.contains(ww)) return 1;
				}
				for(String ww: targetTags3){
					if(sLow.contains(ww)) return 3;
				}
			};
		}
		
		return 0;
	}
	
	private static void processFile(String name, BufferedWriter bw1,
			BufferedWriter bw2, BufferedWriter bw3){
		
		System.out.println("processing " + name);
		int count = 0;
		
		File file = new File("data/" + name);
		BufferedReader br = null;
		
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
					int res = filterByWords(story);
					if(res == 3){
						bw3.write(line);
						bw3.newLine();
					} else if (res == 2){
						bw2.write(line);
						bw2.newLine();
					} else if (res == 1){
						bw1.write(line);
						bw1.newLine();
					}
				} catch (JSONException e){ e.printStackTrace(); }
				
				count += 1;
					
			}

		} catch (IOException e){e.printStackTrace();}
		
		try{
			br.close();
			
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args){
		

		File fout1 = new File("out-RS-1.txt");
		File fout2 = new File("out-RS-2.txt");
		File fout3 = new File("out-RS-3.txt");
		
		FileOutputStream fos1 = null,fos2 = null,fos3 = null;
		
		try {
			fos1 = new FileOutputStream(fout1);
			fos2 = new FileOutputStream(fout2);
			fos3 = new FileOutputStream(fout3);
		} catch (FileNotFoundException e) { e.printStackTrace();}
		
		BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(fos1));
		BufferedWriter bw2 = new BufferedWriter(new OutputStreamWriter(fos2));
		BufferedWriter bw3 = new BufferedWriter(new OutputStreamWriter(fos3));

		File data = new File("data");
		File[] files = data.listFiles();
		String[] fil = data.list();
		
		for(String s: fil){
			if(!s.equals(".DS_Store")){
				System.out.println("processing " + s);
				processFile(s,bw1,bw2,bw3);
			}
		}
		
		try{
			bw1.close();
			bw2.close();
			bw3.close();
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
}
