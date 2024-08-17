package dev.aleesk.arsenal;

import dev.aleesk.arsenal.commands.CommandManager;
import dev.aleesk.arsenal.commands.impl.ArsenalCommand;
import dev.aleesk.arsenal.commands.impl.kit.KitsCommand;
import dev.aleesk.arsenal.commands.impl.kit.admin.KitAdminCommand;
import dev.aleesk.arsenal.commands.impl.kit.admin.subcommands.*;
import dev.aleesk.arsenal.listeners.KitListener;
import dev.aleesk.arsenal.models.category.CategoryManager;
import dev.aleesk.arsenal.models.kit.KitManager;
import dev.aleesk.arsenal.user.UserListener;
import dev.aleesk.arsenal.user.UserManager;
import dev.aleesk.arsenal.utilities.ChatUtil;
import dev.aleesk.arsenal.utilities.file.FileConfig;
import dev.aleesk.arsenal.utilities.menu.ButtonListener;
import dev.aleesk.arsenal.utilities.prompt.PromptHandler;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Arsenal extends JavaPlugin {

    private FileConfig configFile, languageFile, kitFile, categoryFile, usersFile;

    private KitManager kitManager;
    private CategoryManager categoryManager;
    private UserManager userManager;

    private PromptHandler promptHandler;

    @Override
    public void onEnable() {
        this.log(ChatUtil.LONG_LINE);
        this.log("");
        this.log("     &aName: &b" + this.getName());
        this.log("     &aVersion: &e" + this.getDescription().getVersion());
        this.log("     &aAuthors: &6" + this.getDescription().getAuthors());
        this.log("");
        this.log(ChatUtil.LONG_LINE);
        this.registerConfigs();
        this.registerManagers();
        this.registerListeners();
        this.registerCommands();
    }

    @Override
    public void onDisable() {
        if (userManager != null) this.userManager.onDisable();
    }

    public void onReload() {
        this.configFile.reload();
        this.languageFile.reload();
        this.kitFile.reload();
        this.categoryFile.reload();
        this.usersFile.reload();
        this.loadManagers();
    }

    public void registerConfigs() {
        this.configFile = new FileConfig(this, "config.yml");
        this.languageFile = new FileConfig(this, "language.yml");
        this.kitFile = new FileConfig(this, "kit/kit.yml");
        this.categoryFile = new FileConfig(this, "kit/category.yml");
        this.usersFile = new FileConfig(this, "users.yml");
    }

    public void registerManagers() {
        this.categoryManager = new CategoryManager();
        this.kitManager = new KitManager(this);
        this.userManager = new UserManager(this);
        this.promptHandler = new PromptHandler(this);
        this.loadManagers();
    }

    public void loadManagers(){
        this.categoryManager.loadOrRefresh();
        this.kitManager.loadOrRefresh();
    }

    public void registerListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new KitListener(), this);
        pluginManager.registerEvents(new UserListener(this), this);
        pluginManager.registerEvents(new ButtonListener(), this);
    }

    public void registerCommands() {
        CommandManager commandManager = new CommandManager(this);
        commandManager.registerCommands(new ArsenalCommand(this));
        commandManager.registerCommands(new KitsCommand(this));
        commandManager.registerCommands(new KitAdminCommand());
        commandManager.registerCommands(new KitEditorCommand(this));
        commandManager.registerCommands(new KitSettingsCommand(this));
        commandManager.registerCommands(new KitGiveCommand(this));
        commandManager.registerCommands(new KitCooldownCommand(this));
        commandManager.registerCommands(new KitResetCommand(this));
        commandManager.registerCommands(new KitListCommand(this));
    }

    public void log(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatUtil.translate(message));
    }

    public static Arsenal get() {
        return Arsenal.getPlugin(Arsenal.class);
    }

}