package de.rollocraft.lobbySystem.Commands;

import de.rollocraft.lobbySystem.Manager.DuelManager;

import de.rollocraft.lobbySystem.Utils.Maps.PlayerSecretKeyMap;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

public class DuelCommand implements CommandExecutor, TabCompleter {
    private DuelManager duelManager;
    private String action;
    private String secretKey;

    public DuelCommand(DuelManager duelManager) {
        this.duelManager = duelManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command");
            return true;
        }
        Player player = (Player) sender;

        Player target = Bukkit.getPlayer(args[0]);
        //Fall des Servers aufrufs
        if (args.length == 3) {
            action = args[1];
            secretKey = args[2];
        }

        //Überprüfen ob der Spieler einen Spieler angegeben hat
        if (args.length == 0) {
            player.sendMessage("Usage: /duel <player>");
            return true;
        }
        if (target == null) {
            player.sendMessage("Player not found");
            return true;
        }

        PlayerSecretKeyMap playerSecretKeyMap = PlayerSecretKeyMap.getInstance();

        // /duel CoolerSpieler -> player rollocraft, target CoolerSpieler
        // /duel rollocraft accept key -> player CoolerSpieler, target rollocraft

        SecretKey storedSecretKeyforPlayer = playerSecretKeyMap.getSecretKeyForPlayer(player);
        if (storedSecretKeyforPlayer == null) {
            player.sendMessage("No secret key found for target");
            return true;
        }
        String encodedPlayerKey = Base64.getEncoder().encodeToString(storedSecretKeyforPlayer.getEncoded());

        //erstelle die accept/decline Nachricht
        //In beiden Zenarien wird Player und Target vertauscht
        TextComponent acceptMessage = new TextComponent(ChatColor.GREEN + "[accept]");
        acceptMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/duel " + player.getName() + " accept " + encodedPlayerKey));
        TextComponent declineMessage = new TextComponent(ChatColor.RED + "[decline]");
        declineMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/duel " + player.getName() + " decline " + encodedPlayerKey));

        TextComponent[] messageComponents = new TextComponent[] {
                new TextComponent("You got a duel request from "),
                new TextComponent(target.getName()),
                new TextComponent(" "),
                acceptMessage,
                new TextComponent(" | "),
                declineMessage
        };

        if (args.length == 1){
            target.spigot().sendMessage(messageComponents);
            player.sendMessage(ChatColor.GREEN + "Duel request sent to " + target.getName());
            return true;
        }

        //Check den secret key
        SecretKey storedSecretKey = playerSecretKeyMap.getSecretKeyForPlayer(target);
        if (storedSecretKey == null) {
            player.sendMessage("No secret key found for target");
            return true;
        }
        String encodedKey = Base64.getEncoder().encodeToString(storedSecretKey.getEncoded());

        if (encodedKey == null) {
            player.sendMessage("Invalid secret key");
            return true;
        }

        if (!encodedKey.equals(secretKey)) {
            player.sendMessage("Keys didnt match");
            return true;
        }

        //Vertauschter tagert und player!!!! -> Ist so richtig und gewollt!!!!
        if ("accept".equals(action)) {
            duelManager.acceptDuel(target, player);
            player.sendMessage("lasse" + target.getName() + "alles auswählen");
            try {
                playerSecretKeyMap.setSecretKeyForPlayer(target, PlayerSecretKeyMap.generateSecretKey());
            } catch (NoSuchAlgorithmException e) {
                player.sendMessage("Error generating new secret key");
            }
        } else if ("decline".equals(action)) {
            duelManager.declineDuel(target, player);
            try {
                playerSecretKeyMap.setSecretKeyForPlayer(target, PlayerSecretKeyMap.generateSecretKey());
            } catch (NoSuchAlgorithmException e) {
                player.sendMessage("Error generating new secret key");
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return null;
        } else {
            return null;
        }
    }
}