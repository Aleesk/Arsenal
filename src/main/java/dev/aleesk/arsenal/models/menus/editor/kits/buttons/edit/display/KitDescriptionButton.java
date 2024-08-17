package dev.aleesk.arsenal.models.menus.editor.kits.buttons.edit.display;

import com.cryptomorin.xseries.XMaterial;
import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.kit.Kit;
import dev.aleesk.arsenal.models.menus.editor.kits.KitDisplayMenu;
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
public class KitDescriptionButton extends Button {

    private final Kit kit;

    private final Arsenal plugin;

    private final KitDisplayMenu menu;

    @Override
    public ItemStack getButtonItem(Player player) {
        List<String> lore = new ArrayList<>();
        lore.add("&7If you need to edit the kit");
        lore.add("&7description you can do it very");
        lore.add("&7easily with this editor.");
        lore.add("");
        lore.add("&7Description:");

        List<String> loreDescription = this.kit.getLoreDescription();
        for (int i = 0; i < loreDescription.size(); i++) {
            lore.add((menu.selectedDescriptionLine == i ? "&a➨" : "") + " &6▸ &r" + loreDescription.get(i));
        }

        lore.add("");
        lore.add("&bRight-Click to add new line.");
        lore.add("&dMiddle-Click to remove selected line.");
        lore.add("&eLeft-Click to scroll lines.");

        return new ItemBuilder(XMaterial.PAPER.parseMaterial())
                .setName("&aDescription")
                .setLore(lore)
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        playSuccess(player);
        if (clickType.isLeftClick()) {
            if (kit.getLoreDescription().isEmpty()) return;

            if (kit.getLoreDescription().size() == (menu.selectedDescriptionLine + 1)) {
                menu.selectedDescriptionLine = 0;
            } else {
                ++menu.selectedDescriptionLine;
            }
        } else if (clickType.isCreativeAction()) {
            if (kit.getLoreDescription().isEmpty()) return;

            kit.getLoreDescription().remove(menu.selectedDescriptionLine);
            kit.save();

            menu.selectedDescriptionLine = 0;
        }
        else if (clickType.isRightClick()) {
            ChatUtil.sendMessage(player, "&aWrite new description line for '&r" + kit.getName() + "&a' kit.");
            ChatUtil.sendMessage(player, "&eType '&ccancel&e' or '&cleave&e' in the chat to cancel the process.");
            new KitEditStringPrompt(kit, plugin, (string) -> {

                kit.getLoreDescription().add(string);
                kit.save();

                ChatUtil.sendMessage(player, "&aYou have added the description line '&r" + string + "&a' from '&r" + kit.getName() + "&a' kit.");

            }).startPrompt(player);

        }
    }
}
