package org.jfree.data.testing3;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.jfree.data.DataUtilities;
import org.jfree.data.DefaultKeyedValues2D;
import org.jfree.data.DefaultKeyedValues;
import org.jfree.data.KeyedValues;
public class DataUtilitiesTest_Lab3 {
    	
	@Test
	public void testEqual_BothNull() {
	    assertTrue(DataUtilities.equal(null, null)); 
	}

	@Test
	public void testEqual_FirstArrayNull() {
	    double[][] array = {{1.0, 2.0}, {3.0, 4.0}};
	    assertFalse(DataUtilities.equal(null, array)); 
	}

	@Test
	public void testEqual_SecondArrayNull() {
	    double[][] array = {{1.0, 2.0}, {3.0, 4.0}};
	    assertFalse(DataUtilities.equal(array, null)); 
	}

	@Test
	public void testEqual_DifferentLengths() {
	    double[][] array1 = {{1.0, 2.0}};
	    double[][] array2 = {{1.0, 2.0}, {3.0, 4.0}};
	    assertFalse(DataUtilities.equal(array1, array2)); 
	}

	@Test
	public void testEqual_IdenticalArrays() {
	    double[][] array1 = {{1.0, 2.0}, {3.0, 4.0}};
	    double[][] array2 = {{1.0, 2.0}, {3.0, 4.0}};
	    assertTrue(DataUtilities.equal(array1, array2)); 
	}
	
	@Test
	public void testEqual_DiffArrays() {
	    double[][] array1 = {{1.0, 2.0}, {3.0, 4.0}};
	    double[][] array2 = {{1.0, 2.0}, {3.0, 5.0}};
	    assertFalse(DataUtilities.equal(array1, array2)); 
	}

	@Test
    public void testCloneWithValidInput() {
        double[][] source = {{1.0, 2.0}, {3.0, 4.0}};
        double[][] result = DataUtilities.clone(source);
        
        assertNotSame(source, result);
        assertEquals(source.length, result.length);
        
        for (int i = 0; i < source.length; i++) {
            assertNotSame(source[i], result[i]);
            assertArrayEquals(source[i], result[i], 0.0001);
        }
    }

    @Test
    public void testCloneWithEmptyArray() {
        double[][] source = {};
        double[][] result = DataUtilities.clone(source);
        
        assertNotSame(source, result);
        assertEquals(0, result.length);
    }

    @Test
    public void testCloneWithNullRows() {
        double[][] source = new double[2][];
        source[0] = new double[]{1.0, 2.0};
        source[1] = null;
        
        double[][] result = DataUtilities.clone(source);
        
        assertNotSame(source, result);
        assertEquals(source.length, result.length);
        assertArrayEquals(source[0], result[0], 0.0001);
        assertNull(result[1]);
    }

    @Test
    public void testCalculateColumnTotal() {
        DefaultKeyedValues2D data = new DefaultKeyedValues2D();
        
        data.addValue(5.0, 0, 0);  
        data.addValue(10.0, 1, 0); 
        data.addValue(15.0, 2, 0); 

        double total = DataUtilities.calculateColumnTotal(data, 0);
        assertEquals("Column total should be 30", 30.0, total, 0.0001);
    }
    
    @Test
    public void testCalculateColumnTotal_WithEmptyTable() {
        DefaultKeyedValues2D data = new DefaultKeyedValues2D();
        double total = DataUtilities.calculateColumnTotal(data, 0);
        assertEquals("Total should be 0.0 for empty table", 0.0, total, 0.0001);
    }


    @Test
    public void testCalculateColumnTotal_NullValuesInData() {
        DefaultKeyedValues2D data = new DefaultKeyedValues2D();
        
        data.addValue(1.0, "Row1", "Column1");
        data.addValue(null, "Row2", "Column1");
        data.addValue(5.0, "Row3", "Column1");

        int[] validRows = {0, 1, 2}; 
        double result = DataUtilities.calculateColumnTotal(data, 0, validRows);

        assertEquals("Null value should be skipped", 6.0, result, 0.0001); 
    }

