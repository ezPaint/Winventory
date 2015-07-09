package com.simoncomputing.app.winventory.bo;

import static org.junit.Assert.*;
import org.junit.*;

import com.simoncomputing.app.winventory.domain.*;
import com.simoncomputing.app.winventory.dao.*;
import com.simoncomputing.app.winventory.util.*;

public class TestAccessTokenBo {

    @Before
    public void setup() {
        SessionFactory.initializeForTest();
    }

    @Test
    public void test() throws BoException {

        AccessTokenBo accessTokenBo = AccessTokenBo.getInstance();

        AccessToken accessToken = TestAccessTokenDao.createAccessToken();
        int count = accessTokenBo.create( accessToken );
        assertEquals( 1, count );

        AccessToken readRecord = accessTokenBo.read( accessToken.getUserKey() );
        assertNotNull( readRecord.getUserKey() );

        TestAccessTokenDao.compareRecords( accessToken, readRecord );

        TestAccessTokenDao.modifyRecord( accessToken );
        count = accessTokenBo.update( accessToken );
        assertEquals( 1, count );

        count = accessTokenBo.delete( accessToken.getUserKey());
        assertEquals( 1, count );

        readRecord = accessTokenBo.read( accessToken.getUserKey());
        assertNull( readRecord );

    }
    // PROTECTED CODE -->

}