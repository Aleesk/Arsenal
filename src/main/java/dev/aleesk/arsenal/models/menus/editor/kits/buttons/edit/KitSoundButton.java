package dev.aleesk.arsenal.models.menus.editor.kits.buttons.edit;

import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.kit.Kit;
import dev.aleesk.arsenal.models.menus.editor.kits.KitSoundMenu;
import dev.aleesk.arsenal.utilities.ChatUtil;
import dev.aleesk.arsenal.utilities.item.ItemBuilder;
import dev.aleesk.arsenal.utilities.menu.Button;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class KitSoundButton extends Button {

    private final Kit kit;

    private final Arsenal plugin;

    public KitSoundButton(Kit kit, Arsenal plugin) {
        this.kit = kit;
        this.plugin = plugin;
    }

    public ItemStack getButtonItem(Player player) {

        return (new ItemBuilder(com.cryptomorin.xseries.XMaterial.NOTE_BLOCK.parseMaterial()))
                .setName("&aSound")
                .setLore("&7Customize the sound that will",
                        "&7be played when selecting this kit.",
                        "",
                        "&7Sound: &b" + (this.kit.getSound() != null ? ChatUtil.toReadable(this.kit.getSound()) : "&cNone"),
                        "",
                        "&eClick to edit!").build();
    }

    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        new KitSoundMenu(kit, plugin).openMenu(player);
        playNeutral(player);
    }
}
