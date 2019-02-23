package com.phoenixx.bot.commands.ticket;

import com.phoenixx.bot.commands.Command;
import com.phoenixx.bot.handlers.ConfigHandler;
import com.phoenixx.bot.utils.Perms;
import com.phoenixx.bot.utils.References;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * @author Phoenixx
 * - Crafting Dead Discord Bot
 * - 2019-02-22
 * - 6:55 PM
 **/
public class SetLogChannelCommand implements Command
{
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event)
    {
        if(Perms.check(3, event))
        {
            return;

        }

        if(args.length > 0) {
            String logChannelID = args[0];

            if (logChannelID.matches("[0-9]+") && logChannelID.length() > 5) {
                if (event.getGuild().getTextChannelById(logChannelID) != null) {
                    ConfigHandler.logChannelID = logChannelID;
                    ConfigHandler.saveProp(ConfigHandler.botProperties, ConfigHandler.logChannelID, "logChannelID");
                    ConfigHandler.saveConfig(ConfigHandler.dir, ConfigHandler.botConfigFile, ConfigHandler.botProperties, ConfigHandler.botConfigComment);

                    event.getTextChannel().sendMessage("Set new log channel to: **" + logChannelID + " (Channel name: " + event.getGuild().getTextChannelById(logChannelID).getAsMention() + ")**").queue();
                } else {
                    event.getTextChannel().sendMessage("Please use a valid role ID! https://support.discordapp.com/hc/en-us/articles/206346498-Where-can-I-find-my-User-Server-Message-ID-").queue();
                }

            } else {
                event.getTextChannel().sendMessage("Please use a valid role ID! https://support.discordapp.com/hc/en-us/articles/206346498-Where-can-I-find-my-User-Server-Message-ID-").queue();
            }
        } else {
            event.getTextChannel().sendMessage("**<channel_id> is a required argument! " + help() + "**").queue();
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return "USAGE: " + ConfigHandler.botPrefix + "setLogChannel <channel_id>";
    }

    @Override
    public String description() {
        return "Sets the channel where tickets get logged";
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
