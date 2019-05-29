/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cv8;

/**
 *
 * @author xmejzli2
 */
public class MyMatrixUtils {
    public static double[][] multiplyMatrices(double[][] A, double B[][]){
        double[][] C = new double[A.length][B[0].length];
        
        for(int i = 0; i < A.length; i++){
            for(int k = 0; k < B[0].length; k++){
                C[i][k] = 0;
                for(int j = 0; j < A[0].length; j++){
                    C[i][k] += (A[i][j]*B[j][k]);
                }
            }
        }
        return C;
    }
    
    public static void printMatrixMultiplicationTest(){
        double[][] A = new double[][]{{1,2,3},{4,5,6}};
        double[][] B = new double[][]{{-1,2},{4,5},{7,8}};
        
        double[][] C = multiplyMatrices(A,B);
        
        for(int i = 0; i < C.length; i++){
            for(int j = 0; j < C[0].length; j++){
                System.out.print(C[i][j]+" ");
            }
            System.out.print("\n");
        }
    }
    
    public static void printMatrix(double[][] C){
        for(int i = 0; i < C.length; i++){
            for(int j = 0; j < C[0].length; j++){
                System.out.print(C[i][j]+" ");
            }
            System.out.print("\n");
        }
    }
    
    public static double[][] rotation(double angle){
        double a = angle * 2*Math.PI/360;
        return new double[][]{
            {Math.cos(a), -Math.sin(a), 0}, 
            {Math.sin(a), Math.cos(a) , 0}, 
            {0,           0           , 1}
        };
    }
    
    public static double[][] translation(double[] t){
        return new double[][]{
            {1, 0, t[0]}, 
            {0, 1, t[1]}, 
            {0, 0, 1   }
        };
    }
    
    public static double[][] scaling(double[] s){
        return new double[][]{
            {s[0], 0   , 0}, 
            {0   , s[1], 0}, 
            {0   , 0   , 1}
        };
    }
    
    public static double[][] shear(double k){
        return new double[][]{
            {1, k, 0}, 
            {0, 1, 0}, 
            {0, 0, 1}
        };
    }
    
    public static double[][] combine(double[][][] transformations){
        double[][] result = new double[][]{
            {1, 0, 0},
            {0, 1, 0},
            {0, 0, 1}
        };
        for(int i = 0; i < transformations.length; i++){
            result = multiplyMatrices(transformations[i], result);
        }
        return result;
    }
}
