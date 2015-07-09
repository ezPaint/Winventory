package com.simoncomputing.app.winventory.domain;


/**
* The Many<->Many Table describing relationship between users and teams.
*/
public class TeamToUser {

    private Long      key;
    private Integer   userId;
    private Integer   teamId;

    public Long      getKey() { return key; }
    public void      setKey( Long value ) { key = value; }
    public Integer   getUserId() { return userId; }
    public void      setUserId( Integer value ) { userId = value; }
    public Integer   getTeamId() { return teamId; }
    public void      setTeamId( Integer value ) { teamId = value; }
    // PROTECTED CODE -->

}