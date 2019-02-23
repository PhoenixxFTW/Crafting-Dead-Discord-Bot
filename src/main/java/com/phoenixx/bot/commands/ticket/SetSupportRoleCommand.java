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
public class SetSupportRoleCommand implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if(Perms.check(3, event))
        {
            return;

        }

        if(args.length > 0) {
            String supportID = args[0];

            if (supportID.matches("[0-9]+") && supportID.length() > 5) {
                if (event.getGuild().getRoleById(supportID) != null) {
                    ConfigHandler.supportRole = supportID;
                    ConfigHandler.saveProp(ConfigHandler.botProperties, ConfigHandler.supportRole, "supportRole");
                    ConfigHandler.saveConfig(ConfigHandler.dir, ConfigHandler.botConfigFile, ConfigHandler.botProperties, ConfigHandler.botConfigComment);

                    event.getTextChannel().sendMessage("Set new support role ID to: **" + supportID + " (Support Role name: " + event.getGuild().getRoleById(supportID).getAsMention() + ")**").queue();
                } else {
                    event.getTextChannel().sendMessage("Please use a valid role ID! https://support.discordapp.com/hc/en-us/articles/206346498-Where-can-I-find-my-User-Server-Message-ID-").queue();
                }

            } else {
                event.getTextChannel().sendMessage("Please use a valid role ID! https://support.discordapp.com/hc/en-us/articles/206346498-Where-can-I-find-my-User-Server-Message-ID-").queue();
            }
        } else {
            event.getTextChannel().sendMessage("**<supportRole_ID> is a required argument! " + help() + "**").queue();
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return "USAGE: " + ConfigHandler.botPrefix + "setSupport <supportRole_ID>";
    }

    @Override
    public String description() {
        return "Sets the support role that can access ticket channels.";
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
