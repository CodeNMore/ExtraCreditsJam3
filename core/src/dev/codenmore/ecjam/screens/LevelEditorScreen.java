package dev.codenmore.ecjam.screens;

import javax.swing.JOptionPane;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonValue.PrettyPrintSettings;
import com.badlogic.gdx.utils.JsonValue.ValueType;
import com.badlogic.gdx.utils.JsonWriter.OutputType;

import dev.codenmore.ecjam.Game;
import dev.codenmore.ecjam.assets.Assets;
import dev.codenmore.ecjam.entities.Entity;
import dev.codenmore.ecjam.entities.EntityFactory;
import dev.codenmore.ecjam.level.tile.Tile;
import dev.codenmore.ecjam.level.tile.TileFactory;
import dev.codenmore.ecjam.ui.ClickHandler;
import dev.codenmore.ecjam.ui.TextButton;
import dev.codenmore.ecjam.ui.TextureButton;

public class LevelEditorScreen extends Screen {

	private int id;
	private int width, height;
	private int[][] tileIds;
	
	private float rtwidth = Game.WIDTH - 200, rtheight = Game.HEIGHT, riconsize = 32;
	private float rtsize = 8;
	private Array<TextureButton> buttons;
	private Array<Entity> entities;
	
	private int curTileId = 0;
	private float entX, entY;
	private int spawnX, spawnY;
	private boolean needsRelease = false;
	
	public LevelEditorScreen() {
		id = Integer.parseInt(JOptionPane.showInputDialog("Level ID to load or create:"));
		buttons = new Array<TextureButton>();
		entities = new Array<Entity>();
		
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
		
		Array<String> allEntityNames = EntityFactory.getAllEntityNames();
		for(int i = 0;i < allEntityNames.size;i++) {
			final String name = allEntityNames.get(i);
			buttons.add(new TextButton(name, Assets.getRegion("pixel"), rtwidth + 40, 
					25 + i * riconsize, riconsize*4, riconsize/1.8f, false, Color.RED, new ClickHandler() {
				@Override
				public void onClick() {
					entities.add(EntityFactory.makeEntityEditor(name, entX * Tile.TILE_SIZE, entY * Tile.TILE_SIZE));
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
			for(int y = 0;y < height;y++) {
				for(int x = 0;x < width;x++) {
					if(x == 0 || x == width - 1 || y == 0 || y == height - 1)
						tileIds[x][y] = 1;
					else
						tileIds[x][y] = 0;
				}
			}
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
		}else if(Gdx.input.isKeyJustPressed(Keys.BACKSPACE) || Gdx.input.isKeyJustPressed(Keys.DEL)) {
			for(Entity e : entities) {
				if(e.getX() == entX * Tile.TILE_SIZE && e.getY() == entY * Tile.TILE_SIZE) {
					entities.removeValue(e, true);
					break;
				}
			}
		}else if(Gdx.input.isKeyJustPressed(Keys.S)) {
			spawnX = (int) entX;
			spawnY = (int) entY;
		}else if(Gdx.input.isKeyJustPressed(Keys.R)) {
			int nw = (int) entX;
			int nh = (int) entY;
			int[][] nt = new int[nw + 1][nh + 1];
			for(int y = 0;y < nh;y++) {
				for(int x = 0;x < nw;x++) {
					nt[x][y] = tileIds[x][y];
				}
			}
			tileIds = nt;
			width = nw;
			height = nh;
		}
		
		if(Gdx.input.isButtonPressed(Buttons.RIGHT)) {
			// Check for option clicks
			Vector2 coords = unproject(Gdx.input.getX(), Gdx.input.getY());
			int tx = (int) (coords.x / rtsize);
			int ty = (int) (coords.y / rtsize);
			if(tx >= 0 && ty >= 0 && tx < width && ty < height) {
				entX = tx;
				entY = ty;
			}else if(!needsRelease){
				for(TextureButton b : buttons)
					if(b.getBounds().contains(coords))
						b.onClick();
				needsRelease = true;
			}
		}else if(Gdx.input.isButtonPressed(Buttons.LEFT)) {
			// Draw on map
			Vector2 coords = unproject(Gdx.input.getX(), Gdx.input.getY());
			int tx = (int) (coords.x / rtsize);
			int ty = (int) (coords.y / rtsize);
			if(tx >= 0 && ty >= 0 && tx < width && ty < height) {
				tileIds[tx][ty] = curTileId;
			}else {
				for(TextureButton b : buttons)
					if(b.getBounds().contains(coords))
						b.onClick();
			}
		}else {
			needsRelease = false;
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
		
		batch.setColor(Color.GREEN);
		for(Entity e : entities) {
			batch.draw(Assets.getRegion("pixel"), (e.getX() / Tile.TILE_SIZE) * rtsize + rtsize/3,
					(e.getY() / Tile.TILE_SIZE) * rtsize + rtsize/3, rtsize/3, rtsize/3);
		}
		
		batch.setColor(Color.RED);
		batch.draw(Assets.getRegion("pixel"), entX * rtsize + rtsize / 2, entY * rtsize + rtsize / 2, 
				rtsize / 3, rtsize / 3);
		batch.setColor(Color.YELLOW);
		batch.draw(Assets.getRegion("pixel"), spawnX * rtsize + 1, spawnY * rtsize + 1, 
				rtsize / 4, rtsize / 4);
		batch.setColor(Color.WHITE);
		
		batch.end();
	}
	
	public JsonValue toJson() {
		JsonValue ret = new JsonValue(ValueType.object);
		ret.addChild("id", new JsonValue(id));
		ret.addChild("width", new JsonValue(width));
		ret.addChild("height", new JsonValue(height));
		ret.addChild("spawnX", new JsonValue(spawnX));
		ret.addChild("spawnY", new JsonValue(spawnY));
		ret.addChild("lives", new JsonValue(2));
		
		JsonValue rows = new JsonValue(ValueType.array);
		for(int y = 0;y < height;y++) {
			JsonValue cols = new JsonValue(ValueType.array);
			for(int x = 0;x < width;x++) {
				cols.addChild(new JsonValue(tileIds[x][y]));
			}
			rows.addChild(cols);
		}
		ret.addChild("tileIds", rows);
		
		JsonValue ents = new JsonValue(ValueType.array);
		for(Entity e : entities)
			ents.addChild(e.toJson());
		ret.addChild("entities", ents);
		
		return ret;
	}
	
	public void fromJson(JsonValue json) {
		id = json.getInt("id");
		width = json.getInt("width");
		height = json.getInt("height");
		spawnX = json.getInt("spawnX");
		spawnY = json.getInt("spawnY");
		tileIds = new int[width][height];
		
		JsonValue rows = json.get("tileIds");
		for(int y = 0;y < height;y++) {
			JsonValue cols = rows.get(y);
			for(int x = 0;x < width;x++) {
				tileIds[x][y] = cols.getInt(x);
			}
		}
		
		entities.clear();
		JsonValue ents = json.get("entities");
		for(int i = 0;i < ents.size;i++) {
			JsonValue ent = ents.get(i);
			Entity e = EntityFactory.makeEntity(ent.getString("name"), ent);
			entities.add(e);
		}
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}
	
}
