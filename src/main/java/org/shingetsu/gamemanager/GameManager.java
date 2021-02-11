package org.shingetsu.gamemanager;

import org.bukkit.plugin.java.JavaPlugin;
import org.shingetsu.gamemanager.maps.MapManager;
import org.shingetsu.gamemanager.utils.PluginLoader;

public final class GameManager extends JavaPlugin {
    private static GameManager instance;
    private static MapManager mapManager;

    @Override
    public void onEnable() {
        setInstance(this);
        setMapManager(new MapManager());
        new PluginLoader().load();
    }

    @Override
    public void onDisable() {
        // plugin shutdown logic
    }

    public static GameManager getInstance() {
        return instance;
    }

    public static void setInstance(GameManager instance) {
        GameManager.instance = instance;
    }

    public static MapManager getMapManager() {
        return mapManager;
    }

    public static void setMapManager(MapManager mapManager) {
        GameManager.mapManager = mapManager;
    }
}
