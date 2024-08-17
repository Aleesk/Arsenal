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
public class KitGlowButton extends Button {
    private final Kit kit;

    public ItemStack getButtonItem(Player player) {
        return (new ItemBuilder(this.kit.isGlow() ? XMaterial.GLOWSTONE_DUST.parseMaterial() : XMaterial.GUNPOWDER.parseMaterial()))
                .setName("&aGlow")
                        .setLore("&7Use this if you want",
                                "&7glow in the category.",
                                "",
                                "&7Enable: " + (kit.isGlow() ? "&aTrue" : "&cFalse"),
                                "",
                                "&eClick to toggle!").build();
    }

    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        playSuccess(player);
        kit.setGlow(!this.kit.isGlow());
        kit.save();
    }
}