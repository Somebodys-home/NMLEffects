package io.github.NoOne.nMLEffects;

import io.github.NoOne.nMLConfig.ConfigTemplate;
import io.github.NoOne.nMLEffects.commands.GiveEffectCommand;
import io.github.NoOne.nMLEffects.commands.RemoveEffectCommand;
import io.github.NoOne.nMLEffects.saveEffects.SaveEffectsListener;
import io.github.NoOne.nMLEffects.saveEffects.SavedEffectsManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class NMLEffects extends JavaPlugin {
    private static NMLEffects instance;
    private ConfigTemplate savedEffectsConfig;
    private SavedEffectsManager savedEffectsManager;

    @Override
    public void onEnable() {
        instance = this;

        savedEffectsConfig = new ConfigTemplate(this, "effects");
        savedEffectsConfig.loadConfig();

        savedEffectsManager = new SavedEffectsManager(savedEffectsConfig);

        CustomEffectTracker.showEffectsTask();
        CustomEffectTracker.tickDownEffectsTask();

        getCommand("giveEffect").setExecutor(new GiveEffectCommand());
        getCommand("removeEffect").setExecutor(new RemoveEffectCommand());
        getServer().getPluginManager().registerEvents(new SaveEffectsListener(this), this);
    }

    @Override
    public void onDisable() {
        savedEffectsManager.saveEffectsToConfig(CustomEffectTracker.getCustomEffects());
        savedEffectsConfig.saveConfig();
    }

    public static NMLEffects getInstance() {
        return instance;
    }

    public SavedEffectsManager getSavedEffectsManager() {
        return savedEffectsManager;
    }
}
