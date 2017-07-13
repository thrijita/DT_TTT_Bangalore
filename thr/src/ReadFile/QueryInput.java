/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ReadFile;

import java.util.Scanner;

/**
 *
 * @author admin
 */
public class QueryInput {
//class to receive the query input from the user
    //-------------------------------------------
    /**
     * @param args the command line arguments
     */
     QueryParser stringParserObj;
    public String sendQuery()
    {
        stringParserObj=new QueryParser();
        String inputQuery;
    
        Scanner scannerobj=new Scanner(System.in);
        System.out.println("Enter the query:-");
        inputQuery=scannerobj.nextLine();
        stringParserObj.executeQuery(inputQuery);
        return inputQuery;
    }
    
        
        
        
    public static void main(String[] args) {
       // System.out.println("hi dear project.....!");
       QueryParser stringParserObj=new QueryParser();
        stringParserObj.stringSplitArray();
        stringParserObj.csvParams();
        //s1.booleanCheck();
        stringParserObj.sendCSVparams();
        
    }
    
}
