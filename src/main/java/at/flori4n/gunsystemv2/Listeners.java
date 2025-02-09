package at.flori4n.gunsystemv2;

import com.google.gson.JsonIOException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

public class Listeners implements Listener {
    private final GunManager gunManager =GunManager.getInstance();
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getItem()==null)return;
        if (!Gun.isGun(event.getItem()))return;
        Gun gun = gunManager.getGun(event.getPlayer());
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
        if (!Gun.isGun(player.getItemInHand()))return;
        Gun gun = gunManager.getGun(player);
        if (gun.shoot()) event.setCancelled(true);
    }
    @EventHandler
    public void onProjectileLand(ProjectileHitEvent event) {
        if (event.getEntity().hasMetadata(Gun.SIGNATURE+".damage")){
            ParticleManager.getInstance().removeProjectile(event.getEntity());
            event.getEntity().remove();
        }
    }
    @EventHandler
    public void playerHoldItem(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        gunManager.removeGunIfPresent(player);
        ItemStack item = player.getInventory().getItem(event.getNewSlot());
        if (!Gun.isGun(item))return;
        gunManager.addGun(
                new Gun(item, player)
        );
    }
    //needed cuz swaps in inventory don't call item in hand event
    @EventHandler
    public void inventoryClick(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof Player))return;
        Player player = (Player) event.getInventory().getHolder();;
        if (player.getInventory().getHeldItemSlot()!=event.getSlot())return;
        gunManager.removeGunIfPresent(player);
        ItemStack item = event.getCursor();
        if (!Gun.isGun(item))return;
        gunManager.addGun(new Gun(item, player));
    }
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        Bukkit.getScheduler().scheduleSyncDelayedTask(GunSystemV2.getPlugin(), () -> {
            if (Gun.isGun(player.getItemInHand())){
                gunManager.addGun(new Gun(player.getItemInHand(), player.getPlayer()));
            };
        },2);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        gunManager.removeGunIfPresent(player);
    }
}
