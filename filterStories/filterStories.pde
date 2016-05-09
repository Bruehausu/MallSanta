/**
 * LoadFile 1
 * 
 * Loads a text file that contains two numbers separated by a tab ('\t').
 * A new pair of numbers is loaded each frame and used to draw a point on the screen.
 */

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


BufferedReader br;
BufferedWriter bw;
String fileName = "out-RC_2011-12.txt";

boolean running;
String currLine;
String currStory;



void setup() {
  size(1000, 1000);
  background(0);
  stroke(255);
  frameRate(12);
  
  try{
    print(System.getProperty("user.dir"));
    running = true;
  
    File file = new File("Documents/filterStories/data/" + fileName);
    br = new BufferedReader(new FileReader(file));
  
    File fout = new File("Documents/filterStories/data/out-" + fileName);
    FileOutputStream fos = new FileOutputStream(fout);
    bw = new BufferedWriter(new OutputStreamWriter(fos));
    readNext();
  } catch(Exception e){
    e.printStackTrace();
  }
  
  
  
  
}

void draw() {
  background(0);
  text(currStory, 10, 10, 990, 990);
}

void stop(){
  try{
    br.close();
    bw.close();
    currStory = "stopped";
    running = false;
  } catch(Exception e){
    
  }
}

void readNext(){
  try{
    currLine = br.readLine();
    if(currLine != null){
      currStory = JSONObject.parse(currLine).getString("text");
    } else {
      stop();
    }
  } catch (Exception e){
    e.printStackTrace();
  }
}
  

void keyPressed() {
  if(running){
    
    println("coded:" + key + " | "  + keyCode);

    try{
      if(keyCode == 10){
        println("return : " + currLine);
        println("" + (bw == null));
        bw.write(currLine);
        bw.newLine();
        readNext();
      } else if (keyCode == 8){
        println("back");
        readNext();
      } else if (keyCode == 9) {
        println("tab");
        stop();
      }
        
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}