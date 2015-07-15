package com.simoncomputing.app.winventory.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

import org.apache.ibatis.session.*;

import com.simoncomputing.app.winventory.dao.*;
import com.simoncomputing.app.winventory.domain.Address;
import com.simoncomputing.app.winventory.util.BoException;

public class AddressBo {

    private static AddressBo instance = new AddressBo();

    public static AddressBo getInstance() {
        return instance;
    } 

    private AddressBo() {
    } 

    public int create( Address value ) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            AddressDao mapper = session.getMapper( AddressDao.class );
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

    public int update( Address value ) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            AddressDao mapper = session.getMapper( AddressDao.class );
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
            AddressDao mapper = session.getMapper( AddressDao.class );
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

    public Address read( Long key ) throws BoException {
        SqlSession session = null;
        Address result;
        String where = "KEY='" + key + "' ";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put( "where", where );

        try {
            session = SessionFactory.getSession();
            AddressDao mapper = session.getMapper( AddressDao.class );
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

    // PROTECTED CODE -->
    
    /**
     * Returns a {@link List} of all {@link Address} objects from the database
     * 
     * @return A {@link List} of all {@link Address} objects
     * @throws BoException
     */
    public List<Address> getAll() throws BoException {
        SqlSession session = null;
        List<Address> list;

        try {
            session = SessionFactory.getSession();
            AddressDao mapper = session.getMapper(AddressDao.class);
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

    
    /**
     * Returns a {@link List} of all active {@link Address} objects from the database
     * 
     * @return A {@link List} of all active {@link Address} objects
     * @throws BoException
     */
    public List<Address> getAllActive() throws BoException {
        SqlSession session = null;
        List<Address> list;

        try {
            session = SessionFactory.getSession();
            AddressDao mapper = session.getMapper(AddressDao.class);
            list = mapper.getAllActive();
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