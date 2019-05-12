/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cv12;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
            //for(int[] vertex : pathList){
            //    System.out.print("("+vertex[0]+", "+vertex[1]+")");
            //}
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
    
    
    
    public static ArrayList loadMatrixOfEdgesDynamite(String file){
        int numberOfLines = 0;
        int numberOfColumns = 0;

        try(BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line = br.readLine();
            while (line != null) {
                numberOfLines++;
                numberOfColumns = line.length();
                line = br.readLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(Cv12.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        int[][] field = new int[numberOfLines][numberOfColumns];
        int[] positionA = null;
        int[] positionB = null;
        
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {

            for (int i = 0; i < numberOfLines; i++) {
                String line = br.readLine();
                for(int j = 0; j < numberOfColumns; j++){
                    char letter = line.charAt(j);
                    if(letter == 'A'){
                        positionA = new int[]{i, j, 1};
                    }
                    if(letter == 'B'){
                        positionB = new int[]{i, j, 1};
                    }
                    
                    if(letter == '-' || letter == 'A' || letter == 'B'){
                        field[i][j] = 1;
                    }
                    else{
                        field[i][j] = numberOfLines*numberOfColumns;
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Cv12.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        
        ArrayList<int[]>[][] matrix = new ArrayList[numberOfLines][numberOfColumns];
        
        for (int i = 0; i < numberOfLines; i++) {
            for(int j = 0; j < numberOfColumns; j++){
                matrix[i][j] = new ArrayList();
            }
        }
        
        for (int i = 0; i < numberOfLines; i++) {
            for(int j = 0; j < numberOfColumns; j++){
                if(i - 1 >= 0){
                    matrix[i][j].add(new int[]{i - 1, j, field[i - 1][j], Integer.MAX_VALUE});
                }
                if(i + 1 < numberOfLines){
                    matrix[i][j].add(new int[]{i + 1, j, field[i + 1][j], Integer.MAX_VALUE});
                }
                if(j - 1 >= 0){
                    matrix[i][j].add(new int[]{i, j - 1, field[i][j - 1], Integer.MAX_VALUE});
                }
                if(j + 1 < numberOfColumns){
                    matrix[i][j].add(new int[]{i, j + 1, field[i][j + 1], Integer.MAX_VALUE});
                }
            }
        }
        
        ArrayList result = new ArrayList();
        
        result.add(matrix);
        result.add(positionA);
        result.add(positionB);
        
        return result;
    }
    
    public static ArrayList findPathDynamite(ArrayList<int[]> pathList, int[] startVertex, int[] endVertex, ArrayList<int[]>[][] matrixOfEdges){
        int[][][] predecessorMatrix = new int[matrixOfEdges.length][matrixOfEdges[0].length][2];
        
        int[][] priceMatrix = new int[matrixOfEdges.length][matrixOfEdges[0].length];
        for(int i = 0; i < priceMatrix.length; i++){
            for(int j = 0; j < priceMatrix[0].length; j++){
                priceMatrix[i][j] = Integer.MAX_VALUE;
            }
        }
        
        priceMatrix[endVertex[0]][endVertex[1]] = 0;
        
        ArrayList<int[]> unvisited = new ArrayList();
        
        for(int i = 0; i < priceMatrix.length; i++){
            for(int j = 0; j < priceMatrix[0].length; j++){
                unvisited.add(new int[]{i, j});
            }
        }
        
        while(!unvisited.isEmpty()){
            int[] pointer = null;
            int[] extracted = new int[2];
            int minPrice = Integer.MAX_VALUE;
            
            for(int[] vertex : unvisited){
                if(priceMatrix[vertex[0]][vertex[1]] < minPrice){
                    minPrice = priceMatrix[vertex[0]][vertex[1]];
                    extracted[0] = vertex[0];
                    extracted[1] = vertex[1];
                    pointer = vertex;
                }
            }
            unvisited.remove(pointer);
                
            for(int[] neighborVertex: matrixOfEdges[extracted[0]][extracted[1]]){
                int alt = priceMatrix[extracted[0]][extracted[1]] + neighborVertex[2];
                
                if(alt < priceMatrix[neighborVertex[0]][neighborVertex[1]]){
                    priceMatrix[neighborVertex[0]][neighborVertex[1]] = alt;
                    predecessorMatrix[neighborVertex[0]][neighborVertex[1]][0] = extracted[0];
                    predecessorMatrix[neighborVertex[0]][neighborVertex[1]][1] = extracted[1];
                }
            }
        }
        
        ArrayList resultPath = new ArrayList();
        int[] vertex = startVertex;
        while(!areVerticesSame(vertex, endVertex)){
            resultPath.add(vertex);
            vertex = predecessorMatrix[vertex[0]][vertex[1]];
        }
        resultPath.add(vertex);
        return resultPath;
    }
    
    public static void writeResult(String file, ArrayList<int[]> pathList){
        int numberOfLines = 0;
        int numberOfColumns = 0;

        try(BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line = br.readLine();
            while (line != null) {
                numberOfLines++;
                numberOfColumns = line.length();
                line = br.readLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(Cv12.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        char[][] field = new char[numberOfLines][numberOfColumns];
        
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {

            for (int i = 0; i < numberOfLines; i++) {
                String line = br.readLine();
                for(int j = 0; j < numberOfColumns; j++){
                    field[i][j] = line.charAt(j);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Cv12.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for(int[] vertex : pathList){
                field[vertex[0]][vertex[1]] = 'X';
        }
        
        PrintWriter writer;
        try {
            writer = new PrintWriter("dynamit-vysledek", "UTF-8");
            
            for (int i = 0; i < numberOfLines; i++) {
                for(int j = 0; j < numberOfColumns; j++){
                    writer.print(field[i][j]);
                }
                writer.print("\n");
            }
            
            writer.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Cv12.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Cv12.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public static void BDynamite(String file){
        System.out.println("------------");
        System.out.println("B: "+ file);
        
        
        ArrayList list = loadMatrixOfEdgesDynamite(file); 
        ArrayList<int[]>[][] matrixOfEdges = (ArrayList<int[]>[][]) list.get(0);
        
        
        int[] startVertex = (int[]) list.get(1);
        //System.out.println(startVertex[0] + " " + startVertex[1] + " " + startVertex[2]);
        int[] endVertex = (int[]) list.get(2);
        //System.out.println(endVertex[0] + " " + endVertex[1] + " " + endVertex[2]);
        
        ArrayList<int[]> pathList = findPathDynamite(new ArrayList(), startVertex, endVertex, matrixOfEdges);
        for(int[] vertex : pathList){
                System.out.print("("+vertex[0]+", "+vertex[1]+")");
        }
        writeResult(file, pathList);
        
        System.out.println("\n------------");
    }
    
    public static void main(String [] args)
    {
        
        //ANumberLabyrinth("bludiste4.txt");
        //ANumberLabyrinth("bludiste5-1.txt");
        //ANumberLabyrinth("bludiste5-2.txt");
        //ANumberLabyrinth("bludiste10.txt");
        
        BDynamite("dynamit.txt");
    }
}
