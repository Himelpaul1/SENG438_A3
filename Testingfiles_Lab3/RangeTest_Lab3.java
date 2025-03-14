package org.jfree.data.testing3;

import org.jfree.data.Range;
import org.junit.Test;
import static org.junit.Assert.*;
import java.lang.reflect.Method; 
import java.lang.reflect.Field;



public class RangeTest_Lab3 {


    @Test
    public void testGetLowerBound_ThrowsException() {
        try {
            Range invalidRange = createInvalidRange();
            invalidRange.getLowerBound();
            fail("Expected IllegalArgumentException was not thrown");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void testGetUpperBound_ThrowsException() {
        try {
            Range invalidRange = createInvalidRange();
            invalidRange.getUpperBound();
            fail("Expected IllegalArgumentException was not thrown");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void testGetLength_ThrowsException() {
        try {
            Range invalidRange = createInvalidRange();
            invalidRange.getLength();
            fail("Expected IllegalArgumentException was not thrown");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }	
	
    @Test
    public void testConstructor_ValidRange() {
        Range range = new Range(1.0, 5.0);
        assertEquals(1.0, range.getLowerBound(), 0.000001);
        assertEquals(5.0, range.getUpperBound(), 0.000001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_InvalidRange() {
        new Range(5.0, 1.0);
    }

    @Test
    public void testGetLength() {
        Range range = new Range(1.0, 5.0);
        assertEquals(4.0, range.getLength(), 0.000001);
    }

    @Test
    public void testGetCentralValue() {
        Range range = new Range(1.0, 5.0);
        assertEquals(3.0, range.getCentralValue(), 0.000001);
    }

    @Test
    public void testContains_WithinRange() {
        Range range = new Range(1.0, 5.0);
        assertTrue(range.contains(3.0));
    }

    @Test
    public void testContains_LowerBound() {
        Range range = new Range(1.0, 5.0);
        assertTrue(range.contains(1.0));
    }

    @Test
    public void testContains_UpperBound() {
        Range range = new Range(1.0, 5.0);
        assertTrue(range.contains(5.0));
    }

    @Test
    public void testContains_OutsideRange_Lower() {
        Range range = new Range(1.0, 5.0);
        assertFalse(range.contains(0.5));
    }

    @Test
    public void testContains_OutsideRange_Upper() {
        Range range = new Range(1.0, 5.0);
        assertFalse(range.contains(5.5));
    }

    @Test
    public void testIntersects_WithDoubles_Intersecting() {
        Range range = new Range(1.0, 5.0);
        assertTrue(range.intersects(0.0, 2.0));
        assertTrue(range.intersects(4.0, 6.0));
        assertTrue(range.intersects(2.0, 4.0));
        assertTrue(range.intersects(1.0, 5.0));
    }

    @Test
    public void testIntersects_WithDoubles_NotIntersecting() {
        Range range = new Range(1.0, 5.0);
        assertFalse(range.intersects(0.0, 0.5));
        assertFalse(range.intersects(5.5, 6.0));
    }

    @Test
    public void testIntersects_WithRange_Intersecting() {
        Range range1 = new Range(1.0, 5.0);
        Range range2 = new Range(0.0, 2.0);
        assertTrue(range1.intersects(range2));

        Range range3 = new Range(4.0, 6.0);
        assertTrue(range1.intersects(range3));
    }

    @Test
    public void testIntersects_WithRange_NotIntersecting() {
        Range range1 = new Range(1.0, 5.0);
        Range range2 = new Range(0.0, 0.5);
        assertFalse(range1.intersects(range2));

        Range range3 = new Range(5.5, 6.0);
        assertFalse(range1.intersects(range3));
    }

    @Test
    public void testConstrain_WithinRange() {
        Range range = new Range(1.0, 5.0);
        assertEquals(3.0, range.constrain(3.0), 0.000001);
    }

    @Test
    public void testConstrain_BelowRange() {
        Range range = new Range(1.0, 5.0);
        assertEquals(1.0, range.constrain(0.5), 0.000001);
    }

    @Test
    public void testConstrain_AboveRange() {
        Range range = new Range(1.0, 5.0);
        assertEquals(5.0, range.constrain(5.5), 0.000001);
    }

    @Test
    public void testCombine_BothNull() {
        assertNull(Range.combine(null, null));
    }

    @Test
    public void testCombine_OneNull() {
        Range range1 = new Range(1.0, 5.0);
        Range combined1 = Range.combine(range1, null);
        assertEquals(1.0, combined1.getLowerBound(), 0.000001);
        assertEquals(5.0, combined1.getUpperBound(), 0.000001);

        Range range2 = new Range(6.0, 10.0);
        Range combined2 = Range.combine(null, range2);
        assertEquals(6.0, combined2.getLowerBound(), 0.000001);
        assertEquals(10.0, combined2.getUpperBound(), 0.000001);
    }

    @Test
    public void testCombine_NoOverlap() {
        Range range1 = new Range(1.0, 5.0);
        Range range2 = new Range(6.0, 10.0);
        Range combined = Range.combine(range1, range2);
        assertEquals(1.0, combined.getLowerBound(), 0.000001);
        assertEquals(10.0, combined.getUpperBound(), 0.000001);
    }

    @Test
    public void testCombine_Overlap() {
        Range range1 = new Range(1.0, 5.0);
        Range range2 = new Range(3.0, 7.0);
        Range combined = Range.combine(range1, range2);
        assertEquals(1.0, combined.getLowerBound(), 0.000001);
        assertEquals(7.0, combined.getUpperBound(), 0.000001);
    }

     @Test
    public void testCombineIgnoringNaN_BothNull() {
        assertNull(Range.combineIgnoringNaN(null, null));
    }

    @Test
    public void testCombineIgnoringNaN_OneNull() {
        Range range1 = new Range(1.0, 5.0);
        Range combined1 = Range.combineIgnoringNaN(range1, null);
        assertEquals(1.0, combined1.getLowerBound(), 0.000001);
        assertEquals(5.0, combined1.getUpperBound(), 0.000001);

        Range range2 = new Range(6.0, 10.0);
        Range combined2 = Range.combineIgnoringNaN(null, range2);
        assertEquals(6.0, combined2.getLowerBound(), 0.000001);
        assertEquals(10.0, combined2.getUpperBound(), 0.000001);
    }

    @Test
    public void testCombineIgnoringNaN_NaNRange() {
        Range range1 = new Range(1.0, 5.0);
        Range rangeNaN = new Range(Double.NaN, Double.NaN);

        Range combined1 = Range.combineIgnoringNaN(range1, rangeNaN);
        assertEquals(1.0, combined1.getLowerBound(), 0.000001);
        assertEquals(5.0, combined1.getUpperBound(), 0.000001);

        Range combined2 = Range.combineIgnoringNaN(rangeNaN, range1);
         assertEquals(1.0, combined2.getLowerBound(), 0.000001);
        assertEquals(5.0, combined2.getUpperBound(), 0.000001);

        Range combined3 = Range.combineIgnoringNaN(null, rangeNaN);
        assertNull(combined3);

        Range combined4 = Range.combineIgnoringNaN(rangeNaN, null);
        assertNull(combined4);
    }


    @Test
    public void testCombineIgnoringNaN_NoOverlap() {
        Range range1 = new Range(1.0, 5.0);
        Range range2 = new Range(6.0, 10.0);
        Range combined = Range.combineIgnoringNaN(range1, range2);
        assertEquals(1.0, combined.getLowerBound(), 0.000001);
        assertEquals(10.0, combined.getUpperBound(), 0.000001);
    }

    @Test
    public void testCombineIgnoringNaN_Overlap() {
        Range range1 = new Range(1.0, 5.0);
        Range range2 = new Range(3.0, 7.0);
        Range combined = Range.combineIgnoringNaN(range1, range2);
        assertEquals(1.0, combined.getLowerBound(), 0.000001);
        assertEquals(7.0, combined.getUpperBound(), 0.000001);
    }

    @Test
    public void testCombineIgnoringNaN_WithNaNValues() {
        Range range1 = new Range(1.0, Double.NaN);
        Range range2 = new Range(Double.NaN, 10.0);
        Range combined = Range.combineIgnoringNaN(range1, range2);
        assertNull(combined);

        Range range3 = new Range(1.0, 5.0);
        Range range4 = new Range(Double.NaN, 7.0);
        Range combined2 = Range.combineIgnoringNaN(range3, range4);
        assertEquals(1.0, combined2.getLowerBound(), 0.000001);
        assertEquals(7.0, combined2.getUpperBound(), 0.000001);
    }

    @Test
    public void testExpandToInclude_NullRange() {
        Range expanded = Range.expandToInclude(null, 5.0);
        assertEquals(5.0, expanded.getLowerBound(), 0.000001);
        assertEquals(5.0, expanded.getUpperBound(), 0.000001);
    }

    @Test
    public void testExpandToInclude_BelowRange() {
        Range range = new Range(1.0, 5.0);
        Range expanded = Range.expandToInclude(range, 0.0);
        assertEquals(0.0, expanded.getLowerBound(), 0.000001);
        assertEquals(5.0, expanded.getUpperBound(), 0.000001);
    }

    @Test
    public void testExpandToInclude_AboveRange() {
        Range range = new Range(1.0, 5.0);
        Range expanded = Range.expandToInclude(range, 6.0);
        assertEquals(1.0, expanded.getLowerBound(), 0.000001);
        assertEquals(6.0, expanded.getUpperBound(), 0.000001);
    }

    @Test
    public void testExpandToInclude_WithinRange() {
        Range range = new Range(1.0, 5.0);
        Range expanded = Range.expandToInclude(range, 3.0);
        assertEquals(1.0, expanded.getLowerBound(), 0.000001);
        assertEquals(5.0, expanded.getUpperBound(), 0.000001);
    }

    @Test
    public void testExpand_ValidMargins() {
        Range range = new Range(1.0, 5.0);
        Range expanded = Range.expand(range, 0.1, 0.2);
        assertEquals(0.6, expanded.getLowerBound(), 0.000001);
        assertEquals(5.8, expanded.getUpperBound(), 0.000001);
    }

    @Test
    public void testExpand_LowerMarginGreaterThanUpperMargin() {
        Range range = new Range(1.0, 5.0);
        Range expanded = Range.expand(range, 0.5, 0.1);
        assertEquals(3.0, expanded.getLowerBound(), 0.000001);
        assertEquals(3.0, expanded.getUpperBound(), 0.000001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExpand_NullRange() {
        Range.expand(null, 0.1, 0.2);
    }

    @Test
    public void testShift_NoZeroCrossing() {
        Range base = new Range(1.0, 5.0);
        Range shifted = Range.shift(base, 2.0, false);
        assertEquals(3.0, shifted.getLowerBound(), 0.000001);
        assertEquals(7.0, shifted.getUpperBound(), 0.000001);

        Range shiftedNegative = Range.shift(base, -0.5, false);
        assertEquals(0.5, shiftedNegative.getLowerBound(), 0.000001);
        assertEquals(4.5, shiftedNegative.getUpperBound(), 0.000001);

        Range baseNegative = new Range(-5.0, -1.0);
        Range shiftedNegative2 = Range.shift(baseNegative, 0.5, false);
        assertEquals(-4.5, shiftedNegative2.getLowerBound(), 0.000001);
        assertEquals(-0.5, shiftedNegative2.getUpperBound(), 0.000001);

        Range shiftedToZero = Range.shift(base, -5.0, false);
        assertEquals(0.0, shiftedToZero.getLowerBound(), 0.000001);
        assertEquals(0.0, shiftedToZero.getUpperBound(), 0.000001);

        Range baseZero = new Range(-1.0, 1.0);
        Range shiftedZero = Range.shift(baseZero, -0.5, false);
        assertEquals(0.0, shiftedZero.getLowerBound(), 0.000001);
        assertEquals(0.5, shiftedZero.getUpperBound(), 0.000001);
    }

    @Test
    public void testShift_AllowZeroCrossing() {
        Range base = new Range(1.0, 5.0);
        Range shifted = Range.shift(base, -2.0, true);
        assertEquals(-1.0, shifted.getLowerBound(), 0.000001);
        assertEquals(3.0, shifted.getUpperBound(), 0.000001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShift_NullBase() {
          Range.shift(null, 2.0, true);
    }

    @Test
    public void testScale_ValidFactor() {
        Range base = new Range(1.0, 5.0);
        Range scaled = Range.scale(base, 2.0);
        assertEquals(2.0, scaled.getLowerBound(), 0.000001);
        assertEquals(10.0, scaled.getUpperBound(), 0.000001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testScale_NegativeFactor() {
        Range base = new Range(1.0, 5.0);
        Range.scale(base, -2.0);
    }

    @Test
    public void testEquals_EqualRanges() {
        Range range1 = new Range(1.0, 5.0);
        Range range2 = new Range(1.0, 5.0);
        assertEquals(range1, range2);
    }

    @Test
    public void testEquals_NotEqualRanges() {
        Range range1 = new Range(1.0, 5.0);
        Range range2 = new Range(2.0, 6.0);
        assertNotEquals(range1, range2);
    }

    @Test
    public void testEquals_DifferentObject() {
        Range range = new Range(1.0, 5.0);
        assertNotEquals(range, new Object());
    }

    @Test
    public void testHashcode() {
        Range range1 = new Range(1.0, 5.0);
        Range range2 = new Range(1.0, 5.0);
        assertEquals(range1.hashCode(), range2.hashCode());
    }

    @Test
    public void testIsNaNRange() {
        Range range1 = new Range(Double.NaN, Double.NaN);
        assertTrue(range1.isNaNRange());

        Range range2 = new Range(1.0, 5.0);
        assertFalse(range2.isNaNRange());

        Range range3 = new Range(1.0, Double.NaN);
        assertFalse(range3.isNaNRange());

        Range range4 = new Range(Double.NaN, 5.0);
        assertFalse(range4.isNaNRange());
    }

    @Test
    public void testShiftWithNoZeroCrossing() throws Exception {
        Range range = new Range(1, 5);

        Method shiftWithNoZeroCrossingMethod = Range.class.getDeclaredMethod("shiftWithNoZeroCrossing", double.class, double.class);
		shiftWithNoZeroCrossingMethod.setAccessible(true);
        double result1 = (double) shiftWithNoZeroCrossingMethod.invoke(range, 2.0, 1.0); // Value: 2.0, Delta: 1.0
        assertEquals(3.0, result1, 0.0001);

        double result2 = (double) shiftWithNoZeroCrossingMethod.invoke(range, -2.0, 1.0); // Value: -2.0, Delta: 1.0
        assertEquals(-1.0, result2, 0.0001);

        double result3 = (double) shiftWithNoZeroCrossingMethod.invoke(range, 1.0, -2.0); // Value: 1.0, Delta: -2.0
        assertEquals(0.0, result3, 0.0001);

        double result4 = (double) shiftWithNoZeroCrossingMethod.invoke(range, -1.0, 2.0); // Value: -1.0, Delta: 2.0
        assertEquals(0.0, result4, 0.0001);

        double result5 = (double) shiftWithNoZeroCrossingMethod.invoke(range, 0.0, 2.0); // Value: 0.0, Delta: 2.0
        assertEquals(2.0, result5, 0.0001);
    }
    
	@Test
	public void testExpand_LowerGreaterThanUpper() {
	    Range range = new Range(5.0, 10.0);

	    Range expanded = Range.expand(range, 5.0, -6.0); 
	    double expectedValue = -20.0; 

	    assertEquals(expectedValue, expanded.getLowerBound(), 0.000001);
	    assertEquals(expectedValue, expanded.getUpperBound(), 0.000001);
	}




    @Test
    public void testShift_DirectCall() {
        Range base = new Range(1.0, 5.0);
        Range shifted = Range.shift(base, 2.0); 
        assertEquals(3.0, shifted.getLowerBound(), 0.000001);
        assertEquals(7.0, shifted.getUpperBound(), 0.000001);
    }
	
    private Range createInvalidRange() throws Exception {
        Range invalidRange = new Range(1.0, 5.0); 

        Field lowerField = Range.class.getDeclaredField("lower");
        Field upperField = Range.class.getDeclaredField("upper");
        lowerField.setAccessible(true);
        upperField.setAccessible(true);

        lowerField.setDouble(invalidRange, 10.0);
        upperField.setDouble(invalidRange, 5.0);

        return invalidRange;
    }
}
