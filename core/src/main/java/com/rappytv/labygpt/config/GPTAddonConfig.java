package com.rappytv.labygpt.config;

import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.property.ConfigProperty;

@ConfigName("settings")
public class GPTAddonConfig extends AddonConfig {

    @SwitchSetting
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);
    private final OpenAISubConfig openAI = new OpenAISubConfig();
    private final GPTSubConfig gpt = new GPTSubConfig();
    @SwitchSetting
    private final ConfigProperty<Boolean> saveHistory = new ConfigProperty<>(true);

    @Override
    public ConfigProperty<Boolean> enabled() {
        return enabled;
    }
    public OpenAISubConfig openAI() {
        return openAI;
    }
    public GPTSubConfig gpt() {
        return gpt;
    }
    public Boolean saveHistory() {
        return saveHistory.get();
    }
}
