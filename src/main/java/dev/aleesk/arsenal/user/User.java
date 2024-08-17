package dev.aleesk.arsenal.user;

import dev.aleesk.arsenal.models.kit.KitCooldown;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class User {

    private final UUID uuid;
    private final String name;
    private boolean ReceivedKit;
    private List<KitCooldown> kitCooldowns;
    //private List<String> kitOneTime;

    public User(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.kitCooldowns = new ArrayList<>();
        //this.kitOneTime = new ArrayList<>();
    }

    public void load(UserManager userManager) {
        userManager.getDatabase().load(this);
    }

    public void save(UserManager userManager) {
        userManager.getDatabase().save(this);
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public KitCooldown getKitCooldown(String name){
        return kitCooldowns.stream().filter(kitCooldown -> kitCooldown.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public void addKitCooldown(String kitName, long time){
        removeKitCooldown(kitName);

        KitCooldown kitCooldown = new KitCooldown(kitName);
        kitCooldown.setKitNextClaimDate(time);

        kitCooldowns.add(kitCooldown);
    }

    public void addKitCooldown(String kitName, String time){
        removeKitCooldown(kitName);

        KitCooldown kitCooldown = new KitCooldown(kitName);
        kitCooldown.setKitNextClaimDate(time);

        kitCooldowns.add(kitCooldown);
    }

    public void removeKitCooldown(String kitName){
        kitCooldowns.removeIf(kitCooldown -> kitCooldown.getName().equalsIgnoreCase(kitName));
    }

    /*public boolean hasKitOneTime(String kitName){
        return kitOneTime.contains(kitName);
    }

    public void addKitOneTime(String kitName){
        kitOneTime.add(kitName);
    }*/
}
