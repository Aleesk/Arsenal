package dev.aleesk.arsenal.models.menus.editor.kits.buttons.edit;


import com.cryptomorin.xseries.XMaterial;
import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.kit.Kit;
import dev.aleesk.arsenal.models.menus.editor.kits.KitCategoriesMenu;
import dev.aleesk.arsenal.utilities.ChatUtil;
import dev.aleesk.arsenal.utilities.item.ItemBuilder;
import dev.aleesk.arsenal.utilities.menu.Button;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class KitCategoryButton extends Button {
    private final Kit kit;

    private final Arsenal plugin;

    public KitCategoryButton(Kit kit, Arsenal plugin) {
        this.kit = kit;
        this.plugin = plugin;
    }

    public ItemStack getButtonItem(Player player) {
        return (new ItemBuilder((this.kit.getCategory() == null || this.plugin.getCategoryManager().getCategories().isEmpty()) ? XMaterial.MINECART.parseMaterial() : XMaterial.CHEST_MINECART.parseMaterial()))
                .setName("&aCategory")
                        .setLore("&7Change the current kit category",
                                "",
                                "&7Category: &r" + ((this.kit.getCategory() == null || this.plugin.getCategoryManager().getCategories().isEmpty()) ? "&cNone" : this.kit.getCategory().getDisplayName()),
                                "",
                                "&eClick to edit!").build();
    }

    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        if (this.plugin.getCategoryManager().getCategories().isEmpty()) {
            playFail(player);
            ChatUtil.sendMessage(player, "&cThere are no categories created yet.");
            return;
        }
        playNeutral(player);
        new KitCategoriesMenu(kit, plugin).openMenu(player);
    }
}
