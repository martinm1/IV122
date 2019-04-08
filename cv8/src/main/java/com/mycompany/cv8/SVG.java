package com.mycompany.cv8;

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
        
    }
    
    public void line(double x1, double y1, double x2, double y2, int r, int g, int b){
        text+="\n  <line x1=\""+x1+"\" y1=\""+y1+
          "\" x2=\""+x2+"\" y2=\""+y2+
          "\" style=\"stroke:rgb("+r+","+g+","+b+");stroke-width:1\"/>";
    }; 
    
    public void circle(double x1, double y1, double r){
        //text+="\n  <circle cx=\"50\" cy=\"50\" r=\"40\" stroke=\"green\" stroke-width=\"4\" fill=\"yellow\" />";
        text+="\n  <circle cx=\""+x1+"\" cy=\""+y1+"\" r=\""+r+"\" stroke=\"red\" stroke-width=\"1\" fill=\"none\"/>";
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
}