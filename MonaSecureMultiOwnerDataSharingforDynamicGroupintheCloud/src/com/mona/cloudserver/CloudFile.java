package com.mona.cloudserver;

import java.io.File;

public class CloudFile {

	private int filedataid;
	private String groupname;
	private String loginid;
	private String groupsignature;
	private String changegroupsignature;
	private String pubKey;
	private String filename;
	private String filetype;
	private String timestamp;
	private String revocation;
	private String autheriseduser;
	private	File encryptfiledata;
	
	
	
	public File getEncryptfiledata() {
		return encryptfiledata;
	}

	public void setEncryptfiledata(File encryptfiledata) {
		this.encryptfiledata = encryptfiledata;
	}

	public int getFiledataid() {
		return filedataid;
	}

	public void setFiledataid(int filedataid) {
		this.filedataid = filedataid;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public String getGroupsignature() {
		return groupsignature;
	}

	public void setGroupsignature(String groupsignature) {
		this.groupsignature = groupsignature;
	}

	public String getPubKey() {
		return pubKey;
	}

	public void setPubKey(String pubKey) {
		this.pubKey = pubKey;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFiletype() {
		return filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getRevocation() {
		return revocation;
	}

	public void setRevocation(String revocation) {
		this.revocation = revocation;
	}

	public String getAutheriseduser() {
		return autheriseduser;
	}

	public void setAutheriseduser(String autheriseduser) {
		this.autheriseduser = autheriseduser;
	}

	public String getChangegroupsignature() {
		return changegroupsignature;
	}

	public void setChangegroupsignature(String changegroupsignature) {
		this.changegroupsignature = changegroupsignature;
	}

}
