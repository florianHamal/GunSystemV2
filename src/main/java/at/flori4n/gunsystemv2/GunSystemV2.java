package at.flori4n.gunsystemv2;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class GunSystemV2 extends JavaPlugin {
    @Getter
    private static GunSystemV2 plugin;
    @Override
    public void onEnable() {
        plugin = this;
        Bukkit.getPluginManager().registerEvents(new Listeners(),this);
        getCommand("guns").setExecutor(new Commands());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
