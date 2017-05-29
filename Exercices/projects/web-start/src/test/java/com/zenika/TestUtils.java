/**
 * 
 */
package com.zenika;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author acogoluegnes
 *
 */
public class TestUtils {

	public static int getAvailablePort() {
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(0);
			return ss.getLocalPort();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if(ss != null && !ss.isClosed()) {
				try {
					ss.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
	
}
