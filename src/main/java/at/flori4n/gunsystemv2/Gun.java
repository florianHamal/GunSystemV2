package at.flori4n.gunsystemv2;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.github.paperspigot.Title;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
public class Gun {
    private ItemStack item;
    private Player holder;
    private int reloadTime;
    private int magSize;
    private int currMag;
    private String name;
    private int damage;
    private int speed;
    private int timeout;
    private String status;
    private String effect;
    private int projectileSpeed;
    public static final String SIGNATURE = "at.flori4n_.GunSystemV2";

    public Gun(ItemStack item, Player holder) {
        this.item = item;
        this.holder = holder;
        List<String> lore = item.getItemMeta().getLore();
        currMag  = Integer.parseInt(lore.get(0).split("/")[0]);
        magSize = Integer.parseInt(lore.get(0).split("/")[1]);
        damage = Integer.parseInt(lore.get(1).split(" ")[1]);
        reloadTime = Integer.parseInt(lore.get(2).split(" ")[1]);
        speed = Integer.parseInt(lore.get(3).split(" ")[1]);
        timeout = Integer.parseInt(lore.get(4).split(" ")[1]);
        status = lore.get(5).split(" ")[1];
        effect = (lore.get(7).split(" ")[1]);
        projectileSpeed = Integer.parseInt(lore.get(8).split(" ")[1]);
        name = item.getItemMeta().getDisplayName();
    }

    private void updateLore() {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        lore.set(4, "Timeout: "+timeout);
        lore.set(5, "Status: "+ status);
        lore.set(0,currMag+"/"+magSize);
        meta.setLore(lore);
        holder.getItemInHand().setItemMeta(meta);
    }

    public void update(){
        if (timeout == 1)status = "-";
        if (timeout>0){
            timeout--;
            updateLore();
        }
        if (!status.equals("-"))
            holder.sendTitle(new Title(ChatColor.RED+status,String.valueOf(timeout),0,5,0));
        else
            holder.sendTitle(new Title("", currMag+"/"+magSize,0,5,0));
    }

    public boolean isReady(){
        return timeout == 0;
    }

    public static Gun createGun(Player holder,String name,int damage,int reloadTime, int speed, int magSize,String effect,int projectileSpeed) {
        ItemStack item = holder.getItemInHand();
        List<String> lore = new ArrayList<>();
        lore.add(magSize+"/"+magSize);
        lore.add("Damage: " + damage);
        lore.add("Reload_Time: " + reloadTime);
        lore.add("Speed: " + speed);
        lore.add("Timeout: 0");
        lore.add("Status: -");
        lore.add(SIGNATURE);
        lore.add("Effect: "+effect);
        lore.add("Projectile_Speed: "+projectileSpeed);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setLore(lore);
        itemMeta.setDisplayName(name);
        item.setItemMeta(itemMeta);
        return new Gun(item,holder);
    }
    public static boolean isGun(ItemStack item) {
        try {
            List<String> lore = item.getItemMeta().getLore();
            return Objects.equals(lore.get(6), SIGNATURE);
        }catch (NullPointerException e){
            //if there is no lore
            return false;
        }

    }
    public boolean shoot(){
        if (currMag<=0){
            return false;
        }
        currMag --;
        Arrow projectile = holder.launchProjectile(Arrow.class);
        timeout=speed;
        projectile.setVelocity(projectile.getVelocity().multiply(projectileSpeed));
        projectile.setMetadata(SIGNATURE+".damage", new FixedMetadataValue(GunSystemV2.getPlugin(),damage));
        projectile.setMetadata(SIGNATURE+".effect", new FixedMetadataValue(GunSystemV2.getPlugin(),effect));
        ParticleManager.getInstance().addProjectile(projectile);
        projectile.setShooter(holder);
        holder.getLocation().getWorld().playSound(holder.getLocation(), Sound.PISTON_RETRACT,2f,2f);
        updateLore();
        return true;
    };
    public void reload(){
        timeout = reloadTime;
        currMag = magSize;
        status = "Reloading";
        updateLore();
    };


    @Override
    public String toString() {
        return "Gun{" +
                "reloadTime=" + reloadTime +
                ", magSize=" + magSize +
                ", currMag=" + currMag +
                ", damage=" + damage +
                ", speed=" + speed +
                ", timeout=" + timeout +
                ", status='" + status + '\'' +
                '}';
    }

    private void updateGunStats(){
        Gun gun = createGun(holder,name,damage,reloadTime,speed,magSize,effect,projectileSpeed);
        GunManager gunManager = GunManager.getInstance();
        gunManager.getGuns().put(holder,gun);
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
        updateGunStats();
    }
    public void setReloadTime(int reloadTime) {
        this.reloadTime = reloadTime;
        updateGunStats();
    }

    public void setMagSize(int magSize) {
        this.magSize = magSize;
        updateGunStats();
    }

    public void setName(String name) {
        this.name = name;
        updateGunStats();
    }

    public void setDamage(int damage) {
        this.damage = damage;
        updateGunStats();
    }

    public void setSpeed(int speed) {
        this.speed = speed;
        updateGunStats();
    }

    public void setEffect(String effect) {
        this.effect = effect;
        updateGunStats();
    }
}
