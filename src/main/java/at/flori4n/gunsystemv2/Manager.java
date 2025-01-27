package at.flori4n.gunsystemv2;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Manager {
    public static void start(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(GunSystemV2.getPlugin(),new Runnable(){
            @Override
            public void run() {
                for(Player p : Bukkit.getOnlinePlayers()){
                    ItemStack item = p.getItemInHand();
                    if (!Gun.isGun(item))return;
                    Gun gun = new Gun(item,p);

                }
            }
        },0,5);
    }
}
