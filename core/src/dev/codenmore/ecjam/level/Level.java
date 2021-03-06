package dev.codenmore.ecjam.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import dev.codenmore.ecjam.Game;
import dev.codenmore.ecjam.assets.Assets;
import dev.codenmore.ecjam.assets.Saver;
import dev.codenmore.ecjam.entities.EntityFactory;
import dev.codenmore.ecjam.entities.EntityManager;
import dev.codenmore.ecjam.entities.player.Player;
import dev.codenmore.ecjam.level.tile.Tile;
import dev.codenmore.ecjam.level.tile.TileFactory;
import dev.codenmore.ecjam.screens.EndScreen;
import dev.codenmore.ecjam.screens.GameScreen;
import dev.codenmore.ecjam.screens.ScreenManager;

public class Level {
	
	// Globals
	public static final float GRAVITY = 9f;
	
	// Display stuff
	private OrthographicCamera cam;
	private SpriteBatch batch;
	private Viewport viewport;
	
	private GameScreen gameScreen;
	private int id;
	private int spawnX, spawnY, lives;
	private int width, height;
	private int[][] tileIds;
	private boolean needsInstantCenter = true;
	
	private JsonValue originalJson;
	private EntityManager entityManager;

	public Level(GameScreen gameScreen, int levelId) {
		this.gameScreen = gameScreen;
		
		cam = new OrthographicCamera(Game.WIDTH, Game.HEIGHT);
		cam.setToOrtho(false, Game.WIDTH, Game.HEIGHT);
		viewport = new FitViewport(Game.WIDTH, Game.HEIGHT, cam);
		batch = new SpriteBatch();
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		entityManager = new EntityManager(this);
		
		originalJson = new JsonReader().parse(Gdx.files.internal("levels/" + levelId + ".txt"));
		fromJson(originalJson);
		
		entityManager.addEntity(new Player(spawnX * Tile.TILE_SIZE, spawnY * Tile.TILE_SIZE, lives));
	}
	
	public void tick(float delta) {
		// Tick all entities
		entityManager.tick(delta);
		
		// Instant-center
		if(needsInstantCenter) {
			centerOn(entityManager.getPlayer().getRenderBounds(), (Float.MAX_VALUE));
			needsInstantCenter = false;
		}
	}
	
	public void render() {
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		
		// Only render visible tiles
		int sy = Math.max(0, (int) ((cam.position.y - Game.HEIGHT / 2) / Tile.TILE_SIZE));
		int ey = Math.min(height, (int) ((cam.position.y + Game.HEIGHT / 2) / Tile.TILE_SIZE) + 1);
		int sx = Math.max(0, (int) ((cam.position.x - Game.WIDTH / 2) / Tile.TILE_SIZE));
		int ex = Math.min(width, (int)((cam.position.x + Game.WIDTH / 2) / Tile.TILE_SIZE) + 1);
		
		// Render them
		for(int y = sy;y < ey;y++) {
			for(int x = sx;x < ex;x++) {
				TileFactory.getTile(tileIds[x][y]).render(batch, x, y, this);
			}
		}
		
		entityManager.render(batch);
		
		batch.end();
	}
	
	public void complete() {
		Saver.completedLevel(id);
		Assets.playSound("unlocking");
		ScreenManager.swapScreen(new EndScreen(false));
	}
	
	public void die() {
		ScreenManager.swapScreen(new EndScreen(true));
	}
	
	public void centerOn(Rectangle bounds, float delta) {
		float dx = bounds.x + bounds.width / 2;
		float dy = bounds.y + bounds.height / 2;
		float mult = Math.min(6 * delta, 1f);
		cam.position.add((dx - cam.position.x) * mult, 
				(dy - cam.position.y) * mult, 0);
	}
	
	public boolean anyTileSolidWithin(Rectangle bounds) {
		int tx0 = (int) (bounds.x / Tile.TILE_SIZE);
		int tx1 = (int) ((bounds.x + bounds.width) / Tile.TILE_SIZE);
		int ty0 = (int) (bounds.y / Tile.TILE_SIZE);
		int ty1 = (int) ((bounds.y + bounds.height) / Tile.TILE_SIZE);
		
		for(int ty = ty0;ty <= ty1;ty++) {
			for(int tx = tx0;tx <= tx1;tx++) {
				if(getTile(tx, ty).isSolid())
					return true;
			}
		}
		
		return false;
	}
	
	public Tile getTile(int x, int y) {
		if(x < 0 || y < 0 || x >= width || y >= height)
			return null;
		return TileFactory.getTile(tileIds[x][y]);
	}
	
	public void fromJson(JsonValue json) {
		id = json.getInt("id");
		width = json.getInt("width");
		height = json.getInt("height");
		spawnX = json.getInt("spawnX");
		spawnY = json.getInt("spawnY");
		lives = json.getInt("lives");
		tileIds = new int[width][height];
		
		JsonValue rows = json.get("tileIds");
		for(int y = 0;y < height;y++) {
			JsonValue cols = rows.get(y);
			for(int x = 0;x < width;x++) {
				tileIds[x][y] = cols.getInt(x);
			}
		}
		
		JsonValue ents = json.get("entities");
		for(int i = 0;i < ents.size;i++) {
			JsonValue ejson = ents.get(i);
			entityManager.addEntity(EntityFactory.makeEntity(ejson.getString("name"), ejson));
		}
	}
	
	public void resize(int width, int height) {
		viewport.update(width, height);
	}
	
	public void dispose() {
		batch.dispose();
	}
	
	public int getId() {
		return id;
	}
	
	public OrthographicCamera getCam() {
		return cam;
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public GameScreen getGameScreen() {
		return gameScreen;
	}
	
}
