package city.genkoku.changeserver.command;

import city.genkoku.changeserver.ChangeServer;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChangeServerCommand implements CommandExecutor {

    public static final String COMMAND = "changeserver";

    private static final String NO_PERMISSION = ChatColor.RED + "ChangeServer: You don't have the permission.";
    private static final String PLAYER_NOT_FOUND = ChatColor.RED + "player not found.";
    private static final String USAGE = ChatColor.RED + "ChangeServer: /changeserver [<player>] <server>";
    private static final String PERMISSION_USE_OWN = "changeserver.use";
    private static final String PERMISSION_USE_OTHER = "changeserver.use.other";

    private final ChangeServer plugin;

    public ChangeServerCommand(ChangeServer plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (commandLabel.equalsIgnoreCase(COMMAND)) {
            if (sender instanceof Player) {
                if ((args.length == 1 && !sender.hasPermission(PERMISSION_USE_OWN))) {
                    sender.sendMessage(NO_PERMISSION);
                    return false;
                }
                if ((args.length == 2 && !sender.hasPermission(PERMISSION_USE_OTHER))) {
                    sender.sendMessage(NO_PERMISSION);
                    return false;
                }
            }

            if (args.length == 1 && sender instanceof Player) {
                sendPlayer((Player) sender, args[0]);
                return true;
            }

            if (args.length == 2) {
                Player player = Bukkit.getPlayer(args[0]);
                if (player == null) {
                    sender.sendMessage(PLAYER_NOT_FOUND);
                    return false;
                }
                sendPlayer(player, args[1]);
                return true;
            }
        }
        sender.sendMessage(USAGE);
        return false;
    }

    private void sendPlayer(Player player, String server) {
        try {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("ConnectOther");
            out.writeUTF(player.getName());
            out.writeUTF(server);
            player.sendPluginMessage(this.plugin, "BungeeCord", out.toByteArray());
        } catch (NullPointerException ignored) {
        }
    }
}