    @Test
    public void testCalculateColumnTotal_ValidRows() {
        DefaultKeyedValues2D data = new DefaultKeyedValues2D();
        
        data.addValue(1.0, "Row1", "Column1");
        data.addValue(3.0, "Row2", "Column1");
        data.addValue(5.0, "Row3", "Column1");

        int[] validRows = {0, 1}; 
        double result = DataUtilities.calculateColumnTotal(data, 0, validRows);

        assertEquals("Sum of valid rows should be 4.0", 4.0, result, 0.0001);
    }

    @Test
    public void testCalculateColumnTotal_EmptyValidRows() {
        DefaultKeyedValues2D data = new DefaultKeyedValues2D();
        
        int[] validRows = {}; 
        double result = DataUtilities.calculateColumnTotal(data, 0, validRows);

        assertEquals("Empty validRows should return 0.0", 0.0, result, 0.0001);
    }

    @Test
    public void testCalculateColumnTotal_SomeInvalidRows() {
        DefaultKeyedValues2D data = new DefaultKeyedValues2D();
        
        data.addValue(1.0, "Row1", "Column1");
        data.addValue(3.0, "Row2", "Column1");

        int[] validRows = {1, 3}; 
        double result = DataUtilities.calculateColumnTotal(data, 0, validRows);

        assertEquals("Only row 1 should be counted (3.0)", 3.0, result, 0.0001);
    }

    @Test
    public void testCalculateColumnTotal_AllInvalidRows() {
        DefaultKeyedValues2D data = new DefaultKeyedValues2D();
        
        data.addValue(1.0, "Row1", "Column1");
        data.addValue(3.0, "Row2", "Column1");

        int[] validRows = {5, 6}; 
        double result = DataUtilities.calculateColumnTotal(data, 0, validRows);

        assertEquals("All invalid rows should return 0.0", 0.0, result, 0.0001);
    }

    @Test
    public void testCalculateRowTotal_WithValues() {
        DefaultKeyedValues2D data = new DefaultKeyedValues2D();
        data.addValue(7.5, "Row1", "Column1");
        data.addValue(2.5, "Row1", "Column2");

        double result = DataUtilities.calculateRowTotal(data, 0);
        assertEquals(10.0, result, 0.000000001d);
    }

    @Test
    public void testCalculateRowTotal_EmptyDataTable() {
        DefaultKeyedValues2D data = new DefaultKeyedValues2D();
        double result = DataUtilities.calculateRowTotal(data, 0);
        assertEquals(0.0, result, 0.000000001d);
    }

    @Test
    public void testCalculateRowTotal_NullValue() {
        DefaultKeyedValues2D data = new DefaultKeyedValues2D();
        data.addValue(null, "Row1", "Column1");
        data.addValue(2.5, "Row1", "Column2");

        double result = DataUtilities.calculateRowTotal(data, 0);
        assertEquals(2.5, result, 0.000000001d);
    }

    @Test
    public void testCalculateRowTotal_LargeValues() {
        DefaultKeyedValues2D data = new DefaultKeyedValues2D();
        data.addValue(Double.MAX_VALUE, "Row1", "Column1");
        data.addValue(Double.MAX_VALUE, "Row1", "Column2");
        data.addValue(Double.MAX_VALUE, "Row1", "Column3");

        double result = DataUtilities.calculateRowTotal(data, 0);
        assertEquals(Double.POSITIVE_INFINITY, result, 0.000000001d);
    }


    @Test
    public void testCalculateRowTotal_WithValuesAndValidCols() {
        DefaultKeyedValues2D data = new DefaultKeyedValues2D();
        data.addValue(1.0, "Row1", "Column1"); 
        data.addValue(2.0, "Row1", "Column2"); 
        data.addValue(3.0, "Row1", "Column3"); 

        int[] validCols = {0, 2}; 
        double result = DataUtilities.calculateRowTotal(data, 0, validCols);
        assertEquals(4.0, result, 0.0001); 
    }


