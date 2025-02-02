package at.flori4n.gunsystemv2;

import org.bukkit.Effect;
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
                    Gun.createGun(player.getItemInHand(),strings[1],Integer.parseInt(strings[2]),Integer.parseInt(strings[3]),Integer.parseInt(strings[4]),Integer.parseInt(strings[5]),strings[6],Integer.parseInt(strings[7]));
                    break;
                case "getEffectNames":
                    player.sendMessage(getEffectNames());
                default:
                    player.sendMessage("wrong Command");
            }
        }catch (RuntimeException e){
            player.sendMessage(e.getMessage());
        }
        return false;
    }

    private String getEffectNames(){
        return Effect.CLICK2.getName()+ ", "+
                Effect.BOW_FIRE.getName()+ ", "+
                Effect.CLICK1.getName()+ ", "+
                Effect.DOOR_TOGGLE.getName()+ ", "+
                Effect.EXTINGUISH.getName()+ ", "+
                Effect.RECORD_PLAY.getName()+ ", "+
                Effect.GHAST_SHRIEK.getName()+ ", "+
                Effect.GHAST_SHOOT.getName()+ ", "+
                Effect.BLAZE_SHOOT.getName()+ ", "+
                Effect.ZOMBIE_CHEW_WOODEN_DOOR.getName()+ ", "+
                Effect.ZOMBIE_CHEW_IRON_DOOR.getName()+ ", "+
                Effect.ZOMBIE_DESTROY_DOOR.getName()+ ", "+
                Effect.SMOKE.getName()+ ", "+
                Effect.STEP_SOUND.getName()+ ", "+
                Effect.POTION_BREAK.getName()+ ", "+
                Effect.ENDER_SIGNAL.getName()+ ", "+
                Effect.MOBSPAWNER_FLAMES.getName()+ ", "+
                Effect.FIREWORKS_SPARK.getName()+ ", "+
                Effect.CRIT.getName()+ ", "+
                Effect.MAGIC_CRIT.getName()+ ", "+
                Effect.POTION_SWIRL.getName()+ ", "+
                Effect.POTION_SWIRL_TRANSPARENT.getName()+ ", "+
                Effect.SPELL.getName()+ ", "+
                Effect.INSTANT_SPELL.getName()+ ", "+
                Effect.WITCH_MAGIC.getName()+ ", "+
                Effect.NOTE.getName()+ ", "+
                Effect.PORTAL.getName()+ ", "+
                Effect.FLYING_GLYPH.getName()+ ", "+
                Effect.FLAME.getName()+ ", "+
                Effect.LAVA_POP.getName()+ ", "+
                Effect.FOOTSTEP.getName()+ ", "+
                Effect.SPLASH.getName()+ ", "+
                Effect.PARTICLE_SMOKE.getName()+ ", "+
                Effect.EXPLOSION_HUGE.getName()+ ", "+
                Effect.EXPLOSION_LARGE.getName()+ ", "+
                Effect.EXPLOSION.getName()+ ", "+
                Effect.VOID_FOG.getName()+ ", "+
                Effect.SMALL_SMOKE.getName()+ ", "+
                Effect.CLOUD.getName()+ ", "+
                Effect.COLOURED_DUST.getName()+ ", "+
                Effect.SNOWBALL_BREAK.getName()+ ", "+
                Effect.WATERDRIP.getName()+ ", "+
                Effect.LAVADRIP.getName()+ ", "+
                Effect.SNOW_SHOVEL.getName()+ ", "+
                Effect.SLIME.getName()+ ", "+
                Effect.HEART.getName()+ ", "+
                Effect.VILLAGER_THUNDERCLOUD.getName()+ ", "+
                Effect.HAPPY_VILLAGER.getName()+ ", "+
                Effect.LARGE_SMOKE.getName()+ ", "+
                Effect.ITEM_BREAK.getName()+ ", "+
                Effect.TILE_BREAK.getName()+ ", "+
                Effect.TILE_DUST.getName();

    }

}
