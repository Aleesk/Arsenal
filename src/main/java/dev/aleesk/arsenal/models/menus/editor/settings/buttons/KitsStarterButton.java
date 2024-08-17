package dev.aleesk.arsenal.models.menus.editor.settings.buttons;

import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.kit.Kit;
import dev.aleesk.arsenal.utilities.ChatUtil;
import dev.aleesk.arsenal.utilities.file.FileConfig;
import dev.aleesk.arsenal.utilities.item.ItemBuilder;
import dev.aleesk.arsenal.utilities.menu.Button;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class KitsStarterButton extends Button {

    private final Kit kit;

    private final FileConfig configFile = Arsenal.get().getConfigFile();

    private final String path = "kit.starter.";

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(kit.getMaterial())
                .setName(configFile.getString(path + "kit").equals(kit.getName()) ? "&aCurrent: &r" + kit.getDisplayName() : "&c" + kit.getDisplayName())
                .setLore("&7Click to define this kit, which",
                        "&7the player is equipped with when",
                        "&7entering the game.",
                        "",
                        "&eLeft-Click to set!").build();
    }

    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        playSuccess(player);
        configFile.getConfiguration().set(path + "kit", kit.getName());
        ChatUtil.sendMessage(player, "&a'&f" + kit.getName() + "&a' kit has been set as starter kit!");
        configFile.save();
    }
}
