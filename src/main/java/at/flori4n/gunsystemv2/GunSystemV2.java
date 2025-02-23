package at.flori4n.gunsystemv2;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class GunSystemV2 extends JavaPlugin {
    @Getter
    private static GunSystemV2 plugin;

    private ProtocolManager protocolManager;

    @Override
    public void onEnable() {
        plugin = this;
        protocolManager = ProtocolLibrary.getProtocolManager();
        Bukkit.getPluginManager().registerEvents(new Listeners(),this);
        getCommand("guns").setExecutor(new Commands());
        GunManager.start();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
