package com.simoncomputing.app.winventory.bo;

import static org.junit.Assert.*;
import org.junit.*;

import com.simoncomputing.app.winventory.domain.*;
import com.simoncomputing.app.winventory.dao.*;
import com.simoncomputing.app.winventory.util.*;

public class TestSmtpBo {

    @Before
    public void setup() {
        SessionFactory.initializeForTest();
    }

    @Test
    public void test() throws BoException {

        SmtpBo smtpBo = SmtpBo.getInstance();

        Smtp smtp = TestSmtpDao.createSmtp();
        int count = smtpBo.create( smtp );
        assertEquals( 1, count );

        Smtp readRecord = smtpBo.read( smtp.getKey() );
        assertNotNull( readRecord.getKey() );

        TestSmtpDao.compareRecords( smtp, readRecord );

        TestSmtpDao.modifyRecord( smtp );
        count = smtpBo.update( smtp );
        assertEquals( 1, count );

        count = smtpBo.delete( smtp.getKey());
        assertEquals( 1, count );

        readRecord = smtpBo.read( smtp.getKey());
        assertNull( readRecord );

    }
    // PROTECTED CODE -->

}