/*
 * Copyright (c) 2014-2015, NTUU KPI, Computer systems department and/or its affiliates. All rights reserved.
 * NTUU KPI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 */

package ua.kpi.comsys.test2.implementation;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assume.assumeTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;

public class ScaleOfNotationChangeTest {
    static String BINARY="1000011010010011001101111100011011110001110011110110110001101";
    static String TERNARY="22002001022022101111201212020022201000";
    static String OCTAL="103223157433616366615";
    static String DECIMAL="1212144468782345613";
    static String HEXADECIMAL="10D266F8DE39ED8D";

    static NumberListImpl list;
    NumberListImpl actual;

    @BeforeClass
    public static void beforeClass() {
        list = new NumberListImpl(DECIMAL);
        assertNotNull("List is null while it shouldn't!", list);
        assertFalse("List is empty while it shouldn't!", list.isEmpty());
    }

    @After
    public void tearDown() {
        if (actual != null) {
            actual.clear();
            actual = null;
        }
    }

    @AfterClass
    public static void afterClass() {
        list.clear();
        list = null;
    }

    @Test
    public void testToTernary() {
        assumeTrue(NumberListImpl.getRecordBookNumber() % 5  == 0);

        actual = list.changeScale();

        assertNotNull("Result is null while it shouldn't!", actual);
        assertEquals("Invalid result of toDecimalString() ", DECIMAL, actual.toDecimalString());
        assertEquals("Invalid result of toString() ", BINARY, list.toString());
        assertEquals("Invalid result of changeScale() ", TERNARY, actual.toString());

    }

    @Test
    public void testToOctal() {
        assumeTrue(NumberListImpl.getRecordBookNumber() % 5  == 1);

        actual = list.changeScale();

        assertNotNull("Result is null while it shouldn't!", actual);
        assertEquals("Invalid result of toDecimalString() ", DECIMAL, actual.toDecimalString());
        assertEquals("Invalid result of toString() ", TERNARY, list.toString());
        assertEquals("Invalid result of changeScale() ", OCTAL, actual.toString());

    }

    @Test
    public void testToDecimal() {
        assumeTrue(NumberListImpl.getRecordBookNumber() % 5  == 2);

        actual = list.changeScale();

        assertNotNull("Result is null while it shouldn't!", actual);
        assertEquals("Invalid result of toDecimalString() ", DECIMAL, list.toDecimalString());
        assertEquals("Invalid result of toString() ", OCTAL, list.toString());

        assertEquals("Invalid result of changeScale() ", DECIMAL, actual.toString());

    }

    @Test
    public void testToHex() {
        assumeTrue(NumberListImpl.getRecordBookNumber() % 5  == 3);

        actual = list.changeScale();

        assertNotNull("Result is null while it shouldn't!", actual);
        assertEquals("Invalid result of toDecimalString() ", DECIMAL, actual.toDecimalString());
        assertEquals("Invalid result of toString() ", DECIMAL, list.toString());

        assertEquals("Invalid result of changeScale() ", HEXADECIMAL, actual.toString());

    }

    @Test
    public void testToBinary() {
        assumeTrue(NumberListImpl.getRecordBookNumber() % 5  == 4);

        actual = list.changeScale();

        assertNotNull("Result is null while it shouldn't!", actual);
        assertEquals("Invalid result of toDecimalString() ", DECIMAL, actual.toDecimalString());
        assertEquals("Invalid result of toString() ", HEXADECIMAL, list.toString());

        assertEquals("Invalid result of changeScale() ", BINARY, actual.toString());

    }
}
