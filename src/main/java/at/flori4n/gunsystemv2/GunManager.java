package at.flori4n.gunsystemv2;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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
        Bukkit.getScheduler().scheduleSyncRepeatingTask(GunSystemV2.getPlugin(),new Runnable(){
            @Override
            public void run() {
                guns.forEach((player, gun) -> {
                    gun.update();
                });
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
    public void addGun(Gun gun){
        guns.put(gun.getHolder(),gun);
    }
    public void removeGunIfPresent(Gun gun){
        guns.remove(gun.getHolder());
    }
    public void removeGunIfPresent(Player holder){
        guns.remove(holder);
    }
    public Gun getGun(Player holder){
        return guns.get(holder);
    }
}
