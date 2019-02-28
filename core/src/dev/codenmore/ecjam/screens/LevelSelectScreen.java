package dev.codenmore.ecjam.screens;

import javax.swing.JOptionPane;

import dev.codenmore.ecjam.editor.LevelEditorScreen;

public class LevelSelectScreen extends Screen {

	public LevelSelectScreen() {
		
	}
	
	@Override
	public void tick(float delta) {
//		ScreenManager.pushScreen(new GameScreen(0));
		String lvlId = JOptionPane.showInputDialog("Level ID to load or create:");
		ScreenManager.pushScreen(new LevelEditorScreen(Integer.parseInt(lvlId)));
	}
	
	@Override
	public void render() {
		super.render();
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}
	
}
