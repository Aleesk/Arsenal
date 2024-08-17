package dev.aleesk.arsenal.utilities.prompt;

import dev.aleesk.arsenal.Arsenal;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public abstract class Prompt<T> {

	public static Prompt<?> END_PROMPT = null;
	
	public abstract void handleBegin(Player player);
	
	public abstract void handleCancel(Player player);
	
	public abstract void handleFailed(Player player, String input);
	
	public abstract T handleInput(Player player, String input);
	
	public abstract Prompt acceptInput(Player player, T value);
	
	public void startPrompt(Player player) {
		startPrompt(player, TimeUnit.SECONDS.toMillis(20L));
	}
	
	public void startPrompt(Player player, long timeout) {
		PromptHandler promptHandler = Arsenal.get().getPromptHandler();
		promptHandler.startPrompt(player, this, timeout);
	}
}
