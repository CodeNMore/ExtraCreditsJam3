package dev.codenmore.ecjam.screens;

import javax.swing.JOptionPane;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonValue.PrettyPrintSettings;
import com.badlogic.gdx.utils.JsonValue.ValueType;
import com.badlogic.gdx.utils.JsonWriter.OutputType;

import dev.codenmore.ecjam.Game;
import dev.codenmore.ecjam.level.tile.Tile;
import dev.codenmore.ecjam.level.tile.TileFactory;
import dev.codenmore.ecjam.ui.ClickHandler;
import dev.codenmore.ecjam.ui.TextureButton;

public class LevelEditorScreen extends Screen {

	private int id;
	private int width, height;
	private int[][] tileIds;
	
	private float rtwidth = Game.WIDTH - 200, rtheight = Game.HEIGHT, riconsize = 32;
	private float rtsize = 8;
	private Array<TextureButton> buttons;
	
	private int curTileId = 0;
	
	public LevelEditorScreen() {
		id = Integer.parseInt(JOptionPane.showInputDialog("Level ID to load or create:"));
		buttons = new Array<TextureButton>();
		
		Tile[] allTiles = TileFactory.getAllTiles();
		for(int i = 0;i < allTiles.length;i++) {
			final int tid = allTiles[i].getId();
			buttons.add(new TextureButton(allTiles[i].getTexture(), rtwidth + 5, 
					5 + i * riconsize, riconsize, riconsize, new ClickHandler() {
				@Override
				public void onClick() {
					curTileId = tid;
				}}));
		}
		
		if(Gdx.files.internal("levels/" + id + ".txt").exists()) {
			// Load level
			fromJson(new JsonReader().parse(Gdx.files.internal("levels/" + id + ".txt")));
		}else {
			// New level
			width = Integer.parseInt(JOptionPane.showInputDialog("Enter width:"));
			height = Integer.parseInt(JOptionPane.showInputDialog("Enter height:"));
			tileIds = new int[width][height];
		}
		
		rtsize = (float) Math.min(rtwidth / width, rtheight / height);
	}
	
	@Override
	public void tick(float delta) {
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			// Save the level
			JsonValue json = toJson();
			PrettyPrintSettings ps = new PrettyPrintSettings();
			ps.outputType = OutputType.json;
			Gdx.files.absolute("C:\\Users\\Tony\\Desktop\\Workspaces\\GDX_Workspace\\ECJam3\\core\\assets\\levels\\" + id + ".txt")
				.writeString(json.prettyPrint(ps), false);
			System.out.println("level " + id + " saved");
			ScreenManager.disposeScreen();
		}
		
		if(Gdx.input.isButtonPressed(Buttons.RIGHT)) {
			// Check for option clicks
			Vector2 coords = unproject(Gdx.input.getX(), Gdx.input.getY());
			for(TextureButton b : buttons)
				if(b.getBounds().contains(coords))
					b.onClick();
		}else if(Gdx.input.isButtonPressed(Buttons.LEFT)) {
			// Draw on map
			Vector2 coords = unproject(Gdx.input.getX(), Gdx.input.getY());
			int tx = (int) (coords.x / rtsize);
			int ty = (int) (coords.y / rtsize);
			if(tx >= 0 && ty >= 0 && tx < width && ty < height) {
				tileIds[tx][ty] = curTileId;
			}
		}
	}
	
	@Override
	public void render() {
		super.render();
		batch.begin();
		
		for(TextureButton b : buttons)
			b.render(batch);
		
		for(int y = 0;y < height;y++) {
			for(int x = 0;x < width;x++) {
				TileFactory.getTile(tileIds[x][y]).render(batch, x * rtsize, y * rtsize, rtsize, rtsize);
			}
		}
		
		batch.end();
	}
	
	public JsonValue toJson() {
		JsonValue ret = new JsonValue(ValueType.object);
		ret.addChild("id", new JsonValue(id));
		ret.addChild("width", new JsonValue(width));
		ret.addChild("height", new JsonValue(height));
		
		JsonValue rows = new JsonValue(ValueType.array);
		for(int y = 0;y < height;y++) {
			JsonValue cols = new JsonValue(ValueType.array);
			for(int x = 0;x < width;x++) {
				cols.addChild(new JsonValue(tileIds[x][y]));
			}
			rows.addChild(cols);
		}
		ret.addChild("tileIds", rows);
		
		return ret;
	}
	
	public void fromJson(JsonValue json) {
		id = json.getInt("id");
		width = json.getInt("width");
		height = json.getInt("height");
		tileIds = new int[width][height];
		
		JsonValue rows = json.get("tileIds");
		for(int y = 0;y < height;y++) {
			JsonValue cols = rows.get(y);
			for(int x = 0;x < width;x++) {
				tileIds[x][y] = cols.getInt(x);
			}
		}
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}
	
}