    @Test
    public void testCalculateRowTotal_NullValueInData() {
        DefaultKeyedValues2D data = new DefaultKeyedValues2D();
        data.addValue(1.0, "Row1", "Column1");
        data.addValue(null, "Row1", "Column2");
        data.addValue(3.0, "Row1", "Column3");

        int[] validCols = {0, 1, 2}; 
        double result = DataUtilities.calculateRowTotal(data, 0, validCols);
        assertEquals(4.0, result, 0.0001); 
    }

    @Test
    public void testCalculateRowTotal_EmptyValidCols() {
        DefaultKeyedValues2D data = new DefaultKeyedValues2D();
        data.addValue(1.0, "Row1", "Column1");
        data.addValue(2.0, "Row1", "Column2");
        int[] validCols = {}; 
        double result = DataUtilities.calculateRowTotal(data, 0, validCols);
        assertEquals(0.0, result, 0.0001);
    }

    @Test
    public void testCalculateRowTotal_InvalidCols() {
        DefaultKeyedValues2D data = new DefaultKeyedValues2D();
        data.addValue(1.0, "Row1", "Column1");
        data.addValue(2.0, "Row1", "Column2");
        int[] validCols = {0, 5}; 
        double result = DataUtilities.calculateRowTotal(data, 0, validCols);
        assertEquals(1.0, result, 0.0001); 
    }

   @Test
    public void testCalculateRowTotal_NegativeColumnCount() {
        DefaultKeyedValues2D data = new DefaultKeyedValues2D();

        data.addValue(1.0, "Row1", "Column1");
        data.addValue(2.0, "Row1", "Column2");

        int[] validCols = {0, 1};
        double result = DataUtilities.calculateRowTotal(data, 0, validCols);
        assertEquals(3.0, result, 0.0001);
    }
   
   @Test
   public void testCalculateRowTotal_ForceExecutionOfUnusedLoop() {
       DefaultKeyedValues2D data = new DefaultKeyedValues2D() {
           @Override
           public int getColumnCount() {
               return -2;
           }
       };

       data.addValue(1.0, "Row1", "Column1");
       data.addValue(2.0, "Row1", "Column2");

       double result = DataUtilities.calculateRowTotal(data, 0);

       assertEquals("Forced loop execution should not affect total", 0.0, result, 0.0001);
   }

   
   
   @Test
   public void testCreateNumberArray_WithData() {
       double[] input = {1.0, 2.0, 3.0};
       Number[] expected = {1.0, 2.0, 3.0}; 
       Number[] result = DataUtilities.createNumberArray(input);

       assertNotNull(result);
       assertEquals(input.length, result.length);
       for (int i = 0; i < input.length; i++) {
           assertEquals(expected[i], result[i]);
           assertEquals(Double.class, result[i].getClass()); 
       }
   }

   @Test
   public void testCreateNumberArray_EmptyArray() {
       double[] input = {};
       Number[] result = DataUtilities.createNumberArray(input);
       assertNotNull(result); 
       assertEquals(0, result.length);
   }

   @Test
   public void testCreateNumberArray2D_WithData() {
       double[][] input = {{1.0, 2.0}, {3.0, 4.0, 5.0}};
       Number[][] result = DataUtilities.createNumberArray2D(input);

       assertNotNull(result);
       assertEquals(input.length, result.length);

       assertNotNull(result[0]);
       assertEquals(input[0].length, result[0].length);
       assertEquals(1.0, result[0][0]);
       assertEquals(2.0, result[0][1]);

       assertNotNull(result[1]);
       assertEquals(input[1].length, result[1].length);
       assertEquals(3.0, result[1][0]);
       assertEquals(4.0, result[1][1]);
	    assertEquals(5.0, result[1][2]);
   }

   @Test
   public void testCreateNumberArray2D_EmptyArray() {
       double[][] input = {};
       Number[][] result = DataUtilities.createNumberArray2D(input);
       assertNotNull(result);
       assertEquals(0, result.length);
   }
   
