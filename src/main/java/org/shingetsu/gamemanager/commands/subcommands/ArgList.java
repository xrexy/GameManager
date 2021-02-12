package org.shingetsu.gamemanager.commands.subcommands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shingetsu.gamemanager.GameManager;
import org.shingetsu.gamemanager.commands.CommandInterface;
import org.shingetsu.gamemanager.exceptions.MapDirectoryException;
import org.shingetsu.gamemanager.maps.Map;
import org.shingetsu.gamemanager.maps.MapManager;
import org.shingetsu.gamemanager.utils.Utils;

import java.io.File;
import java.util.logging.Level;

public class ArgList implements CommandInterface {
    private final GameManager gameManager = GameManager.getInstance();
    private final MapManager mapManager = new MapManager();

    @Override
    public String getCommand() {
        return "list";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Player player = (Player) sender;
        try {
            File dir = new File(gameManager.getDataFolder(), "/maps/");

            if (!dir.exists()) dir.mkdir();
            if (!dir.isDirectory()) {
                Utils.sendMessage(player, "messages.list-fail");
                throw new MapDirectoryException();
            }

            File[] directoryListing = dir.listFiles();
            if (directoryListing == null || directoryListing.length == 0) {
                Utils.warn("Maps folder is either non existent or is empty");
                Utils.sendMessage(player, "messages.list-fail");
                return true;
            }

            Utils.sendRawMessage(player, Utils.process(Utils.getString("messages.list-success").replace("%count%", directoryListing.length + "")));

            for (File child : directoryListing) {
                if (child.getName().equalsIgnoreCase("current-map.json")) continue;

                Map map = mapManager.loadMap(child);
                Location location = map.getLocation();

                TextComponent message = new TextComponent(Utils.colorize(Utils.getString("messages.list-entry").replace("%map%", map.getName())));
                String locationString = location.getX() + " " + location.getY() + " " + location.getZ() + " " + location.getYaw() + " " + location.getPitch();

                message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + player.getName() + " " + locationString));
                message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(Utils.colorize(Utils.getString("messages.list-file-hover").replace("%map%", map.getName())))));
                player.spigot().sendMessage(message);
            }
        } catch (Exception exception) {
            Utils.debug(exception, Level.SEVERE, "Something went wrong while listing files from directory!");
        }
        return true;
    }
}
