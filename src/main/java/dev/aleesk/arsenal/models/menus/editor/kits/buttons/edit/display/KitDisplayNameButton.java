package dev.aleesk.arsenal.models.menus.editor.kits.buttons.edit.display;

import com.cryptomorin.xseries.XSound;
import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.kit.Kit;
import dev.aleesk.arsenal.models.prompt.KitEditStringPrompt;
import dev.aleesk.arsenal.utilities.ChatUtil;
import dev.aleesk.arsenal.utilities.item.ItemBuilder;
import dev.aleesk.arsenal.utilities.menu.Button;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class KitDisplayNameButton extends Button {
    private final Kit kit;

    private final Arsenal plugin;

    public ItemStack getButtonItem(Player player) {
        return (new ItemBuilder(com.cryptomorin.xseries.XMaterial.NAME_TAG.parseMaterial()))
                .setName("&aDisplayname")
                .setLore("&7Customize the Displayname of this kit",
                        "&7according to your preferences.",
                        "",
                        "&7Displayname: &r" + kit.getDisplayName(),
                        "",
                        "&eClick to edit!.").build();
    }

    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        ChatUtil.sendMessage(player, "&eYou're now editing name of '&f" + this.kit.getName() + "&e' kit.");
        ChatUtil.sendMessage(player, "&eType '&ccancel&e' or '&cleave&e' in the chat to cancel the process.");
        playSuccess(player);
        new KitEditStringPrompt(kit, plugin, (string) -> {
            String name = kit.getDisplayName();
            kit.setDisplayName(string);
            kit.save();
            XSound.ENTITY_VILLAGER_YES.play(player);
            ChatUtil.sendMessage(player, "&aKit Displayname has been changed from '&r" + name + "&e' to '&r" + string + "&e'.");
        }).startPrompt(player);
    }
}
