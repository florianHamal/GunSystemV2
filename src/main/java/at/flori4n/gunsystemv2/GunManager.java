package at.flori4n.gunsystemv2;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GunManager {
    private static GunManager instance;
    @Getter
    @Setter
    private Map<Player,Gun> guns = new HashMap<>();
    private void start(){
        //gun update coroutine
        Bukkit.getScheduler().scheduleSyncRepeatingTask(GunSystemV2.getPlugin(),new Runnable(){
            @Override
            public void run() {
                guns.forEach((player, gun) -> {
                    gun.update();
                });
            }
        },0,1);
        //gun scan coroutine
        Bukkit.getScheduler().scheduleSyncRepeatingTask(GunSystemV2.getPlugin(),new Runnable(){
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    ItemStack item = player.getItemInHand();
                    if (Gun.isGun(item)){
                        if ((!guns.containsKey(player))||(!guns.get(player).getItem().equals(item))){
                            guns.put(player,new Gun(item,player));
                        }
                        guns.get(player).updateLore();
                    }else {
                        guns.remove(player);
                    }
                });
            }
        },0,20);
    }
    private GunManager(){
        start();
    }
    public static GunManager getInstance(){
        if (instance == null)instance = new GunManager();
        return instance;
    }
    public Gun getGun(Player holder){
        return guns.get(holder);
    }
}
