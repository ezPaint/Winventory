package com.simoncomputing.app.winventory.dao;

import java.util.List;
import java.util.Map;

import com.simoncomputing.app.winventory.domain.RefCondition;
import com.simoncomputing.app.winventory.util.DaoException;

public interface RefConditionDao { 

    public int create( RefCondition value ) throws DaoException;

    public int update( RefCondition value ) throws DaoException;

    public int delete( Map<String, Object> map ) throws DaoException;

    public RefCondition read( Map<String, Object> map ) throws DaoException;


    // PROTECTED CODE -->
    
    public List<RefCondition> getAll() throws DaoException;

}