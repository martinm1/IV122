package com.mycompany.IV122;

import java.io.PrintWriter;

/**
 *
 * @author martin
 */
public class SVG {
    int width;
    int height;
    String name;
    String text = "";
    
    public SVG(int width, int height, String name){
        this.width = width;
        this.height = height;
        this.name = name;
        text = "<!DOCTYPE html>\n" + "<html>\n" + "<body>\n" +
          "\n<h1>"+ name +"</h1>\n\n" +
          "<svg width=\"" + width + "\" height=\"" + height + "\">";
        //text+="\n  <circle cx=\"50\" cy=\"50\" r=\"40\" stroke=\"green\" stroke-width=\"4\" fill=\"yellow\" />";
    }
    
    public void line(int x1, int y1, int x2, int y2, int r, int g, int b){
        text+="\n  <line x1=\""+x1+"\" y1=\""+y1+
          "\" x2=\""+x2+"\" y2=\""+y2+
          "\" style=\"stroke:rgb("+r+","+g+","+b+");stroke-width:1\"/>";
    }; 
    
    public void save(){
        text+="\n</svg> \n" +"\n" + "</body>\n" + "</html>";
        
        try{
            PrintWriter pw = new PrintWriter(name+ ".svg");
            pw.print(text);
            pw.close();
        } catch (Exception e){
            System.out.println(e);
        }
    }
}
