package com.rappytv.labygpt;

import com.rappytv.labygpt.api.GPTMessage;
import com.rappytv.labygpt.commands.GPTCommand;
import com.rappytv.labygpt.config.GPTAddonConfig;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;
import java.util.ArrayList;

@AddonMain
public class GPTAddon extends LabyAddon<GPTAddonConfig> {

    public static final String prefix = "§8[§9LabyGPT§8] §7";
    public static final ArrayList<GPTMessage> queryHistory = new ArrayList<>();

    @Override
    protected void enable() {
        registerSettingCategory();

        registerCommand(new GPTCommand(this));
    }

    @Override
    protected Class<? extends GPTAddonConfig> configurationClass() {
        return GPTAddonConfig.class;
    }
}
