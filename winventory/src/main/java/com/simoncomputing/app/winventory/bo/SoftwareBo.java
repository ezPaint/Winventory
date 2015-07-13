package com.simoncomputing.app.winventory.bo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.simoncomputing.app.winventory.dao.SessionFactory;
import com.simoncomputing.app.winventory.dao.SoftwareDao;
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

    /**
     * Get a list of software objects that have the same name.
     * @param name Name of the software object 
     * @return A list of software objects with the same name
     * @throws BoException
     */
    public List<Software> getListByName( String name ) throws BoException {
        SqlSession session = null;
        List<Software> list;

        try {
            session = SessionFactory.getSession();
            SoftwareDao mapper = session.getMapper( SoftwareDao.class );
            list = mapper.getListByName( name );
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
     * Get a list of software objects that share the same serial number
     * @param serialNo
     * @return
     * @throws BoException
     */
    public List<Software> getListBySerialNo( String serialNo ) throws BoException {
        SqlSession session = null;
        List<Software> list;

        try {
            session = SessionFactory.getSession();
            SoftwareDao mapper = session.getMapper( SoftwareDao.class );
            list = mapper.getListBySerialNo( serialNo );
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
     * Get a list of software objects that share the same license key
     * @param licenseKey
     * @return
     * @throws BoException
     */
    public List<Software> getListByLicenseKey( String licenseKey ) throws BoException {
        SqlSession session = null;
        List<Software> list;

        try {
            session = SessionFactory.getSession();
            SoftwareDao mapper = session.getMapper( SoftwareDao.class );
            list = mapper.getListByLicenseKey( licenseKey );
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
     * Get a list of software objects that share the same purchased date
     * @param date
     * @return
     * @throws BoException
     */
    public List<Software> getListByPurchasedDate( Date date ) throws BoException {
        SqlSession session = null;
        List<Software> list;

        try {
            session = SessionFactory.getSession();
            SoftwareDao mapper = session.getMapper( SoftwareDao.class );
            list = mapper.getListByPurchasedDate( date );
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
     * Get a list of software objects that share the same expiration date.
     * @param date
     * @return
     * @throws BoException
     */
    public List<Software> getListByExpirationDate( Date date ) throws BoException {
        SqlSession session = null;
        List<Software> list;

        try {
            session = SessionFactory.getSession();
            SoftwareDao mapper = session.getMapper( SoftwareDao.class );
            list = mapper.getListByExpirationDate( date );
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
     * Get a list of software objects between (inclusive) the dates start and end.
     * @param start
     * @param end
     * @return
     * @throws BoException
     */
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
    
    /**
     * Retrieves all software objects from database (Software table) matching search term.
     * @param searchText
     * @return List of all software objects matching search term.
     * @throws BoException
     */
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
    
    /**
     * Retrieves max size software objects from database (Software table).
     * @param size
     * @return List of software objects to populate results page for empty search bar.
     * @throws BoException
     */
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

    /**
     * Retrieve a list of software objects with an expiration date between 'start' and 'end'
     * @param start
     * @param end
     * @return
     * @throws BoException
     */
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
    
    /**
     * Get a list of software objects within a cost range
     * @param minCost
     * @param maxCost
     * @return
     * @throws BoException
     */
    public List<Software> getListByCostRange( String minCost, String maxCost ) throws BoException {
    	SqlSession session = null;
        List<Software> list;

        try {
            session = SessionFactory.getSession();
            SoftwareDao mapper = session.getMapper( SoftwareDao.class );
            list = mapper.getListByCostRange( minCost, maxCost );
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
     * Retrieves all software objects from the database (Software table) matching search terms.
     * @param columns
     * @param searches
     * @return List of all software objects matching search terms.
     * @throws BoException
     */
    public List<Software> searchAdvanced(ArrayList<String> columns,
            ArrayList<ArrayList<String>> searches) throws BoException {
        SqlSession session = null;
        List<Software> list = null; 

        if (columns.equals("") || searches == null) {
            return null;
        }

        try {
            session = SessionFactory.getSession();
            SoftwareDao mapper = session.getMapper(SoftwareDao.class);
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

    /**
     * Retrieves a list of Software objects narrowed down to cost range
     * @param results
     * @param minCost
     * @param maxCost
     * @return List of Software objects from database (Software table)
     * @throws BoException 
     */
    public List<Software> searchCostRange(List<Software> results, String minCost, String maxCost) throws BoException {
    	List<Software> costs = new ArrayList<Software>(); 
    	List<Software> returnedResults = new ArrayList<Software>();    	
    	try {
    		if (minCost != null && maxCost != null)
    		costs = getListByCostRange(minCost, maxCost);
    		
    		for (Software s : results) {
    			for ( Software c : costs ) {
    				if (s.equals(c))
    					returnedResults.add(s);
    			}
    		}
    		
    	} catch (BoException e){ 
            throw new BoException( e );
    	}
    	
    	return costs;
    }
    
    /**
     * Searches a given list of Software objects and returns only those objects
     * that fall within a specified range of dates
     * 
     * @param results
     *            the list of Software objects to be searched and refined
     * @param dates
     *            the range of dates returned objects' purchased and/or
     *            expiration date should fall within
     * @return the narrowed down list of Software objects
     * @throws BoException 
     */
    public List<Software> searchDateRange(List<Software> results, ArrayList<String> dates) throws BoException {
        //Holds software objects between date ranges
        List<Software> list = new ArrayList<Software>();

        if (dates.get(0).equals("") && dates.get(1).equals("") && dates.get(2).equals("")
                && dates.get(3).equals("")) {
            return list;
        }

        List<Software> purchased = new ArrayList<Software>();
        List<Software> expired = new ArrayList<Software>();

        try {
            if (dates.get(2).equals("") && dates.get(3).equals("")) {
                // if only purchased dates are entered, expired should contain
                // all values in purchased
                list = getListByPurchaseRange(Date.valueOf(dates.get(0)),
                        Date.valueOf(dates.get(1)));

            } else if (dates.get(0).equals("") && dates.get(1).equals("")) {
                // if only expired dates are entered, purchased should contain
                // all values in expired
                list = getListByExpirationRange(Date.valueOf(dates.get(2)),
                        Date.valueOf(dates.get(3)));

            } else {
                // otherwise, purchased should contain only dates within
                // purchased range and expired should only contain dates within
                // the expired range
                purchased = getListByPurchaseRange(Date.valueOf(dates.get(0)),
                        Date.valueOf(dates.get(1)));
                expired = getListByExpirationRange(Date.valueOf(dates.get(2)),
                        Date.valueOf(dates.get(3)));
                
                //Calculate the intersection of 'purchased' and 'expired'
                for (Software p : purchased){
                    for (Software e : expired){
                        if (p.compareDates(e)) list.add(p);
                    }
                }
            }

        } catch (BoException e) {
            throw new BoException( e );
        }

        return list;
    }
    
    /**
     * Deletes all data (rows) from SOFTWARE
     * @throws BoException
     */
    public void deleteAll() throws BoException {
        SqlSession session = null;

        try {
            session = SessionFactory.getSession();
            SoftwareDao mapper = session.getMapper( SoftwareDao.class );
            mapper.deleteAll();
            session.commit();

        } catch ( Exception e ) {
            session.rollback();
            throw new BoException( e );

        } finally { 
            if ( session != null )
                session.close();
        }
    }
}