package com.simoncomputing.app.winventory.bo;

import static org.junit.Assert.*;
import org.junit.*;

import com.simoncomputing.app.winventory.domain.*;
import com.simoncomputing.app.winventory.dao.*;
import com.simoncomputing.app.winventory.util.*;

public class TestRefHardwareTypeBo {

    @Before
    public void setup() {
        SessionFactory.initializeForTest();
    }

    @Test
    public void test() throws BoException {

        RefHardwareTypeBo refHardwareTypeBo = RefHardwareTypeBo.getInstance();

        RefHardwareType refHardwareType = TestRefHardwareTypeDao.createRefHardwareType();
        int count = refHardwareTypeBo.create( refHardwareType );
        assertEquals( 1, count );

        RefHardwareType readRecord = refHardwareTypeBo.read( refHardwareType.getCode() );
        assertNotNull( readRecord.getCode() );

        TestRefHardwareTypeDao.compareRecords( refHardwareType, readRecord );

        TestRefHardwareTypeDao.modifyRecord( refHardwareType );
        count = refHardwareTypeBo.update( refHardwareType );
        assertEquals( 1, count );

        count = refHardwareTypeBo.delete( refHardwareType.getCode());
        assertEquals( 1, count );

        readRecord = refHardwareTypeBo.read( refHardwareType.getCode());
        assertNull( readRecord );

    }
    // PROTECTED CODE -->

}