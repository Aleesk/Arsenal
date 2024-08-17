package dev.aleesk.arsenal.models.menus.editor.kits.buttons;

import dev.aleesk.arsenal.models.category.Category;
import dev.aleesk.arsenal.models.kit.Kit;
import dev.aleesk.arsenal.utilities.ChatUtil;
import dev.aleesk.arsenal.utilities.item.ItemBuilder;
import dev.aleesk.arsenal.utilities.menu.Button;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class KitSetCategoryButton extends Button {

    private final Kit kit;

    private final Category category;

    public ItemStack getButtonItem(Player player) {
        return (new ItemBuilder(this.category.getMaterial(),1, this.category.getData()))
                .setName(category.equals(kit.getCategory()) ? "&aCurrent: &r" + category.getDisplayName() : "&r" + category.getDisplayName())
                .setEnchanted(this.category.isGlow(), this.category.isEnchant())
                .setSkullOwner(this.category.getSkullOwner())
                .setLore("&7Select this category to associate",
                        "&7it with the kit. ",
                        "",
                        "&eClick to set!").build();
    }

    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        playSuccess(player);
        kit.setCategory(this.category);
        kit.setSlot(-1);
        kit.save();
        ChatUtil.sendMessage(player, "&a'&f" + kit.getName() + "'&a kit category has been set to '&f" + this.category.getName() + "&a'.");
    }
}
