package dev.aleesk.arsenal.models.menus.editor.settings.buttons;

import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.utilities.file.FileConfig;
import dev.aleesk.arsenal.utilities.item.ItemBuilder;
import dev.aleesk.arsenal.utilities.menu.Button;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class RowsButton extends Button {
    private final FileConfig configFile = Arsenal.get().getConfigFile();
    private final String path = "kit_gui.rows";

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(com.cryptomorin.xseries.XMaterial.ANVIL.parseMaterial())
                .setName("&aRows GUI")
                .setLore("&7Change the rows to the one",
                        "&7you want from the kit GUI.",
                        "",
                        "&7Current: &6" + configFile.getString(path),
                        "",
                        "&bRight-Click to &cdecrease&b!",
                        "&eLeft-Click to &aincrease&e!").build();
    }

    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        int rows = configFile.getInt(path);
        playSuccess(player);
        if (clickType.isLeftClick()) {
            if (rows >= 1 && rows < 6) {
                configFile.getConfiguration().set(path, rows + 1);
                configFile.save();
            }
        } if (clickType.isRightClick()) {
            if (rows > 1 && rows <= 6) {
                configFile.getConfiguration().set(path, rows - 1);
                configFile.save();
            }
        }
    }
}
