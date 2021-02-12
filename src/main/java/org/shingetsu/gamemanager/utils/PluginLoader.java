package org.shingetsu.gamemanager.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.shingetsu.gamemanager.GameManager;
import org.shingetsu.gamemanager.commands.CommandManager;
import org.shingetsu.gamemanager.commands.subcommands.ArgList;
import org.shingetsu.gamemanager.commands.subcommands.ArgNew;
import org.shingetsu.gamemanager.commands.ManagemapsCmd;
import org.shingetsu.gamemanager.files.FileAPI;
import org.shingetsu.gamemanager.maps.MapManager;

public class PluginLoader {
    private final GameManager gameManager = GameManager.getInstance();
    private final FileAPI fileAPI = new FileAPI();
    private final MapManager mapManager = GameManager.getMapManager();

    /**
     * <b>onEnable only</b>
     */
    public void load() {
        loadFiles();
        loadCommands();
    }

    /**
     * Loads config.yml and calls FileAPI to load all custom files
     */
    void loadFiles() {
        gameManager.getConfig().options().copyDefaults(true);
        gameManager.saveDefaultConfig();

        fileAPI.loadFiles();
        fileAPI.createAllFiles();
    }

    /**
     * Loads all registered commands (From CommandManager)
     */
    void loadCommands() {
        // /managemaps
        final ManagemapsCmd managemaps = new ManagemapsCmd();
        final PluginCommand manageMapsCommand = gameManager.getCommand(managemaps.getCommand());
        final CommandManager manageMapsCommandManager = new CommandManager(managemaps.getCommand());

        if (manageMapsCommand == null) {
            Bukkit.getLogger().severe(String.format("[%s] - Disabled, couldn't load managemaps command!", gameManager.getDescription().getName()));
            gameManager.getServer().getPluginManager().disablePlugin(gameManager);
            return;
        }

        manageMapsCommand.setExecutor(manageMapsCommandManager);
//        mainPluginCommand.setTabCompleter(new STTabCompleter());

        manageMapsCommandManager.register(managemaps.getCommand(), managemaps);

        ArgNew aNew = new ArgNew();
        manageMapsCommandManager.register(aNew.getCommand(), aNew);

        ArgList aList = new ArgList();
        manageMapsCommandManager.register(aList.getCommand(), aList);
    }
}
