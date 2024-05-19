package de.rollocraft.lobbySystem.Minecraft.Commands;

import de.rollocraft.lobbySystem.Minecraft.Utils.Enum.CreatorSettings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static de.rollocraft.lobbySystem.Minecraft.Utils.BookCreator.generateBook;

public class CreatorSettingsCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player p)) return false;
        p.openBook(generateBook(CreatorSettings.Pages.p1));
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        return List.of();
    }
}
