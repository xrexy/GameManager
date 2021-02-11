package org.shingetsu.gamemanager.maps;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.shingetsu.gamemanager.GameManager;
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

    public Map(String name, Location location) {
        this.location = location;
        this.name = name;
        this.players = new ArrayList<>();
        createJson();
    }

    public Map(Location location, String name, ArrayList<Player> players) {
        this.location = location;
        this.name = name;
        this.players = players;
        createJson();
    }

    private void createJson() {
        String fileName = name.replace(" ", "_");
        File file = new File(GameManager.getInstance().getDataFolder(), "/maps/" + fileName + ".json");
        if (file.exists()) return;
        file.getParentFile().mkdirs();

        Bukkit.getLogger().warning("Creating file... path: " + file.getPath());
        try {
            JSONObject json = new JSONObject();
            json.put("location", location);
            json.put("name", name);

            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(json.toJSONString());
            fileWriter.close();
        } catch (IOException exception) {
            Utils.debug(exception, Level.SEVERE, MapManager.PREFIX + "Couldn't create file for map: " + name);
        }
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
