package dev.aleesk.arsenal.models.menus.editor.kits.buttons.edit.display;

import com.cryptomorin.xseries.XMaterial;
import dev.aleesk.arsenal.models.kit.Kit;
import dev.aleesk.arsenal.utilities.item.ItemBuilder;
import dev.aleesk.arsenal.utilities.menu.Button;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class KitEnchantButton extends Button {

    private final Kit kit;

    public ItemStack getButtonItem(Player player) {
        return (new ItemBuilder(XMaterial.ENCHANTING_TABLE.parseMaterial()))
                .setName("&aHide Enchant")
                .setLore("&7Use this if you want to hide the",
                        "&7enchantment of the kit.",
                        "",
                        "&7Enable: " + (kit.isEnchant() ? "&aTrue" : "&cFalse"),
                        "",
                        "&eClick to toggle!").build();
    }


    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        playSuccess(player);
        kit.setEnchant(!this.kit.isEnchant());
        kit.save();
    }
}
