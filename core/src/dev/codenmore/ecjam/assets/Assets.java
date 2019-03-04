package dev.codenmore.ecjam.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ObjectMap;

public class Assets {
	
	private static AssetManager manager = null;
	private static ObjectMap<String, TextureRegion> regions;
	private static BitmapFont font = null;
	
	private Assets() {}
	
	public static void init() {
		if(manager != null) 
			return;
		
		regions = new ObjectMap<String, TextureRegion>();
		
		manager = new AssetManager();
		manager.load("textures/pack.atlas", TextureAtlas.class);
		manager.load("fatpixel.fnt", BitmapFont.class);
	}
	
	public static TextureRegion getRegion(String name) {
		if(regions.containsKey(name))
			return regions.get(name);
		regions.put(name, manager.get("textures/pack.atlas", TextureAtlas.class).findRegion(name));
		return regions.get(name);
	}
	
	public static BitmapFont getFontMicro(Color c) {
		font.getData().setScale(0.36f);
		font.setColor(c);
		return font;
	}
	
	public static BitmapFont getFontSm(Color c) {
		font.getData().setScale(0.4f);
		font.setColor(c);
		return font;
	}
	
	public static BitmapFont getFontMd(Color c) {
		font.getData().setScale(0.75f);
		font.setColor(c);
		return font;
	}
	
	public static BitmapFont getFontLg(Color c) {
		font.getData().setScale(1.0f);
		font.setColor(c);
		return font;
	}
	
	public static boolean step() {
		if(manager.update()) {
			if(font == null)
				font = manager.get("fatpixel.fnt", BitmapFont.class);
			return true;
		}
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
