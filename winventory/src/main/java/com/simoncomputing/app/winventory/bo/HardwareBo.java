package com.simoncomputing.app.winventory.bo;


import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.sql.Date;

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

    public List<Hardware> getListByLocationId( Long key ) throws BoException {
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

    public List<Hardware> getListByUserId( Long key ) throws BoException {
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

    /**
     * Returns a {@link List} of all {@link Hardware} from the database
     * 
     * @return A {@link List} of all {@link Hardware} objects
     * @throws BoException
     */
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

    /**
     * Returns a {@link List} of all {@link Hardware} that have a non-null
     * userID (and are associated with a User) from the database
     * 
     * @return A {@link List} of all {@link Hardware} objects meeting the
     *         "in use" criteria
     * @throws BoException
     */
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

    /**
     * Returns a {@link List} of all {@link Hardware} that have a null userID
     * (and are not associated with a User) from the database
     * 
     * @return A {@link List} of all {@link Hardware} objects meeting the
     *         "in use" criteria
     * @throws BoException
     */
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

    /**
     * Returns a {@link List} of all {@link Hardware} objects in the database
     * that contain the {@code search} String in any of their fields
     * 
     * @param search
     *            The String that will be checked for in all of the fields in
     *            the {@link Hardware} items
     * @return A {@link List} of {@link Hardware} objects matching the search
     *         criteria
     * @throws BoException
     */
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

    /**
     * Returns a {@link List} of types of {@link Hardware} in order of the most
     * frequently occurring types for all database entries. The {@code limit}
     * parameter specifies how many of these most frequently occurring types to
     * return
     * 
     * @param limit
     *            Specifies how many of the top types to return
     * @return A {@link List} of Strings which are the top types in the database
     * @throws BoException
     */
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

    /**
     * Returns a {@link List} of all {@link Hardware} objects in the database
     * that match the advanced search criteria. The {@code columns} parameter
     * contains a list of Strings containing field names that a {@link Hardware}
     * object could have; it corresponds to the {@code searches} parameters,
     * which contains lists of Strings that will be search for each
     * corresponding field. The {@code stored} and {@code owned} parameters are
     * booleans which indicate that the search is being performed on items in
     * storage or in use if the boolean value is true.
     * 
     * @param columns
     *            An {@link ArrayList} of Strings that could be any of the
     *            fields in {@link Hardware}. Each index corresponds to the same
     *            index in the {@code searches} parameter
     * @param searches
     *            An {@link ArrayList} of {@link ArrayList} of Strings; these
     *            ArrayLists contain all of the keywords to search for a given
     *            field in any {@link Hardware} object. Each index corresponds
     *            to the same index in the {@code columns} parameter
     * @param stored
     *            A boolean which is true if the search is to return results in
     *            storage only
     * @param owned
     *            A boolean which is true if the search is to return results in
     *            use only
     * @return A {@link List} of {@link Hardware} objects matching the search
     *         criteria
     * @throws BoException
     */
    public List<Hardware> searchAdvanced(ArrayList<String> columns,
                    ArrayList<ArrayList<String>> searches, Boolean stored, Boolean owned,
                    Boolean cost, double minCost, double maxCost,
                    Boolean date, Date startDate, Date endDate)
                    throws BoException {
        SqlSession session = null;
        List<Hardware> list;

        if (columns.equals("") || searches == null) {
            // TODO throw application error
            return null;
        }

        try {
            session = SessionFactory.getSession();
            HardwareDao mapper = session.getMapper(HardwareDao.class);
            list = mapper.searchAdvanced(columns, searches, stored, owned,
                    cost, minCost, maxCost, date, startDate, endDate);
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