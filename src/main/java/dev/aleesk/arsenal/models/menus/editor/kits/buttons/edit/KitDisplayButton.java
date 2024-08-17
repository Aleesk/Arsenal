package dev.aleesk.arsenal.models.menus.editor.kits.buttons.edit;

import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.kit.Kit;
import dev.aleesk.arsenal.models.menus.editor.kits.KitDisplayMenu;
import dev.aleesk.arsenal.utilities.item.ItemBuilder;
import dev.aleesk.arsenal.utilities.menu.Button;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class KitDisplayButton extends Button {

    private final Kit kit;
    private final Arsenal plugin;

    public ItemStack getButtonItem(Player player) {
        List<String> lore = new ArrayList<>();
        lore.add("&7Displayname: &r" + this.kit.getDisplayName());
        lore.add("&7Material: &b" + this.kit.getMaterial().name());
        lore.add("&7Data: &6" + this.kit.getData());
        lore.add("&7Glow: " + (this.kit.isGlow() ? "&aEnable" : "&cDisable"));
        lore.add("&7Hide Enchant: " + (this.kit.isEnchant() ? "&aEnable" : "&cDisable"));
        lore.add("&7Description:");
        if (kit.getLoreDescription().isEmpty()) {
            lore.add(" &cEmpty");
        } else {
            for (int i = 0; i < kit.getLoreDescription().size(); i++) {
                lore.add(" &6" + (i + 1) + ". ▸ &r" + kit.getLoreDescription().get(i));
            }
        }
        lore.add("");
        lore.add("&eClick to edit!");
        return (new ItemBuilder(com.cryptomorin.xseries.XMaterial.BOOK.parseMaterial()))
                .setName("&aDisplay")
                .setLore(lore)
                .build();
    }

    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        playNeutral(player);
        new KitDisplayMenu(kit, plugin).openMenu(player);
    }
}
