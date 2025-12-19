/*
 * Copyright (c) 2014-2015, NTUU KPI, Computer systems department and/or its affiliates. All rights reserved.
 * NTUU KPI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 */

package ua.kpi.comsys.test2.implementation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public abstract class NumberListTest {
    NumberListImpl list;

    @Before
    public void setUp() {
        assertTrue("Incorrect number of student's record book",
                NumberListImpl.getRecordBookNumber() > 4100 &&
                 NumberListImpl.getRecordBookNumber() < 4429);
    }

    @After
    public void tearDown() {
        if (list != null) {
            list.clear();
            list = null;
        }
    }

    @Test
    public void testEmptyConstructor() {
        list = new NumberListImpl();
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    @Test
    public void testSingleDigit() {
        list = new NumberListImpl();
        list.add(1);
        assertEquals(1, list.size());
        assertFalse(list.isEmpty());

        String str = list.toString();
        assertNotNull(str);
        assertEquals("1", list.toString());

    }

}
