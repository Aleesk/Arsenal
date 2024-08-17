package dev.aleesk.arsenal.models.menus.editor.settings.buttons;

import com.cryptomorin.xseries.XMaterial;
import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.utilities.file.FileConfig;
import dev.aleesk.arsenal.utilities.item.ItemBuilder;
import dev.aleesk.arsenal.utilities.menu.Button;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class GlowDecorationsButton extends Button{
    private final FileConfig configFile = Arsenal.get().getConfigFile();
    private final String path = "kit_gui.decorations.glow";

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(XMaterial.GLOWSTONE_DUST.parseMaterial())
                .setName("&aGlow Decorations")
                .setLore("&7Use this if you want",
                        "&7glow in all decorations.",
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
