package com.simoncomputing.app.winventory.dao;

import java.util.List;
import java.util.Map;

import com.simoncomputing.app.winventory.domain.Team;
import com.simoncomputing.app.winventory.util.DaoException;

public interface TeamDao { 

    public int create( Team value ) throws DaoException;

    public int update( Team value ) throws DaoException;

    public int delete( Map<String, Object> map ) throws DaoException;

    public Team read( Map<String, Object> map ) throws DaoException;

    public List<Team> getListByLeaderId( Integer key ) throws DaoException;

    // PROTECTED CODE -->

}