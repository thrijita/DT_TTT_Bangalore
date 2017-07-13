/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import ReadFile.QueryParser;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author admin
 */
public class InputTest {
    
    @Test(expected=Exception.class)
    public void inputQueryPositivetest()
    {
      QueryParser queryInput=new QueryParser();
        assertNotNull(queryInput.stringSplitArray());
        System.out.println("Hello");
    }

    @Test(expected=Exception.class)
    public void inputQueryNegativeTest()
    {
        QueryParser queryInput=new QueryParser();
        assertEquals("Failure input",5,queryInput.stringSplitArray().length);
        System.out.println("correct query");
    }
}
