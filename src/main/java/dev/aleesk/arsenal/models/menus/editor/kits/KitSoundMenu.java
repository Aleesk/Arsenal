package dev.aleesk.arsenal.models.menus.editor.kits;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import com.google.common.collect.Maps;
import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.kit.Kit;
import dev.aleesk.arsenal.utilities.ChatUtil;
import dev.aleesk.arsenal.utilities.item.ItemBuilder;
import dev.aleesk.arsenal.utilities.menu.Button;
import dev.aleesk.arsenal.utilities.menu.buttons.BackButton;
import dev.aleesk.arsenal.utilities.menu.buttons.PageButton;
import dev.aleesk.arsenal.utilities.menu.pagination.PaginatedMenu;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class KitSoundMenu extends PaginatedMenu {

    private final Kit kit;

    private final Arsenal plugin;

    public KitSoundMenu(Kit kit, Arsenal plugin){
        this.kit = kit;
        this.plugin = plugin;
    }

    public String getPrePaginatedTitle(Player player) { return this.kit.getName() + " Kit Sound"; }

    public int getSize() { return 6 * 9; }

    public int getMaxItemsPerPage(Player player) {
        return 5 * 9;
    }

    public Map<Integer, Button> getAllPagesButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();
        AtomicInteger count = new AtomicInteger();
        for (XSound sound : XSound.values()) {
            if (sound.isSupported()) {
                buttons.put(count.getAndIncrement(), new SoundButton(sound, kit, plugin));
            }
        }
        return buttons;
    }

    public Map<Integer, Button> getGlobalButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();
        buttons.put(0, new PageButton(-1, this));
        buttons.put(3, new BackButton(new KitEditMenu(this.kit, plugin)));
        buttons.put(5, new SoundInfoButton(this.kit));
        buttons.put(8, new PageButton(1, this));
        return buttons;
    }

    public boolean isUpdateAfterClick() {
        return true;
    }

    private static class SoundButton extends Button {
        private final XSound sound;
        private final Kit kit;
        private final Arsenal plugin;

        public SoundButton(XSound sound, Kit kit, Arsenal plugin) {
            this.sound = sound;
            this.kit = kit;
            this.plugin = plugin;
        }

        public ItemStack getButtonItem(Player player) {
            return (new ItemBuilder(XMaterial.NOTE_BLOCK.parseMaterial()))
                    .setName(this.kit.getSound() != null && this.kit.getSound().equals(this.sound.name()) ? "&aCurrent: &f" + ChatUtil.toReadable(this.kit.getSound()) : "&a" + ChatUtil.toReadable(this.sound.name()))
                    .setLore("&7click to listen or assign the sound",
                            "&7to the kit, so that it can be heard",
                            "&7when equipping.",
                            "",
                            "&bRight-Click to listen!",
                            "&eClick to set!").build();
        }

        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            if (clickType.isRightClick()) {
                this.sound.play(player);
            } else {
                this.kit.setSound(sound.name());
                this.kit.save();
                new KitEditMenu(kit, plugin).openMenu(player);
            }
        }
    }

    @RequiredArgsConstructor
    private static class SoundInfoButton extends Button {

        private final Kit kit;

        @Override
        public ItemStack getButtonItem(Player player) {
            return (new ItemBuilder(Material.BARRIER))
                    .setName("&aRemove Sound")
                    .setLore("&4Warning: &cif you do this, you will",
                            "&cnot be able to undo changes, but you",
                            "&ccan still assign the sound again.",
                            "",
                            "&7Sound: &b" + (kit.getSound() != null ? ChatUtil.toReadable(kit.getSound()) : "&cNone"),
                            "",
                            "&eClick to remove!").build();
        }

        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            playSuccess(player);
            this.kit.setSound(null);
            this.kit.save();
        }
    }
}

