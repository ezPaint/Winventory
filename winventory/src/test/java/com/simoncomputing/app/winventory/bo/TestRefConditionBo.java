package com.simoncomputing.app.winventory.bo;

import static org.junit.Assert.*;
import org.junit.*;

import com.simoncomputing.app.winventory.domain.*;
import com.simoncomputing.app.winventory.dao.*;
import com.simoncomputing.app.winventory.util.*;

public class TestRefConditionBo {

    @Before
    public void setup() {
        SessionFactory.initializeForTest();
    }

    @Test
    public void test() throws BoException {

        RefConditionBo refConditionBo = RefConditionBo.getInstance();

        RefCondition refCondition = TestRefConditionDao.createRefCondition();
        int count = refConditionBo.create( refCondition );
        assertEquals( 1, count );

        RefCondition readRecord = refConditionBo.read( refCondition.getCode() );
        assertNotNull( readRecord.getCode() );

        TestRefConditionDao.compareRecords( refCondition, readRecord );

        TestRefConditionDao.modifyRecord( refCondition );
        count = refConditionBo.update( refCondition );
        assertEquals( 1, count );

        count = refConditionBo.delete( refCondition.getCode());
        assertEquals( 1, count );

        readRecord = refConditionBo.read( refCondition.getCode());
        assertNull( readRecord );

    }
    // PROTECTED CODE -->

}