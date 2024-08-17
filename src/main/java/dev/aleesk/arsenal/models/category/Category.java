package dev.aleesk.arsenal.models.category;

import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.utilities.file.FileConfig;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

@Getter @Setter
public class Category {

    private final FileConfig categoryFile = Arsenal.get().getCategoryFile();
    private final String name;
    private String displayName;
    private String skullOwner;
    private Material material;
    private int data;
    private int slot;
    private boolean glow;
    private boolean enchant;
    private List<String> description;
    private String permission;
    private boolean shouldPermission;

    public Category(String name) {
        this.name = name;
    }

    public void save() {
        ConfigurationSection section = this.categoryFile.getConfiguration().getConfigurationSection("categories");
        if (section == null)
            section = this.categoryFile.getConfiguration().createSection("categories");
        section.set(this.name + ".icon.displayname", this.displayName);
        section.set(this.name + ".icon.skull_owner", this.skullOwner);
        section.set(this.name + ".icon.description", this.description);
        section.set(this.name + ".icon.material", this.material.name());
        section.set(this.name + ".icon.data", this.data);
        section.set(this.name + ".icon.glow", this.glow);
        section.set(this.name + ".icon.enchant", this.enchant);
        section.set(this.name + ".icon.slot", this.slot);
        section.set(this.name + ".permission.enable", this.shouldPermission);
        section.set(this.name + ".permission.permission", this.permission);
        this.categoryFile.save();
        this.categoryFile.reload();
    }
}
