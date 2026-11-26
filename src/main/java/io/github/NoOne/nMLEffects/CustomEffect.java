package io.github.NoOne.nMLEffects;

import io.github.NoOne.nMLItems.enums.ItemStat;
import io.github.NoOne.nMLItems.itemDictionary.Food;
import io.github.NoOne.nMLPlayerStats.statSystem.PlayerStat;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class CustomEffect {
    private PlayerStat playerStat;
    private double change;
    private int seconds;
    private int secondsLeft;
    private boolean markedForRemoval;

    public CustomEffect(PlayerStat playerStat, double change, int seconds) {
        this.playerStat = playerStat;
        this.change = change;
        this.seconds = seconds;
        secondsLeft = seconds;
        markedForRemoval = false;
    }

    public void tickDown() {
        secondsLeft--;

        if (secondsLeft == 0) {
            markedForRemoval = true;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CustomEffect customEffect) {
            return customEffect.playerStat.equals(this.playerStat) && customEffect.change == this.change && customEffect.seconds == this.seconds;
        } else {
            return false;
        }
    }

    public Component toComponent() {
        ItemStat itemStat = playerStat.toItemStat();
        NamedTextColor namedTextColor = switch (itemStat.toChatColor()) {
            case "§f" -> NamedTextColor.WHITE;
            case "§a" -> NamedTextColor.GREEN;
            case "§4" -> NamedTextColor.DARK_RED;
            case "§c" -> NamedTextColor.RED;
            case "§b" -> NamedTextColor.AQUA;
            case "§2" -> NamedTextColor.DARK_GREEN;
            case "§e" -> NamedTextColor.YELLOW;
            case "§7" -> NamedTextColor.GRAY;
            case "§5" -> NamedTextColor.DARK_PURPLE;
            case "§9" -> NamedTextColor.BLUE;
            default -> NamedTextColor.DARK_GRAY;
        };

        String changeString;

        if (change < 0) {
            changeString = "-";
        } else {
            changeString = "+";
        }

        if (this.change == (int) this.change) {
            changeString += String.valueOf((int) this.change);
        } else {
            changeString += String.valueOf(this.change);
        }

        String timeString;

        if (seconds > 0) { // for effects with a timer on it
            timeString = "(" + Food.formatTime(secondsLeft) + ")";
        } else { // for infinite effects
            timeString = "(∞)";
        }

        return Component
                .text(changeString + " " + itemStat + " " + itemStat.toEmoji() + " ", namedTextColor)
                .append(
                        Component.text(timeString, NamedTextColor.DARK_GRAY)
                );
    }

    public PlayerStat getPlayerStat() {
        return playerStat;
    }

    public double getChange() {
        return change;
    }

    public boolean isMarkedForRemoval() {
        return markedForRemoval;
    }
}
