package at.flori4n.gunsystemv2;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.entity.Projectile;


import java.util.ArrayList;
import java.util.List;

public class ParticleManager {
    private static ParticleManager instance;
    private final List<Projectile> projectiles = new ArrayList<>();
    private ParticleManager() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(GunSystemV2.getPlugin(),new Runnable(){
            @Override
            public void run() {
                for (Projectile projectile:projectiles){
                    if (projectile.hasMetadata(Gun.SIGNATURE+".effect")){
                        Effect effect = Effect.getByName(projectile.getMetadata(Gun.SIGNATURE+".effect").get(0).asString());
                        projectile.getWorld().spigot().playEffect(projectile.getLocation(),effect,1,1,0,0,0,0.01f,1 ,500);
                    }
                }
            }
        },0,1);
    }
    public static ParticleManager getInstance() {
        if (instance == null)instance = new ParticleManager();
        return instance;
    }
    public void addProjectile(Projectile projectile){
        projectiles.add(projectile);
    }
    public void removeProjectile(Projectile projectile){
        projectiles.remove(projectile);
    }
}
