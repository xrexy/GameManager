package org.shingetsu.gamemanager.maps;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.shingetsu.gamemanager.files.AbstractYamlFile;
import org.shingetsu.gamemanager.utils.Utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;

public class MapManager extends AbstractYamlFile {
    public static String PREFIX = "[MAPS] ";
    private final HashMap<Integer, Map> maps = new HashMap<>();

    public Map loadMap(File file) {
        AtomicReference<Location> location = null;
        AtomicReference<String> name = null;
        AtomicReference<ArrayList<Player>> players = null;

        try (FileReader fileReader = new FileReader(file)) {
            JSONParser jsonParser = new JSONParser();

            JSONArray jsonArray = (JSONArray) jsonParser.parse(fileReader);
            Bukkit.getLogger().warning(jsonArray.toJSONString());

            //Iterate over employee array
            jsonArray.forEach(_obj_ -> {
                JSONObject obj = (JSONObject) _obj_;
                players.set((ArrayList<Player>) obj.get("players"));
                location.set((Location) obj.get("location"));
                name.set((String) obj.get("name"));
            });
        } catch (IOException | ParseException exception) {
            Utils.debug(exception, Level.SEVERE, MapManager.PREFIX + "Error while parsing file " + file.getName());
        }
        return new Map(location.get(), name.get(), players.get());
    }

    public void newMap(String name, Location location) {
        maps.put(maps.size() + 1, new Map(name, location));
    }

    public HashMap<Integer, Map> getMaps() {
        return maps;
    }

    @Override
    public String getFileName() {
        return "maps/maps-list.yml";
    }

    @Override
    public boolean isResource() {
        return false;
    }
}
