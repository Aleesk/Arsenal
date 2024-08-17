package dev.aleesk.arsenal.commands.impl.kit.admin.subcommands;

import dev.aleesk.arsenal.Arsenal;
import dev.aleesk.arsenal.models.kit.Kit;
import dev.aleesk.arsenal.utilities.ChatUtil;
import dev.aleesk.arsenal.commands.BaseCommand;
import dev.aleesk.arsenal.commands.Command;
import dev.aleesk.arsenal.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitGiveCommand extends BaseCommand {
   private final Arsenal plugin;
    public KitGiveCommand(Arsenal plugin) {
        this.plugin = plugin;
    }

    @Command(name = "kitadmin.give", permission = "arsenal.command.admin.give", aliases = {"kita.give"})
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String label = command.getLabel().replace(".give", "");
        String[] args = command.getArgs();
        if (args.length < 2) {
            ChatUtil.sendMessage(sender, "&cUsage: /" + label + " give <kit> <player>");
            return;
        }
        String kitName = args[0];
        Kit kit = this.plugin.getKitManager().getByName(kitName);
        if (kit == null) {
            ChatUtil.sendMessage(sender, "&cKit '" + kitName + "' not found.");
            return;
        }
        Player player = Bukkit.getPlayer(args[1]);
        if (player == null) {
            ChatUtil.sendMessage(sender, "&cPlayer '" + args[0] + "' not found.");
            return;
        }
        kit.give(player, true);
    }
}
