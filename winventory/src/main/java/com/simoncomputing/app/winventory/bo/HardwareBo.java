package com.simoncomputing.app.winventory.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

import org.apache.ibatis.session.*;

import com.simoncomputing.app.winventory.dao.*;
import com.simoncomputing.app.winventory.domain.Hardware;
import com.simoncomputing.app.winventory.util.BoException;

public class HardwareBo {

    private static HardwareBo instance = new HardwareBo();

    public static HardwareBo getInstance() {
        return instance;
    } 

    private HardwareBo() {
    } 

    public int create( Hardware value ) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            HardwareDao mapper = session.getMapper( HardwareDao.class );
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

    public int update( Hardware value ) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            HardwareDao mapper = session.getMapper( HardwareDao.class );
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
            HardwareDao mapper = session.getMapper( HardwareDao.class );
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

    public Hardware read( Long key ) throws BoException {
        SqlSession session = null;
        Hardware result;
        String where = "KEY='" + key + "' ";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put( "where", where );

        try {
            session = SessionFactory.getSession();
            HardwareDao mapper = session.getMapper( HardwareDao.class );
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

    public List<Hardware> getListByLocationId( Integer key ) throws BoException {
        SqlSession session = null;
        List<Hardware> list;

        try {
            session = SessionFactory.getSession();
            HardwareDao mapper = session.getMapper( HardwareDao.class );
            list = mapper.getListByLocationId( key );
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

    public List<Hardware> getListByUserId( Integer key ) throws BoException {
        SqlSession session = null;
        List<Hardware> list;

        try {
            session = SessionFactory.getSession();
            HardwareDao mapper = session.getMapper( HardwareDao.class );
            list = mapper.getListByUserId( key );
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

    public List<Hardware> getAll() throws BoException {
        SqlSession session = null;
        List<Hardware> list;

        try {
            session = SessionFactory.getSession();
            HardwareDao mapper = session.getMapper(HardwareDao.class);
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

    public List<Hardware> getInUse() throws BoException {
        SqlSession session = null;
        List<Hardware> list;

        try {
            session = SessionFactory.getSession();
            HardwareDao mapper = session.getMapper(HardwareDao.class);
            list = mapper.getInUse();
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
    
    public List<Hardware> getStorage() throws BoException {
        SqlSession session = null;
        List<Hardware> list;

        try {
            session = SessionFactory.getSession();
            HardwareDao mapper = session.getMapper(HardwareDao.class);
            list = mapper.getStorage();
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

    public List<Hardware> searchBasic(String search) throws BoException {

        SqlSession session = null;
        List<Hardware> list;

        try {
            session = SessionFactory.getSession();
            HardwareDao mapper = session.getMapper(HardwareDao.class);
            list = mapper.searchBasic(search);
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

    public List<String> getTopTypes(Integer limit) throws BoException {
        SqlSession session = null;
        List<String> list;

        try {
            session = SessionFactory.getSession();
            HardwareDao mapper = session.getMapper(HardwareDao.class);
            list = mapper.getTopTypes(limit);
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

    public List<Hardware> searchAdvanced(ArrayList<String> columns,
            ArrayList<ArrayList<String>> searches) throws BoException {
        SqlSession session = null;
        List<Hardware> list;

        if (columns.equals("") || searches == null) {
            // TODO throw application error
            return null;
        }

        try {
            session = SessionFactory.getSession();
            HardwareDao mapper = session.getMapper(HardwareDao.class);
            list = mapper.searchAdvanced(columns, searches);
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