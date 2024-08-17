package dev.aleesk.arsenal.user.database;

import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.user.User;
import dev.aleesk.arsenal.user.UserManager;
import lombok.RequiredArgsConstructor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public abstract class Database {

    public final Arsenal plugin;
    public final UserManager userManager;

    public abstract void load(User user);

    public abstract void save(User user);

    public abstract boolean reset(CommandSender sender, OfflinePlayer offlinePlayer, boolean all);

    public abstract void resetAll(CommandSender sender);

    public abstract User getUserFromDB(String name);

    public abstract User getUserFromDB(UUID uuid);

    public abstract List<User> getUsersFromDB();
}
