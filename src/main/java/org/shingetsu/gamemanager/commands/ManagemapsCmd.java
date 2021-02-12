package org.shingetsu.gamemanager.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shingetsu.gamemanager.utils.Utils;

public class ManagemapsCmd implements CommandInterface {
    @Override
    public String getCommand() {
        return "managemaps";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Utils.sendMultilineMessage((Player) sender, "messages.help");
        return true;
    }
}
