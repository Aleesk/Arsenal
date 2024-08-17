package dev.aleesk.arsenal.models.menus.editor.categories.buttons;

import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.category.Category;
import dev.aleesk.arsenal.models.menus.editor.categories.CategoryEditMenu;
import dev.aleesk.arsenal.utilities.ChatUtil;
import dev.aleesk.arsenal.utilities.item.ItemBuilder;
import dev.aleesk.arsenal.utilities.menu.Button;
import dev.aleesk.arsenal.utilities.menu.menus.ConfirmMenu;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
public class CategoryInfoButton extends Button {

    private final Category category;
    private final Arsenal plugin;
    private final boolean editable;

    public ItemStack getButtonItem(Player player) {
        List<String> lore = new ArrayList<>();
        lore.add("&7Displayname: &r" + category.getDisplayName());
        lore.add("&7Material: &b" + category.getMaterial().name());
        lore.add("&7Data: &6" + category.getData());
        lore.add("&7Glow: " + getEnable(category.isGlow()));
        lore.add("&7Hide Enchant: " + getEnable(category.isEnchant()));
        lore.add("&7Slot: &6" + category.getSlot());
        lore.add("&7Permission State: " + getEnable(category.isShouldPermission()));
        lore.add("&7Permission: &b" + category.getPermission());
        lore.add("&7Description lines:");
        if (category.getDescription().isEmpty()) {
            lore.add(" &cEmpty");
        } else {
            for (int i = 0; i < category.getDescription().size(); i++) {
                lore.add(" &6" + (i + 1) + ". &e▸ &r" + category.getDescription().get(i));
            }
        }
        if (editable) {
            lore.add("");
            lore.add("&bRight-Click to delete!");
            lore.add("&eClick to edit!");
        }
        return (new ItemBuilder(this.category.getMaterial()))
                .setData(this.category.getData())
                .setName("&aCategory: &r" +this.category.getDisplayName())
                .setEnchanted(this.category.isGlow(), this.category.isEnchant())
                .setSkullOwner(this.category.getSkullOwner())
                .setLore(lore)
                .build();
    }

    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        if (editable) {
            playNeutral(player);
            if (clickType.isRightClick()) {
                ConfirmMenu confirmMenu = new ConfirmMenu(ChatUtil.translate("&cDelete Category: " + this.category.getDisplayName()), confirmed -> {
                    if (confirmed) {
                        ChatUtil.sendMessage(player, ChatUtil.translate("&a'&f" + this.category.getName() + "&f&a' category deleted"));
                        plugin.getCategoryManager().delete(this.category.getName());
                        playSuccess(player);
                    } else {
                        ChatUtil.sendMessage(player, ChatUtil.translate("&cCategory delete has been cancelled."));
                        playFail(player);
                    }
                });
                confirmMenu.openMenu(player);
            } else {
                (new CategoryEditMenu(category, plugin)).openMenu(player);
            }
        }
    }

    private String getEnable(boolean state) {
        return state ? "&aEnable" : "&cDisable";
    }
}
