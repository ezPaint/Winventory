package com.simoncomputing.app.winventory.bo;

import java.util.List;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.*;

import com.simoncomputing.app.winventory.dao.*;
import com.simoncomputing.app.winventory.domain.TeamToUser;
import com.simoncomputing.app.winventory.util.BoException;

public class TeamToUserBo {

    private static TeamToUserBo instance = new TeamToUserBo();

    public static TeamToUserBo getInstance() {
        return instance;
    } 

    private TeamToUserBo() {
    } 

    public int create( TeamToUser value ) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            TeamToUserDao mapper = session.getMapper( TeamToUserDao.class );
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

    public int update( TeamToUser value ) throws BoException {
        SqlSession session = null;
        int result = 0;

        try {
            session = SessionFactory.getSession();
            TeamToUserDao mapper = session.getMapper( TeamToUserDao.class );
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
            TeamToUserDao mapper = session.getMapper( TeamToUserDao.class );
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

    public TeamToUser read( Long key ) throws BoException {
        SqlSession session = null;
        TeamToUser result;
        String where = "KEY='" + key + "' ";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put( "where", where );

        try {
            session = SessionFactory.getSession();
            TeamToUserDao mapper = session.getMapper( TeamToUserDao.class );
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

    public List<TeamToUser> getListByUserId( Integer key ) throws BoException {
        SqlSession session = null;
        List<TeamToUser> list;

        try {
            session = SessionFactory.getSession();
            TeamToUserDao mapper = session.getMapper( TeamToUserDao.class );
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

    public List<TeamToUser> getListByTeamId( Integer key ) throws BoException {
        SqlSession session = null;
        List<TeamToUser> list;

        try {
            session = SessionFactory.getSession();
            TeamToUserDao mapper = session.getMapper( TeamToUserDao.class );
            list = mapper.getListByTeamId( key );
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

}