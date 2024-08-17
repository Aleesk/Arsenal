package dev.aleesk.arsenal.models.menus.editor.categories.buttons;

import com.cryptomorin.xseries.XSound;
import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.category.Category;
import dev.aleesk.arsenal.models.category.CategoryManager;
import dev.aleesk.arsenal.models.prompt.CategoryCreateStringPrompt;
import dev.aleesk.arsenal.utilities.ChatUtil;
import dev.aleesk.arsenal.utilities.item.ItemBuilder;
import dev.aleesk.arsenal.utilities.menu.Button;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

@RequiredArgsConstructor
public class CategoryCreateButton extends Button {

    private final Arsenal plugin;

    public ItemStack getButtonItem(Player player) {
        return (new ItemBuilder(com.cryptomorin.xseries.XMaterial.WRITABLE_BOOK.parseMaterial()))
                .setName("&aCreate a Category")
                .setLore(Arrays.asList(
                        "&7Create your own categories with this",
                        "&7category builder that designs kits with",
                        "&7default features, but you can edit them.",
                        "",
                        "&eClick to create a new category!")).build();
    }

    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        CategoryManager categoryManager = this.plugin.getCategoryManager();
        playSuccess(player);
        new CategoryCreateStringPrompt(this.plugin, (string) -> {
            Category category = categoryManager.getByName(string);
            if (category != null) {
                ChatUtil.sendMessage(player, "&cCategory '&f" + category.getName() + "&c' already created.");
                XSound.ENTITY_VILLAGER_NO.play(player);
                return;
            }
            categoryManager.create(string);
            XSound.ENTITY_VILLAGER_YES.play(player);
            ChatUtil.sendMessage(player, "&aCategory '&f" + string + "&a' has been create.");
        }).startPrompt(player);
    }
}
