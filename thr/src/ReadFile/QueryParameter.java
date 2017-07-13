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
public class QueryParameter {
    String classPath,firstCond,secondCond;
    String orderByCol,selectColumn,sortByCol,filepath,groupByCol;
    boolean orderByExist,allColumnExist,hasorderbyField,hasgroupbyField,hasWhere;
    
    //getters setters for file path
    public String getFilepath() {
        return filepath;
    }
    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
    
    public ParseController ParseControllerObject=new ParseController();
    OtherConditions othercond=new OtherConditions();
    
    
    public void display()
    {
      
    }
    
    //method to split the query accordingly
    public void querySplitter(String receivedQuery)
    {
        String firstCond=null,secondCond=null;
        if(receivedQuery.contains("order by"))
        {
            firstCond=receivedQuery.split("order by")[0].trim();
            orderByCol=receivedQuery.split("order by")[1].trim();
            firstCond=firstCond.split("from")[0].trim();
            selectColumn=firstCond.split("select")[1].trim();
            this.fieldChecking(selectColumn);
            orderByExist=true;
        }
   
        if(receivedQuery.contains("group by"))
        {
            firstCond=receivedQuery.split("group by")[0].trim();
            groupByCol=receivedQuery.split("group by")[1].trim();
            if(receivedQuery.contains("where"))
            {
                secondCond=firstCond.split("where")[1].trim();
				String relationalqry=secondCond.split("and|or")[0].trim();
				this.remainingExpressionProcessing(relationalqry);
				firstCond=firstCond.split("where")[0].trim();	
            }
            
			firstCond=firstCond.split("from")[0].trim();
			secondCond=firstCond.split("select")[1].trim();
			this.fieldChecking(groupByCol);
			hasgroupbyField=true;
        }
        
        
         else if(receivedQuery.contains("where"))
		{
			firstCond=receivedQuery.split("where")[0];
			secondCond=receivedQuery.split("where")[1];
			secondCond=secondCond.trim();
			String relationalqry=secondCond.split("and|or")[0].trim();
			System.out.println(relationalqry);
			this.remainingExpressionProcessing(relationalqry);
			selectColumn=firstCond.split("select")[1].trim();
			this.fieldChecking(selectColumn);
			hasWhere=true;
		}
		
        
        else
		{
			firstCond=receivedQuery.split("from")[0].trim();
			selectColumn=firstCond.split("select")[1].trim();
			this.fieldChecking(selectColumn);
		}
		System.out.println(firstCond);
                System.out.println(secondCond);
               System.out.println(orderByCol);
              System.out.println(selectColumn);
              
              System.out.println(othercond.getColumn()+"     "+othercond.getOperator()+"     "+othercond.getValue());
                
    }
    
    //method to check the fields existence
    public void fieldChecking(String inputColumn)
    {
        if(inputColumn.trim().contains("*") && inputColumn.length()==1)
		{
			allColumnExist=true;
		}
		if(inputColumn.trim().contains(","))
		{
			String columnlist[]=inputColumn.split(",");
			
			int i=0;
			for(String column:columnlist)
			{
				
                                ParseControllerObject.colnames.put(column, i);
				i++;
			}
			
		}
    }
    
    private void remainingExpressionProcessing(String relationqry)
	{
		String operators[]={">","<",">=","<=","=","!="};
		
		for(String operator:operators)
		{
			if(relationqry.contains(operator))
			{
			othercond.setColumn(relationqry.split(operator)[0].trim());
                        othercond.setValue(relationqry.split(operator)[1].trim());
			othercond.setOperator(operator);
			break;
			}
		}
	}
    
    
    
    
}
