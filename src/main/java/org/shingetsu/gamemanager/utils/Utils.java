package org.shingetsu.gamemanager.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.shingetsu.gamemanager.GameManager;

import java.util.logging.Level;

public class Utils {
    private static final GameManager gameManager = GameManager.getInstance();
    private static final FileConfiguration config = gameManager.getConfig();

    public static void debug(Exception exception, Level level, String message) {
        Bukkit.getLogger().log(level, colorize(message));
        if (config.getBoolean("debug"))
            exception.printStackTrace();
    }

    public static void warn(Object o) {
        Bukkit.getLogger().warning(colorize(o.toString()));
    }

    public static String process(String toProcess) {
        return toProcess.replace("%prefix%", getString("prefix"));
    }

    public static void sendMultilineMessage(Player p, String path) {
        StringBuilder message = new StringBuilder();
        for (String s : config.getStringList(path))
            message.append(colorize(process(s)));
        p.sendMessage(message.toString());
    }

    public static void sendMessage(Player p, String path) {
        p.sendMessage(colorize(process(getString(path))));
    }

    public static void sendRawMessage(Player p, String message) {
        p.sendMessage(colorize(message));
    }

    public static String getString(String path) {
        return config.getString(path);
    }

    public static String colorize(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }
}
