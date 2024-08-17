package dev.aleesk.arsenal.models.menus.editor.kits.buttons;

import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.kit.Kit;
import dev.aleesk.arsenal.models.menus.editor.kits.KitEditMenu;
import dev.aleesk.arsenal.utilities.ChatUtil;
import dev.aleesk.arsenal.utilities.item.ItemBuilder;
import dev.aleesk.arsenal.utilities.menu.Button;
import dev.aleesk.arsenal.utilities.menu.menus.ConfirmMenu;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class KitInfoButton extends Button {

    private final Kit kit;
    private final Arsenal plugin;
    private final boolean editable;

    public ItemStack getButtonItem(Player player) {
        List<String> lore = new ArrayList<>();
        lore.add("&7Displayname: &r" + kit.getDisplayName());
        lore.add("&7Material: &b" + kit.getMaterial().name());
        lore.add("&7Data: &6" + kit.getData());
        lore.add("&7Glow: " + getEnable(kit.isGlow()));
        lore.add("&7Hide Enchant: " + getEnable(kit.isEnchant()));
        lore.add("&7Description lines:");
        if (kit.getLoreDescription().isEmpty()) {
            lore.add(" &cEmpty");
        } else {
            for (int i = 0; i < kit.getLoreDescription().size(); i++) {
                lore.add(" &6" + (i + 1) + ". ▸ &r" + kit.getLoreDescription().get(i));
            }
        }
        lore.add("&7Permission: &b" + kit.getPermission());
        lore.add("&7Permission Enable: " + (kit.isShouldPermission() ? "&aTrue" : "&cFalse"));
        lore.add("&7Slot: &6" + kit.getSlot());
        lore.add("&7Category: &b" + (kit.getCategory() != null ? kit.getCategory().getName() : "&cNone"));
        lore.add("&7Cooldown: &6" + kit.getCooldown());
        lore.add("&7Sound: &b" + (kit.getSound() != null ? ChatUtil.toReadable(kit.getSound()) : "&cNone"));
        lore.add("&7Show: " + (kit.isShow() ? "&aTrue" : "&cFalse"));
        lore.add("&7Commands Player:");
        if (kit.getCommandsPlayer().isEmpty()) {
            lore.add(" &cEmpty");
        } else {
            for (int i = 0; i < kit.getCommandsPlayer().size(); i++) {
                lore.add(" &6" + (i + 1) + ". &e▸ &r" + kit.getCommandsPlayer().get(i));
            }
        }
        lore.add("&7Commands Console:");
        if (kit.getCommandsConsole().isEmpty()) {
            lore.add(" &cEmpty");
        } else {
            for (int i = 0; i < kit.getCommandsConsole().size(); i++) {
                lore.add(" &6" + (i + 1) + ". &e▸ &r" + kit.getCommandsConsole().get(i));
            }
        }
        if (editable) {
            lore.add("");
            lore.add("&bRight-Click to delete!");
            lore.add("&eClick to edit!");
        }
        return (new ItemBuilder(this.kit.getMaterial()))
                .setData(this.kit.getData())
                .setName("&aKit: &r" +this.kit.getDisplayName())
                .setEnchanted(this.kit.isGlow(), this.kit.isEnchant())
                .setSkullOwner(this.kit.getSkullOwner())
                .setLore(lore)
                .build();
    }

    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        if (editable) {
            playNeutral(player);
            if (clickType.isRightClick()) {
                ConfirmMenu confirmMenu = new ConfirmMenu(ChatUtil.translate("&cDelete Kit: &r" + this.kit.getDisplayName()), confirmed -> {
                    if (confirmed) {
                        ChatUtil.sendMessage(player, ChatUtil.translate("&a'&f" + this.kit.getName() + "&f' kit deleted."));
                        plugin.getKitManager().delete(this.kit.getName());
                        playSuccess(player);
                    } else {
                        ChatUtil.sendMessage(player, ChatUtil.translate("&cKit delete has been cancelled."));
                        playFail(player);
                    }
                });
                confirmMenu.openMenu(player);
            } else {
                (new KitEditMenu(kit, plugin)).openMenu(player);
            }
        }
    }

    private String getEnable(boolean state) {
        return state ? "&aEnable" : "&cDisable";
    }
}
