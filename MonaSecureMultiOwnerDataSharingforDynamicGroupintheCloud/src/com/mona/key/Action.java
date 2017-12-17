package com.mona.key;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

import javax.swing.JTextArea;



public class Action {

	public String getSource() {
		// TODO Auto-generated method stub
		return new SourceAndPort().getSource();
	}

	public int getPort() {
		// TODO Auto-generated method stub
		return new SourceAndPort().getPort();
	}

	public void setProperty(String file, String source, String text) {
		// TODO Auto-generated method stub
		try {
			Properties properties = new Properties();
			FileOutputStream fos = new FileOutputStream(file, true);
			properties.setProperty(source, text);
			properties.store(fos, source);
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void routing(String sign,int REQ_NO,MulticastRx mrx, Vector<String> path, String dest) {
		// TODO Auto-generated method stub

		try {
			Set<String> set = mrx.hm.keySet();
			Iterator<String> it = set.iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				String[] data = key.split(",");
				String nnode = data[0];
				String nsys = data[2];
				int nPort = Integer.parseInt(data[3]);
				if (getAvailable(path, nnode)) {
					Socket socket = new Socket(nsys, nPort);
					ObjectOutputStream oos = new ObjectOutputStream(socket
							.getOutputStream());
					oos.writeObject("RREQ");
					oos.writeObject(path);
					oos.writeObject(dest);
					oos.writeObject(REQ_NO);
					oos.writeObject(sign);
				}

			}

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	Vector<String> neigh = new Vector<String>();
	public void sendRREQ(String sign,int REQ_NO, Vector<String> path, String dest) {
		// TODO Auto-generated method stub
		
		try {
			for (int i = 0; i < neigh.size(); i++) {
				String nei=neigh.get(i);
				if (getAvailable(path,nei)) {
					int nPort = getPort(nei);
					Socket socket = new Socket("localhost", nPort);
					ObjectOutputStream oos = new ObjectOutputStream(socket
							.getOutputStream());
					oos.writeObject("RREQ");
					oos.writeObject(path);
					oos.writeObject(dest);
					oos.writeObject(REQ_NO);
					oos.writeObject(sign);
				}
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean getAvailable(Vector<String> path, String nei) {
		// TODO Auto-generated method stub
		return !path.contains(nei);
	}

	public int getPort(String nei) {
		// TODO Auto-generated method stub
		int mydis=0;
		try {
			Properties properties = new Properties();
			FileInputStream fis = new FileInputStream("Ports.properties");
			properties.load(fis);
			fis.close();
			mydis = Integer.parseInt(properties.getProperty(nei));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mydis;
	}
	public String getProperty(String file,String key) {
		// TODO Auto-generated method stub
		String value="";
		try {
			Properties properties = new Properties();
			FileInputStream fis = new FileInputStream(file);
			properties.load(fis);
			fis.close();
			value = properties.getProperty(key);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	public void sendData(String dest, Integer p, int[] endata) {
		// TODO Auto-generated method stub
		try {
			Socket socket = new Socket(dest, p);
			ObjectOutputStream oos = new ObjectOutputStream(socket
					.getOutputStream());
			oos.writeObject("Data");
			oos.writeObject(endata);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getDistance() {
		// TODO Auto-generated method stub
		return new SourceAndPort().getDistance();
	}

	public String getLocalHost() {
		// TODO Auto-generated method stub
		String addr="";
		try {
			addr= InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return addr;
	}

	public void sendMACT(String sign, int request_number, int port, String source,
			String dest, Integer p) {
		// TODO Auto-generated method stub
		try {
			Socket socket = new Socket(dest, p);
			ObjectOutputStream oos = new ObjectOutputStream(socket
					.getOutputStream());
			oos.writeObject("MACT");
			oos.writeObject(sign);
			oos.writeObject(request_number);
			oos.writeObject(source);
			oos.writeObject(port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public String getSystem() {
		// TODO Auto-generated method stub
		String str="";
		try {
			str=InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
	
}
