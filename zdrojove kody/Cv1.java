package com.mycompany.IV122;

import ij.ImagePlus;
import ij.io.FileSaver;
import ij.process.ColorProcessor;

/**
 *
 * @author xmejzli2
 */




public class Cv1 {
    
    
    
    public static int getNumOfDivisors(int i){
        int numOfDivisorsOfI = 0;
        for(int j = 1; j < i; j++){
                if(i % j == 0) numOfDivisorsOfI++;
        }
        return numOfDivisorsOfI;
    }
    
    
    
    public static void one(){
        int number = 1;
        int maxNumOfDivisors = 0;
        boolean unambiguous = true;
        
        for(int i = 1; i < 10000; i++){
            int numOfDivisorsOfI = getNumOfDivisors(i);

            if (numOfDivisorsOfI >= maxNumOfDivisors){
                number = i;
                if (numOfDivisorsOfI == maxNumOfDivisors){
                    unambiguous = false;
                }
                if (numOfDivisorsOfI > maxNumOfDivisors){
                    unambiguous = true;
                }
                maxNumOfDivisors = numOfDivisorsOfI;
            }
        }
        
        System.out.println("1 : cislo s nejvetsim poctem delitelu: " + number);
        System.out.println("1 : pocet delitelu toho cisla: " + maxNumOfDivisors);
        System.out.println("1 : cislo je jednoznacne: " + unambiguous);
    }
    
    public static boolean canBeDecomposed(int i){
        boolean canBeDecomposed = false;
        for(int j = 1; j < Math.sqrt(i); j++){
            for(int k = 1; k < Math.sqrt(i); k++){
                for(int l = 1; l < Math.sqrt(i); l++){
                    if (i == j*j + k*k + l*l)
                        canBeDecomposed = true;
                }
            }
        }
        return canBeDecomposed;
    }

    public static void two(){
        int numOfNumbersCannotBeDecomposed = 0;
        
        
        for(int i = 1; i < 1000; i++){
            if(!canBeDecomposed(i))
                numOfNumbersCannotBeDecomposed++;
        }
        System.out.println("2 : pocet cisel: " + numOfNumbersCannotBeDecomposed);
    }
    
    public static int getNumOfSteps(int i){
        int steps = 0;
        do{
            if(i % 2 == 0)
                i = (int) (i/2);
            else
                i = 3*i + 1;
            
            steps++;
        } while(i != 1);
        
        return steps;
    }
    
    public static void three(){
        int numOfSteps = 0;
        int number = 1;
        for(int i = 1; i < 10000; i++){
            if(numOfSteps < getNumOfSteps(i)){
                numOfSteps = getNumOfSteps(i);
                number = i;
            }
        }
        System.out.println("3 : cislo: " + number);
        System.out.println("3 : pocet kroku: " + numOfSteps);
    } 
    
    public static boolean isPrime(int number){
        if(number == 0 || number == 1){
            return false;
        }
        
        boolean prime = true;
        
        for (int i = 2; i<=Math.sqrt(number); i++){
            if (number % i == 0){
                prime = false;
            }
        }
        
        return prime;
    }
    
    public static void four(){
        int sum = 0;
        
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                for (int k = 0; k < 10; k++){
                    if(i != 3 && j != 3 && k!= 3){
                        int number = 100*i+10*j+k;
                        if (isPrime(number)){
                            sum+= number;
                        }
                    } 
                }
        System.out.println("4 : soucet: " + sum);
    }
    
    public static int euclid(int smaller, int larger){
        if(smaller > larger){
            int r = smaller;
            smaller = larger;
            larger = r;
        }
        while(smaller != 0){
            int r = larger % smaller;
            larger = smaller;
            smaller = r;
        }
        
        return larger;
    }
    
    public static void five(){
        int first = 1;
        int second = 1;
        
        do{
            int firstBefore = first;
            first = second;
            second = firstBefore + second + euclid(firstBefore, second);
        } while (second < 1000000);
        System.out.println("5 : cislo: " + second);
    }
    
