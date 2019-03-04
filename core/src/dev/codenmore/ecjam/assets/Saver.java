package dev.codenmore.ecjam.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Saver {
	
	private Saver() {}
	
	public static void completedLevel(int i) {
		Preferences prefs = Gdx.app.getPreferences("LifeRecyle");
		if(prefs.getInteger("level", 0) < i)
			prefs.putInteger("level", i);
		prefs.flush();
	}
	
	public static int getLatestCompleted() {
		Preferences prefs = Gdx.app.getPreferences("LifeRecyle");
		return prefs.getInteger("level", 0);
	}

}
