package com.simoncomputing.app.winventory.bo;

import static org.junit.Assert.*;
import org.junit.*;

import com.simoncomputing.app.winventory.domain.*;
import com.simoncomputing.app.winventory.dao.*;
import com.simoncomputing.app.winventory.util.*;

public class TestGoogleClientBo {

    @Before
    public void setup() {
        SessionFactory.initializeForTest();
    }

    @Test
    public void test() throws BoException {

        GoogleClientBo googleClientBo = GoogleClientBo.getInstance();

        GoogleClient googleClient = TestGoogleClientDao.createGoogleClient();
        int count = googleClientBo.create( googleClient );
        assertEquals( 1, count );

        GoogleClient readRecord = googleClientBo.read( googleClient.getKey() );
        assertNotNull( readRecord.getKey() );

        TestGoogleClientDao.compareRecords( googleClient, readRecord );

        TestGoogleClientDao.modifyRecord( googleClient );
        count = googleClientBo.update( googleClient );
        assertEquals( 1, count );

        count = googleClientBo.delete( googleClient.getKey());
        assertEquals( 1, count );

        readRecord = googleClientBo.read( googleClient.getKey());
        assertNull( readRecord );

    }
    // PROTECTED CODE -->

}