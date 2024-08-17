package dev.aleesk.arsenal.models.menus.editor.settings.buttons;

import com.cryptomorin.xseries.XMaterial;
import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.menus.editor.settings.DecorationsMenu;
import dev.aleesk.arsenal.utilities.item.ItemBuilder;
import dev.aleesk.arsenal.utilities.menu.Button;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

@RequiredArgsConstructor
public class DecorationsButton extends Button {
    private final Arsenal plugin;

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(XMaterial.RED_TULIP.parseMaterial())
                .setName("&aDecorations GUI")
                .setLore("&7You can place your favorite decorations with",
                        "&7the items and their spaces that you want.",
                        "&7To save your changes you must close the menu.",
                        "",
                        "&eClick to edit!").build();
    }

    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        playSuccess(player);
        new DecorationsMenu(plugin).openMenu(player);
        player.setMetadata("arsenal-decorations-menu", new FixedMetadataValue(Arsenal.get(), true));
    }
}
