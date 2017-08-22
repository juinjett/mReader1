package com.mmclub.mreader;

import android.app.Application;

public class MyApplication extends Application {

	// Exit Program

	private static boolean isProgramExit = false;

	public void setExit(boolean exit) {
		isProgramExit= exit;
	}

	public boolean isExit() {
		return isProgramExit;
	}

}