    public static void bitmap(){
        int w = 500;
        int h = 500;
        
        ColorProcessor ip = new ColorProcessor(w, h);
        
        
        int[] pixelRGB = new int[3];
        
        pixelRGB[0] = 0;
        pixelRGB[1] = 0;
        pixelRGB[2] = 0;
        
        for (int x = 0; x < w; x++)
        {
            for (int y = 0; y < h; y++)
            {
                pixelRGB[0] = x*(256-1)/w;
                pixelRGB[2] = y*(256-1)/h;
                ip.putPixel(x, y, pixelRGB);
            }
        }
        ImagePlus img = new ImagePlus("image", ip);
        //img.show();
        FileSaver fs = new FileSaver(img);
        fs.saveAsPng("bitmap.png");
    }
    
    public static void star4(){
        int size = 500;
        
        SVG svg = new SVG(size, size, "star4");
        for(int i = 0; i <= size/2; i = i+25){
            svg.line(size/2, size/2+i, size-i, size/2, 0, 0, 0);
            svg.line(size/2, size/2-i, size-i, size/2, 0, 0, 0);
            
            svg.line(size/2, size/2+i, i, size/2, 0, 0, 0);
            svg.line(size/2, size/2-i, i, size/2, 0, 0, 0);
        }
        svg.save();
    }
    
    public static void weirdThing(){
        int size = 500;
        
        SVG svg = new SVG(size, size, "weirdThing");
        for(int i = 0; i <= size; i = i+25){
            svg.line(size, i, size-i, size, 0, 0, 0);
            svg.line(size, i, i, 0, 0, 0, 0);
            
            svg.line(0, i, size-i, 0, 0, 0, 0);
            svg.line(0, i, i, size, 0, 0, 0);
        }
        svg.save();
    }

    public static void ulamSpiral(){
        int w = 200;
        int h = 200;
        
        int maximum = w*h;
        
        ColorProcessor ip = new ColorProcessor(w, h);
        
        
        
        int[] pixelRGB = new int[3];
        int[] pixelRGB2 = new int[3];
        
        pixelRGB[0] = 255;
        pixelRGB[1] = 255;
        pixelRGB[2] = 255;
        
        pixelRGB2[0] = 0;
        pixelRGB2[1] = 0;
        pixelRGB2[2] = 0;
        
        int x = 0;//w-1;
        int y = 0;//h-1;
        int zerox = 0;
        int zeroy = 0;
        
        do{
            for(; x < w; x++){
                if(isPrime(maximum)){
                    ip.putPixel(x, y, pixelRGB2);
                }
                else{
                    ip.putPixel(x, y, pixelRGB);
                }
                maximum--;
                //if(pixelRGB[1] == 0) pixelRGB[1] = 255;
                //else pixelRGB[1] = 0;
            }
            x--;//fix x overflow
            zeroy++;//first row is finished
            y++;//go to the next row
            
            for(;y < h; y++){
                if(isPrime(maximum)){
                    ip.putPixel(x, y, pixelRGB2);
                }
                else{
                    ip.putPixel(x, y, pixelRGB);
                }
                maximum--;
                //if(pixelRGB[1] == 0) pixelRGB[1] = 255;
                //else pixelRGB[1] = 0;
            }
            y--;//fix y overflow   
            w--;//last column is finised
            x--;//go to previous column
            
            for(; x >= zerox; x--){
                if(isPrime(maximum)){
                    ip.putPixel(x, y, pixelRGB2);
                }
                else{
                    ip.putPixel(x, y, pixelRGB);
                }
                maximum--;
                //if(pixelRGB[1] == 0) pixelRGB[1] = 255;
                //else pixelRGB[1] = 0;
            }
            x++;//fix x overflow
            h--;//last row is finised
            y--;//go to previous row
            
            for(; y >= zeroy; y--){
                if(isPrime(maximum)){
                    ip.putPixel(x, y, pixelRGB2);
                }
                else{
                    ip.putPixel(x, y, pixelRGB);
                }
                maximum--;
                //if(pixelRGB[1] == 0) pixelRGB[1] = 255;
                //else pixelRGB[1] = 0;
            }
            y++;//fix y overflow
            zerox++;//first column in finished
            x++;//go to next column
            

            
            //System.out.println(x+" " + y+" " + maximum);
        }while(maximum > 0);
        
        ImagePlus img = new ImagePlus("image", ip);
        //img.show();
        
        FileSaver fs = new FileSaver(img);
        fs.saveAsPng("ulamSpiral.png");
    }
    
