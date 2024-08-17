package dev.aleesk.arsenal.models.menus.view;

import com.google.common.collect.Maps;
import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.category.Category;
import dev.aleesk.arsenal.models.kit.Kit;
import dev.aleesk.arsenal.utilities.menu.Button;
import dev.aleesk.arsenal.utilities.menu.Menu;
import dev.aleesk.arsenal.utilities.menu.buttons.BackButton;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class KitPreviewMenu extends Menu {

    private final Kit kit;
    private final ItemStack[] armor;
    private final ItemStack[] contents;
    private final ItemStack offHand;
    private final Arsenal plugin;

    public KitPreviewMenu(Kit kit, Arsenal plugin) {
        this.kit = kit;
        this.armor = kit.getArmor().clone();
        this.contents = kit.getContents().clone();
        this.offHand = kit.getOffHand() != null ? kit.getOffHand().clone() : null;
        this.plugin = plugin;
    }

    public String getTitle(Player player) {
        return this.kit.getName() + " Kit Preview";
    }

    public int getSize() {
        return 6 * 9;
    }

    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();
        for (int i = 0; i < this.contents.length; i++) {
            if (this.contents[i] != null)
                buttons.put(i, new KitItemStack(this.contents[i].clone()));
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
        Category category = plugin.getCategoryManager().getCategory();
        for(int i = 36; i < getSize() - 1; i++) {
            if (i > 44 && i < 49) {
                continue;
            }
            buttons.put(i, getPlaceholderButton());
        }
        buttons.put(getSize() - 1, new BackButton(new KitGUI(plugin, category)));
        if(this.kit.getOffHand() != null) buttons.put(getSize() - 4, new KitItemStack(offHand.clone()));
        return buttons;
    }

    private static class KitItemStack extends Button {
        private final ItemStack itemStack;

        public KitItemStack(ItemStack itemStack) {
            this.itemStack = itemStack;
        }

        public ItemStack getButtonItem(Player player) {
            return this.itemStack;
        }

    }
}
