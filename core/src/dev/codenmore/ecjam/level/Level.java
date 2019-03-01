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
import dev.codenmore.ecjam.entities.EntityManager;
import dev.codenmore.ecjam.entities.player.Player;
import dev.codenmore.ecjam.level.tile.Tile;
import dev.codenmore.ecjam.level.tile.TileFactory;
import dev.codenmore.ecjam.screens.GameScreen;

public class Level {
	
	// Globals
	public static final float GRAVITY = 9f;
	
	// Display stuff
	private OrthographicCamera cam;
	private SpriteBatch batch;
	private Viewport viewport;
	
	private GameScreen gameScreen;
	private int id;
	private int width, height;
	private int[][] tileIds;
	
	private EntityManager entityManager;

	public Level(GameScreen gameScreen, int levelId) {
		this.gameScreen = gameScreen;
		
		cam = new OrthographicCamera(Game.WIDTH, Game.HEIGHT);
		cam.setToOrtho(false, Game.WIDTH, Game.HEIGHT);
		viewport = new FitViewport(Game.WIDTH, Game.HEIGHT, cam);
		batch = new SpriteBatch();
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		entityManager = new EntityManager(this);
		entityManager.addEntity(new Player(128, 128));
		
		fromJson(new JsonReader().parse(Gdx.files.internal("levels/" + levelId + ".txt")));
	}
	
	public void tick(float delta) {
		entityManager.tick(delta);
	}
	
	public void render() {
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		
		for(int y = 0;y < height;y++) {
			for(int x = 0;x < width;x++) {
				TileFactory.getTile(tileIds[x][y]).render(batch, x * Tile.TILE_SIZE, y * Tile.TILE_SIZE);
			}
		}
		
		entityManager.render(batch);
		
		batch.end();
	}
	
	public void centerOn(Rectangle bounds) {
		cam.position.set(bounds.x + bounds.width / 2, bounds.y + bounds.height / 2, 0);
	}
	
	public Tile getTile(int x, int y) {
		return TileFactory.getTile(tileIds[x][y]);
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
