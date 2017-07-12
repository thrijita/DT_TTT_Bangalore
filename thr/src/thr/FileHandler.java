/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thr;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 *
 * @author admin
 */
public class FileHandler {
    
  static String  classPath,firstCond,secondCond;
  static String  str,finalData[],withoutHeader[],conditions[];
  
  static Map<String,Integer> headerObj=new HashMap<String,Integer>();//to print all data with headers
  static Map<Integer,String> rowdataObj=new TreeMap<>();//to print all row data without headers
  
    
    public  void filehandler(String classPath,String firstCond,String secondCond)
    {
        this.classPath=classPath;
        this.firstCond=firstCond;
        this.secondCond=secondCond;
        System.out.println("The Driven path is :"+classPath);
        try
        {
            getAllData();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        header_retrieval();
        rowData();
        indiv_columndata();
        
        
    }
  //method to get all data form the file
    public void getAllData() throws FileNotFoundException, IOException
    {
        
      FileReader fr=new FileReader("E:\\"+classPath);
      BufferedReader br=new BufferedReader(fr);
      List<String> list=new ArrayList<String>();
      while((str=br.readLine())!=null)
      {
          list.add(str);
      }
      String strArray[]=list.toArray(new String[0]);
      finalData=Arrays.copyOf(strArray,strArray.length-1);
      System.out.println("Total no of elements :"+finalData.length);
        for(String allData:finalData)
        {
            System.out.println(allData);
        }
    }
    
    //method to get only headers position
    public static Map<String,Integer> header_retrieval()
    {
        String header;
        header=finalData[0];
        String headerData[]=header.split(",");
        for(int i=0;i<headerData.length;i++)
                {
                   headerObj.put(headerData[i], i);
                }
        System.out.println(headerObj);
      return headerObj;
      
        
    }
    
//method to get individual row data without headers
public static Map<Integer,String> rowData()   
{
 withoutHeader=Arrays.copyOfRange(finalData,1,finalData.length);//prints row 1 to end row   
    for(int i=0;i<withoutHeader.length;i++)
    {
        rowdataObj.put(i+1,withoutHeader[i]);
    }
    System.out.println(rowdataObj);
    return rowdataObj;
}
    

//method to get individual column wise data

     public static void indiv_columndata()
     {
         int position;
         conditions=firstCond.split(",");
         System.out.println(conditions.length);
        
         
         Set entrySet = headerObj.entrySet();
 
    // Obtaining an iterator for the entry set
    Iterator it = entrySet.iterator();
 
    // Iterate through HashMap entries(Key-Value pairs)
   // System.out.println("HashMap Key-Value Pairs : ");
    while(it.hasNext())
    {
    for(int i=0;i<conditions.length;i++)
    {
       Map.Entry me = (Map.Entry)it.next();
      
      String value=(String) me.getKey();
    if(value.contains(conditions[i]))
    {
        position=(int) me.getValue();
        
        for(int s=0;s<withoutHeader.length;s++)
        {
            String[] temp=withoutHeader[s].split(",");
            System.out.println(temp[position]);
            
        }
        
    }
    
    }
    }
     
    }
}



