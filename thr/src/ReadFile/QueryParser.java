/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ReadFile;

/**
 *
 * @author admin
 */
public class QueryParser {
    
    String queryBreaker[],classPath,firstCond,secondCond,columns;
   boolean hasSelect,hasAsterisk,whereExists,groupbyExists,sortbyExists,orderbyExists,byExists,hasSum,hasCount;
   QueryInput InputObj;
  String receivedQuery;  
   int queryLength,queryBreakerlength;
   
   //creating object for custom object class
   QueryParameter cusObj=new QueryParameter();
   FileHandler filehandleObj=new FileHandler();
   
   
   
   
   public QueryParameter parseQuery(String queryString)
   {
       
       QueryParameter queryParameter = new QueryParameter();
       
       
       //parse the queryString
       //set properties to queryParameter
       
       //and return queryParameter
       return null;
       
     
       
   }
 
   public String[] stringSplitArray()
    {
        InputObj=new QueryInput();
        receivedQuery=InputObj.sendQuery();
        queryBreaker=receivedQuery.split(" ");
        queryLength=receivedQuery.length();
        queryBreakerlength=queryBreaker.length;
        return queryBreaker;
    }
   
    
    public String[] csvParams()
    {
        for(int i = 0;i<queryBreakerlength;i++)
        {
            if(queryBreaker[i].contains(".csv"))
            {
                classPath=queryBreaker[i];
            }
//            if(queryBreaker[i].contains("where"))
//            {
//                secondCond=queryBreaker[i+1];
//            }
//            firstCond=queryBreaker[1];
            
            
        }
        columns=queryBreaker[1];    
    System.out.println(classPath+columns);
    return queryBreaker;
        //System.out.println("class path is "+classPath+"first cond is "+firstCond+"second cond is "+secondCond);
    }
  
   /* public void booleanCheck()
    {
        for(int i=0;i<queryBreaker.length;i++)
            
                {
                    if(queryBreaker[0].contains("select"))
                        {
                            has_select=true;
        }
        if(queryBreaker[1].contains("*"))
        {
            has_asterisk=true;
        }  
        if(queryBreaker[i].contains("where"))
        {
            whereExists=true;
        }
              orderbyExists=received_query.contains("order by");
            sortbyExists=received_query.contains("sort by");
            groupbyExists=received_query.contains("group by");   
            
            
            if(queryBreaker[i].contains("sum"))
        {
            has_sum=true;
        }
        if(queryBreaker[i].contains("count"))
        {
            has_count=true;
        }
                }
        
    }*/
    
    public boolean inputQueryChecker(String receivedQuery)
    {
      if(receivedQuery.contains("select")&& receivedQuery.contains("from")||receivedQuery.contains("*")||receivedQuery.contains("where")||receivedQuery.contains("group by")||receivedQuery.contains("sort by")||receivedQuery.contains("order by"))
        {
            return true;
        }    
        else{
        return false;    
        }
    }
    public void sendCSVparams()
    {
        //CustomObject cusObj=new CustomObject(classPath,firstCond,secondCond);
        
       // cusObj.customobject(classPath,firstCond,secondCond);
        cusObj.setFilepath(classPath);
        if(executeQuery(receivedQuery))
        {
            cusObj.querySplitter(receivedQuery);
            //filehandleObj.fileHandle(columns);
            
        }
        cusObj.display();
        
        
    }
    public boolean executeQuery(String receivedQuery)
    {
       if(inputQueryChecker(receivedQuery))
        {
         QueryParameter custobj=new QueryParameter();
         custobj.querySplitter(receivedQuery);
        
        
         return true;
        }
        else
        {
        System.out.println("Improper query format");    
        return false;
        } 
    }
    
    
            
}
