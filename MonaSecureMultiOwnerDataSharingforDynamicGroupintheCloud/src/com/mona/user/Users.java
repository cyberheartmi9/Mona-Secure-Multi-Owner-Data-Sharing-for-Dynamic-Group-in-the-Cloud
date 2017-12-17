package com.mona.user;

import java.util.Date;

public class Users {

	private String loginid;
	private String password;
	private int userid;
	private int groupid;
	private String groupname;
	private String newgroupname;
	private String oldgroupname;
	private String groupsignature;
	private String logintype;
	private String userkey;
	private Date requestDate;

	public Users() {
		super();
	}

	public Users(String loginid, String password) {
		super();
		this.loginid = loginid;
		this.password = password;
	}

	public Users(String loginid, String password, String groupname) {
		super();
		this.loginid = loginid;
		this.password = password;
		this.groupname = groupname;
	}

	public Users(String loginid, String password, String userkey, int i) {
		super();
		this.loginid = loginid;
		this.password = password;
		this.userkey = userkey;
	}

	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

	public String getGroupsignature() {
		return groupsignature;
	}

	public void setGroupsignature(String groupsignature) {
		this.groupsignature = groupsignature;
	}

	public String getLogintype() {
		return logintype;
	}

	public void setLogintype(String logintype) {
		this.logintype = logintype;
	}

	public String getNewgroupname() {
		return newgroupname;
	}

	public void setNewgroupname(String newgroupname) {
		this.newgroupname = newgroupname;
	}

	public String getOldgroupname() {
		return oldgroupname;
	}

	public void setOldgroupname(String oldgroupname) {
		this.oldgroupname = oldgroupname;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public String getUserkey() {
		return userkey;
	}

	public void setUserkey(String userkey) {
		this.userkey = userkey;
	}

}
