package ns.spacepirate.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by sukhmac on 17-11-10.
 */
public class LevelParser
{
    public static ArrayList<GameSector> parse()
    {
        ArrayList<GameSector> sectorList = new ArrayList<GameSector>(50);

        try {
            FileHandle file = Gdx.files.internal("levels/level2.json");

            Gdx.app.debug("FILE: ", file.toString());
            InputStream is = file.read();
            BufferedReader buf = new BufferedReader(new InputStreamReader(is));
            String line = buf.readLine();
            StringBuilder sb = new StringBuilder();
            while(line != null)
            {
                sb.append(line).append("\n");
                line = buf.readLine();
            }

            String fileAsString = sb.toString();

            Json json = new Json();
            sectorList = json.fromJson(ArrayList.class, fileAsString);
            //System.out.println(sectors);

            is.close();

        }catch (Exception e) {
            Gdx.app.error("ERROR", e.getMessage());
        }

        return sectorList;
    }

}
