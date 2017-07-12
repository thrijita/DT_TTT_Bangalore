/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thr;

import java.util.Scanner;

/**
 *
 * @author admin
 */
public class StringInput {
//class to receive the query input from the user
    //-------------------------------------------
    /**
     * @param args the command line arguments
     */
     static StringParser stringParserObj=new StringParser();
    public String sendQuery()
    {
        String inputQuery;
    
        Scanner scannerobj=new Scanner(System.in);
        System.out.println("Enter the query:-");
        inputQuery=scannerobj.nextLine();
        stringParserObj.executeQuery(inputQuery);
        return inputQuery;
    }
    
        
        
        
    public static void main(String[] args) {
        System.out.println("hi dear project.....!");
       
        stringParserObj.strArray();
        stringParserObj.csvParams();
        //s1.booleanCheck();
        stringParserObj.sendCSVparams();
        
    }
    
}
