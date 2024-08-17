package dev.aleesk.arsenal.models.menus.editor.categories.buttons.edit;

import com.cryptomorin.xseries.XMaterial;
import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.category.Category;
import dev.aleesk.arsenal.models.menus.editor.categories.CategorySlotMenu;
import dev.aleesk.arsenal.utilities.item.ItemBuilder;
import dev.aleesk.arsenal.utilities.menu.Button;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class CategorySlotButton extends Button {
    private final Category category;

    private final Arsenal plugin;

    public ItemStack getButtonItem(Player player) {
        return (new ItemBuilder(XMaterial.PAINTING.parseMaterial()))
                .setName("&aSlot")
                .setLore("&7You can change the GUI kit slot",
                        "&7to one that is available.",
                        "",
                        "&7Slot: &6" + category.getSlot(),
                        "",
                        "&eClick to edit!").build();
    }

    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        new CategorySlotMenu(this.category, this.plugin).openMenu(player);
        playNeutral(player);
    }
}
