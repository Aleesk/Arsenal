package dev.aleesk.arsenal.user.database.impl;

import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.kit.KitCooldown;
import dev.aleesk.arsenal.user.User;
import dev.aleesk.arsenal.user.UserManager;
import dev.aleesk.arsenal.user.database.Database;
import dev.aleesk.arsenal.utilities.ChatUtil;
import dev.aleesk.arsenal.utilities.file.FileConfig;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class FlatFileDatabase extends Database {

    private final FileConfig fileConfig;
    private final FileConfiguration configuration;

    public FlatFileDatabase(Arsenal plugin, UserManager userManager) {
        super(plugin, userManager);

        this.fileConfig = new FileConfig(Arsenal.get(), "users.yml");
        this.configuration = fileConfig.getConfiguration();
    }

    @Override
    public void load(User user) {
        String path = "users." + user.getUuid().toString();

        if (configuration.contains(path)) {
            ConfigurationSection userSection = configuration.getConfigurationSection(path);
            boolean hasReceivedKit = userSection.getBoolean("has_received_kit", false);
            user.setReceivedKit(hasReceivedKit);

            ConfigurationSection cooldownSection = userSection.getConfigurationSection("cooldowns");
            if (cooldownSection != null) {
                for (String kit : cooldownSection.getKeys(false)) {
                    String cooldown = cooldownSection.getString(kit);
                    user.addKitCooldown(kit, cooldown);
                }
            }
        }
    }

    @Override
    public void save(User user) {
        String path = "users." + user.getUuid().toString();

        configuration.set(path + ".name", user.getName());
        configuration.set(path + ".has_received_kit", user.isReceivedKit());

        ConfigurationSection cooldownSection = configuration.createSection(path + ".cooldowns");
        for (KitCooldown cooldown : user.getKitCooldowns()) {
                String kitName = cooldown.getName();
                cooldownSection.set(kitName, cooldown.getKitNextClaimDateFormatted());
        }
        fileConfig.save();
    }

    @Override
    public void resetAll(CommandSender sender) {
        plugin.getUserManager().onDisable();
        AtomicInteger resetted = new AtomicInteger(0);
        for (String uuid : configuration.getConfigurationSection("users").getKeys(false)) {
            OfflinePlayer target = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
            if (reset(sender, target, true)) {
                resetted.incrementAndGet();
            }
        }
        if (resetted.get() == 0) {
            ChatUtil.sendMessage(sender, " &cData not found. (already reset?)");
            return;
        }
        configuration.set("users", Collections.EMPTY_MAP);
        this.fileConfig.save();
        this.fileConfig.reload();
        ChatUtil.sendMessage(sender, " &aSuccessfully reset &e" + resetted.get() + "&a players data.");
    }

    @Override
    public boolean reset(CommandSender sender, OfflinePlayer offlinePlayer, boolean all) {
        UUID uuid = offlinePlayer.getUniqueId();
        boolean reset = false;
        if (configuration.contains("users." + uuid)) {
            reset = true;
            configuration.set("users." + uuid, null);
            fileConfig.save();
        }
        if (!all) {
            ChatUtil.sendMessage(sender, " &aSuccessfully reset &e" + offlinePlayer.getName() + "&a data.");
        }
        return reset;
    }

    @Override
    public User getUserFromDB(String name) {
        User user = new User(findUUID(name), name);
        load(user);
        return user;
    }

    @Override
    public User getUserFromDB(UUID uuid) {
        User user = new User(uuid, findName(uuid));
        load(user);
        return user;
    }

    @Override
    public List<User> getUsersFromDB() {
        List<User> users = new ArrayList<>();

        for (String key : configuration.getConfigurationSection("users").getKeys(false)) {
            users.add(getUserFromDB(UUID.fromString(key)));
        }

        return users;
    }

    private UUID findUUID(String name) {
        String path = "users.";

        for (String key : configuration.getConfigurationSection(path).getKeys(false)) {
            if (configuration.getString(path + key + ".name").equalsIgnoreCase(name)) return UUID.fromString(key);
        }

        return null;
    }

    private String findName(UUID uuid) {
        return configuration.getString("users." + uuid.toString() + ".name");
    }
}

