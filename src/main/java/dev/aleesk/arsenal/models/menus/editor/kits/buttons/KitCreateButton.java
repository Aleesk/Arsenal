package dev.aleesk.arsenal.models.menus.editor.kits.buttons;

import com.cryptomorin.xseries.XSound;
import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.kit.Kit;
import dev.aleesk.arsenal.models.kit.KitManager;
import dev.aleesk.arsenal.models.prompt.KitCreateStringPrompt;
import dev.aleesk.arsenal.utilities.ChatUtil;
import dev.aleesk.arsenal.utilities.item.ItemBuilder;
import dev.aleesk.arsenal.utilities.menu.Button;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

@RequiredArgsConstructor
public class KitCreateButton extends Button {

    private final Arsenal plugin;

    public ItemStack getButtonItem(Player player) {
        return (new ItemBuilder(com.cryptomorin.xseries.XMaterial.WRITABLE_BOOK.parseMaterial()))
                .setName("&aCreate a Kit")
                .setLore(Arrays.asList(
                        "&7Create your own kits with this kit builder",
                        "&7that designs kits with default features,",
                        "&7but you can edit them.",
                        "",
                        "&eClick to create a new kit!")).build();
    }

    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        KitManager kitManager = this.plugin.getKitManager();
        playSuccess(player);
        new KitCreateStringPrompt(this.plugin, (string) -> {
            Kit kit = kitManager.getByName(string);
            if (kit != null) {
                ChatUtil.sendMessage(player, "&c'&f" + kit.getName() + "&c' kit already created.");
                XSound.ENTITY_VILLAGER_NO.play(player);
                return;
            }
            kitManager.create(string);
            XSound.ENTITY_VILLAGER_YES.play(player);
            ChatUtil.sendMessage(player, "&a'&f" + string + "&a' kit has been create.");
        }).startPrompt(player);
    }
}
