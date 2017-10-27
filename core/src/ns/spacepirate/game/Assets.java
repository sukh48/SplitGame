package ns.spacepirate.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/************************************************************************************
 * @author Sukhpreet Singh Lotey
 * 
 * Responsible for loading and disposing off all the assets of the game application.
 * 
 ************************************************************************************/
public class Assets
{
	public static final String T_BACKGROUND = "backgrounds/Background4.png";
	public static final String F_FONT_PATH = "fonts/computer_pixel-7.ttf";
	
	public static final Assets inst = new Assets();

	private AssetManager assetManager;
	private BitmapFont font60;
	private BitmapFont font30;
	
	private Assets()
	{
		// singleton 
	}
	
	public void init()
	{
		this.assetManager = new AssetManager();
		load();
	}
	
	public void load()
	{
		// load textures
		assetManager.load(T_BACKGROUND, Texture.class);
		assetManager.load("sprites/SpriteAtlas.txt", TextureAtlas.class);

		loadFonts();

        System.out.println("Hello");
		
		assetManager.finishLoading();
	}
	
	private void loadFonts()
    {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(F_FONT_PATH));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size=30;
        params.color= Color.WHITE;
        font30 = generator.generateFont(params);
        params.size=60;
        font60 = generator.generateFont(params);        
    }
	
	public Texture getTexture(String texture)
	{
		return assetManager.get(texture, Texture.class);
	}

	public TextureRegion getSpriteTexture(String name)
	{
		TextureAtlas atlas = assetManager.get("sprites/SpriteAtlas.txt", TextureAtlas.class);
		return atlas.findRegion(name);
	}
	
	public BitmapFont getSmallFont()
	{
		return font30;
	}
	
	public BitmapFont getBigFont()
	{
		return font60;
	}
	
	public void dispose()
	{
		assetManager.dispose();
		font30.dispose();
		font60.dispose();
		assetManager = null;
	}
}
