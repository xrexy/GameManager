package org.shingetsu.gamemanager.commands.subcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shingetsu.gamemanager.GameManager;
import org.shingetsu.gamemanager.commands.CommandInterface;
import org.shingetsu.gamemanager.maps.MapManager;
import org.shingetsu.gamemanager.utils.Utils;

import java.util.Arrays;
import java.util.logging.Level;

public class ArgNew implements CommandInterface {
    private final GameManager gameManager = GameManager.getInstance();
    private final MapManager mapManager = GameManager.getMapManager();

    @Override
    public String getCommand() {
        return "new";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Player player = (Player) sender;

        if (args.length < 2) {
            Utils.sendMessage(player, "messages.missing-args");
            return true;
        }

        try {
            StringBuilder mapName = new StringBuilder();
            for (int i = 1; i < args.length; i++)
                mapName.append(args[i]).append(" ");

            String name = mapName.substring(0, mapName.length() - 1); // removes the useless " " at the end

            if (mapManager.newMap(name, player.getLocation()))
                Utils.sendRawMessage(player, Utils.process(Utils.getString("messages.new-map").replace("%map_name%", name)));
            else
                Utils.sendRawMessage(player, Utils.process(Utils.getString("messages.new-map-exists").replace("%map_name%", name)));
        } catch (Exception e) {
            Utils.debug(e, Level.WARNING, "Something went wrong while creating new Map");
        }

        return false;
    }
}
