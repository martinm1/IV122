/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cv12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author martin
 */
public class Cv12 {
    
    
    public static ArrayList<int[]>[][] loadMatrixOfEdges(String file){
        int numberOfLines = 0;

        try(BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line = br.readLine();
            while (line != null) {
                numberOfLines++;
                line = br.readLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(Cv12.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int[][] field = new int[numberOfLines][numberOfLines];
        
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {

            for (int i = 0; i < numberOfLines; i++) {
                String line = br.readLine();
                line = line.replaceAll(" ", "");
                for(int j = 0; j < numberOfLines; j++){
                    field[i][j] = Integer.decode(""+line.charAt(j));
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Cv12.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        /*
        for (int i = 0; i < numberOfLines; i++) {
            for(int j = 0; j < numberOfLines; j++){
                System.out.print(field[i][j]+" ");
            }
            System.out.print("\n");
        }*/
        
        ArrayList<int[]>[][] result = new ArrayList[numberOfLines][numberOfLines];
        
        for (int i = 0; i < numberOfLines; i++) {
            for(int j = 0; j < numberOfLines; j++){
                result[i][j] = new ArrayList();
            }
        }
        
        for (int i = 0; i < numberOfLines; i++) {
            for(int j = 0; j < numberOfLines; j++){
                if(field[i][j] != 0){ //i hereby forbid loops
                    if(i - field[i][j] >= 0){
                        result[i][j].add(new int[]{i - field[i][j], j});
                    }
                    if(i + field[i][j] < numberOfLines){
                        result[i][j].add(new int[]{i + field[i][j], j});
                    }
                    if(j - field[i][j] >= 0){
                        result[i][j].add(new int[]{i, j - field[i][j]});
                    }
                    if(j + field[i][j] < numberOfLines){
                        result[i][j].add(new int[]{i, j + field[i][j]});
                    }
                }
            }
        }
        
        /*
        for(int i = 0; i < result[0][0].size(); i++){
            System.out.println(result[0][0].get(i)[0] +" " + result[0][0].get(i)[1]);
        }*/
        
        return result;
    }

    
    public static boolean areVerticesSame(int[] startVertex, int[] endVertex){
        return (startVertex[0] == endVertex[0] && startVertex[1] == endVertex[1]);
    }
    
    public static boolean constainsVertex(ArrayList<int[]> pathList, int[] vertex){
        for(int[] v : pathList){
            if (areVerticesSame(v, vertex)) return true;
        }
        return false;
    }
    
    public static ArrayList findPaths(ArrayList listOfPaths, ArrayList<int[]> pathList, int[] startVertex, int[] endVertex, ArrayList<int[]>[][] matrixOfEdges){
        pathList.add(startVertex);
        
        if(areVerticesSame(startVertex, endVertex)){
            listOfPaths.add(pathList);
        }
        else{
            for(int[] edgeVertex: matrixOfEdges[startVertex[0]][startVertex[1]]){
                if(!constainsVertex(pathList, edgeVertex)){
                    ArrayList pathList1 = new ArrayList();
                    pathList1.addAll(pathList);
                    findPaths(listOfPaths, pathList1, edgeVertex, endVertex, matrixOfEdges);
                }
            }
        }
        
        return listOfPaths;
    }
    
    public static void printPaths(ArrayList<ArrayList<int[]>> listOfPaths){
        for(ArrayList<int[]> pathList : listOfPaths){
            for(int[] vertex : pathList){
                System.out.print("("+vertex[0]+", "+vertex[1]+")");
            }
            System.out.print("\n");
        }
    }
    
    public static ArrayList findShortestPaths(ArrayList<ArrayList<int[]>> listOfPaths){
        ArrayList listOfShortestPaths = new ArrayList();
        int shortestPathLength = Integer.MAX_VALUE;
        
        for(ArrayList<int[]> pathList : listOfPaths){
            if(pathList.size() < shortestPathLength) shortestPathLength = pathList.size();
        }
        
        for(ArrayList<int[]> pathList : listOfPaths){
            if(pathList.size() == shortestPathLength) listOfShortestPaths.add(pathList);
        }
        
        return listOfShortestPaths;
    }
    
    public static void ANumberLabyrinth(String file){
        System.out.println("------------");
        System.out.println("A: "+ file);
        
        ArrayList<int[]>[][] matrixOfEdges = loadMatrixOfEdges(file);
        
        int[] startVertex = new int[]{0, 0};
        int[] endVertex = new int[]{matrixOfEdges.length - 1, matrixOfEdges[0].length - 1};
        
        ArrayList listOfPaths = findPaths(new ArrayList(), new ArrayList(), startVertex, endVertex, matrixOfEdges);
        //printPaths(listOfPaths);
        
        ArrayList listOfShortestPaths = findShortestPaths(listOfPaths);
        
        printPaths(listOfShortestPaths);
        if(listOfShortestPaths.size() == 1) System.out.println("nejkratsi cesta je jednoznacna");
        else System.out.println("nejkratsi cesta neni jednoznacna");
        System.out.println("------------");
    }
    
    public static void main(String [] args)
    {
        
        ANumberLabyrinth("bludiste4.txt");
        ANumberLabyrinth("bludiste5-1.txt");
        ANumberLabyrinth("bludiste5-2.txt");
        ANumberLabyrinth("bludiste10.txt");
    }
}