    public static int euclidNumOfStepsSubtraction(int smaller, int larger){
        if(smaller > larger){
            int r = smaller;
            smaller = larger;
            larger = r;
        }
        
        if (smaller == 0) return 0;
        else return euclidNumOfStepsSubtraction(smaller, larger-smaller) + 1;
    }
    
    public static int euclidNumOfStepsModulo(int smaller, int larger){
        if(smaller > larger){
            int r = smaller;
            smaller = larger;
            larger = r;
        }
        
        if (smaller == 0) return 0;
        else return euclidNumOfStepsSubtraction(larger % smaller, smaller) + 1;
    }
    
    
    public static void euclidAll(int type){ //0=subtraction, 1=modulo, 2=combined
        int w = 500;
        int h = 500;
        
        ColorProcessor ip = new ColorProcessor(w, h);
        
        
        int[] pixelRGB = new int[3];
        
        pixelRGB[0] = 0;
        pixelRGB[1] = 0;
        pixelRGB[2] = 0;
        
        for (int x = 0; x < w; x++)
        {
            for (int y = 0; y < h; y++)
            {
                if(type==0){
                    if(euclidNumOfStepsSubtraction(x,y) < 256){
                        pixelRGB[0] = euclidNumOfStepsSubtraction(x,y);
                    }
                    else{
                        pixelRGB[0] = 0;
                    }
                    ip.putPixel(x, y, pixelRGB);
                }
                else if (type==1){
                    if(euclidNumOfStepsModulo(x,y) < 256){
                        pixelRGB[0] = euclidNumOfStepsModulo(x,y);
                    }
                    else{
                        pixelRGB[0] = 0;
                    }
                    ip.putPixel(x, y, pixelRGB);
                
                }
                else if (type==2){
                    if(euclidNumOfStepsSubtraction(x,y) < 256){
                        pixelRGB[0] = euclidNumOfStepsSubtraction(x,y);
                    }
                    else{
                        pixelRGB[0] = 0;
                    }
                    if(euclidNumOfStepsModulo(x,y) < 256){
                        pixelRGB[1] = euclidNumOfStepsModulo(x,y);
                    }
                    else{
                        pixelRGB[1] = 0;
                    }
                    ip.putPixel(x, y, pixelRGB);
                }
            }
        }
        ImagePlus img = new ImagePlus("image", ip);
        //img.show();
        
        FileSaver fs = new FileSaver(img);
        if(type==0){
            fs.saveAsPng("euclidSubtraction.png");
        }
        else if (type==1){
            fs.saveAsPng("euclidModulo.png");
        }
        else if (type==2){
            fs.saveAsPng("euclidCombined.png");
        }
    }
    
    public static void euclidSubtraction(){
        euclidAll(0);
    }
    
    public static void euclidModulo(){
        euclidAll(1);
    }
    
    public static void euclidCombined(){
        euclidAll(2);
    }
    
    public static void main(String [] args)
    {
        one();
        two();
        three();
        four();
        five();
        bitmap();
        star4();
        weirdThing();
        ulamSpiral();
        euclidSubtraction();
        euclidModulo();
        euclidCombined();
    }
}
