package dev.aleesk.arsenal.models.menus.editor.kits;

import com.cryptomorin.xseries.XMaterial;
import com.google.common.collect.Maps;
import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.kit.Kit;
import dev.aleesk.arsenal.utilities.ChatUtil;
import dev.aleesk.arsenal.utilities.item.ItemBuilder;
import dev.aleesk.arsenal.utilities.menu.Button;
import dev.aleesk.arsenal.utilities.menu.Menu;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class KitLootMenu extends Menu {

    private final Kit kit;
    private final ItemStack[] armor;
    private final ItemStack[] contents;
    private final ItemStack offHand;
    private final Arsenal plugin;

    public KitLootMenu(Kit kit, Arsenal plugin) {
        this.kit = kit;
        this.armor = kit.getArmor().clone();
        this.contents = kit.getContents().clone();
        this.offHand = kit.getOffHand() != null ? kit.getOffHand().clone() : null;
        this.plugin = plugin;
    }

    public int getSize() {
        return 6 * 9;
    }

    @Override
    public String getTitle(Player player) {
        return kit.getName() + " Kit Loot";
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();
        for (int i = 0; i < this.contents.length; i++) {
            if (this.contents[i] != null)
                buttons.put(i, new KitItemStack(this.contents[i].clone()));
        }

        for(int i = 36; i < getSize() - 2; i++) {
            if (i > 44 && i < 49) continue;
            buttons.put(i, getPlaceholderButton());
        }

        if (this.armor.length != 0) {
            ItemStack helmet = this.armor[0];
            if (helmet != null)
                buttons.put(48, new KitItemStack(helmet.clone()));
            ItemStack chestplate = this.armor[1];
            if (chestplate != null)
                buttons.put(47, new KitItemStack(chestplate.clone()));
            ItemStack leggings = this.armor[2];
            if (leggings != null)
                buttons.put(46, new KitItemStack(leggings.clone()));
            ItemStack boots = this.armor[3];
            if (boots != null)
                buttons.put(45, new KitItemStack(boots.clone()));
        }

        if(this.kit.getOffHand() != null) buttons.put(getSize() - 4, new KitItemStack(offHand.clone()));
        buttons.put(getSize() - 2, new ConfirmLoot(this.kit, this.plugin));
        buttons.put(getSize() - 1, new CancelLoot(this.kit, this.plugin));
        return buttons;
    }

    public boolean isCancelPlayerInventory() {
        return false;
    }

    private static class KitItemStack extends Button {
        private final ItemStack itemStack;

        public KitItemStack(ItemStack itemStack) {
            this.itemStack = itemStack;
        }

        public ItemStack getButtonItem(Player player) {
            return this.itemStack;
        }

        public boolean shouldCancel(Player player, int slot, ClickType clickType) {
            return false;
        }
    }

    @RequiredArgsConstructor
    private static class ConfirmLoot extends Button {
        private final Kit kit;
        private final Arsenal plugin;

        public ItemStack getButtonItem(Player player) {
            return (new ItemBuilder(XMaterial.LIME_DYE.parseMaterial()))
                    .setData(10)
                    .setName("&aConfirm Loot")
                    .build();
        }

        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            ItemStack[] armor = new ItemStack[4];
            ItemStack[] contents = new ItemStack[36];

            Inventory inventory = player.getOpenInventory().getTopInventory();

            for (int i = 48; i > 44; i--) {
                ItemStack item = inventory.getItem(i);
                int relativeIndex = 48 - i;
                if (item != null && !item.getType().equals(Material.AIR)) {
                    armor[relativeIndex] = item.clone();
                } else {
                    armor[relativeIndex] = null;
                }
            }

            for (int i = 0; i < 36; i++) {
                ItemStack item = inventory.getItem(i);
                if (item != null && !item.getType().equals(Material.AIR)) {
                    contents[i] = item.clone();
                } else {
                    contents[i] = null;
                }
            }
            if (this.kit.getOffHand() != null) this.kit.setOffHand(inventory.getItem(50));

            this.kit.setArmor(armor);
            this.kit.setContents(contents);
            this.kit.save();
            ChatUtil.sendMessage(player, "&a'&f" + this.kit.getName() + "&a' kit Loot has been updated.");
            playSave(player);
            new KitEditMenu(this.kit, this.plugin).openMenu(player);
        }
    }

    @RequiredArgsConstructor
    private static class CancelLoot extends Button {

        private final Kit kit;
        private final Arsenal plugin;

        public ItemStack getButtonItem(Player player) {
            return (new ItemBuilder(XMaterial.RED_DYE.parseMaterial()))
                    .setData(1)
                    .setName("&cCancel Process")
                    .build();
        }

        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            playFail(player);
            new KitEditMenu(this.kit, this.plugin).openMenu(player);
        }
    }
}
