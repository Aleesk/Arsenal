package dev.aleesk.arsenal.models.kit;

import com.cryptomorin.xseries.XSound;
import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.category.Category;
import dev.aleesk.arsenal.user.User;
import dev.aleesk.arsenal.utilities.BukkitUtil;
import dev.aleesk.arsenal.utilities.ChatUtil;
import dev.aleesk.arsenal.utilities.OffHandUtil;
import dev.aleesk.arsenal.utilities.file.FileConfig;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
public class Kit {

    private final FileConfig kitFile = Arsenal.get().getKitFile();

    private final FileConfig langFile = Arsenal.get().getLanguageFile();

    private final FileConfig configFile = Arsenal.get().getConfigFile();

    private final String name;

    private String displayName;

    private Material material;

    private String skullOwner;

    private int data;

    private int slot;

    private boolean glow;

    private boolean enchant;

    private List<String> loreDescription;

    private List<String> loreUnlocked;

    private List<String> loreLocked;

    private ItemStack[] armor;

    private ItemStack[] contents;

    private ItemStack offHand;

    private String permission;

    private boolean shouldPermission;

    private int cooldown;

    private String sound;

    private boolean show;

    private List<String> commandsPlayer;

    private List<String> commandsConsole;

    private Category category;

    public Kit(String name) {
        this.name = name;
        if(BukkitUtil.SERVER_VERSION_INT >= 9) {
            this.offHand = new ItemStack(Material.AIR);
        }
    }

    public void save() {
        ConfigurationSection section = this.kitFile.getConfiguration().getConfigurationSection("kits");
        if (section == null)
            section = this.kitFile.getConfiguration().createSection("kits");
        section.set(this.name + ".icon.displayname", this.displayName);
        section.set(this.name + ".icon.skull_owner", this.skullOwner);
        section.set(this.name + ".icon.material", this.material.name());
        section.set(this.name + ".icon.data", this.data);
        section.set(this.name + ".icon.glow", this.glow);
        section.set(this.name + ".icon.enchant", this.enchant);
        section.set(this.name + ".icon.slot", this.slot);
        section.set(this.name + ".icon.lore.description", this.loreDescription);
        section.set(this.name + ".icon.lore.unlocked", this.loreUnlocked);
        section.set(this.name + ".icon.lore.locked", this.loreLocked);
        section.set(this.name + ".armor", BukkitUtil.serializeItemStackArray(this.armor));
        section.set(this.name + ".contents", BukkitUtil.serializeItemStackArray(this.contents));
        if(this.offHand != null) section.set(this.name + ".offhand", BukkitUtil.serializeItemStack(this.offHand));
        section.set(this.name + ".cooldown", this.cooldown);
        section.set(this.name + ".sound", this.sound);
        section.set(this.name + ".show", this.show);
        section.set(this.name + ".permission.permission", this.permission);
        section.set(this.name + ".permission.enable", this.shouldPermission);
        section.set(this.name + ".commands.player", this.commandsPlayer);
        section.set(this.name + ".commands.console", this.commandsConsole);
        section.set(this.name + ".category", (this.category == null) ? "" : this.category.getName());
        this.kitFile.save();
        this.kitFile.reload();
    }

    public void equip(Player player) {
        ItemStack[] contents = this.contents.clone();
        ItemStack[] armorContents = this.armor.clone();
        if (!Arsenal.get().getConfigFile().getBoolean("kit.clear_inventory")) {
            for (ItemStack itemStack : contents) {
                if (itemStack != null && !itemStack.getType().equals(Material.AIR)) {
                    ItemStack itemStackClone = itemStack.clone();
                    Map<Integer, ItemStack> drops = player.getInventory().addItem(itemStackClone);
                    for (ItemStack dropItem : drops.values())
                        player.getWorld().dropItem(player.getLocation().add(0.0D, 1.0D, 0.0D), dropItem);
                }
            }
            int countArmor = 0;
            for (ItemStack armorPiece : player.getInventory().getArmorContents()) {
                if (armorPiece == null || armorPiece.getType().equals(Material.AIR))
                    countArmor++;
            }
            if (countArmor == 4) {
                player.getInventory().setArmorContents(armorContents);
            } else {
                for (ItemStack itemStack : armorContents) {
                    if (itemStack != null && !itemStack.getType().equals(Material.AIR)) {
                        ItemStack itemStackClone = itemStack.clone();
                        Map<Integer, ItemStack> drops = player.getInventory().addItem(itemStackClone);
                        for (ItemStack dropItem : drops.values()) {
                            player.getWorld().dropItem(player.getLocation().add(0.0D, 1.0D, 0.0D), dropItem);
                        }
                    }
                }
            }
            if(this.offHand != null){
                ItemStack offhand = this.offHand.clone();
                if(OffHandUtil.getOffHandItem(player) == null || Objects.requireNonNull(OffHandUtil.getOffHandItem(player)).getType() == Material.AIR){
                    OffHandUtil.setOffHandItem(player, offhand);
                } else player.getWorld().dropItem(player.getLocation().add(0.0D, 1.0D, 0.0D), offhand);
            }
        } else {
            player.getInventory().setContents(contents);
            player.getInventory().setArmorContents(armorContents);
            if(this.offHand != null) {
                ItemStack offhand = this.offHand.clone();
                OffHandUtil.setOffHandItem(player, offhand);
            }
        }
        if (this.sound != null) {
            XSound.play(player, this.sound);
        }
        if (!getCommandsPlayer().isEmpty()) {
            getCommandsPlayer().forEach(string -> Bukkit.getServer().dispatchCommand(player, ChatUtil.format(string, player.getName())));
        }

        if (!getCommandsConsole().isEmpty()) {
            getCommandsConsole().forEach(string -> Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), ChatUtil.format(string, player.getName())));
        }
        player.updateInventory();
    }

    public void give(Player player, boolean admin) {
        if (admin) {
            equip(player);
            ChatUtil.sendMessage(player, langFile.getString("kit.equip").replace("%kit%", getName()));
            return;
        }
        if (!player.hasPermission(getPermission()) && kitFile.getBoolean("kits." + getName() + ".permission.enable")) {
            ChatUtil.sendMessage(player, langFile.getString("kit.no_permission").replace("%kit%", getName()).replace("%store%", configFile.getString("store")));
            return;
        }
        User user = Arsenal.get().getUserManager().getUser(player.getUniqueId());
        KitCooldown kitCooldown = user.getKitCooldown(getName());
        if (kitCooldown != null && !kitCooldown.isExpired()) {
            ChatUtil.sendMessage(player, langFile.getString("kit.cooldown")
                    .replace("%kit%", getName())
                    .replace("%cooldown_left%",
                            kitCooldown.getCooldownRemaining()
                    ));
            return;
        }

        equip(player);
        if (getCooldown() > 0) {
            Arsenal.get().getUserManager().getUser(player.getUniqueId()).addKitCooldown(getName(), getCooldown());
        }
        ChatUtil.sendMessage(player, langFile.getString("kit.equip").replace("%kit%", getName()));
    }
}
