package at.flori4n.gunsystemv2;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class Listeners implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getItem()==null)return;
        if (!Gun.isGun(event.getItem()))return;
        Gun gun = new Gun(event.getItem(), event.getPlayer());
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
    public void entityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.PROJECTILE) return;
        if (!event.getEntity().hasMetadata(Gun.SIGNATURE))return;
        event.setDamage(
                event.getDamager().getMetadata(Gun.SIGNATURE).get(0).asInt()
        );
    }
}
