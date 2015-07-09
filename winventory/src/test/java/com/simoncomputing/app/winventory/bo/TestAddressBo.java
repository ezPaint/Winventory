package com.simoncomputing.app.winventory.bo;

import static org.junit.Assert.*;
import org.junit.*;

import com.simoncomputing.app.winventory.domain.*;
import com.simoncomputing.app.winventory.dao.*;
import com.simoncomputing.app.winventory.util.*;

public class TestAddressBo {

    @Before
    public void setup() {
        SessionFactory.initializeForTest();
    }

    @Test
    public void test() throws BoException {

        AddressBo addressBo = AddressBo.getInstance();

        Address address = TestAddressDao.createAddress();
        int count = addressBo.create( address );
        assertEquals( 1, count );

        Address readRecord = addressBo.read( address.getKey() );
        assertNotNull( readRecord.getKey() );

        TestAddressDao.compareRecords( address, readRecord );

        TestAddressDao.modifyRecord( address );
        count = addressBo.update( address );
        assertEquals( 1, count );

        count = addressBo.delete( address.getKey());
        assertEquals( 1, count );

        readRecord = addressBo.read( address.getKey());
        assertNull( readRecord );

    }
    // PROTECTED CODE -->

}