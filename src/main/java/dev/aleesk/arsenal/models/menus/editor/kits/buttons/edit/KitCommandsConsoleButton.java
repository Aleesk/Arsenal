package dev.aleesk.arsenal.models.menus.editor.kits.buttons.edit;

import com.cryptomorin.xseries.XMaterial;
import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.kit.Kit;
import dev.aleesk.arsenal.models.menus.editor.kits.KitEditMenu;
import dev.aleesk.arsenal.models.prompt.KitEditStringPrompt;
import dev.aleesk.arsenal.utilities.ChatUtil;
import dev.aleesk.arsenal.utilities.item.ItemBuilder;
import dev.aleesk.arsenal.utilities.menu.Button;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class KitCommandsConsoleButton extends Button{
    private final Kit kit;
    private final Arsenal plugin;
    private final KitEditMenu menu;

    @Override
    public ItemStack getButtonItem(Player player) {
        List<String> lore = new ArrayList<>();
        lore.add("&7Commands:");
        if (kit.getCommandsConsole().isEmpty()) {
            lore.add(" &cEmpty");
        } else {
            for (int i = 0; i < kit.getCommandsPlayer().size(); i++) {
                lore.add((menu.selectedConsoleCommand == i ? "&a➨" : "") + " &6" + (i + 1) + ". ▸ &r" + kit.getCommandsConsole().get(i));
            }
        }
        lore.add("");
        lore.add("&bRight-Click to add new command.");
        lore.add("&dMiddle-Click to remove selected command.");
        lore.add("&eLeft-Click to scroll commands.");

        return new ItemBuilder(XMaterial.COMMAND_BLOCK_MINECART.parseMaterial())
                .setName("&aConsole Commands")
                .setLore(lore)
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        if (clickType.isLeftClick()) {
            if (kit.getCommandsPlayer().isEmpty()) return;

            if (kit.getCommandsPlayer().size() == (menu.selectedConsoleCommand + 1)) {
                menu.selectedConsoleCommand = 0;
            } else {
                ++menu.selectedConsoleCommand;
            }
        } else if (clickType.isRightClick()) {
            player.closeInventory();

            new KitEditStringPrompt(kit, plugin, (string) -> {

                kit.getCommandsPlayer().add(string);
                kit.save();

                ChatUtil.sendMessage(player,"&aYou have added the player command '&r/" + string + "&a' to '&r" + kit.getName() + "&a' kit.");

            }).startPrompt(player);

            ChatUtil.sendMessage(player, "&eType '&ccancel&e' or '&cleave&e' in the chat to cancel the process.");
            ChatUtil.sendMessage(player, "&eWrite new player command for '&r" + kit.getName() + "&a' kit.");
            ChatUtil.sendMessage(player, "&eExample&6: &rbroadcast %player% has just opened the Example kit.");
        } else if (clickType.isCreativeAction()) {
            if (kit.getCommandsPlayer().isEmpty()) return;

            kit.getCommandsPlayer().remove(menu.selectedConsoleCommand);
            kit.save();

            menu.selectedConsoleCommand = 0;
        }
    }
}
