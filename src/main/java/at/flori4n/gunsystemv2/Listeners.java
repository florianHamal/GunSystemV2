package at.flori4n.gunsystemv2;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.*;

public class Listeners implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getItem()==null)return;
        Player player = event.getPlayer();
        if (!Gun.isGun(player.getItemInHand()))return;
        Gun gun = new Gun(player.getItemInHand(),player);
        if (!gun.isReady())return;
        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
            gun.reload();
        }
        else {
            gun.shoot();
        }
        event.setCancelled(true);
    }
    @EventHandler
    public void checkImpact(EntityDamageByEntityEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.PROJECTILE) return;
        if (event.getDamager().hasMetadata(Gun.SIGNATURE+".damage")){
            event.setDamage(event.getDamager().getMetadata(Gun.SIGNATURE+".damage").get(0).asInt());
        }
    }
    //if enemy is in range and gun is ready to shoot plugin prefers to fire the gun instead of hitting the player with the gun
    @EventHandler
    public void onHitWithWeapon(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player))return;
        Player player = (Player) event.getDamager();
        if (Gun.isGun(player.getItemInHand()))return;
        Gun gun = new Gun(player.getItemInHand(),player);
        if (gun.shoot()) event.setCancelled(true);
    }
    @EventHandler
    public void onProjectileLand(ProjectileHitEvent event) {
        if (event.getEntity().hasMetadata(Gun.SIGNATURE+".damage")){
            ParticleManager.getInstance().removeProjectile(event.getEntity());
            event.getEntity().remove();
        }
    }
}
