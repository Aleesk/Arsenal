package dev.aleesk.arsenal.utilities.prompt.defaults;

import dev.aleesk.arsenal.utilities.JavaUtil;
import dev.aleesk.arsenal.utilities.prompt.Prompt;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public abstract class DoublePrompt extends Prompt<Double> {

	@Override
	public void handleFailed(Player player, String input) {
		player.sendMessage(ChatColor.RED + "Double value '" + ChatColor.WHITE + input + ChatColor.RED + "' is invalid.");
	}
	
	@Override
	public Double handleInput(Player player, String input) {
		Double doubl = JavaUtil.tryParseDouble(input);
		if (doubl == null) {
			return null;
		}
		
		return doubl;
	}
}
