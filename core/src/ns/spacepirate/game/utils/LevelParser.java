package ns.spacepirate.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.io.BufferedReader;
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
//        JsonReader json = new JsonReader();
//        JsonValue root = json.parse(Gdx.files.internal("levels/level1.json"));
//        System.out.println(root);
//
//        JsonValue levels = root.get("levels");
//        Iterator i = levels.iterator();
//        GameSector sectors[] = new GameSector[levels.size];
//        int currIndex=0;
//        while(i.hasNext()) {
//            JsonValue level = (JsonValue)i.next();
//            JsonValue objs = level.get("Objs");
//
//            sectors[currIndex] = new GameSector();
//
//            Iterator objIter = objs.iterator();
//            while (objIter.hasNext()) {
//                JsonValue obj = (JsonValue)objIter.next();
//                sectors[currIndex].addObj(obj.getFloat("x"), obj.getFloat("y"));
//            }
//
//            currIndex++;
//            System.out.println(objs.size);
//        }
//
//        System.out.println("Success");
        ArrayList<GameSector> sectorList = new ArrayList<GameSector>(10);

        try {
            InputStream is = new FileInputStream(Gdx.files.internal("levels/level2.json").file());
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

        }

        return sectorList;
    }

}
