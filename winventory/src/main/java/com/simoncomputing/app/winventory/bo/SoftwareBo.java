package com.simoncomputing.app.winventory.bo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
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

    public int create(Software value) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            SoftwareDao mapper = session.getMapper(SoftwareDao.class);
            result = mapper.create(value);
            session.commit();

        } catch (Exception e) {
            session.rollback();
            throw new BoException(e);

        } finally {
            if (session != null)
                session.close();
        }

        return result;
    }

    public int update(Software value) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            SoftwareDao mapper = session.getMapper(SoftwareDao.class);
            result = mapper.update(value);
            session.commit();

        } catch (Exception e) {
            session.rollback();
            throw new BoException(e);

        } finally {
            if (session != null)
                session.close();
        }

        return result;
    }

    public int delete(Long key) throws BoException {
        SqlSession session = null;
        int result = 0;
        String where = "KEY='" + key + "' ";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("where", where);

        try {
            session = SessionFactory.getSession();
            SoftwareDao mapper = session.getMapper(SoftwareDao.class);
            result = mapper.delete(map);
            session.commit();

        } catch (Exception e) {
            session.rollback();
            throw new BoException(e);

        } finally {
            if (session != null)
                session.close();
        }

        return result;
    }

    public Software read(Long key) throws BoException {
        SqlSession session = null;
        Software result;
        String where = "KEY='" + key + "' ";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("where", where);

        try {
            session = SessionFactory.getSession();
            SoftwareDao mapper = session.getMapper(SoftwareDao.class);
            result = mapper.read(map);
            session.commit();

        } catch (Exception e) {
            session.rollback();
            throw new BoException(e);

        } finally {
            if (session != null)
                session.close();
        }

        return result;
    }

    public List<Software> getListByName(String key) throws BoException {
        SqlSession session = null;
        List<Software> list;

        try {
            session = SessionFactory.getSession();
            SoftwareDao mapper = session.getMapper(SoftwareDao.class);
            list = mapper.getListByName(key);
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

    public List<Software> getListBySerialNo(String key) throws BoException {
        SqlSession session = null;
        List<Software> list;

        try {
            session = SessionFactory.getSession();
            SoftwareDao mapper = session.getMapper(SoftwareDao.class);
            list = mapper.getListBySerialNo(key);
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

    public List<Software> getListByLicenseKey(String key) throws BoException {
        SqlSession session = null;
        List<Software> list;

        try {
            session = SessionFactory.getSession();
            SoftwareDao mapper = session.getMapper(SoftwareDao.class);
            list = mapper.getListByLicenseKey(key);
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

    public List<Software> getListByPurchasedDate(Date key) throws BoException {
        SqlSession session = null;
        List<Software> list;

        try {
            session = SessionFactory.getSession();
            SoftwareDao mapper = session.getMapper(SoftwareDao.class);
            list = mapper.getListByPurchasedDate(key);
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

    public List<Software> getListByExpirationDate(Date key) throws BoException {
        SqlSession session = null;
        List<Software> list;

        try {
            session = SessionFactory.getSession();
            SoftwareDao mapper = session.getMapper(SoftwareDao.class);
            list = mapper.getListByExpirationDate(key);
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

    // PROTECTED CODE -->

    public List<Software> getListByPurchaseRange(Date start, Date end) throws BoException {
        SqlSession session = null;
        List<Software> list;

        try {
            session = SessionFactory.getSession();
            SoftwareDao mapper = session.getMapper(SoftwareDao.class);
            list = mapper.getListByPurchaseRange(start, end);
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
    public List<Software> search(String searchText) throws BoException {
        SqlSession session = null;
        List<Software> list;

        try {
            session = SessionFactory.getSession();
            SoftwareDao mapper = session.getMapper(SoftwareDao.class);
            list = mapper.search(searchText);
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

    public List<Software> getDefaultResults(int size) throws BoException {
        SqlSession session = null;
        List<Software> list;

        try {
            session = SessionFactory.getSession();
            SoftwareDao mapper = session.getMapper(SoftwareDao.class);
            list = mapper.getDefaultResults(size);
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

    public List<Software> getListByExpirationRange(Date start, Date end) throws BoException {
        SqlSession session = null;
        List<Software> list;

        try {
            session = SessionFactory.getSession();
            SoftwareDao mapper = session.getMapper(SoftwareDao.class);
            list = mapper.getListByExpirationRange(start, end);
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

    public List<Software> searchAdvanced(ArrayList<String> columns,
            ArrayList<ArrayList<String>> searches) throws BoException {
        SqlSession session = null;
        List<Software> list;

        if (columns.equals("") || searches == null) {
            // TODO throw application error
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
     * 
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
     * Searches a given list of Software objects and returns only those objects
     * that fall within a specified range of dates
     * 
     * @param results
     *            the list of Software objects to be searched and refined
     * @param dates
     *            the range of dates returned objects' purchased and/or
     *            expiration date should fall within
     * @return the narrowed down list of Software objects
     */
    public List<Software> searchDateRange(List<Software> results, ArrayList<String> dates) {
        // will contain the narrowed down list of Software objects
        List<Software> list = new ArrayList<Software>();

        List<Software> purchased = new ArrayList<Software>();
        List<Software> expired = new ArrayList<Software>();

        try {

            if (dates.get(2).equals("")&& dates.get(3).equals("")) {
                // if only purchased dates are entered, expired should contain
                // all values in purchased
                purchased = getListByPurchaseRange(Date.valueOf(dates.get(0)),
                        Date.valueOf(dates.get(1)));
                expired = purchased;
                
            } else if (dates.get(0).equals("") && dates.get(1).equals("")) {
                // if only expired dates are entered, purchased should contain
                // all values in expired
                expired = getListByExpirationRange(Date.valueOf(dates.get(2)),
                        Date.valueOf(dates.get(3)));
                purchased = expired;
                
            } else {
                // otherwise, purchased should contain only dates within
                // purchased range and expired should only contain dates within
                // the expired range
                purchased = getListByPurchaseRange(Date.valueOf(dates.get(0)),
                        Date.valueOf(dates.get(1)));
                expired = getListByExpirationRange(Date.valueOf(dates.get(2)),
                        Date.valueOf(dates.get(3)));
            }

            // iterate over the results list and check if a piece of Software is
            // also in the purchased and expired lists. If it is, add it to the
            // narrowed down list, otherwise ignore it.
            for (Software s : results) {
                for (Software p : purchased) {
                    for (Software e : expired) {
                        if (s.equals(p) && s.equals(e) && p.equals(e)) {
                            list.add(e);
                        }
                    }
                }
            }
        } catch (BoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return list;

    }
}