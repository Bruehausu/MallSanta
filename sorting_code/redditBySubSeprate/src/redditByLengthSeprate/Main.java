package redditByLengthSeprate;

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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	private static String targets[] = {"30daysit", 
			"100sets", 
			"100wordstories",
			"relationship_advice",
			"AskReddit",
			"reddit.com",
			"IAmA",
			"canada",
			"Christianity",
			"TwoXChromosomes",
			"ihaveissues",
			"alcoholism",
			"bisexual",
			"bayarea",
			"cripplingalcoholism",
			"offmychest",
			"actuallesbians",
			"BreakUps",
			"daddit",
			"confession",
			"Psychonaut",
			"Mommit",
			"bestof",
			"self",
			"relationshipadvice",
			"BurningMan",
			"LSD",
			"Drugs"
	};
	
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
		
		Map<String, BufferedWriter> outs = new HashMap<String, BufferedWriter>();
		List<String> tars = Arrays.asList(targets);

		File data = new File("data");
		File[] files = data.listFiles();
		for(File f: files){
			if(!f.getPath().equals("data/.DS_Store")){
				System.out.println("processing " + f.getPath());
				
				BufferedReader br = null;
				try {
					br = new BufferedReader(new FileReader(f));
				} catch (FileNotFoundException e) {
					// FIXME : Consider throwing an exception that can be caught.
					e.printStackTrace();
				}
				
				try {
					
					int count = 0;
					
					String line = br.readLine();
					while (line != null) {
						
						if (count % 1000000 == 0){
							System.out.println("processed " + count + "lines");
						}
						
						//output.add(line);
						
						
						if(line == null){break;}
						
						try {
							JSONObject jObject = new JSONObject(line);
							//String story = jObject.getString("text");
							String subreddit = jObject.getString("subeddit");
							
							if(outs.containsKey(subreddit)){
								outs.get(subreddit).write(line);
								outs.get(subreddit).newLine();
							} else if (tars.contains(subreddit)){
								System.out.println("adding subreddit: " + subreddit);
								File fout = new File("RS_" + subreddit + ".txt");
									
								FileOutputStream fos = null;
									
								try {
									fos = new FileOutputStream(fout);
								} catch (FileNotFoundException e) { e.printStackTrace();}
									
								BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
								bw.write(line);
								bw.newLine();
								outs.put(subreddit, bw);
							}
							
						} catch (JSONException e){ 
							e.printStackTrace(); 
							System.exit(0);
						}
						
						count += 1;
						line = br.readLine();
							
					}

				} catch (IOException e){e.printStackTrace();}
				
				try{
					br.close();
				} catch(IOException e){
					e.printStackTrace();
				}
			}
		}
		
		try{
			for(Map.Entry<String, BufferedWriter> ent: outs.entrySet()){
				ent.getValue().close();
			}

		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
