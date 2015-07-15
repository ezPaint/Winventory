package com.simoncomputing.app.winventory.bo;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

import org.apache.ibatis.session.*;

import com.simoncomputing.app.winventory.dao.*;
import com.simoncomputing.app.winventory.domain.Location;
import com.simoncomputing.app.winventory.util.BoException;

public class LocationBo {

    private static LocationBo instance = new LocationBo();

    public static LocationBo getInstance() {
        return instance;
    } 

    private LocationBo() {
    } 

    public int create( Location value ) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            LocationDao mapper = session.getMapper( LocationDao.class );
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

    public int update( Location value ) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            LocationDao mapper = session.getMapper( LocationDao.class );
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
            LocationDao mapper = session.getMapper( LocationDao.class );
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

    public Location read( Long key ) throws BoException {
        SqlSession session = null;
        Location result;
        String where = "KEY='" + key + "' ";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put( "where", where );

        try {
            session = SessionFactory.getSession();
            LocationDao mapper = session.getMapper( LocationDao.class );
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

    public List<Location> getListByAddressId( Integer key ) throws BoException {
        SqlSession session = null;
        List<Location> list;

        try {
            session = SessionFactory.getSession();
            LocationDao mapper = session.getMapper( LocationDao.class );
            list = mapper.getListByAddressId( key );
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
    
    /**
     * Returns a {@link List} of all {@link Location} objects from the database
     * 
     * @return A {@link List} of all {@link Location} objects
     * @throws BoException
     */
    public List<Location> getAll() throws BoException {
        SqlSession session = null;
        List<Location> list;

        try {
            session = SessionFactory.getSession();
            LocationDao mapper = session.getMapper(LocationDao.class);
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