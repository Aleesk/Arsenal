package dev.aleesk.arsenal.models.menus.editor.settings.buttons;

import com.cryptomorin.xseries.XMaterial;
import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.utilities.file.FileConfig;
import dev.aleesk.arsenal.utilities.item.ItemBuilder;
import dev.aleesk.arsenal.utilities.menu.Button;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class KitClearInventoryButton extends Button {
    private final FileConfig configFile = Arsenal.get().getConfigFile();

    private final String path = "kit.clear_inventory";
    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(XMaterial.ARMOR_STAND.parseMaterial())
                .setName("&aClear Inventory")
                .setLore("&7Activate this if you want to clear",
                        "&7the player's inventory before a kit",
                        "&7is equipped.",
                        "",
                        "&7Enable: " + (configFile.getBoolean(path) ? "&aTrue" : "&cFalse"),
                        "",
                        "&eClick to toggle!").build();
    }

    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        playSuccess(player);
        configFile.getConfiguration().set(path, !configFile.getBoolean(path));
        configFile.save();
    }
}
