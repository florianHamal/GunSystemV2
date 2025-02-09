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
                for(Player p : Bukkit.getOnlinePlayers()){
                    if (Gun.isGun(p.getItemInHand())){
                        new Gun(p.getItemInHand(),p).update();
                    }
                }
            }
        },0,1);
    }
    private GunManager(){
        start();
    }
    public static GunManager getInstance(){
        if (instance == null)instance = new GunManager();
        return instance;
    }
}
