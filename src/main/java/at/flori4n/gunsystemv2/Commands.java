package at.flori4n.gunsystemv2;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.stream.Collectors;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        if (!player.hasPermission("setup"))return false;
        try {
            switch (strings[0]) {
                case "createGun":
                    Gun.createGun(player.getItemInHand(),strings[1],Integer.parseInt(strings[2]),Integer.parseInt(strings[3]),Integer.parseInt(strings[4]),Integer.parseInt(strings[5]),strings[6]);
                    break;
                default:
                    player.sendMessage("wrong Command");
            }
        }catch (RuntimeException e){
            player.sendMessage(e.getMessage());
        }
        return false;
    }
}
