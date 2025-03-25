package com.rappytv.labygpt.core.config;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget.ButtonSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget.TextFieldSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.util.MethodOrder;

public class OpenAISubConfig extends Config {

    // Define the URL for OpenAI and self-hosted AI
    @TextFieldSetting
    @SpriteSlot(size = 32, x = 2)
    private final ConfigProperty<String> openAIUrl = new ConfigProperty<>("https://api.openai.com/v1");
    
    @TextFieldSetting
    @SpriteSlot(size = 32, x = 2)
    private final ConfigProperty<String> selfHostUrl = new ConfigProperty<>("http://localhost:5000/v1"); // Example for self-hosted AI
    
    @TextFieldSetting
    @SpriteSlot(size = 32, x = 2)
    private final ConfigProperty<String> bearer = new ConfigProperty<>("");
    
    @SwitchSetting
    @SpriteSlot(size = 32, x = 3)
    private final ConfigProperty<Boolean> shareUsername = new ConfigProperty<>(true);
    private final ConfigProperty<Boolean> useOpenAI = new ConfigProperty<>(true); // Toggle between OpenAI and Self-Host

    @MethodOrder(after = "bearer")
    @ButtonSetting
    @SpriteSlot(size = 32, y = 1)
    public void bearerHelp(Setting setting) {
        Laby.labyAPI().minecraft().chatExecutor().openUrl("https://platform.openai.com/account/api-keys");
    }

    public String apiurl() {
        // Toggle the URL based on the switch
        return useOpenAI.get() ? openAIUrl.get() : selfHostUrl.get();
    }
    
    public String bearer() {
        return bearer.get();
    }
    public Boolean shareUsername() {
        return shareUsername.get();
    }
    public Boolean useOpenAI() {
        return useOpenAI.get();
    }
}
