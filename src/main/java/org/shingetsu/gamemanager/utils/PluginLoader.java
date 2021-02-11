package org.shingetsu.gamemanager.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.shingetsu.gamemanager.GameManager;
import org.shingetsu.gamemanager.commands.CommandManager;
import org.shingetsu.gamemanager.commands.subcommands.ArgNew;
import org.shingetsu.gamemanager.files.FileAPI;
import org.shingetsu.gamemanager.maps.MapManager;

public class PluginLoader {
    private final GameManager gameManager = GameManager.getInstance();
    private final FileAPI fileAPI = new FileAPI();
    private final MapManager mapManager = GameManager.getMapManager();
    private final String MAIN_COMMAND = "managemaps";

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
        final PluginCommand mainPluginCommand = gameManager.getCommand(MAIN_COMMAND);
        final CommandManager commandManager = new CommandManager(MAIN_COMMAND);

        if (mainPluginCommand == null) {
            Bukkit.getLogger().severe(String.format("[%s] - Disabled, couldn't load main command!", gameManager.getDescription().getName()));
            gameManager.getServer().getPluginManager().disablePlugin(gameManager);
            return;
        }

        mainPluginCommand.setExecutor(commandManager);
//        mainPluginCommand.setTabCompleter(new STTabCompleter());

        ArgNew aNew = new ArgNew();
        commandManager.register(aNew.getCommand(), aNew);
    }
}