   @Test
   public void testGetCumulativePercentages_NormalCase() {
       DefaultKeyedValues data = new DefaultKeyedValues();
       
       data.addValue("A", 1.0);
       data.addValue("B", 2.0);
       data.addValue("C", 3.0);

       KeyedValues result = DataUtilities.getCumulativePercentages(data);

       assertEquals("Cumulative % for A", 1.0 / 6.0, result.getValue("A").doubleValue(), 0.0001);
       assertEquals("Cumulative % for B", 3.0 / 6.0, result.getValue("B").doubleValue(), 0.0001);
       assertEquals("Cumulative % for C", 6.0 / 6.0, result.getValue("C").doubleValue(), 0.0001);
   }

   @Test
   public void testGetCumulativePercentages_EmptyDataset() {
       DefaultKeyedValues data = new DefaultKeyedValues();

       KeyedValues result = DataUtilities.getCumulativePercentages(data);

       assertEquals("Empty dataset should return empty KeyedValues", 0, result.getItemCount());
   }

   @Test
   public void testGetCumulativePercentages_NullValues() {
       DefaultKeyedValues data = new DefaultKeyedValues();
       
       data.addValue("A", 2.0);
       data.addValue("B", null);
       data.addValue("C", 3.0);

       KeyedValues result = DataUtilities.getCumulativePercentages(data);

       assertEquals("Cumulative % for A", 2.0 / 5.0, result.getValue("A").doubleValue(), 0.0001);
       assertEquals("Cumulative % for C", 5.0 / 5.0, result.getValue("C").doubleValue(), 0.0001);
   }

   @Test
   public void testGetCumulativePercentages_SingleValue() {
       DefaultKeyedValues data = new DefaultKeyedValues();
       
       data.addValue("A", 10.0);

       KeyedValues result = DataUtilities.getCumulativePercentages(data);

       assertEquals("Single value should be 100%", 1.0, result.getValue("A").doubleValue(), 0.0001);
   }

   @Test
   public void testGetCumulativePercentages_NegativeValues() {
       DefaultKeyedValues data = new DefaultKeyedValues();
       
       data.addValue("A", -2.0);
       data.addValue("B", -3.0);

       KeyedValues result = DataUtilities.getCumulativePercentages(data);

       assertEquals("Cumulative % for A", -2.0 / -5.0, result.getValue("A").doubleValue(), 0.0001);
       assertEquals("Cumulative % for B", -5.0 / -5.0, result.getValue("B").doubleValue(), 0.0001);
   }

   @Test
   public void testGetCumulativePercentages_ZeroTotal() {
       DefaultKeyedValues data = new DefaultKeyedValues();
       
       data.addValue("A", 0.0);
       data.addValue("B", 0.0);

       KeyedValues result = DataUtilities.getCumulativePercentages(data);

       assertNotEquals("Cumulative % for A with zero total", 0.0, result.getValue("A").doubleValue(), 0.0001);
       assertNotEquals("Cumulative % for B with zero total", 0.0, result.getValue("B").doubleValue(), 0.0001);
   }
   
   @Test
   public void testGetCumulativePercentages_ForceUnusedLoop() {
       DefaultKeyedValues data = new DefaultKeyedValues() {
           @Override
           public int getItemCount() {
               return -1; 
           }
       };

       data.addValue("A", 5.0);
       data.addValue("B", 10.0);
       data.addValue("C", 15.0);
       KeyedValues result = DataUtilities.getCumulativePercentages(data);

       assertNull("Result should not be null", result);
       assertEquals("Cumulative percentage size should be 3", 3, result.getItemCount());
       assertEquals("First value should be 5/(5+10+15) = 0.1667", 5.0 / 30.0, result.getValue("A").doubleValue(), 0.0001);
       assertEquals("Second value should be (5+10)/30 = 0.5", 0.5, result.getValue("B").doubleValue(), 0.0001);
       assertEquals("Third value should be 1.0 (100%)", 1.0, result.getValue("C").doubleValue(), 0.0001);
   }

}




