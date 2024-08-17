package dev.aleesk.arsenal.models.menus.editor.categories.buttons.edit;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.category.Category;
import dev.aleesk.arsenal.models.prompt.CategoryEditStringPrompt;
import dev.aleesk.arsenal.utilities.ChatUtil;
import dev.aleesk.arsenal.utilities.item.ItemBuilder;
import dev.aleesk.arsenal.utilities.menu.Button;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class CategoryPermissionButton extends Button {
    private final Category category;

    private final Arsenal plugin;

    public CategoryPermissionButton(Category category, Arsenal plugin) {
        this.category = category;
        this.plugin = plugin;
    }

    public ItemStack getButtonItem(Player player) {
        return (new ItemBuilder(XMaterial.OAK_SIGN.parseMaterial()))
                .setName("&aPermission")
                .setLore("&7Edit this permit to one of",
                        "&7your preference",
                        "",
                        "&7Permission: &b" + category.getPermission(),
                        "&7Enable: " + (category.isShouldPermission() ? "&aTrue" : "&cFalse"),
                        "",
                        "&bRight-Click to toggle!",
                        "&eLeft-Click to edit!").build();
    }

    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        if(clickType.isRightClick()){
            category.setShouldPermission(!this.category.isShouldPermission());
            category.save();
        } else if(clickType.isLeftClick()) {
            ChatUtil.sendMessage(player, "&eYou're now editing permission of '&f" + category.getName() + "&e'.");
            ChatUtil.sendMessage(player, "&eType '&ccancel&e' or '&cleave&e' in the chat to cancel the process.");
            playSuccess(player);
            new CategoryEditStringPrompt(category, this.plugin, (string) -> {
                String permission = category.getPermission();
                category.setPermission(string);
                category.save();
                XSound.ENTITY_VILLAGER_YES.play(player);
                ChatUtil.sendMessage(player, "&eCategory permission has been changed from '&r" + permission + "&e' to '&r" + string + "&e'.");
            }).startPrompt(player);
        }
    }
}
