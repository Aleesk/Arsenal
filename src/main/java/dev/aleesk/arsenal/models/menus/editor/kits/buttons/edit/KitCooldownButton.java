package dev.aleesk.arsenal.models.menus.editor.kits.buttons.edit;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.kit.Kit;
import dev.aleesk.arsenal.models.prompt.KitEditStringPrompt;
import dev.aleesk.arsenal.utilities.ChatUtil;
import dev.aleesk.arsenal.utilities.JavaUtil;
import dev.aleesk.arsenal.utilities.TimeUtil;
import dev.aleesk.arsenal.utilities.item.ItemBuilder;
import dev.aleesk.arsenal.utilities.menu.Button;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class KitCooldownButton extends Button {
    private final Kit kit;
    private final Arsenal plugin;

    public KitCooldownButton(Kit kit, Arsenal plugin) {
        this.kit = kit;
        this.plugin = plugin;
    }

    public ItemStack getButtonItem(Player player) {
        return (new ItemBuilder(XMaterial.CLOCK.parseMaterial()))
                .setName("&aCooldown")
                        .setLore("&7Adjust the kit's cooldown according to",
                                "&7your preferences. Define the waiting",
                                "&7time between uses with a simple and",
                                "&7easy-to-follow format.",
                                "",
                                "&7Cooldown: &6"+ TimeUtil.getTimeFormatted(this.kit.getCooldown()),
                                "",
                                "&eClick to edit!").build();
    }

    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        ChatUtil.sendMessage(player, "&eYou're now editing cooldown of '&f" + this.kit.getName() + "&e'.");
        ChatUtil.sendMessage(player, "&eType '&ccancel&e' or '&cleave&e' in the chat to cancel the process.");
        ChatUtil.sendMessage(player, "");
        ChatUtil.sendMessage(player, "&eExample: &c24d 12h 30m 15s");
        playSuccess(player);
        int oldCooldown = kit.getCooldown();
        new KitEditStringPrompt(kit, this.plugin, (string) -> {
            this.kit.setCooldown(JavaUtil.formatInt(string));
            this.kit.save();
            XSound.ENTITY_VILLAGER_YES.play(player);
            ChatUtil.sendMessage(player, "&eKit cooldown has been changed from '&r" + TimeUtil.getTimeFormatted(oldCooldown) + "&e' to '&r" +
                    TimeUtil.getTimeFormatted(kit.getCooldown()) + "&e'.");
        }).startPrompt(player);
    }
}