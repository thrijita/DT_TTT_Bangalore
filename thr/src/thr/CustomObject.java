/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thr;

/**
 *
 * @author admin
 */
public class CustomObject {
    String classPath,firstCond,secondCond;
    String orderBy_Col,select_Column,sortBy_Col;
    boolean orderBy_Exist,allColumn_Exist;

    public ParseController ParseController_Object=new ParseController();
   
    
    public void display()
    {
      System.out.println(classPath+firstCond+secondCond);
      FileHandler fh=new FileHandler();
      fh.filehandler(classPath,firstCond,secondCond);
    }
    
    //method to split the query accordingly
    public void querySplitter(String received_query)
    {
        String firstCond=null,secondCond=null;
        if(received_query.contains("order by"))
        {
            firstCond=received_query.split("order by")[0].trim();
            orderBy_Col=received_query.split("order by")[1].trim();
            firstCond=firstCond.split("from")[0].trim();
            select_Column=secondCond.split("select")[1].trim();
            this.field_Checking(select_Column);
            orderBy_Exist=true;
        }
   
        if(received_query.contains("group by"))
        {
            firstCond=received_query.split("group by")[0].trim();
            orderBy_Col=received_query.split("group by")[1].trim();
            firstCond=firstCond.split("from")[0].trim();
            sortBy_Col=secondCond.split("select")[1].trim();
        }
    }
    
    //method to check the fields existence
    public void field_Checking(String input_column)
    {
        if(input_column.trim().contains("*") && input_column.length()==1)
		{
			allColumn_Exist=true;
		}
		if(input_column.trim().contains(","))
		{
			String columnlist[]=input_column.split(",");
			
			int i=0;
			for(String column:columnlist)
			{
				
                                ParseController_Object.colnames.put(column, i);
				i++;
			}
			
		}
    }
    
}
