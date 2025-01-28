package at.flori4n.gunsystemv2;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.github.paperspigot.Title;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
@AllArgsConstructor
public class Gun {
    ItemStack item;
    private Player owner;
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

    public Gun(ItemStack item, Player owner) {
        this.item = item;
        this.owner = owner;
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
    private void updateLore(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        lore.set(4, "Timeout: "+timeout);
        lore.set(5, "Status: "+ status);
        lore.set(0,currMag+"/"+magSize);
        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    public void update(){
        if (timeout == 1)status = "-";
        if (timeout>0) timeout--;
        if (!status.equals("-"))
            owner.sendTitle(new Title(ChatColor.RED+status,String.valueOf(timeout),0,40,0));
        else
            owner.sendTitle(new Title("", currMag+"/"+magSize,0,40,0));

        updateLore(item);
    }

    public boolean isReady(){
        return timeout == 0;
    }

    public static void createGun(ItemStack item,String name,int damage,int reloadTime, int speed, int magSize,String effect,int projectileSpeed) {
        List<String> lore = new ArrayList<>();
        lore.add(magSize+"/"+magSize);
        lore.add("Damage: " + damage);
        lore.add("Reload_Time: " + reloadTime);
        lore.add("Speed: " + speed);
        lore.add("Timeout: 0");
        lore.add("Status: -");
        lore.add(SIGNATURE);
        if (Effect.getByName(effect)==null) System.out.println("PENIS___________");;
        lore.add("Effect: "+effect);
        lore.add("Projectile_Speed: "+projectileSpeed);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setLore(lore);
        itemMeta.setDisplayName(name);
        item.setItemMeta(itemMeta);
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
        Arrow projectile = owner.launchProjectile(Arrow.class);
        timeout=speed;
        projectile.setVelocity(projectile.getVelocity().multiply(projectileSpeed));
        projectile.setMetadata(SIGNATURE+".damage", new FixedMetadataValue(GunSystemV2.getPlugin(),damage));
        projectile.setMetadata(SIGNATURE+".effect", new FixedMetadataValue(GunSystemV2.getPlugin(),effect));
        ParticleManager.getInstance().addProjectile(projectile);
        projectile.setShooter(owner);
        updateLore(item);
        return true;
    };
    public void reload(){
        timeout = reloadTime;
        currMag = magSize;
        status = "Reloading";
        updateLore(item);
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
}
