package net.begad666.bc.plugin.customprotocolsettings.features;

import java.util.ArrayList;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.begad666.bc.plugin.customprotocolsettings.utils.Config;
import net.begad666.bc.plugin.customprotocolsettings.utils.Processarraylists;
import net.begad666.bc.plugin.customprotocolsettings.utils.ProcessStrings;

public class DisconnectBlockedProtocols implements Listener {
	
	@EventHandler
	 public void beforeLogin(PreLoginEvent event) 
	 {
		 int version = event.getConnection().getVersion();
		 ArrayList<Integer> list = Processarraylists.processversionforchecking();
		 if (list.contains(version));
		 {
	     
		 }
		 if (!list.contains(version))
		 {
		 event.getConnection().disconnect(new TextComponent(ProcessStrings.replacecodesandcolors(ProcessStrings.replaceplaceholders(Config.getconfig().getString("blocked-message")))));
		 }
	 }

}
