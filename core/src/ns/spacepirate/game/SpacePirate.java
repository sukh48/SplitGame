package ns.spacepirate.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FillViewport;

import ns.spacepirate.game.screens.GameScreen;

public class SpacePirate extends Game
{

	public static final int V_WIDTH=720;
	public static final int V_HEIGHT=1280;

	public SpriteBatch batcher;
	public OrthographicCamera cam;
	public FillViewport viewport;

	@Override
	public void create ()
	{
		batcher = new SpriteBatch();

		initView();
		initAssets();
		start();
	}

	private void initView()
	{
		cam = new OrthographicCamera();
		cam.setToOrtho(false, V_WIDTH, V_HEIGHT);
		cam.position.set(V_WIDTH /2, V_HEIGHT /2, 0);

		/* Fit viewport allows to have correct screen ratio for game rendering */
		viewport= new FillViewport(V_WIDTH, V_HEIGHT, cam);
		viewport.apply();
	}

	private void initAssets()
	{
		Assets.inst.init();
		Assets.inst.load();
	}

	private void start()
	{
		setScreen(new GameScreen(this));
	}

	@Override
	public void resize(int width, int height)
	{
		super.resize(width, height);
		viewport.update(width,height);
		cam.position.set(V_WIDTH/2, V_HEIGHT/2, 0);
	}

	@Override
	public void render()
	{
		GL20 gl = Gdx.gl;
		gl.glClearColor(0.0f, 1.0f, 1.0f, 0.0f);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		super.render();
	}
}