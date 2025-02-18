package at.flori4n.gunsystemv2;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ParticleManager {
    private static ParticleManager instance;

    private ProtocolManager protocolManager;
    private final List<Projectile> projectiles = new ArrayList<>();
    private ParticleManager() {
        protocolManager = ProtocolLibrary.getProtocolManager();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(GunSystemV2.getPlugin(),new Runnable(){
            @Override
            public void run() {
                for (Projectile projectile:projectiles){
                    if (!projectile.hasMetadata(Gun.SIGNATURE+".effect"))continue;
                    Effect effect = Effect.getByName(projectile.getMetadata(Gun.SIGNATURE+".effect").get(0).asString());
                    if (effect != null)
                        projectile.getWorld().spigot().playEffect(projectile.getLocation(),effect,1,1,0,0,0,0.01f,1 ,500);

                    sendColorParticle(projectile.getLocation(),Color.blue);
                }
            }
        },0,1);
    }
    private void sendColorParticle(Location loc, Color color){
        double r = Math.max(color.getRed()/255.0,0.001);
        double g = color.getGreen()/255.0;
        double b = color.getBlue()/255.0;
        try{
            PacketContainer packet = new PacketContainer(PacketType.Play.Server.WORLD_PARTICLES);
            packet.getParticles().write(0, EnumWrappers.Particle.REDSTONE);
            packet.getBooleans().write(0,true);
            //Location
            packet.getFloat().write(0,(float)loc.getX());
            packet.getFloat().write(1,(float)loc.getY());
            packet.getFloat().write(2,(float)loc.getZ());
            //Color Data
            packet.getFloat().write(3,(float)r);
            packet.getFloat().write(4,(float)g);
            packet.getFloat().write(5,(float)b);
            //Scale
            packet.getFloat().write(6,1.0f);
            //Particle Count (0 for color customization support)
            packet.getIntegers().write(0,0);

            for(Player inWorld:loc.getWorld().getPlayers()){
                protocolManager.sendServerPacket(inWorld,packet);
            }

        }catch(Exception e) {
            e.printStackTrace();
        }
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
