package net.begad666.bc.plugin.customprotocolsettings.features;

import net.begad666.bc.plugin.customprotocolsettings.Main;
import net.begad666.bc.plugin.customprotocolsettings.utils.Config;
import net.begad666.bc.plugin.customprotocolsettings.utils.MainUtils;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.ArrayList;

public class DisconnectNotAllowedUsers implements Listener {

    @EventHandler(priority = 32)
    public void beforeLoginChecks(PreLoginEvent event) {
        int protocol = event.getConnection().getVersion();
        ArrayList<Integer> allowedprotocols = (ArrayList<Integer>) Config.getconfig().getIntList("settings.allowed-protocols");
        ArrayList<String> allowedplayers = (ArrayList<String>) Config.getconfig().getStringList("allowed-players");
        if (Config.getconfig().getBoolean("settings.maintenance-enabled")) {
            if (Main.OnlineMode ? allowedplayers.contains(event.getConnection().getUniqueId().toString()) : allowedplayers.contains(event.getConnection().getName())) {
                if (allowedprotocols.contains(protocol)) {
                    //Ready to Go
                } else {
                    event.getConnection().disconnect(new TextComponent(MainUtils.replaceall(Config.getconfig().getString("messages.not-supported-client-message"))));
                    event.setCancelled(true);
                }
            } else {
                event.getConnection().disconnect(new TextComponent(MainUtils.replaceall(Config.getconfig().getString("messages.maintenance-message"))));
                event.setCancelled(true);
            }
        } else {
            if (ProxyServer.getInstance().getOnlineCount() >= Config.getconfig().getInt("network-info.max-players")) {
                if (Main.OnlineMode ? allowedplayers.contains(event.getConnection().getUniqueId().toString()) : allowedplayers.contains(event.getConnection().getName())) {
                    //Ready to Go
                } else {
                    event.getConnection().disconnect(new TextComponent(MainUtils.replaceall(Config.getconfig().getString("messages.full-message"))));
                    event.setCancelled(true);
                }
            }
            //Ready to Go
        }
    }
}
