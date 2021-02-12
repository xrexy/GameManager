package org.shingetsu.gamemanager.maps;

import org.bukkit.Location;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.shingetsu.gamemanager.GameManager;
import org.shingetsu.gamemanager.exceptions.MapDirectoryException;
import org.shingetsu.gamemanager.utils.LocationHandler;
import org.shingetsu.gamemanager.utils.Utils;

import java.io.File;
import java.io.FileReader;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;

public class MapManager {
    public static String PREFIX = "[MAPS] ";
    private Map currentMap = null;
    private final GameManager gameManager = GameManager.getInstance();

    public Map loadMap(File file) {
        JSONParser parser = new JSONParser();
        try {
            FileReader fileReader = new FileReader(file);
            Object obj = parser.parse(fileReader);

            JSONObject jsonObject = (JSONObject) obj;

            fileReader.close();
            return new Map((String) jsonObject.get("name"), LocationHandler.getLocationFromString((String) jsonObject.get("location")));
        } catch (Exception exception) {
            Utils.debug(exception, Level.SEVERE, "Couldn't load map from file " + file.getName());
        }
        return null;
    }

    public Map getRandomMap() {
        try {
            File dir = new File(gameManager.getDataFolder(), "/maps/");

            if (!dir.exists()) throw new MapDirectoryException();
            if (!dir.isDirectory()) throw new MapDirectoryException();

            File[] directoryListing = dir.listFiles();
            if (directoryListing == null || directoryListing.length == 0) {
                Utils.warn("Maps folder is either non existent or is empty");
                throw new MapDirectoryException();
            }

            return loadMap(directoryListing[ThreadLocalRandom.current().nextInt(directoryListing.length - 1)]);
        } catch (Exception exception) {
            Utils.debug(exception, Level.SEVERE, "Something went wrong while listing files from directory!");
        }
        return null;
    }

    public void changeCurrentMap() {
        // TODO D:
        this.currentMap = getRandomMap();
    }

    public boolean newMap(String name, Location location) {
        Map newMap = new Map(name, location);

        return newMap.createJson();
    }
}
