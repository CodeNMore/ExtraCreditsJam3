package dev.codenmore.ecjam.screens;

import java.util.Stack;

import com.badlogic.gdx.Gdx;

public class ScreenManager {
	
	private static Stack<Screen> screens = new Stack<Screen>();
	
	private ScreenManager() {}
	
	public static Screen getCurrentScreen() {
		if(screens.isEmpty()) return null;
		return screens.peek();
	}
	
	public static void swapScreen(Screen s) {
		if(getCurrentScreen() != null) {
			Screen sp = screens.pop();
			sp.hide();
			sp.dispose();
		}
		screens.push(s);
		s.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		s.show();
	}
	
	public static void pushScreen(Screen s) {
		if(getCurrentScreen() != null)
			getCurrentScreen().hide();
		screens.push(s);
		s.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		s.show();
	}
	
	public static void disposeScreen() {
		if(getCurrentScreen() != null) {
			Screen s = screens.pop();
			s.hide();
			s.dispose();
		}
		if(getCurrentScreen() != null) {
			getCurrentScreen().resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			getCurrentScreen().show();
		}
	}
	
	public static void disposeAllScreens() {
		while(getCurrentScreen() != null)
			disposeScreen();
	}

}
