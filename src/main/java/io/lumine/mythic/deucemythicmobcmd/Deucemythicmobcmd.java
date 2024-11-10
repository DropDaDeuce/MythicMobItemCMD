package io.lumine.mythic.deucemythicmobcmd;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public final class Deucemythicmobcmd extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Logger log = this.getLogger();
        getServer().getPluginManager().registerEvents(new MythicItemGenerate(), this);
        log.info("We're loaded baby!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
