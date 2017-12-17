package com.mona.key;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class MulticastRx extends Thread {
	private InetAddress ia;
	public HashMap<String, Long> hm = new HashMap<String, Long>();
	int dis;

	private void availability() {
		// TODO Auto-generated method stub
		new Thread(this) {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				while (true) {
					try {
						Date date = new Date();
						long pre = date.getTime();
						Thread.sleep(1000);
						Set<String> set = hm.keySet();
						Iterator<String> it = set.iterator();
						while (it.hasNext()) {
							String key = (String) it.next();
							long time = hm.get(key);
							if (time < pre) {
								hm.remove(key);
								// refresh(hm);
								break;
							}
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.out.println(e.getMessage());
						continue;
					}
				}
			}

		}.start();
	}

}