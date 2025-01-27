package at.flori4n.gunsystemv2;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Particle;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Manager {
    public static void start(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(GunSystemV2.getPlugin(),new Runnable(){
            @Override
            public void run() {
                for(Player p : Bukkit.getOnlinePlayers()){
                    ItemStack item = p.getItemInHand();
                    if (!Gun.isGun(item))return;
                    Gun gun = new Gun(item,p);
                    gun.update();
                }
                spawnEffects();
            }
        },0,5);
    }
    private static List<Projectile> bullets = new ArrayList<>();
    private static void spawnEffects(){
        for (Projectile bullet:bullets){
            float r = (float) 255/255;
            float g = (float) 0/255;
            float b = (float) 0/255;
            Color c =Color.fromRGB(1,1,1);

            bullet.getWorld().spigot().playEffect(bullet.getLocation(),Effect.COLOURED_DUST, 1, 2, 3, 255, 1, 1, 1, 1);
        }
    }
    public static void addBullet(Projectile b){
        bullets.add(b);
    }
    public static void removeBullet(Projectile b){
        bullets.remove(b);
    }

}
