package com.simoncomputing.app.winventory.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

import org.apache.ibatis.session.*;

import com.simoncomputing.app.winventory.dao.*;
import com.simoncomputing.app.winventory.domain.RefCondition;
import com.simoncomputing.app.winventory.util.BoException;

public class RefConditionBo {

    private static RefConditionBo instance = new RefConditionBo();

    public static RefConditionBo getInstance() {
        return instance;
    }

    private RefConditionBo() {
    }

    public int create(RefCondition value) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            RefConditionDao mapper = session.getMapper(RefConditionDao.class);
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

    public int update(RefCondition value) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            RefConditionDao mapper = session.getMapper(RefConditionDao.class);
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

    public int delete(String code) throws BoException {
        SqlSession session = null;
        int result = 0;
        String where = "CODE='" + code + "' ";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("where", where);

        try {
            session = SessionFactory.getSession();
            RefConditionDao mapper = session.getMapper(RefConditionDao.class);
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

    public RefCondition read(String code) throws BoException {
        SqlSession session = null;
        RefCondition result;
        String where = "CODE='" + code + "' ";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("where", where);

        try {
            session = SessionFactory.getSession();
            RefConditionDao mapper = session.getMapper(RefConditionDao.class);
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

    // PROTECTED CODE -->

    /**
     * Returns a {@link List} of all {@Link RefCondition} objects from
     * the database
     * 
     * @return A {@link List} of all {@Link RefCondition} objects
     * @throws BoException
     */
    public List<RefCondition> getAll() throws BoException {
        SqlSession session = null;
        List<RefCondition> list;

        try {
            session = SessionFactory.getSession();
            RefConditionDao mapper = session.getMapper(RefConditionDao.class);
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