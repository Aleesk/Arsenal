package dev.aleesk.arsenal.models.category;

import com.cryptomorin.xseries.XMaterial;
import com.google.common.collect.Maps;
import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.utilities.ChatUtil;
import dev.aleesk.arsenal.utilities.file.FileConfig;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

@Getter
public class CategoryManager {
    private final FileConfig categoryFile = Arsenal.get().getCategoryFile();
    private final FileConfig configFile = Arsenal.get().getConfigFile();
    private final Map<String, Category> categories = Maps.newHashMap();

    public void loadOrRefresh() {
        this.categories.clear();
        ConfigurationSection section = this.categoryFile.getConfiguration().getConfigurationSection("categories");
        if (section == null)
            return;
        for (String key : section.getKeys(false)) {
            Category category = new Category(key);
            category.setDisplayName(section.getString(key + ".icon.displayname"));
            category.setSkullOwner(section.getString(key + ".icon.skull_owner"));
            category.setMaterial(Material.valueOf(section.getString(key + ".icon.material")));
            category.setData(section.getInt(key + ".icon.data"));
            category.setGlow(section.getBoolean(key + ".icon.glow"));
            category.setEnchant(section.getBoolean(key + ".icon.enchant"));
            category.setSlot(section.getInt(key + ".icon.slot"));
            category.setDescription(section.getStringList(key + ".icon.description"));
            category.setShouldPermission(section.getBoolean(key + ".permission.enable"));
            category.setPermission(section.getString(key + ".permission.permission"));
            this.categories.put(key, category);
        }
    }
    public void create(String categoryName) {
        Category category = new Category(ChatUtil.strip(categoryName));
        category.setDisplayName(categoryName);
        category.setMaterial(XMaterial.CHEST_MINECART.parseMaterial());
        category.setData(0);
        category.setGlow(true);
        category.setEnchant(true);
        category.setSlot(-1);
        category.setDescription(Arrays.asList(
                "&8Category",
                "",
                "&This category offers a selection",
                "&7of unique kits",
                "",
                "&eClick to view!"));
        category.setShouldPermission(false);
        category.setPermission("arsenal.category." + categoryName);
        category.save();
        this.categories.put(categoryName, category);
    }

    public void delete(String categoryName) {
        Category category = getByName(categoryName);
        ConfigurationSection section = this.categoryFile.getConfiguration().getConfigurationSection("categories");
        if (section == null)
            return;
        section.set(category.getName(), null);
        this.categoryFile.save();
        this.categoryFile.reload();
        this.categories.remove(category.getName());
    }

    public void deleteAll() {
        this.categoryFile.getConfiguration().set("categories", Collections.EMPTY_MAP);
        this.categoryFile.save();
        this.categoryFile.reload();
        this.categories.clear();
    }

    public Category getCategory() {
        String category = configFile.getString("kit_gui.default_category");
        if (category != null) {
            return this.getByName(category);
        }
        return null;
    }

    public Category getByName(String name) {
        for (Map.Entry<String, Category> entry : this.categories.entrySet()) {
            if ((entry.getKey()).equalsIgnoreCase(name))
                return entry.getValue();
        }
        return null;
    }
}
