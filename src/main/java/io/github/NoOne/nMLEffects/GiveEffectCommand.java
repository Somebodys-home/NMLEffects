package io.github.NoOne.nMLEffects;

import io.github.NoOne.nMLCommands.CompletionFormatter;
import io.github.NoOne.nMLPlayerStats.statSystem.PlayerStat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GiveEffectCommand implements CommandExecutor, TabCompleter, CompletionFormatter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (sender instanceof Player player) {
            String stat = args[0];
            double value = Double.parseDouble(args[1]);
            int seconds = Integer.parseInt(args[2]);
            PlayerStat playerStat = PlayerStat.fromString(stat);

            if (playerStat == null) {
                player.sendMessage("§c" + stat + " is not a stat.");
            } else {
                CustomEffectTracker.addCustomEffect(player, new CustomEffect(playerStat, value, seconds));
            }
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        ArrayList<String> completions = new ArrayList<>(){{
            switch (args.length) {
                case 1 -> {
                    for (PlayerStat stat : PlayerStat.values()) {
                        add(stat.toString().toLowerCase());
                    }
                }
                case 2 -> add("<value>");
                case 3 -> add("<seconds>");
            }
        }};

        return formatCompletions(completions, args);
    }
}
