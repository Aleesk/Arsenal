package dev.aleesk.arsenal.user;

import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.kit.Kit;
import dev.aleesk.arsenal.utilities.ChatUtil;
import dev.aleesk.arsenal.utilities.file.FileConfig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class UserListener implements Listener {
    private final UserManager userManager;
    private final Arsenal plugin;

    public UserListener(Arsenal plugin) {
        this.userManager = plugin.getUserManager();
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    private void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        userManager.createUser(event.getUniqueId(), event.getName());
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    private void onPlayerLogin(PlayerLoginEvent event) {
        User user = userManager.getUser(event.getPlayer().getUniqueId());
        if (user == null) {
            event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
            event.setKickMessage(ChatUtil.translate("&cFailed in load your user, please join again."));
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        FileConfig configFile = plugin.getConfigFile();

        boolean enable = configFile.getBoolean("kit.starter.enable");
        if (!enable) return;

        User user = this.userManager.getUser(p.getUniqueId());
        if (user == null) return;

        String kitName = configFile.getString("kit.starter.kit");
        Kit kit = plugin.getKitManager().getKits().get(kitName);
        if (kit == null) return;

        boolean oneTime = configFile.getBoolean("kit.starter.one_time");
        if (oneTime && user.isReceivedKit()) return;

        kit.equip(p);
        if (oneTime) {
            user.setReceivedKit(true);
            user.save(userManager);
        }
        ChatUtil.sendMessage(p, plugin.getLanguageFile().getString("kit.starter").replace("%kit%", kit.getName()));
    }
}
