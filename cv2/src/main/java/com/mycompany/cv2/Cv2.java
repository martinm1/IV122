/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cv2;

import java.util.ArrayList;

/**
 *
 * @author martin
 */
public class Cv2 {
    
    public static ArrayList permutations(ArrayList list, ArrayList tupleList){
        ArrayList finalList = new ArrayList();
        if(list.isEmpty()) {
            finalList.add(tupleList);
        }
        
        for(int i = 0; i < list.size(); i++){
            ArrayList tupleList1 = new ArrayList();
            tupleList1.addAll(tupleList);
            tupleList1.add(list.get(i));
            
            ArrayList list1 = new ArrayList();
            list1.addAll(list);
            list1.remove(i);
            
            finalList.addAll(permutations(list1, tupleList1));
        }
        return finalList;
    }
    
    public static ArrayList variations(ArrayList list, ArrayList tupleList, int k){
        ArrayList finalList = new ArrayList();
        if(k==0) {
            finalList.add(tupleList);
            return finalList;
        }
        
        for(int i = 0; i < list.size(); i++){
            ArrayList tupleList1 = new ArrayList();
            tupleList1.addAll(tupleList);
            tupleList1.add(list.get(i));
            
            ArrayList list1 = new ArrayList();
            list1.addAll(list);
            list1.remove(i);
            
            finalList.addAll(variations(list1, tupleList1, k-1));
        }
        return finalList;
    }
    
    public static ArrayList variationsWithRepetition(ArrayList list, ArrayList tupleList, int k){
        ArrayList finalList = new ArrayList();
        if(k==0) {
            finalList.add(tupleList);
            return finalList;
        }
        
        for(int i = 0; i < list.size(); i++){
            ArrayList tupleList1 = new ArrayList();
            tupleList1.addAll(tupleList);
            tupleList1.add(list.get(i));
            
            finalList.addAll(variationsWithRepetition(list, tupleList1, k-1));
        }
        return finalList;
    }
    
    public static ArrayList combinations(ArrayList list, ArrayList tupleList, int k){
        ArrayList finalList = new ArrayList();
        if(k==0) {
            finalList.add(tupleList);
            return finalList;
        }
        
        if(!list.isEmpty()){
            ArrayList list1 = new ArrayList();
            list1.addAll(list);
            list1.remove(0);

            ArrayList tupleList1 = new ArrayList();
            tupleList1.addAll(tupleList);
            tupleList1.add(list.get(0));

            ArrayList tupleList2 = new ArrayList();
            tupleList2.addAll(tupleList);


            finalList.addAll(combinations(list1, tupleList1, k-1));
            finalList.addAll(combinations(list1, tupleList2, k));
        }
        
        return finalList;
    }
    
    public static ArrayList combinationsWithRepetition(ArrayList list, ArrayList tupleList, int k){
        ArrayList finalList = new ArrayList();
        if(k==0) {
            finalList.add(tupleList);
            return finalList;
        }
        
        if(!list.isEmpty()){
            ArrayList list1 = new ArrayList();
            list1.addAll(list);
            list1.remove(0);
            
            for(int i=1;i<=k;i++){
                ArrayList tupleList1 = new ArrayList();
                tupleList1.addAll(tupleList);
                for(int j=0; j<i;j++){
                    tupleList1.add(list.get(0));
                }
                finalList.addAll(combinationsWithRepetition(list1, tupleList1, k-i));
            }

            ArrayList tupleList2 = new ArrayList();
            tupleList2.addAll(tupleList);
            
            finalList.addAll(combinationsWithRepetition(list1, tupleList2, k));
        }
        
        return finalList;
    }
    
    public static void main(String [] args){
        ArrayList list = new ArrayList();
        list.add('A');
        list.add('B');
        list.add('C');
        //list.add('D');
        System.out.println("pernutace: "+ permutations(list, new ArrayList()));
        System.out.println("variace: "+ variations(list, new ArrayList(), 2));
        System.out.println("variace s opakovanim: "+ variationsWithRepetition(list, new ArrayList(), 2));
        System.out.println("kombinace: "+ combinations(list, new ArrayList(), 2));
        System.out.println("kombinace s opakovanim: : "+ combinationsWithRepetition(list, new ArrayList(), 2));
        
    }
}
