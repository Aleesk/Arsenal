package dev.aleesk.arsenal.commands.impl.kit.admin.subcommands;

import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.utilities.ChatUtil;
import dev.aleesk.arsenal.commands.BaseCommand;
import dev.aleesk.arsenal.commands.Command;
import dev.aleesk.arsenal.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class KitResetCommand extends BaseCommand {
    private final Arsenal plugin;

    public KitResetCommand(Arsenal plugin) {
        this.plugin = plugin;
    }

    @Command(name = "kitadmin.reset", permission = "arsenal.command.admin.reset", aliases = {"kita.reset"}, inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String label = command.getLabel().replace(".reset", "");
        String[] args = command.getArgs();

        if (args.length < 1) {
            ChatUtil.sendMessage(sender, "&cUsage: /" + label + " reset <player|all>");
            return;
        }
        if (args[0].equalsIgnoreCase("all")) {
            if (command.isPlayer()) {
                ChatUtil.sendMessage(sender, "&cOnly the console can run this command.");
                return;
            }
            this.plugin.getUserManager().getDatabase().resetAll(sender);
        } else {
            OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
            if (!player.hasPlayedBefore()) {
                ChatUtil.sendMessage(sender, "&cThat player could not be found!");
                return;
            }
            this.plugin.getUserManager().getDatabase().reset(sender, player, false);
        }
    }
}
