package com.benblamey.core;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SystemInfo {
	
	public static boolean IsWindowsSystem() {
		return System.getProperty("os.name").startsWith("Windows");
	}
	
	public static boolean IsLinuxSystem() {
		return System.getProperty("os.name").startsWith("Linux");
	}

	private static BAGServer _env; 

	/**
	 * Detects the current server - the apps etc. to use are determined by the combination of the SERVER and the identity of the CALLING CODE.
	 * @return
	 */
	public static synchronized BAGServer detectServer() {
		if (_env == null) {
			
			if (IsWindowsSystem()) {
				
				// Impersonate the Live Server.
				_env = BAGServer.LOCAL_MACHINE;
			} else if (IsLinuxSystem()) {
                            
                            try {
                                String dnsName = InetAddress.getLocalHost().getHostName();
                                if (dnsName.equals("localhost") || dnsName.equals("localhost.localdomain")) {
                                    _env = BAGServer.BENBLAMEY_OVH_STAGING;
                                } else if (dnsName.equals("vps116000.ovh.net")) {
                                    _env = BAGServer.BENBLAMEY_OVH;
                                } else {
                                    throw new RuntimeException("Unknown server: " + dnsName);
                                }
                            } catch (UnknownHostException ex) {
                                throw new RuntimeException(ex);
                            }
				
			} else {
				throw new IllegalStateException();
			}
			
		}
		
		return _env;
	}
	
	public static boolean doesServerSendEmail() {
		
		BAGServer server = detectServer();
		
		switch (server) {
                        case BENBLAMEY_OVH: 
				return true;
                        case BENBLAMEY_OVH_STAGING:
			case LOCAL_MACHINE:
				return false;
			default:
				throw new RuntimeException("Server not known:" + server.name());
		}
	}
	
	public static String getEmailDomainName() {
		BAGServer server = detectServer();
		
		switch (server) {
                        case BENBLAMEY_OVH:
				return "benblamey.com";
			case LOCAL_MACHINE:
				return "localhost.com";
			default:
				throw new RuntimeException("Server not known:" + server.name());
		}
	}
	
	public static String getWebDomainName() {
		BAGServer server = detectServer();
		
		switch (server) {
                        case BENBLAMEY_OVH:
				return "www.benblamey.com";
			case LOCAL_MACHINE:
				return "localhost.com";
			default:
				throw new RuntimeException("Server not known:" + server.name());
		}
	}
	
	public static int getPublicHTTPPort() {
		BAGServer server = detectServer();
		
		switch (server) {
                        case BENBLAMEY_OVH:
			case LOCAL_MACHINE:
                        case BENBLAMEY_OVH_STAGING:
				return 8080;
			default:
				throw new RuntimeException("Server not known:" + server.name());
		}		
	}
	
	public enum BAGServer {
                BENBLAMEY_OVH,
                BENBLAMEY_OVH_STAGING,
		LOCAL_MACHINE
                
	}
	
}
