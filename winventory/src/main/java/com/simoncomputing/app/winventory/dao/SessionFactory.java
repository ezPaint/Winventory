package com.simoncomputing.app.winventory.dao;

import java.io.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;

public class SessionFactory {

    public static SqlSessionFactory ssf;
    private static String ENVIRONMENT;
    private static String resource = "com/simoncomputing/app/winventory/dao/mybatis-config.xml";

    private static void setup(){
        InputStream inputStream = null;

        try {
            inputStream = Resources.getResourceAsStream( resource );
        } catch ( IOException e ) {
            e.printStackTrace();
		}

        if ( inputStream==null )
            throw new RuntimeException( "Cannot load myBatis resource " + resource );

        ssf = new SqlSessionFactoryBuilder().build( inputStream , ENVIRONMENT );

    }

    public static SqlSession getSession() {
        return ssf.openSession();
    }

    public static void initialize(){
        setup();
    }

    public static void initializeForTest(){
        ENVIRONMENT = "TESTING";
        setup();
    }
}