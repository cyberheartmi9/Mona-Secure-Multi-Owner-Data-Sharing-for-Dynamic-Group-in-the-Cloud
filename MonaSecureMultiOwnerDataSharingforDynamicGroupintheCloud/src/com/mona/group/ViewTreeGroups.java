package com.mona.group;

import java.io.File;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class ViewTreeGroups extends JFrame {

	private JTree fileTree;
	private FileSystemModel fileSystemModel;
	private JTextArea fileDetailsTextArea = new JTextArea();

	public ViewTreeGroups(String directory) {
		//super("JTree FileSystem Viewer - JavaProgrammingForums.com");
		setTitle("GroupsTree Structure");
		fileDetailsTextArea.setEditable(false);
		fileSystemModel = new FileSystemModel(new File(directory));
		fileTree = new JTree(fileSystemModel);
		fileTree.setEditable(true);
		fileTree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent event) {
				File file = (File) fileTree.getLastSelectedPathComponent();
				fileDetailsTextArea.setText(getFileDetails(file));
			}
		});
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				true, new JScrollPane(fileTree), new JScrollPane(
						fileDetailsTextArea));
		getContentPane().add(splitPane);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(640, 480);
		setVisible(true);
	}

	private String getFileDetails(File file) {
		if (file == null)
			return "";
		StringBuffer buffer = new StringBuffer();
		buffer.append("Name: " + file.getName() + "\n");
		buffer.append("Path: " + file.getPath() + "\n");
		buffer.append("Size: " + file.length() + "\n");
		return buffer.toString();
	}

	public static void main(String args[]) {
		new ViewTreeGroups("groups");
	}
}
