package dev.aleesk.arsenal.models.menus.editor.kits.buttons.edit;

import com.cryptomorin.xseries.XMaterial;
import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.kit.Kit;
import dev.aleesk.arsenal.models.menus.editor.categories.CategoryEditMenu;
import dev.aleesk.arsenal.utilities.item.ItemBuilder;
import dev.aleesk.arsenal.utilities.menu.Button;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class KitGoToCategoryButton extends Button {

    private final Kit kit;
    private final Arsenal plugin;

    public KitGoToCategoryButton(Kit kit, Arsenal plugin){
        this.kit = kit;
        this.plugin = plugin;
    }

    @Override
    public ItemStack getButtonItem(Player player) {
        ItemBuilder itemBuilder = new ItemBuilder(this.kit.getCategory() != null ?  this.kit.getCategory().getMaterial() : XMaterial.MINECART.parseMaterial())
                .setName("&6Go to &bCategory&6: &r" + (this.kit.getCategory() != null ? this.kit.getCategory().getDisplayName() : "&cNone"));
        if (this.kit.getCategory() != null) {
            itemBuilder.setEnchanted(this.kit.getCategory().isGlow(), this.kit.getCategory().isEnchant());
            itemBuilder.setSkullOwner(this.kit.getCategory().getSkullOwner());
        }
        return itemBuilder.build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        if (kit.getCategory() != null) {
            playSuccess(player);
            new CategoryEditMenu(kit.getCategory(), plugin).openMenu(player);
        } else {
            playFail(player);
        }
    }
}
