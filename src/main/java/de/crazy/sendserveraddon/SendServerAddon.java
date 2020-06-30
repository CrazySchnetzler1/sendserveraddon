package de.crazy.sendserveraddon;

import de.crazy.sendserveraddon.commands.HelpCommand;
import de.crazy.sendserveraddon.commands.SendCommand;
import de.crazy.sendserveraddon.commands.ShortcutCommand;
import net.labymod.api.EventManager;
import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.utils.ModColor;

import java.util.List;

public class SendServerAddon extends LabyModAddon {
    public static SendServerAddon addon;

    public static String Prefix = ModColor.cl('7') + "[" + ModColor.cl('6') + "SendServerAddon" + ModColor.cl('7') + "] ";
    private Config config;

    @Override
    public void onEnable() {
        addon = this;
        System.out.println("[Send-Server-Addon] Addon loaded.");
        this.config = new Config("SendServerAddon");
        init();
    }

    @Override
    public void loadConfig() {

    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {

    }

    private void init() {
        EventManager eventManager = getApi().getEventManager();
        eventManager.register(new SendCommand());
        eventManager.register(new ShortcutCommand());
        eventManager.register(new HelpCommand());
    }

    public static SendServerAddon getAddon() {return addon;}

    public Config getAddonConfig() {return config;}

    public void addShortcut(String key, String server) {
        this.config.getConfigAsJsonObject().get("shortcuts").getAsJsonObject().addProperty(key, server);
        this.config.save();
    }

    public boolean removeShortcut(String key) {
        if (!this.config.getConfigAsJsonObject().get("shortcuts").getAsJsonObject().has(key))
            return false;
        this.config.getConfigAsJsonObject().get("shortcuts").getAsJsonObject().remove(key);
        this.config.save();
        return true;
    }
}
