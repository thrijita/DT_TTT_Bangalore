package TestCase;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;

import org.junit.Test;

import com.niit.FileHandler;
import com.niit.QueryParser;

public class QueryProcessingTester {

	

	QueryParser queryparser = new QueryParser();
	FileHandler filehandler = new FileHandler();

	
	//Given Test Cases
	
	@Test
	public void selectAllWithoutWhereTestCase(){
		
		String[] dataSet=queryparser.inputQuerryArray("select * from E:\\Emp.csv");
		assertNotNull(dataSet);
		display("selectAllWithoutWhereTestCase",dataSet);
		
		
	}

	private void display(String string, String[] dataSet)
	{
		System.out.println(string +"    "+dataSet);
		
	}

	
}

