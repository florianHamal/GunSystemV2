package at.flori4n.gunsystemv2;

import org.bukkit.Effect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        if (!player.hasPermission("setup"))return false;
        try {
            switch (strings[0]) {
                case "createGun":
                    Gun gun = Gun.createGun(player,strings[1],Integer.parseInt(strings[2]),Integer.parseInt(strings[3]),Integer.parseInt(strings[4]),Integer.parseInt(strings[5]),strings[6],Integer.parseInt(strings[7]));
                    break;
                case "getEffectNames":
                    player.sendMessage(getEffectNames());
                    break;
                case "help":
                    player.sendMessage("--------help--------\n"+
                                        "/guns getEffectNames\n"+
                                        "/guns createGun name damage reloadTime speed magSize effect projectileSpeed\n"+
                                        "/guns {setName,setDamage,setReloadTime,setSpeed,setMagSize,setEffect,setProjectileSpeed}\n"+
                                        "/guns export " +
                                        "/guns import "+
                                        "--------------------");
                    break;
                case "setName":
                    if (!Gun.isGun(player.getItemInHand()))break;
                    new Gun(player.getItemInHand(),player).setName(strings[1]);
                    break;
                case "setDamage":
                    if (!Gun.isGun(player.getItemInHand()))break;
                    new Gun(player.getItemInHand(),player).setDamage(Integer.parseInt(strings[1]));
                    break;
                case "setReloadTime":
                    if (!Gun.isGun(player.getItemInHand()))break;
                    new Gun(player.getItemInHand(),player).setReloadTime(Integer.parseInt(strings[1]));
                    break;
                case "setSpeed":
                    if (!Gun.isGun(player.getItemInHand()))break;
                    new Gun(player.getItemInHand(),player).setSpeed(Integer.parseInt(strings[1]));
                    break;
                case "setMagSize":
                    if (!Gun.isGun(player.getItemInHand()))break;
                    new Gun(player.getItemInHand(),player).setMagSize(Integer.parseInt(strings[1]));
                    break;
                case "setEffect":
                    if (!Gun.isGun(player.getItemInHand()))break;
                    new Gun(player.getItemInHand(),player).setEffect(String.valueOf(Integer.parseInt(strings[1])));
                    break;
                case "setProjectileSpeed":
                    if (!Gun.isGun(player.getItemInHand()))break;
                    new Gun(player.getItemInHand(),player).setProjectileSpeed(Integer.parseInt(strings[1]));
                    break;
                case "export":
                    exportGun(player.getItemInHand());
                    break;
                case "import":
                    importGun().forEach(g->player.getInventory().addItem(g));
                    break;
                default:
                    player.sendMessage("wrong Command");
            }
        }catch (RuntimeException e){
            player.sendMessage(e.getMessage());
        }
        return false;
    }
    private void exportGun(ItemStack gun){
        FileConfiguration config = GunSystemV2.getPlugin().getConfig();
        List<ItemStack> guns = (List<ItemStack>) config.getList("exportGuns");
        if (guns==null)guns = new ArrayList<>();
        guns.add(gun);
        config.set("exportGuns",guns);
        GunSystemV2.getPlugin().saveConfig();
    }
    public List<ItemStack> importGun(){
        return (List<ItemStack>) GunSystemV2.getPlugin().getConfig().getList("exportGuns");
    }


    //just practicing some regex XD
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
