/*
 * Copyright (c) 2014-2015, NTUU KPI, Computer systems department and/or its affiliates. All rights reserved.
 * NTUU KPI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 */

package ua.kpi.comsys.test2.implementation;

import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StringListTest {
    NumberListImpl list1;
    NumberListImpl list2;

    @After
    public void tearDown() {
        if (list1 != null) {
            list1.clear();
            list1 = null;
        }
        if (list2 != null) {
            list2.clear();
            list2 = null;
        }
    }

    @Test
    public void testSingleDigit() {
        list1 = new NumberListImpl("1");

        assertNotNull("List is null while it shouldn't!", list1);
        assertFalse("List is empty while it shouldn't!", list1.isEmpty());

        assertEquals("Wrong size", 1, list1.size());

        assertEquals("Wrong value", Byte.valueOf((byte) 1), list1.get(0));
    }

    @Test
    public void testToString() {
        String value = "79483758967495604375647803561675463655464562565464565654634156134636";
        list1 = new NumberListImpl(value);

        assertNotNull("List is null while it shouldn't!", list1);
        assertFalse("List is empty while it shouldn't!", list1.isEmpty());

        String res = list1.toDecimalString();
        assertNotNull("Result is null while it shouldn't!", res);
        assertEquals("Wrong value", value, res);
    }

    @Test
    public void testInvalidStringInput() {
        String value = "7948375896749s5604375fd647803561675463655464562565464565654634156134636";
        list1 = new NumberListImpl(value);

        assertNotNull("List is null while it shouldn't!", list1);
        assertTrue("List should be empty!", list1.isEmpty());

        value = "-4";
        list1 = new NumberListImpl(value);

        assertNotNull("List is null while it shouldn't!", list1);
        assertTrue("List should be empty!", list1.isEmpty());
    }


}
