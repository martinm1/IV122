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
            ArrayList resultList1 = new ArrayList();
            resultList1.addAll(tupleList);
            resultList1.add(list.get(i));
            
            ArrayList list1 = new ArrayList();
            list1.addAll(list);
            list1.remove(i);
            
            finalList.addAll(permutations(list1, resultList1));
        }
        return finalList;
    }
    
    public static void main(String [] args){
        ArrayList list = new ArrayList();
        list.add('A');
        list.add('B');
        list.add('C');
        //list.add('D');
        System.out.println(permutations(list, new ArrayList()));
    }
}
