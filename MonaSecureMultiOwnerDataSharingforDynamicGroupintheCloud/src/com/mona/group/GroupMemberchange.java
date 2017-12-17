/*
 * GroupMemberchange.java
 *
 * Created on __DATE__, __TIME__
 */

package com.mona.group;

import java.io.File;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import org.springframework.beans.factory.BeanFactory;

import com.mona.dao.DataBaseDataSource;
import com.mona.dao.RegistrationDAO;
import com.mona.user.Users;

/**
 * 
 * @author __USER__
 */
public class GroupMemberchange extends javax.swing.JFrame {
	private FileSystemModel fileSystemModel;
	RegistrationDAO registrationDAO;
	Users users;

	/** Creates new form GroupMemberchange */
	public GroupMemberchange(Users users) {
		this.users = users;
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		jScrollPane1 = new javax.swing.JScrollPane();
		jTree1 = new javax.swing.JTree();
		jLabel1 = new javax.swing.JLabel();
		jComboBox1 = new javax.swing.JComboBox();
		jButton1 = new javax.swing.JButton();
		BeanFactory beanFactory = new DataBaseDataSource().getDataFactory();
		RegistrationDAO registrationDAO = (RegistrationDAO) beanFactory
				.getBean("registrationDAO");

		boolean b = registrationDAO.viewGroupMembers();
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("GroupMemberChange");
		fileSystemModel = new FileSystemModel(new File("groups"));
		jTree1 = new JTree(fileSystemModel);
		jTree1.setEditable(true);
		jTree1.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent event) {
				File file = (File) jTree1.getLastSelectedPathComponent();

			}
		});
		jScrollPane1.setViewportView(jTree1);

		jLabel1.setText("ListofGroups");
		BeanFactory factory = new DataBaseDataSource().getDataFactory();
		registrationDAO = (RegistrationDAO) factory.getBean("registrationDAO");
		jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(
				registrationDAO.viewgroupNames().split(",")));

		jButton1.setText("ChangeGroups");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout
				.setHorizontalGroup(layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								layout
										.createSequentialGroup()
										.addComponent(
												jScrollPane1,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												113,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addGap(
																				34,
																				34,
																				34)
																		.addComponent(
																				jLabel1)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				jComboBox1,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addGap(
																				65,
																				65,
																				65)
																		.addComponent(
																				jButton1)))
										.addContainerGap(117, Short.MAX_VALUE)));
		layout
				.setVerticalGroup(layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(jScrollPane1,
								javax.swing.GroupLayout.DEFAULT_SIZE, 300,
								Short.MAX_VALUE)
						.addGroup(
								layout
										.createSequentialGroup()
										.addGap(46, 46, 46)
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jLabel1)
														.addComponent(
																jComboBox1,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(26, 26, 26).addComponent(
												jButton1).addContainerGap(183,
												Short.MAX_VALUE)));

		pack();
	}// </editor-fold>

	// GEN-END:initComponents

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
		String groupname = jComboBox1.getSelectedItem().toString();
		if (!groupname.equals(users.getGroupname())) {
			BeanFactory beanFactory = new DataBaseDataSource().getDataFactory();
			RegistrationDAO registrationDAO = (RegistrationDAO) beanFactory
					.getBean("registrationDAO");
			users.setNewgroupname(groupname);
			System.out.println(groupname);
			boolean b = registrationDAO.usergroupcount(users);
			File f = new File("groups");
			boolean delete = f.delete();
			System.out.println("file deleted " + delete);
			new GroupMemberchange(users).setVisible(true);

		} else {

		}

	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new GroupMemberchange(new Users()).setVisible(true);
			}
		});
	}

	// GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JButton jButton1;
	private javax.swing.JComboBox jComboBox1;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTree jTree1;

	// End of variables declaration//GEN-END:variables

}