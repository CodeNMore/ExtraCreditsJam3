package dev.codenmore.ecjam.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ObjectMap;

public class Assets {
	
	private static AssetManager manager = null;
	private static ObjectMap<String, TextureRegion> regions;
	private static BitmapFont font;
	
	private Assets() {}
	
	public static void init() {
		if(manager != null) 
			return;
		
		regions = new ObjectMap<String, TextureRegion>();
		font = new BitmapFont();
		
		manager = new AssetManager();
		manager.load("textures/pack.atlas", TextureAtlas.class);
	}
	
	public static TextureRegion getRegion(String name) {
		if(regions.containsKey(name))
			return regions.get(name);
		regions.put(name, manager.get("textures/pack.atlas", TextureAtlas.class).findRegion(name));
		return regions.get(name);
	}
	
	public static BitmapFont getFont() {
		return font;
	}
	
	public static boolean step() {
		if(manager.update()) 
			return true;
		return false;
	}
	
	public static float getProgress() {
		return manager.getProgress();
	}
	
	public static void dispose() {
		if(manager == null) 
			return;
		manager.dispose();
	}

}
