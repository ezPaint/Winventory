package com.simoncomputing.app.winventory.bo;

import java.util.List;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.*;

import com.simoncomputing.app.winventory.dao.*;
import com.simoncomputing.app.winventory.domain.Event;
import com.simoncomputing.app.winventory.domain.Hardware;
import com.simoncomputing.app.winventory.domain.Software;
import com.simoncomputing.app.winventory.util.BoException;

public class SoftwareBo {

    private static SoftwareBo instance = new SoftwareBo();

    public static SoftwareBo getInstance() {
        return instance;
    } 

    private SoftwareBo() {
    } 

    public int create( Software value ) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            SoftwareDao mapper = session.getMapper( SoftwareDao.class );
            result = mapper.create( value );
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

    public int update( Software value ) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            SoftwareDao mapper = session.getMapper( SoftwareDao.class );
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

    public int delete( Long key ) throws BoException {
        SqlSession session = null;
        int result = 0;
        String where = "KEY='" + key + "' ";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put( "where", where );

        try {
            session = SessionFactory.getSession();
            SoftwareDao mapper = session.getMapper( SoftwareDao.class );
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

    public Software read( Long key ) throws BoException {
        SqlSession session = null;
        Software result;
        String where = "KEY='" + key + "' ";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put( "where", where );

        try {
            session = SessionFactory.getSession();
            SoftwareDao mapper = session.getMapper( SoftwareDao.class );
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

    public List<Software> getListByName( String key ) throws BoException {
        SqlSession session = null;
        List<Software> list;

        try {
            session = SessionFactory.getSession();
            SoftwareDao mapper = session.getMapper( SoftwareDao.class );
            list = mapper.getListByName( key );
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

    public List<Software> getListBySerialNo( String key ) throws BoException {
        SqlSession session = null;
        List<Software> list;

        try {
            session = SessionFactory.getSession();
            SoftwareDao mapper = session.getMapper( SoftwareDao.class );
            list = mapper.getListBySerialNo( key );
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

    public List<Software> getListByLicenseKey( String key ) throws BoException {
        SqlSession session = null;
        List<Software> list;

        try {
            session = SessionFactory.getSession();
            SoftwareDao mapper = session.getMapper( SoftwareDao.class );
            list = mapper.getListByLicenseKey( key );
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

    public List<Software> getListByPurchasedDate( Date key ) throws BoException {
        SqlSession session = null;
        List<Software> list;

        try {
            session = SessionFactory.getSession();
            SoftwareDao mapper = session.getMapper( SoftwareDao.class );
            list = mapper.getListByPurchasedDate( key );
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

    public List<Software> getListByExpirationDate( Date key ) throws BoException {
        SqlSession session = null;
        List<Software> list;

        try {
            session = SessionFactory.getSession();
            SoftwareDao mapper = session.getMapper( SoftwareDao.class );
            list = mapper.getListByExpirationDate( key );
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
    
    public List<Software> getListByPurchaseRange( Date start, Date end ) throws BoException {
        SqlSession session = null;
        List<Software> list;

        try {
            session = SessionFactory.getSession();
            SoftwareDao mapper = session.getMapper( SoftwareDao.class );
            list = mapper.getListByPurchaseRange( start, end );
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
    public List<Software> search( String searchText ) throws BoException {
    	SqlSession session = null;
    	List<Software> list;
    	
    	try {
    		session = SessionFactory.getSession();
    		SoftwareDao mapper = session.getMapper( SoftwareDao.class );
    		list = mapper.search( searchText );
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
    
    public List<Software> getDefaultResults( int size ) throws BoException {
    	SqlSession session = null;
    	List<Software> list;
    	
    	try {
    		session = SessionFactory.getSession();
    		SoftwareDao mapper = session.getMapper( SoftwareDao.class );
    		list = mapper.getDefaultResults( size );
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


    public List<Software> getListByExpirationRange( Date start, Date end ) throws BoException {
        SqlSession session = null;
        List<Software> list;

        try {
            session = SessionFactory.getSession();
            SoftwareDao mapper = session.getMapper( SoftwareDao.class );
            list = mapper.getListByExpirationRange( start, end );
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
    
    
    //Hasn't been tested -- theoretical advanced search method
    public List<Software> advancedSearch(String name, String serialNo, String version, 
                                         String licenseKey, String cost) throws BoException {//, Date pStart, Date pEnd, Date eStart, Date eEnd) throws BoException {
        SqlSession session = null;
        List<Software> list;

        try {
            session = SessionFactory.getSession();
            SoftwareDao mapper = session.getMapper( SoftwareDao.class );
            list = mapper.advancedSearch( name, serialNo, version, licenseKey, cost);//, pStart, pEnd, eStart, eEnd);
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
    
    /**
     * Retrieves all software objects from the database (Software table).
     * @return List of all software objects.
     * @throws BoException
     */
    public List<Software> getAll() throws BoException {
        SqlSession session = null;
        List<Software> list;

        try {
            session = SessionFactory.getSession();
            SoftwareDao mapper = session.getMapper(SoftwareDao.class);
            list = mapper.getAll();
            session.commit();

        } catch (Exception e) {
            session.rollback();
            throw new BoException(e);

        } finally {
            if (session != null)
                session.close();
        }

        return list;
    }
}