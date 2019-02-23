package com.phoenixx.bot.commands.ticket;

import com.phoenixx.bot.commands.Command;
import com.phoenixx.bot.handlers.ConfigHandler;
import com.phoenixx.bot.utils.References;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * @author Phoenixx
 * - Crafting Dead Discord Bot
 * - 2019-02-22
 * - 6:55 PM
 **/
public class SetServerIDCommand implements Command
{

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event)
    {
        String serverID = args[0];

        if(serverID.matches("[0-9]+") && serverID.length() > 5)
        {
            if(event.getGuild().getCategoryById(serverID) != null)
            {
                ConfigHandler.serverID = serverID;
                ConfigHandler.saveProp(ConfigHandler.botProperties, ConfigHandler.supportCategoryID, "supportCategoryID");
                ConfigHandler.saveConfig(ConfigHandler.dir, ConfigHandler.botConfigFile, ConfigHandler.botProperties, ConfigHandler.botConfigComment);

                event.getTextChannel().sendMessage("Set new support category ID to: **" + serverID + " (Category name: "+event.getGuild().getCategoryById(serverID).getName()+")**").queue();
            } else {
                event.getTextChannel().sendMessage("Please use a valid category ID! https://support.discordapp.com/hc/en-us/articles/206346498-Where-can-I-find-my-User-Server-Message-ID-").queue();
            }

        } else {
            event.getTextChannel().sendMessage("Please use a valid category ID! https://support.discordapp.com/hc/en-us/articles/206346498-Where-can-I-find-my-User-Server-Message-ID-").queue();
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return "USAGE: " + ConfigHandler.botPrefix + "setServerID <server_id>";
    }

    @Override
    public String description() {
        return "Sets the guild id for the bot.";
    }

    @Override
    public String commandType() {
        return References.CMDTYPE.tickets;
    }

    @Override
    public int permission() {
        return 3;
    }
}
