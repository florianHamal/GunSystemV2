package at.flori4n.gunsystemv2;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.github.paperspigot.Title;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
public class Gun {
    ItemStack item;
    private Player owner;
    private int reloadTime;
    private int magSize;
    private int currMag;
    private int damage;
    private int speed;
    private int timeout;
    private String status;
    private static final String SIGNATURE = "flori4n_`s GunSystemV2";

    public Gun(ItemStack item, Player owner) {
        this.item = item;
        this.owner = owner;
        List<String> lore = item.getItemMeta().getLore();
        currMag = magSize = Integer.parseInt(lore.get(0).split(" ")[0]);
        magSize = Integer.parseInt(lore.get(0).split(" ")[1]);
        damage = Integer.parseInt(lore.get(1).split(" ")[1]);
        reloadTime = Integer.parseInt(lore.get(2).split(" ")[1]);
        speed = Integer.parseInt(lore.get(3).split(" ")[1]);
        timeout = Integer.parseInt(lore.get(4).split(" ")[1]);
        try {
            status = lore.get(4).split(" ")[2];
        }catch (Exception e){
            status = null;
        }

    }
    private void updateLore(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        lore.set(3, "Status: " +timeout+ " "+ status);
        item.setItemMeta(meta);
    }

    public void update(){
        if (timeout>0)timeout--;
        if (status!=null)owner.sendTitle(new Title("", ChatColor.RED + status));
        updateLore(item);
    }

    public boolean isReady(){
        return timeout == 0;
    }

    public static ItemStack createLore(ItemStack item,String name,int damage,int reloadTime, int speed, int magSize) {
        List<String> lore = new ArrayList<>();
        lore.add(magSize+"/"+magSize);
        lore.add("Damage: " + damage);
        lore.add("Reload_Time: " + reloadTime);
        lore.add("Speed: " + speed);
        lore.add("Status: "+0);
        lore.add(SIGNATURE);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setLore(lore);
        itemMeta.setDisplayName(name);
        item.setItemMeta(itemMeta);
        return item;
    }
    public static boolean isGun(ItemStack item) {
        List<String> lore = item.getItemMeta().getLore();
        return Objects.equals(lore.get(5), SIGNATURE);
    }
    public void shoot(){
        currMag --;
        Arrow projectile = owner.launchProjectile(Arrow.class);
        projectile.setCustomName(SIGNATURE+";"+damage);
        projectile.setVelocity(projectile.getVelocity().multiply(6));
        updateLore(item);
    };
    public void reload(){
        timeout = reloadTime;
        currMag = magSize;
        updateLore(item);
    };

}
