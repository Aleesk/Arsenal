package dev.aleesk.arsenal.models.menus.editor.kits.buttons.edit;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.kit.Kit;
import dev.aleesk.arsenal.models.prompt.KitEditStringPrompt;
import dev.aleesk.arsenal.utilities.ChatUtil;
import dev.aleesk.arsenal.utilities.item.ItemBuilder;
import dev.aleesk.arsenal.utilities.menu.Button;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class KitPermissionButton extends Button {
    private final Kit kit;

    private final Arsenal plugin;

    public KitPermissionButton(Kit kit, Arsenal plugin) {
        this.kit = kit;
        this.plugin = plugin;
    }

    public ItemStack getButtonItem(Player player) {
        return (new ItemBuilder(XMaterial.OAK_SIGN.parseMaterial()))
                .setName("&aPermission")
                        .setLore("&7Edit this permit to one of",
                                "&7your preference",
                                "",
                                "&7Permission: &b" + this.kit.getPermission(),
                                "&7Enable: " + (kit.isShouldPermission() ? "&aTrue" : "&cFalse"),
                                "",
                                "&bRight-Click to toggle!",
                                "&eLeft-Click to edit!").build();
    }

    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        playSuccess(player);
        if (clickType.isRightClick()) {
            kit.setShouldPermission(!this.kit.isShouldPermission());
            kit.save();
        } else if (clickType.isLeftClick()) {
            ChatUtil.sendMessage(player, "&eYou're now editing permission of '&f" + this.kit.getName() + "&e'.");
            ChatUtil.sendMessage(player, "&eType '&ccancel&e' or '&cleave&e' in the chat to cancel the process.");
            new KitEditStringPrompt(kit, this.plugin, (string) -> {
                String permission = kit.getPermission();
                kit.setPermission(string);
                kit.save();
                XSound.ENTITY_VILLAGER_YES.play(player);
                ChatUtil.sendMessage(player, "&eKit permission has been changed from '&r" + permission + "&e' to '&r" + string + "&e'.");
            }).startPrompt(player);
        }
    }
}
