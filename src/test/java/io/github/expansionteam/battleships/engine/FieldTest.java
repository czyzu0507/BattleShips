package io.github.expansionteam.battleships.engine;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.function.Function;

import static org.testng.Assert.*;

@Test()
public class FieldTest {
    @DataProvider(name = "equalityCheck")
    private Object[][] provideEqualObjects() {
        return new Object[][] {
                {new Field(1,1), new Field(1,1), true},
                {new Field(2,2), new Field(2,2), true},
                {new Field(1,1), new Field(1,2), false},
                {new Field(1,1), new Field(2,2), false},
                {new Field(5,3), new Field(3,5), false}
        };
    }

    @DataProvider(name = "compareCheck")
    private Object[][] provideObjectsToCompare() {
        // helper functions to determine sign
        Function<Integer, Boolean> fZero = in -> in == 0;
        Function<Integer, Boolean> fNegative = in -> in < 0;
        Function<Integer, Boolean> fPositive = in -> in > 0;

        return new Object[][] {
                {new Field(1,1), new Field(1,1), fZero},
                {new Field(1,1), new Field(1,2), fNegative},
                {new Field(1,1), new Field(2,1), fNegative},
                {new Field(2,2), new Field(1,2), fPositive},
                {new Field(2,2), new Field(2,1), fPositive},
        };
    }

    @Test(dataProvider = "equalityCheck")
    public void checkHashCodeEquality(final Field f1, final Field f2, final boolean expected) {
        assertEquals(f1.hashCode() == f2.hashCode(), expected);
    }

    @Test(dataProvider = "equalityCheck")
    public void checkEquals(final Field f1, final Field f2, final boolean expected) {
        assertEquals(f1.equals(f2), expected);
    }

    @Test(dataProvider = "compareCheck")
    public void checkCompare(final Field f1, final Field f2, final Function<Integer, Boolean> fun) {
        int compareResult = f1.compareTo(f2);
        assertTrue( fun.apply( compareResult ) );
    }
}