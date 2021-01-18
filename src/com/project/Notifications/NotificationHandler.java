package com.project.Notifications;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;

public class NotificationHandler {

	private SystemTray tray;
	private Image image;
	private TrayIcon trayIcon;
	
	public NotificationHandler() {
		tray = SystemTray.getSystemTray();
		// Create an icon to use
		image = Toolkit.getDefaultToolkit().createImage("icon.png");
        trayIcon = new TrayIcon(image, "Chess Game");
        trayIcon.setImageAutoSize(true);
        
        trayIcon.setToolTip("Chess Game");
        try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendNotificationMessage(String title, String message) {
        trayIcon.displayMessage(title, message, MessageType.INFO);
	}
}