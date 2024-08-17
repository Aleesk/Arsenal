package dev.aleesk.arsenal.models.menus.editor.kits.buttons.edit;

import com.cryptomorin.xseries.XMaterial;
import dev.aleesk.arsenal.models.kit.Kit;
import dev.aleesk.arsenal.utilities.item.ItemBuilder;
import dev.aleesk.arsenal.utilities.menu.Button;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class KitShowButton extends Button {
    private final Kit kit;

    public ItemStack getButtonItem(Player player) {
        return (new ItemBuilder(this.kit.isShow() ? XMaterial.LIME_DYE.parseMaterial() : XMaterial.GRAY_DYE.parseMaterial()))
                .setData(this.kit.isShow() ? 10 : 8)
                .setName("&aShow Kit")
                .setLore("&7click here to toggle",
                        "&7the kit show.",
                        "",
                        "&7Enable: " + (kit.isShow() ? "&aTrue" : "&cFalse"),
                        "",
                        "&eClick to toggle!").build();
    }

    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        playSuccess(player);
        kit.setShow(!this.kit.isShow());
        kit.save();
    }
}
