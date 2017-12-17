package com.mona.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.X509Certificate;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.StringTokenizer;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.mona.cloudserver.CloudFile;
import com.mona.encryption.CipherExample;
import com.mona.key.X509V1Certificate;
import com.mona.user.Users;

public class RegistrationDAO {

	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public boolean registerNewUser(Users users) {

		int a[] = null;

		String countuserssql = "select count(*)-1 from userlogin";
		Integer countusers = jdbcTemplate.query(countuserssql,
				new ResultSetExtractor<Integer>() {

					public Integer extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						int i = 0;
						if (rs.next()) {
							i = rs.getInt(1);
						}
						return i;
					}

				});

		String maxuseridsql = "select nvl(max(userid),100)+1 from userlogin";
		int maxuserid = jdbcTemplate.query(maxuseridsql,
				new ResultSetExtractor<Integer>() {

					public Integer extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						int i = 0;
						if (rs.next()) {
							i = rs.getInt(1);
						}
						return i;
					}

				});

		String maxgroupidsql = "select nvl(max(GROUPID),0)+1 from groups";
		Integer maxgroupid = jdbcTemplate.query(maxgroupidsql,
				new ResultSetExtractor<Integer>() {

					public Integer extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						int i = 0;
						if (rs.next()) {
							i = rs.getInt(1);
						}
						return i;
					}

				});
		String maxgroupuseridsql = "select nvl(max(GROUPUSERID),0)+1 from groupusers";
		Integer maxgroupuserid = jdbcTemplate.query(maxgroupuseridsql,
				new ResultSetExtractor<Integer>() {

					public Integer extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						int i = 0;
						if (rs.next()) {
							i = rs.getInt(1);
						}
						return i;
					}

				});
		X509V1Certificate certificate = certificate = new X509V1Certificate();
		X509Certificate cert = certificate.getCertificate();
		String groupsql = "";
		String groupsqluser = "";
		String sql = "";

		if ((countusers % 3) == 0) {
			sql = "insert into userlogin  values(" + maxuserid + ",'"
					+ users.getLoginid() + "','" + users.getPassword()
					+ "',sysdate,'active','owner','" + users.getUserkey()
					+ "')";

			try {
				String[] s = String.valueOf(cert).split("Signature:");
				groupsql = "insert into groups values(" + maxgroupid
						+ ",'group" + maxgroupid + "',1,'" + s[1].trim()
						+ "','active',sysdate)";
				groupsqluser = "insert into groupusers values("
						+ maxgroupuserid + "," + maxgroupid + "," + maxuserid
						+ ")";
				a = jdbcTemplate.batchUpdate(new String[] { sql, groupsql,
						groupsqluser });
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			sql = "insert into userlogin  values(" + maxuserid + ",'"
					+ users.getLoginid() + "','" + users.getPassword()
					+ "',sysdate,'active','user','" + users.getUserkey() + "')";
			groupsqluser = "insert into groupusers values(" + maxgroupuserid
					+ "," + (maxgroupid - 1) + "," + maxuserid + ")";
			String groupusercountupdate = "update GROUPS set GROUPMEMBERCOUNT=(select GROUPMEMBERCOUNT+1 from GROUPS where groupid="
					+ (maxgroupid - 1) + ") where groupid=" + (maxgroupid - 1);
			a = jdbcTemplate.batchUpdate(new String[] { sql, groupsqluser,
					groupusercountupdate });
		}
		return (a.length == 3 ? true : false);

	}

	public String viewgroupNames() {
		return jdbcTemplate.query("select * from groups order by groupid",
				new ResultSetExtractor<String>() {
					String s = "manager,";

					public String extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						while (rs.next()) {
							s = s + rs.getString(2) + ",";
						}
						return s;
					}
				});

	}

	public String viewGroups() {
		return jdbcTemplate.query("select * from groups order by groupid",
				new ResultSetExtractor<String>() {
					String s = "";

					public String extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						while (rs.next()) {
							s = s + rs.getString(2) + ",";
						}
						return s;
					}
				});

	}

	public String viewUserNames() {
		return jdbcTemplate
				.query(
						"select loginid from USERLOGIN where logintype='user' or logintype='owner' order by userid",
						new ResultSetExtractor<String>() {
							String s = "";

							public String extractData(ResultSet rs)
									throws SQLException, DataAccessException {
								while (rs.next()) {
									s = s + rs.getString(1) + ",";
								}
								return s;
							}
						});

	}

	public String viewFileName() {
		return jdbcTemplate.query(
				"select * from CLOUDFILEDATA order by FILEDATAID",
				new ResultSetExtractor<String>() {
					String s = "";

					public String extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						while (rs.next()) {
							s = s + rs.getString(5) + ",";
						}
						return s;
					}
				});

	}

	public String viewFileName(int userid) {
		return jdbcTemplate
				.query(
						"select * from CLOUDFILEDATA where groupname=(select (select groupname from groups where groupid=groupusers.groupid) from groupusers where userid="
								+ userid + ") order by FILEDATAID",
						new ResultSetExtractor<String>() {
							String s = "";

							public String extractData(ResultSet rs)
									throws SQLException, DataAccessException {
								while (rs.next()) {
									s = s + rs.getString(5) + ",";
								}
								return s;
							}
						});

	}

	public String viewFileKey(String filename) {
		return jdbcTemplate.query(
				"select PUBLICKEY from CLOUDFILEDATA where filename='"
						+ filename.trim() + "'",
				new ResultSetExtractor<String>() {
					String s = "";

					public String extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						while (rs.next()) {
							s = s + rs.getString(1) + ",";
						}
						return s;
					}
				});

	}

	public String viewFileSignature(String groupname) {
		return jdbcTemplate.query(
				"select GROUPSIGNATURE from GROUPS where GROUPNAME='"
						+ groupname.trim() + "'",
				new ResultSetExtractor<String>() {
					String s = "";

					public String extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						while (rs.next()) {
							s = s + rs.getString(1) + ",";
						}
						return s;
					}
				});

	}

	public boolean updateRevocation(String filename) {
		String newusersql = "update CLOUDFILEDATA set REVOCATION='yes' where filename='"
				+ filename.trim() + "'";

		return jdbcTemplate.execute(newusersql,
				new PreparedStatementCallback<Boolean>() {

					@Override
					public Boolean doInPreparedStatement(PreparedStatement ps)
							throws SQLException, DataAccessException {

						return ps.execute();
					}

				});

	}

	public Users viewGroupname(final Users users) {
		return jdbcTemplate
				.query(
						"select userid,(select (select groupname from groups where groupid=GROUPUSERS.groupid) from GROUPUSERS where userid=userlogin.userid) from userlogin where loginid='"
								+ users.getLoginid() + "'",
						new ResultSetExtractor<Users>() {

							public Users extractData(ResultSet rs)
									throws SQLException, DataAccessException {
								if (rs.next()) {
									users.setGroupname(rs.getString(2));
									users.setUserid(rs.getInt(1));

								}
								return users;
							}
						});

	}

	public Users loginCheck(Users users) {

		return jdbcTemplate
				.query(
						"select userid,loginid,logintype,(select groupid from groupusers where userid=userlogin.userid),(select groupname from groups where groupid=(select groupid from groupusers where userid=userlogin.userid)),(select groupsignature from groups where groupid=(select groupid from groupusers where userid=userlogin.userid)),userkey from userlogin,groups where loginid='"
								+ users.getLoginid()
								+ "' and password='"
								+ users.getPassword()
								+ "'and (select groupname from groups where groupid=(select groupid from groupusers where userid=userlogin.userid))='"
								+ users.getGroupname() + "'",
						new ResultSetExtractor<Users>() {

							public Users extractData(ResultSet rs)
									throws SQLException, DataAccessException {
								Users user = new Users();
								if (rs.next()) {

									user.setUserid(rs.getInt(1));

									user.setLoginid(rs.getString(2));

									user.setLogintype(rs.getString(3));

									user.setGroupid(rs.getInt(4));
									user.setGroupname(rs.getString(5));
									user.setGroupsignature(rs.getString(6));
									user.setUserkey(rs.getString(7));

								}
								return user;
							}
						});
	}

	public Users managerLoginCheck(Users users) {

		return jdbcTemplate.query("select * from userlogin where loginid='"
				+ users.getLoginid() + "' and password='" + users.getPassword()
				+ "' and logintype='manager'", new ResultSetExtractor<Users>() {

			public Users extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				Users user = new Users();
				if (rs.next()) {
					user.setUserid(rs.getInt(1));
					user.setLoginid(rs.getString(2));
					user.setLogintype(rs.getString(3));
				}
				return user;
			}
		});
	}

	public Boolean uploadCloudFile(final CloudFile cloudFile) {

		StringTokenizer stringTokenizer1 = new StringTokenizer(cloudFile
				.getGroupsignature().trim());
		StringTokenizer stringTokenizer2 = new StringTokenizer(cloudFile
				.getChangegroupsignature().trim());
		String s1 = "";
		while (stringTokenizer1.hasMoreElements()) {
			s1 = s1 + stringTokenizer1.nextElement();
		}
		String s2 = "";
		while (stringTokenizer2.hasMoreElements()) {
			s2 = s2 + stringTokenizer2.nextElement();
		}

		final String signature;
		String cloudfilesql = "";
		if (s1.equals(s2)) {
			cloudfilesql = "insert into CLOUDFILEDATA values((select nvl(max(FILEDATAID),0)+1 from CLOUDFILEDATA),?,?,?,?,?,?,?,sysdate,'no','yes')";
			signature = cloudFile.getGroupsignature();
		} else {
			cloudfilesql = "insert into CLOUDFILEDATA values((select nvl(max(FILEDATAID),0)+1 from CLOUDFILEDATA),?,?,?,?,?,?,?,sysdate,'no','no')";
			signature = cloudFile.getChangegroupsignature();
		}
		return jdbcTemplate.execute(cloudfilesql,
				new PreparedStatementCallback<Boolean>() {

					@Override
					public Boolean doInPreparedStatement(PreparedStatement ps)
							throws SQLException, DataAccessException {

						ps.setString(1, cloudFile.getGroupname());
						ps.setString(2, cloudFile.getLoginid());

						ps.setString(3, signature);
						ps.setString(4, cloudFile.getFilename());
						ps.setString(5, cloudFile.getPubKey());

						FileInputStream fisfile;
						try {
							fisfile = new FileInputStream(cloudFile
									.getEncryptfiledata());

							ps.setBinaryStream(6, fisfile, (int) cloudFile
									.getEncryptfiledata().length());
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						ps.setString(7, cloudFile.getFiletype());

						return ps.execute();
					}

				});
	}

	public Object[][] viewCloudFiles() {
		return jdbcTemplate.query(
				"select * from CLOUDFILEDATA order by FILEDATAID",
				new ResultSetExtractor<Object[][]>() {
					String s = "{";

					public Object[][] extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						System.out.println(rs.getFetchSize());

						Object obj[][] = new Object[100][9];
						int i = 0;
						while (rs.next()) {

							obj[i][0] = String.valueOf(rs.getInt(1));
							obj[i][1] = rs.getString(2);
							obj[i][2] = rs.getString(3);
							obj[i][3] = rs.getString(4);
							obj[i][4] = rs.getString(5);
							obj[i][5] = rs.getString(6);
							obj[i][6] = String.valueOf(rs.getDate(9));
							obj[i][7] = rs.getString(10);
							obj[i][8] = rs.getString(11);

							/*
							 * s = new String[] { String.valueOf(rs.getInt(1)),
							 * rs.getString(2), rs.getString(3),
							 * rs.getString(4), rs.getString(5),
							 * rs.getString(6), String.valueOf(rs.getDate(9)),
							 * rs.getString(10), rs.getString(11) };
							 */
							i++;
						}

						return obj;
					}
				});

	}

	public Object[][] viewCloudFiles(int userid) {
		return jdbcTemplate
				.query(
						"select * from CLOUDFILEDATA where groupname=(select (select groupname from groups where groupid=groupusers.groupid) from groupusers where userid="
								+ userid + ") order by FILEDATAID",
						new ResultSetExtractor<Object[][]>() {
							String s = "{";

							public Object[][] extractData(ResultSet rs)
									throws SQLException, DataAccessException {
								System.out.println(rs.getFetchSize());

								Object obj[][] = new Object[100][9];
								int i = 0;
								while (rs.next()) {

									obj[i][0] = String.valueOf(rs.getInt(1));
									obj[i][1] = rs.getString(2);
									obj[i][2] = rs.getString(3);
									obj[i][3] = rs.getString(4);
									obj[i][4] = rs.getString(5);
									obj[i][5] = rs.getString(6);
									obj[i][6] = String.valueOf(rs.getDate(9));
									obj[i][7] = rs.getString(10);
									obj[i][8] = rs.getString(11);

									/*
									 * s = new String[] {
									 * String.valueOf(rs.getInt(1)),
									 * rs.getString(2), rs.getString(3),
									 * rs.getString(4), rs.getString(5),
									 * rs.getString(6),
									 * String.valueOf(rs.getDate(9)),
									 * rs.getString(10), rs.getString(11) };
									 */
									i++;
								}

								return obj;
							}
						});

	}

	public Object[][] viewAllCloudUsers() {
		return jdbcTemplate
				.query(
						"select userlogin.*,(select (select groupname from groups where groupid=GROUPUSERS.groupid) from GROUPUSERS where userid=userlogin.userid) from userlogin order by userid",
						new ResultSetExtractor<Object[][]>() {
							String s = "{";

							public Object[][] extractData(ResultSet rs)
									throws SQLException, DataAccessException {
								System.out.println(rs.getFetchSize());

								Object obj[][] = new Object[100][6];
								int i = 0;
								while (rs.next()) {

									obj[i][0] = String.valueOf(rs.getInt(1));
									obj[i][1] = rs.getString(2);
									obj[i][2] = String.valueOf(rs.getDate(4));
									obj[i][3] = rs.getString(5);
									obj[i][4] = rs.getString(6);
									obj[i][5] = rs.getString(7);

									i++;
								}

								return obj;
							}
						});

	}

	public Object[] viewUserLoginMetaData() {
		return jdbcTemplate.query("select * from userlogin",
				new ResultSetExtractor<Object[]>() {
					public Object[] extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						ResultSetMetaData resultSetMetaData = rs.getMetaData();
						String tablemetadata[] = new String[] {
								resultSetMetaData.getColumnName(1),
								resultSetMetaData.getColumnName(2),
								resultSetMetaData.getColumnName(4),
								resultSetMetaData.getColumnName(5),
								resultSetMetaData.getColumnName(6),
								"GroupName", };

						return tablemetadata;
					}
				});
	}

	public Object[] viewCloudMetaData() {
		return jdbcTemplate.query(
				"select * from CLOUDFILEDATA order by FILEDATAID",
				new ResultSetExtractor<Object[]>() {
					public Object[] extractData(ResultSet rs)
							throws SQLException, DataAccessException {

						ResultSetMetaData resultSetMetaData = rs.getMetaData();

						String tablemetadata[] = new String[] {
								resultSetMetaData.getColumnName(1),
								resultSetMetaData.getColumnName(2),
								resultSetMetaData.getColumnName(3),
								resultSetMetaData.getColumnName(4),
								resultSetMetaData.getColumnName(5),
								resultSetMetaData.getColumnName(6),
								resultSetMetaData.getColumnName(9),
								resultSetMetaData.getColumnName(10),
								resultSetMetaData.getColumnName(11), };

						return tablemetadata;
					}
				});
	}

	public String downloadFile(final String filename, final String key)
			throws FileNotFoundException, IOException {
		return jdbcTemplate.query(
				"select * from CLOUDFILEDATA where filename='"
						+ filename.trim() + "'",
				new ResultSetExtractor<String>() {
					public String extractData(ResultSet rs)
							throws SQLException, DataAccessException {

						String path = new File(".").getAbsolutePath();
						boolean b = new File(path + "\\dec").mkdir();
						File file = new File(new File("dec") + "\\" + filename);
						FileOutputStream fileOutputStream = null;
						try {
							fileOutputStream = new FileOutputStream(file);
							byte[] buffer = new byte[1];
							if (rs.next()) {
								InputStream is = rs.getBinaryStream(7);
								while (is.read(buffer) > 0) {
									fileOutputStream.write(buffer);
								}
							}
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String decfile = null;
						try {
							decfile = new CipherExample().decrypt(key, file
									.toString());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return decfile;
					}
				});
	}

	public Boolean deleteFile(final String filename, final String key)
			throws FileNotFoundException, IOException {
		String cloudfilesql = "delete CLOUDFILEDATA where FILENAME=?";

		return jdbcTemplate.execute(cloudfilesql,
				new PreparedStatementCallback<Boolean>() {

					@Override
					public Boolean doInPreparedStatement(PreparedStatement ps)
							throws SQLException, DataAccessException {
						ps.setString(1, filename.trim());
						return ps.execute();
					}

				});
	}

	public Boolean viewGroupMembers() {
		return jdbcTemplate.query("select * from groups",
				new ResultSetExtractor<Boolean>() {
					public Boolean extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						boolean b = new File(new File(".").getAbsolutePath()
								+ "\\" + "groups").mkdir();
						while (rs.next()) {
							boolean path = new File(new File("groups")
									.getAbsolutePath()
									+ "\\" + rs.getString(2)).mkdir();
							try {
								createUserFloders(rs.getInt(1), new File(
										"groups\\" + rs.getString(2))
										.getAbsolutePath());
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
						return true;
					}
				});

	}

	public Boolean createUserFloders(int groupid, final String groupname) {

		return jdbcTemplate
				.query(
						"select (select USERLOGIN.loginid from USERLOGIN where userid=GROUPUSERS.userid) from GROUPUSERS where groupid="
								+ groupid, new ResultSetExtractor<Boolean>() {
							public Boolean extractData(ResultSet rs)
									throws SQLException, DataAccessException {
								while (rs.next()) {
									try {
										System.out.println("aaaaa"
												+ new File(groupname)
														.getAbsolutePath());
										boolean path = new File(new File(
												groupname).getAbsolutePath()
												+ "\\" + rs.getString(1))
												.mkdir();

										createCloudFile(rs.getString(1),
												new File(groupname)
														.getAbsolutePath()
														+ "\\"
														+ rs.getString(1));
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
								return true;
							}
						});

	}

	public Boolean createCloudFile(final String filename, final String filepath)
			throws FileNotFoundException, IOException {
		System.out.println(filename);
		System.out.println(filepath);

		return jdbcTemplate.query(
				"select * from CLOUDFILEDATA where username='"
						+ filename.trim() + "'",
				new ResultSetExtractor<Boolean>() {
					public Boolean extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						try {
							while (rs.next()) {

								File file = new File(filepath + "\\"
										+ rs.getString(5));
								FileOutputStream fileOutputStream = null;
								fileOutputStream = new FileOutputStream(file);
								byte[] buffer = new byte[1];
								InputStream is = rs.getBinaryStream(7);
								while (is.read(buffer) > 0) {
									fileOutputStream.write(buffer);
								}
							}
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						return true;
					}
				});
	}

	public Boolean usergroupcount(final Users users) {

		String countuserssql = "select GROUPMEMBERCOUNT from GROUPS where GROUPNAME='"
				+ users.getNewgroupname() + "'";
		final Boolean usercount = jdbcTemplate.query(countuserssql,
				new ResultSetExtractor<Boolean>() {
					boolean s;

					public Boolean extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						int i = 0;
						if (rs.next()) {
							System.out.println(rs.getInt(1));
							try {
								s = userChangeGroup(rs.getInt(1),
										viewGroupname(users));
							} catch (Exception e) {
								System.out.println(e);
							}
						}
						return s;
					}

				});
		return usercount;
	}

	public Integer groupMemberCount(String groupname) {

		String countuserssql = "select GROUPMEMBERCOUNT from GROUPS where GROUPNAME='"
				+ groupname + "'";
		final Integer usercount = jdbcTemplate.query(countuserssql,
				new ResultSetExtractor<Integer>() {
					int i = 0;

					public Integer extractData(ResultSet rs)
							throws SQLException, DataAccessException {

						if (rs.next()) {
							i = rs.getInt(1);

						}
						return i;

					}

				});
		return usercount;
	}

	public Boolean userChangeGroup(int usercount, Users users) {
		int a[] = null;
		if (usercount < 3) {
			String newgroup = "update GROUPS set GROUPMEMBERCOUNT=GROUPMEMBERCOUNT+1 where GROUPNAME='"
					+ users.getNewgroupname() + "'";

			String oldgroup = "update GROUPS set GROUPMEMBERCOUNT=GROUPMEMBERCOUNT-1 where GROUPNAME='"
					+ users.getGroupname() + "'";

			String groupusers = "update GROUPUSERS set GROUPID=(select GROUPID from GROUPS where GROUPNAME='"
					+ users.getNewgroupname()
					+ "') where USERID="
					+ users.getUserid();
			String cloudfiledata = "update CLOUDFILEDATA set GROUPNAME='"
					+ users.getNewgroupname()
					+ "',GROUPSIGNATURE=(select GROUPSIGNATURE from GROUPS where GROUPNAME='"
					+ users.getNewgroupname() + "') where USERNAME='"
					+ users.getLoginid() + "'";
			System.out.println(newgroup);
			System.out.println(oldgroup);
			System.out.println(groupusers);
			System.out.println(cloudfiledata);
			a = jdbcTemplate.batchUpdate(new String[] { newgroup, oldgroup,
					groupusers, cloudfiledata });
			System.out.println(a.length);
			return true;

		} else {
			return false;
		}

	}

	public Integer viewPraposedSignaturesCount() {
		return jdbcTemplate.query("select count(*) from  groups",
				new ResultSetExtractor<Integer>() {
					Integer s = 0;

					public Integer extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						if (rs.next()) {
							s = rs.getInt(1);
						}
						return s;
					}
				});
	}

	public Integer viewExistedSignaturesCount() {
		return jdbcTemplate.query("select GROUPMEMBERCOUNT from groups",
				new ResultSetExtractor<Integer>() {
					Integer s = 0;

					public Integer extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						while (rs.next()) {
							s = s + rs.getInt(1);
						}
						return s;
					}
				});
	}

	public Integer usercount() {
		return jdbcTemplate.query("select count(userid)-1 from userlogin",
				new ResultSetExtractor<Integer>() {
					Integer s = 0;

					public Integer extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						while (rs.next()) {
							s = s + rs.getInt(1);
						}
						return s;
					}
				});
	}

}
