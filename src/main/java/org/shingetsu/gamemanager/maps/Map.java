package org.shingetsu.gamemanager.maps;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.shingetsu.gamemanager.GameManager;
import org.shingetsu.gamemanager.utils.LocationHandler;
import org.shingetsu.gamemanager.utils.Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

public class Map {
    private Location location;
    private String name;
    private final ArrayList<Player> players;

    protected Map(String name, Location location) {
        this.location = location;
        this.name = name;
        this.players = new ArrayList<>();
    }

    /**
     * Creates a new JSON File with map information. TODO More fields?
     *
     * @return True - if a new file was created, False - if file already exists, or an exception appeared
     */
    public boolean createJson() {
        String fileName = name.replace(" ", "_");
        File file = new File(GameManager.getInstance().getDataFolder(), "/maps/" + fileName + ".json");

        Utils.warn("Creating file " + name + "...");

        if (file.exists()) {
            Utils.warn("Creation of file " + name + " failed! File with this name already exists!");
            return false;
        }
        file.getParentFile().mkdirs();

        try {
            JSONObject json = new JSONObject();
            json.put("location", LocationHandler.getStringFromLocation(location));
            json.put("name", name);

            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(json.toJSONString());
            fileWriter.close();

            Utils.warn("Successfully created file " + name + "!");
            return true;
        } catch (IOException exception) {
            Utils.debug(exception, Level.SEVERE, MapManager.PREFIX + "Couldn't create file for map: " + name);
        }
        return false;
    }

    public Location getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void teleportPlayer(Player player) {
        player.teleport(this.location);
    }
}
