package dev.aleesk.arsenal.models.menus.editor.settings.buttons;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.prompt.TitleStringPrompt;
import dev.aleesk.arsenal.utilities.ChatUtil;
import dev.aleesk.arsenal.utilities.file.FileConfig;
import dev.aleesk.arsenal.utilities.item.ItemBuilder;
import dev.aleesk.arsenal.utilities.menu.Button;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class TitleButton extends Button {

    private final Arsenal plugin;
    private final FileConfig configFile = Arsenal.get().getConfigFile();
    private final String path = "kit_gui.title";

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(XMaterial.NAME_TAG.parseMaterial())
                .setName("&aTitle GUI")
                .setLore("&7Change the title of the",
                        "&7kit GUI to the one you prefer.",
                        "",
                        "&7Title: &r" + configFile.getString(path),
                        "",
                        "&eClick to edit!").build();
    }

    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        ChatUtil.sendMessage(player, "&eYou're now editing title menu of Kit GUI.");
        ChatUtil.sendMessage(player, "&eType '&ccancel&e' or '&cleave&e' in the chat to cancel the process.");
        playSuccess(player);
        new TitleStringPrompt(plugin, (string) -> {
            String title = configFile.getString(path);
            configFile.getConfiguration().set(path, string);
            configFile.save();
            XSound.ENTITY_VILLAGER_YES.play(player);
            ChatUtil.sendMessage(player, "&aTitle has been changed from '&r" + title + "&e' to '&r" + string + "&e'.");
        }).startPrompt(player);
    }
}
