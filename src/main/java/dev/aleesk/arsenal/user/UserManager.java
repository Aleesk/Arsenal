package dev.aleesk.arsenal.user;

import com.google.common.collect.Maps;
import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.user.database.Database;
import dev.aleesk.arsenal.user.database.impl.FlatFileDatabase;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class UserManager {
    private final Arsenal plugin;
    private final Database database;
    private final Map<UUID, User> users;

    public UserManager(Arsenal plugin) {
        this.plugin = plugin;
        this.users = Maps.newHashMap();

        this.database = new FlatFileDatabase(plugin, this);
    }

    public User getUser(UUID uuid) {
        return users.getOrDefault(uuid, null);
    }

    public void createUser(UUID uuid, String name) {
        User user = new User(uuid, name);
        user.load(this);
        users.put(uuid, user);
    }

    public void saveUser(User user) {
        user.save(this);
    }

    public void onDisable() {
        for (User user : users.values()) {
            saveUser(user);
        }
    }
}
