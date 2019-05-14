/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cv13;

import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;

/**
 *
 * @author martin
 */
public class Cv13 {
    
        
    public static SVG drawHexagon(SVG svg, double centerX, double centerY, double radius, ArrayList skip){
        int side = 0;
        for(double angle = 0; angle < 360; angle += 60){
            if(!skip.contains(side)/*side != skip*/){
                svg.line(centerX + radius*Math.cos(angle* 2*Math.PI/360), centerY + radius*Math.sin(angle* 2*Math.PI/360), 
                         centerX + radius*Math.cos((angle+60)* 2*Math.PI/360), centerY + radius*Math.sin((angle+60)* 2*Math.PI/360), 
                        0, 0, 0);
            }
            side++;
        }
        return svg;
    }
    
    public static void draw(int[][][][] matrixOfEdges, double radius){
        int sizeX = (int) Math.floor((2 + 1.5*matrixOfEdges[0].length)*((int)Math.floor(radius+0.5)));
        int sizeY = (int) Math.floor((2 + 1.8*matrixOfEdges.length)*((int)Math.floor(radius+0.5)));
        SVG svg = new SVG(sizeX, sizeY, "result");
        
        for(int i = 0; i < matrixOfEdges.length; i++){
            for(int j = 0; j < matrixOfEdges[0].length; j++){
                double x = 1.25*radius;
                double y = 1.25*radius;
                
                
                x += j * 3 * (radius/2);
                
                if(j % 2 == 0){
                    y += i*2 * Math.sqrt(radius*radius - (radius/2)*(radius/2));
                }
                else{
                    y += (i*2 + 1) * Math.sqrt(radius*radius - (radius/2)*(radius/2));
                }
                
                //int skip = -1;
                ArrayList skip = new ArrayList();
                for(int edgeIndex = 0; edgeIndex < 6; edgeIndex++){
                    if(matrixOfEdges[i][j][edgeIndex] != null){
                        if(matrixOfEdges[i][j][edgeIndex][2] == 0){
                            //skip = edgeIndex;
                            skip.add(edgeIndex);
                        }
                    }
                }
                svg = drawHexagon(svg, x, y, radius, skip);
                if(i == matrixOfEdges.length - 1 && j == matrixOfEdges[0].length - 1) svg.circle(x, y, radius/2.5);
                if(i == 0 && j == 0) svg.circle(x, y, radius/2.5);
            }
        }
        
        
        svg.save();
    }
    
    public static int[][][][] createMatrixOfEdges(int numberOfLines, int numberOfColumns){ 
        int[][][][] matrix = new int[numberOfLines][numberOfColumns][6][3];
        
        for (int i = 0; i < numberOfLines; i++) {
            for(int j = 0; j < numberOfColumns; j++){
                for(int k = 0; k < 6; k++){
                    matrix[i][j][k] = null;
                }
            }
        }
        
        for (int i = 0; i < numberOfLines; i++) {
            for(int j = 0; j < numberOfColumns; j++){
                if(j % 2 == 0){
                    if(j + 1 < numberOfColumns){
                        matrix[i][j][0] = new int[]{i, j + 1, 1};
                    }
                    if(i + 1 < numberOfLines){
                        matrix[i][j][1] = new int[]{i + 1, j, 1};
                    }
                    if(j - 1 >= 0){
                        matrix[i][j][2] = new int[]{i, j - 1, 1};
                    }
                    if(i - 1 >= 0 && j - 1 >= 0){
                        matrix[i][j][3] = new int[]{i - 1, j - 1, 1};
                    }
                    if(i - 1 >= 0){
                        matrix[i][j][4] = new int[]{i - 1, j, 1};
                    }
                    if(i - 1 >= 0 && j + 1 < numberOfColumns){
                        matrix[i][j][5] = new int[]{i - 1, j + 1, 1};
                    }
                }
                else{
                    if(i + 1 < numberOfLines && j + 1 < numberOfColumns){
                        matrix[i][j][0] = new int[]{i + 1, j + 1, 1};
                    }
                    if(i + 1 < numberOfLines){
                        matrix[i][j][1] = new int[]{i + 1, j, 1};
                    }
                    if(i + 1 < numberOfLines && j - 1 >= 0){
                        matrix[i][j][2] = new int[]{i + 1, j - 1, 1};
                    }
                    if(j - 1 >= 0){
                        matrix[i][j][3] = new int[]{i, j - 1, 1};
                    }
                    if(i - 1 >= 0){
                        matrix[i][j][4] = new int[]{i - 1, j, 1};
                    }
                    if(j + 1 < numberOfColumns){
                        matrix[i][j][5] = new int[]{i, j + 1, 1};
                    }
                }
            }
        }
        
        return matrix;
    }
    
    
    public static void randomDFS(int i, int j, boolean[][] visited, int[][][][] matrixOfEdges){
        visited[i][j] = true;
        
        TreeSet ks = new TreeSet();
        
        Random rand = new Random();
        
        int k = 100;
        //for(int k = 0; k < 6; k++){
        
        if(!(i ==  matrixOfEdges.length - 1 && j ==  matrixOfEdges[0].length - 1)){
            while(ks.size() != 6){
                do{
                    k = rand.nextInt(6);
                } while(ks.contains(k));
                ks.add(k);

                if(matrixOfEdges[i][j][k] != null){
                    int i1 = matrixOfEdges[i][j][k][0];
                    int j1 = matrixOfEdges[i][j][k][1];

                    if(!visited[i1][j1]){
                        matrixOfEdges[i][j][k][2] = 0;
                        for(int l = 0; l < 6; l++){
                            if(matrixOfEdges[i1][j1][l] != null){
                                if(matrixOfEdges[i1][j1][l][0] == i && matrixOfEdges[i1][j1][l][1] == j){
                                    matrixOfEdges[i1][j1][l][2] = 0;
                                }
                            }
                        } 
                        randomDFS(i1, j1, visited, matrixOfEdges);
                    }
                }
            }
        }
    }
    
    public static int[][][][] createMaze(int[][][][] matrixOfEdges){
        boolean[][] visited = new boolean[matrixOfEdges.length][matrixOfEdges[0].length];
        
        for(int i = 0; i < visited.length; i++){
            for(int j = 0; j < visited[0].length; j++){
                visited[i][j] = false;
            }
        }
        
        randomDFS(0, 0, visited, matrixOfEdges);
    
        return matrixOfEdges;
    }
    
    public static void cv13(int numberOfLines, int numberOfColumns, double radius){
        int[][][][] matrixOfEdges = createMatrixOfEdges(numberOfLines, numberOfColumns);
        
        matrixOfEdges = createMaze(matrixOfEdges);
        
        draw(matrixOfEdges, radius);
    }
    
    public static void main(String [] args)
    {
        cv13(10, 20, 20);
    }
}
