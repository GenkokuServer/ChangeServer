package city.genkoku.changeserver;

import city.genkoku.changeserver.command.ChangeServerCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class ChangeServer extends JavaPlugin {
    private Logger Log = Bukkit.getLogger();

    @Override
    public void onEnable() {
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getCommand(ChangeServerCommand.COMMAND).setExecutor(new ChangeServerCommand(this));
        this.Log.info("ChangeServer is now loaded and ready to teleport.");
    }

    @Override
    public void onDisable() {
        this.Log.info("ChangeServer is now unloaded.");
    }
}
