package org.shingetsu.gamemanager.commands.subcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shingetsu.gamemanager.commands.CommandInterface;
import org.shingetsu.gamemanager.utils.Utils;

public class ArgNew implements CommandInterface {
    @Override
    public String getCommand() {
        return "new";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Player player = (Player) sender;

        if(args.length <= 1){
            Utils.sendMessage(player, "messages.missing-args");
            return true;
        }

        // command logic

        return false;
    }
}
