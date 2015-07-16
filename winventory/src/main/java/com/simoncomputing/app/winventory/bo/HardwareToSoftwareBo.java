package com.simoncomputing.app.winventory.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.ibatis.session.*;
import com.simoncomputing.app.winventory.dao.*;
import com.simoncomputing.app.winventory.domain.Hardware;
import com.simoncomputing.app.winventory.domain.HardwareToSoftware;
import com.simoncomputing.app.winventory.domain.Software;
import com.simoncomputing.app.winventory.util.BoException;

public class HardwareToSoftwareBo {

    private static HardwareToSoftwareBo instance = new HardwareToSoftwareBo();

    public static HardwareToSoftwareBo getInstance() {
        return instance;
    } 

    private HardwareToSoftwareBo() {
    } 

    public int create( Long hardwareId, Long softwareId ) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            HardwareToSoftwareDao mapper = session.getMapper( HardwareToSoftwareDao.class );
            result = mapper.create( hardwareId, softwareId );
            session.commit();

        } catch ( Exception e ) {
            session.rollback();
            throw new BoException( e );

        } finally { 
            if ( session != null )
                session.close();
        }

        return result;
    }

    public int update( HardwareToSoftware value ) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            HardwareToSoftwareDao mapper = session.getMapper( HardwareToSoftwareDao.class );
            result = mapper.update( value );
            session.commit();

        } catch ( Exception e ) {
            session.rollback();
            throw new BoException( e );

        } finally { 
            if ( session != null )
                session.close();
        }

        return result;
    }

    public int delete( Long hardwareId, Long softwareId ) throws BoException {
        SqlSession session = null;
        int result = 0;
        String where = "HARDWARE_ID='" + hardwareId + "' and " + "SOFTWARE_ID='" + softwareId + "' ";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put( "where", where );

        try {
            session = SessionFactory.getSession();
            HardwareToSoftwareDao mapper = session.getMapper( HardwareToSoftwareDao.class );
            result = mapper.delete( map );
            session.commit();

        } catch ( Exception e ) {
            session.rollback();
            throw new BoException( e );

        } finally { 
            if ( session != null )
                session.close();
        }

        return result;
    }

    public HardwareToSoftware read( Long hardwareId, Long softwareId ) throws BoException {
        SqlSession session = null;
        HardwareToSoftware result;
        String where = "HARDWARE_ID='" + hardwareId + "' and " + "SOFTWARE_ID='" + softwareId + "' ";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put( "where", where );

        try {
            session = SessionFactory.getSession();
            HardwareToSoftwareDao mapper = session.getMapper( HardwareToSoftwareDao.class );
            result = mapper.read( map );
            session.commit();

        } catch ( Exception e ) {
            session.rollback();
            throw new BoException( e );

        } finally { 
            if ( session != null )
                session.close();
        }

        return result;
    }

    public List<HardwareToSoftware> getListByHardwareId( Long key ) throws BoException {
        SqlSession session = null;
        List<HardwareToSoftware> list;

        try {
            session = SessionFactory.getSession();
            HardwareToSoftwareDao mapper = session.getMapper( HardwareToSoftwareDao.class );
            list = mapper.getListByHardwareId( key );
            session.commit();

        } catch ( Exception e ) {
            session.rollback();
            throw new BoException( e );

        } finally { 
            if ( session != null )
                session.close();
        }

        return list;
    }

    public List<HardwareToSoftware> getListBySoftwareId( Long key ) throws BoException {
        SqlSession session = null;
        List<HardwareToSoftware> list;

        try {
            session = SessionFactory.getSession();
            HardwareToSoftwareDao mapper = session.getMapper( HardwareToSoftwareDao.class );
            list = mapper.getListBySoftwareId( key );
            session.commit();

        } catch ( Exception e ) {
            session.rollback();
            throw new BoException( e );

        } finally { 
            if ( session != null )
                session.close();
        }

        return list;
    }

    // PROTECTED CODE -->

    public List<Hardware> getHardwareBySoftwareId( Long key ) throws BoException {
        SqlSession session = null;
        List<Hardware> list;

        try {
            session = SessionFactory.getSession();
            HardwareToSoftwareDao mapper = session.getMapper( HardwareToSoftwareDao.class );
            list = mapper.getHardwareBySoftwareId( key );
            session.commit();

        } catch ( Exception e ) {
            session.rollback();
            throw new BoException( e );

        } finally { 
            if ( session != null )
                session.close();
        }

        return list;
    }
    
    public List<Software> getSoftwareByHardwareId( Long key ) throws BoException {
        SqlSession session = null;
        List<Software> list;

        try {
            session = SessionFactory.getSession();
            HardwareToSoftwareDao mapper = session.getMapper( HardwareToSoftwareDao.class );
            list = mapper.getSoftwareByHardwareId( key );
            session.commit();

        } catch ( Exception e ) {
            session.rollback();
            throw new BoException( e );

        } finally { 
            if ( session != null )
                session.close();
        }

        return list;
    }
    
}