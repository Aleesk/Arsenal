package dev.aleesk.arsenal.models.kit;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.category.Category;
import dev.aleesk.arsenal.user.User;
import dev.aleesk.arsenal.utilities.BukkitUtil;
import dev.aleesk.arsenal.utilities.ChatUtil;
import dev.aleesk.arsenal.utilities.JavaUtil;
import dev.aleesk.arsenal.utilities.file.FileConfig;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.*;

@Getter
public class KitManager {
    private final FileConfig kitFile = Arsenal.get().getKitFile();

    private final FileConfig configFile  = Arsenal.get().getConfigFile();

    private final Map<String, Kit> kits;

    private final Arsenal plugin;

    public KitManager(Arsenal plugin) {
        this.kits = Maps.newHashMap();
        this.plugin = plugin;
    }

    public void loadOrRefresh() {
        this.kits.clear();
        ConfigurationSection section = this.kitFile.getConfiguration().getConfigurationSection("kits");
        if (section == null)
            return;
        for (String key : section.getKeys(false)) {
            Kit kit = new Kit(key);
            kit.setDisplayName(section.getString(key + ".icon.displayname"));
            kit.setSkullOwner(section.getString(key + ".icon.skull_owner"));
            kit.setMaterial(Material.valueOf(section.getString(key + ".icon.material")));
            kit.setData(section.getInt(key + ".icon.data"));
            kit.setGlow(section.getBoolean(key + ".icon.glow"));
            kit.setEnchant(section.getBoolean(key + ".icon.enchant"));
            kit.setSlot(section.getInt(key + ".icon.slot"));
            kit.setLoreDescription(section.getStringList(key + ".icon.lore.description"));
            kit.setLoreUnlocked(section.getStringList(key + ".icon.lore.unlocked"));
            kit.setLoreLocked(section.getStringList(key + ".icon.lore.locked"));
            kit.setArmor(BukkitUtil.deserializeItemStackArray(section.getString(key + ".armor")));
            kit.setContents(BukkitUtil.deserializeItemStackArray(section.getString(key + ".contents")));
            if(kit.getOffHand() != null) kit.setOffHand(BukkitUtil.deserializeItemStack(section.getString(key + ".offhand")));
            kit.setPermission(section.getString(key + ".permission.permission"));
            kit.setShouldPermission(section.getBoolean(key + ".permission.enable"));
            kit.setCooldown(section.getInt(key + ".cooldown"));
            kit.setSound(section.getString(key + ".sound"));
            kit.setShow(section.getBoolean(key + ".show"));
            kit.setCommandsPlayer(section.getStringList(key + ".commands.player"));
            kit.setCommandsConsole(section.getStringList(key + ".commands.console"));
            String category = section.getString(key + ".category");
            Category categoryName = (category == null || category.isEmpty()) ? null : this.plugin.getCategoryManager().getByName(category);
            kit.setCategory(categoryName);
            this.kits.put(key, kit);
        }
    }

    public void create(String kitName) {
        Kit kit = new Kit(ChatUtil.strip(kitName));
        kit.setDisplayName(kitName);
        kit.setMaterial(Material.CHEST);
        kit.setData(0);
        kit.setGlow(true);
        kit.setEnchant(true);
        kit.setSlot(-1);
        kit.setLoreDescription(Arrays.asList(
                "&8Kit",
                "",
                "&7This kit is designed to offer a",
                "&7significant tactical advantage in",
                "&7combat situations."));
        kit.setLoreUnlocked(Arrays.asList(
                "",
                "&7Cooldown: &6%cooldown%",
                "&7Available: %available%",
                "",
                "&bRight-Click to preview!",
                "&eClick to select!"));
        kit.setLoreLocked(Arrays.asList(
                "",
                "&cYou do not have the necessary",
                "&cpermissions to claim this kit.",
                "",
                "&6Purchase at &a%store%",
                "",
                "&bRight-Click to preview!"));
        kit.setArmor(new org.bukkit.inventory.ItemStack[0]);
        kit.setContents(new org.bukkit.inventory.ItemStack[0]);
        kit.setPermission("arsenal.kit." + kitName);
        kit.setShouldPermission(true);
        kit.setCooldown(0);
        kit.setSound(null);
        kit.setShow(true);
        kit.setCommandsPlayer(new ArrayList<>());
        kit.setCommandsConsole(new ArrayList<>());
        kit.save();
        this.kits.put(kitName, kit);
    }

    public void delete(String kitName) {
        Kit kit = getByName(kitName);
        ConfigurationSection section = this.kitFile.getConfiguration().getConfigurationSection("kits");
        if (section == null)
            return;
        section.set(kit.getName(), null);
        this.kitFile.save();
        this.kitFile.reload();
        this.kits.remove(kit.getName());
    }

    public void deleteAll() {
        this.kitFile.getConfiguration().set("kits", Collections.EMPTY_MAP);
        this.kitFile.save();
        this.kitFile.reload();
        this.kits.clear();
    }

    public List<String> getLoreKit(Player player, Kit kit) {
        List<String> lore = Lists.newArrayList();
        if (kit.getLoreDescription() != null) {
            lore.addAll(kit.getLoreDescription());
        }
        if (player.hasPermission(kit.getPermission())) {
            User user = Arsenal.get().getUserManager().getUser(player.getUniqueId());
            KitCooldown kitCooldown = user.getKitCooldown(kit.getName());
            String cooldownRemaining = (kitCooldown != null && !kitCooldown.isExpired())
                    ? kitCooldown.getCooldownRemaining()
                    : null;

            for (String line : kit.getLoreUnlocked()) {
                lore.add(line
                        .replace("%cooldown%", JavaUtil.formatDurationInt(kit.getCooldown()))
                        .replace("%available%", cooldownRemaining != null
                                ? configFile.getString("kit.available_no").replace("%cooldown_left%", cooldownRemaining)
                                : configFile.getString("kit.available_yes")));
            }
        } else {
            for (String line : kit.getLoreLocked()) {
                lore.add(line
                        .replace("%store%", this.configFile.getString("store")));
            }
        }
        return lore;
    }

    public Kit getByName(String name) {
        for (Map.Entry<String, Kit> entry : this.kits.entrySet()) {
            if ((entry.getKey()).equalsIgnoreCase(name))
                return entry.getValue();
        }
        return null;
    }
}
