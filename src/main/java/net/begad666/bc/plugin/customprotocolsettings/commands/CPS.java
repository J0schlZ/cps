package net.begad666.bc.plugin.customprotocolsettings.commands;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.begad666.bc.plugin.customprotocolsettings.Main;
import net.begad666.bc.plugin.customprotocolsettings.features.ChangePingData;
import net.begad666.bc.plugin.customprotocolsettings.features.DisconnectNotAllowedUsers;
import net.begad666.bc.plugin.customprotocolsettings.features.MultiProxy;
import net.begad666.bc.plugin.customprotocolsettings.utils.*;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.PluginManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class CPS extends Command {
	public static boolean isEnabled;

	public CPS() {
		super("cps", "cps.admin");
	}

	public void execute(CommandSender sender, final String[] args) {
		if (args.length != 1) {
			if (args.length != 2) {
				sender.sendMessage(new TextComponent(MainUtils.replacecodesandcolors(Config.getconfig().getString("prefixs.plugin")) + " ------------------------------"));
				sender.sendMessage(new TextComponent(MainUtils.replacecodesandcolors(Config.getconfig().getString("prefixs.plugin")) + " Compile Version: " + Updates.getCompileCurrentVersion()));
				sender.sendMessage(new TextComponent(MainUtils.replacecodesandcolors(Config.getconfig().getString("prefixs.plugin")) + " Version: " + Main.getInstance().getDescription().getVersion()));
				sender.sendMessage(new TextComponent(MainUtils.replacecodesandcolors(Config.getconfig().getString("prefixs.plugin")) + " Compile Config Version: " + Updates.getCompileConfigVersion()));
				sender.sendMessage(new TextComponent(MainUtils.replacecodesandcolors(Config.getconfig().getString("prefixs.plugin")) + " Config Version: " + Config.getconfig().getString("config-version")));
				sender.sendMessage(new TextComponent(MainUtils.replacecodesandcolors(Config.getconfig().getString("prefixs.plugin")) + " Updates: " + Updates.getMessage()));
				TextComponent pluglink = new TextComponent(MainUtils.replacecodesandcolors(Config.getconfig().getString("prefixs.plugin")) + " Plugin Page: Click Here");
				pluglink.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/customprotocolsettings.69385/"));
				sender.sendMessage(pluglink);
				TextComponent devlink = new TextComponent(MainUtils.replacecodesandcolors(Config.getconfig().getString("prefixs.plugin")) + " Developer: Click Here");
				devlink.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/members/begad.478174/"));
				sender.sendMessage(devlink);
				sender.sendMessage(new TextComponent(MainUtils.replacecodesandcolors(Config.getconfig().getString("prefixs.plugin")) + " ------------------------------"));
			}
		} else {
			switch (args[0].toLowerCase()) {
				case "reload":
					reload(sender);
					break;
				case "ps":
				case "ms":
				case "pf":
					sender.sendMessage(new TextComponent(MainUtils.replacecodesandcolors(Config.getconfig().getString("prefixs.plugin")) + " That command was deleted in v2.0, sorry for the inconvenience"));
					break;
				case "license":
					license(sender);
					break;
				case "enable":
					try {
						if ((args[1].equals(Config.getconfig().getString("password")))) {
							enable(sender);
						} else {
							sender.sendMessage(new TextComponent(MainUtils.replacecodesandcolors(Config.getconfig().getString("prefixs.plugin")) + " The password is wrong, please try again"));
						}
					} catch (Exception e) {
						sender.sendMessage(new TextComponent(MainUtils.replacecodesandcolors(Config.getconfig().getString("prefixs.plugin")) + " Please type the password"));
					}
					break;
				case "disable":
					try {
						if ((args[1].equals(Config.getconfig().getString("password")))) {
							disable(sender);
						} else {
							sender.sendMessage(new TextComponent(MainUtils.replacecodesandcolors(Config.getconfig().getString("prefixs.plugin")) + " The password is wrong, please Try Again"));
						}
					} catch (Exception e) {
						sender.sendMessage(new TextComponent(MainUtils.replacecodesandcolors(Config.getconfig().getString("prefixs.plugin")) + " Please type the password"));
					}
					break;
				case "checkdbconnection":
					checkdbconnection(sender);
					break;
				case "pullnow":
					if (Config.getconfig().getBoolean("multiproxy.enable")) {
						if (DatabaseConnectionManager.getConnected()) {
							pullnow(sender);
						} else {
							sender.sendMessage(new TextComponent(MainUtils.replacecodesandcolors(Config.getconfig().getString("prefixs.plugin")) + " The Database Isn't Connected"));
						}
					} else {
						sender.sendMessage(new TextComponent(MainUtils.replacecodesandcolors(Config.getconfig().getString("prefixs.plugin")) + " MultiProxy Isn't Enabled"));
					}
					break;
				default:
					sender.sendMessage(new TextComponent(MainUtils.replacecodesandcolors(Config.getconfig().getString("prefixs.plugin")) + " Unknown argument"));
					break;
			}

		}
	}

	public static void reload(CommandSender sender) {
		boolean result = Config.check();
		if (!result) {
			PluginManager pluginManager = ProxyServer.getInstance().getPluginManager();
			pluginManager.unregisterListeners(Main.getInstance());
			pluginManager.unregisterCommands(Main.getInstance());
		}
		if (Config.getconfig().getBoolean("multiproxy.enable")) {
			Main.getInstance().getLogger().info("Disconnecting From Database...");
			DatabaseConnectionManager.disconnect();
			Main.getInstance().getLogger().info("Reconnecting to Database...");
			DatabaseConnectionManager.connect();
		}
		sender.sendMessage(new TextComponent(MainUtils.replacecodesandcolors(Config.getconfig().getString("prefixs.plugin")) + " Config reload process finished!"));
	}

	public static void license(CommandSender sender) {
		sender.sendMessage(new TextComponent(MainUtils.replacecodesandcolors(Config.getconfig().getString("prefixs.plugin")) + "\r\nMIT License\r\n" +
				"\r\n" +
				"Copyright (c) 2020 Begad666\r\n" +
				"\r\n" +
				"Permission is hereby granted, free of charge, to any person obtaining a copy\r\n" +
				"of this software and associated documentation files (the \"Software\"), to deal\r\n" +
				"in the Software without restriction, including without limitation the rights\r\n" +
				"to use, copy, modify, merge, publish, distribute, sublicense, and/or sell\r\n" +
				"copies of the Software, and to permit persons to whom the Software is\r\n" +
				"furnished to do so, subject to the following conditions:\r\n" +
				"\r\n" +
				"The above copyright notice and this permission notice shall be included in all\r\n" +
				"copies or substantial portions of the Software.\r\n" +
				"\r\n" +
				"THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\r\n" +
				"IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\r\n" +
				"FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE\r\n" +
				"AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\r\n" +
				"LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\r\n" +
				"OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE\r\n" +
				"SOFTWARE."));
	}

	private void enable(CommandSender sender) {
		if (CPS.isEnabled) {
			sender.sendMessage(new TextComponent(MainUtils.replacecodesandcolors(Config.getconfig().getString("prefixs.plugin")) + " The Features is already enabled!"));
		} else {
			sender.sendMessage(new TextComponent(MainUtils.replacecodesandcolors(Config.getconfig().getString("prefixs.plugin")) + " Enabling Plugin Features..."));
			PluginManager pluginManager = ProxyServer.getInstance().getPluginManager();
			pluginManager.registerListener(Main.getInstance(), new ChangePingData());
			pluginManager.registerListener(Main.getInstance(), new DisconnectNotAllowedUsers());
			pluginManager.registerCommand(Main.getInstance(), new Ping());
			if (Config.getconfig().getBoolean("multiproxy.enable")) {
				Main.getInstance().getLogger().info("Connecting To Database...");
				DatabaseConnectionManager.connect();
				ScheduledTasks.autoreconnecttask = ProxyServer.getInstance().getScheduler().schedule(Main.getInstance(), () -> {
					ProxyServer.getInstance().getLogger().info(MainUtils.replacecodesandcolors(Config.getconfig().getString("prefixs.plugin")) + " Reconnecting to database...");
					DatabaseConnectionManager.disconnect();
					DatabaseConnectionManager.connect();
				}, 8, 8, TimeUnit.HOURS);
				if (Config.getconfig().getBoolean("multiproxy.autopull"))
					ScheduledTasks.autopulltask = ProxyServer.getInstance().getScheduler().schedule(Main.getInstance(), () -> {

					}, Config.getconfig().getInt("multiproxy.autopulltime"), Config.getconfig().getInt("multiproxy.autopulltime"), TimeUnit.MINUTES);
			}
			isEnabled = true;
			sender.sendMessage(new TextComponent(MainUtils.replacecodesandcolors(Config.getconfig().getString("prefixs.plugin")) + " The Plugin Features Has been Enabled"));
		}
	}

	private void disable(CommandSender sender) {
		if (!CPS.isEnabled) {
			sender.sendMessage(new TextComponent(MainUtils.replacecodesandcolors(Config.getconfig().getString("prefixs.plugin")) + " The Features is already disabled!"));
		} else {
			sender.sendMessage(new TextComponent(MainUtils.replacecodesandcolors(Config.getconfig().getString("prefixs.plugin")) + " Disabling Plugin Features..."));
			PluginManager pluginManager = ProxyServer.getInstance().getPluginManager();
			pluginManager.unregisterListeners(Main.getInstance());
			pluginManager.unregisterCommand(new Ping());
			if (Config.getconfig().getBoolean("multiproxy.enable")) {
				Main.getInstance().getLogger().info("Disconnecting From Database...");
				DatabaseConnectionManager.disconnect();
			}
			isEnabled = false;
			sender.sendMessage(new TextComponent(MainUtils.replacecodesandcolors(Config.getconfig().getString("prefixs.plugin")) + " The Plugin Features Has been Disabled"));
		}
	}

	private void checkdbconnection(CommandSender sender) {
		if (!DatabaseConnectionManager.getConnected())
			sender.sendMessage(new TextComponent(MainUtils.replacecodesandcolors(Config.getconfig().getString("prefixs.plugin")) + " Not Connected"));
		else
			sender.sendMessage(new TextComponent(MainUtils.replacecodesandcolors(Config.getconfig().getString("prefixs.plugin")) + " Connected"));
	}

	private void pullnow(CommandSender sender) {
		ResultSet data;
		Gson gson = new Gson();
		JsonObject configjson = new JsonObject();
		try {
			data = DatabaseConnectionManager.executeQuery("SELECT configjson FROM cps WHERE groupId='" + Config.getconfig().getString("multiproxy.groupid") + "'");
			while (data != null && data.next())
				configjson = gson.fromJson(data.getString("configjson"), JsonObject.class);
		} catch (JsonSyntaxException e) {
			ProxyServer.getInstance().getLogger().severe(MainUtils.replacecodesandcolors(Config.getconfig().getString("prefixs.plugin") + " Error while processing pulled json data from database, most likely because there is no data, check your table, no changes will be made"));
			return;
		} catch (SQLException e) {
			ProxyServer.getInstance().getLogger().severe(MainUtils.replacecodesandcolors(Config.getconfig().getString("prefixs.plugin") + " Error while pulling data from database, no changes will be made"));
			return;
		}
		if (configjson != null)
			MultiProxy.ApplyData(configjson, Config.getconfig().getBoolean("multiproxy.autosaveconfig"));
		else
			sender.sendMessage(new TextComponent(MainUtils.replacecodesandcolors(Config.getconfig().getString("prefixs.plugin")) + " No data was found in the database"));
	}

}
