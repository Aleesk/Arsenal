package dev.aleesk.arsenal.models.menus.editor.categories.buttons.edit;

import com.cryptomorin.xseries.XMaterial;
import dev.aleesk.arsenal.models.category.Category;
import dev.aleesk.arsenal.utilities.item.ItemBuilder;
import dev.aleesk.arsenal.utilities.menu.Button;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class CategoryEnchantButton extends Button{
    private final Category category;

    public ItemStack getButtonItem(Player player) {
        return (new ItemBuilder(XMaterial.ENCHANTING_TABLE.parseMaterial()))
                .setName("&aHide Enchant")
                .setLore("&7Use this if you want to hide the",
                        "&7enchantment of the category.",
                        "",
                        "&7Enable: " + (category.isEnchant() ? "&aTrue" : "&cFalse"),
                        "",
                        "&eClick to toggle!").build();
    }


    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        playSuccess(player);
        category.setEnchant(!this.category.isEnchant());
        category.save();
    }
}
