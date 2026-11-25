package io.github.NoOne.nMLEffects;

import io.github.NoOne.nMLPlayerStats.statSystem.PlayerStat;

public class CustomEffect {
    private PlayerStat playerStat;
    private double change;
    private int seconds;

    public CustomEffect(PlayerStat playerStat, double change, int seconds) {
        this.playerStat = playerStat;
        this.change = change;
        this.seconds = seconds;
    }

    public PlayerStat getPlayerStat() {
        return playerStat;
    }

    public void setPlayerStat(PlayerStat playerStat) {
        this.playerStat = playerStat;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
