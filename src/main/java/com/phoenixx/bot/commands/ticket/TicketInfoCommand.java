package com.phoenixx.bot.commands.ticket;

import com.phoenixx.bot.commands.Command;
import com.phoenixx.bot.handlers.ConfigHandler;
import com.phoenixx.bot.handlers.TicketManager;
import com.phoenixx.bot.objects.Ticket;
import com.phoenixx.bot.utils.References;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * @author Phoenixx
 * - Crafting Dead Discord Bot
 * - 2019-02-22
 * - 6:55 PM
 **/
public class TicketInfoCommand implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event)
    {
        String channelName = event.getMessage().getChannel().getName();
        TextChannel textChannel = event.getTextChannel();

        if (channelName.contains("ticket"))
        {
            Ticket ticket = TicketManager.getTicketFromChannel(textChannel);

            if(ticket != null)
            {
                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setColor(References.colorTicket);
                embedBuilder.setTitle("Information About ticket-" + ticket.getTicketID());
                embedBuilder.setDescription("**Created by:** " + ticket.getTicketOwner().getAsMention() + "\n**Created at:**: " + ticket.getCreatedOn() + "\n**Ticket ID:** " + ticket.getTicketID());
                embedBuilder.addField("Subject", ticket.getTicketSubject(), false);

                event.getTextChannel().sendMessage(embedBuilder.build()).queue();
            }
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return "USAGE: " + ConfigHandler.botPrefix + "info (Use in ticket channel)";
    }

    @Override
    public String description() {
        return "Displays info about a ticket";
    }

    @Override
    public String commandType() {
        return References.CMDTYPE.tickets;
    }

    @Override
    public int permission() {
        return 0;
    }
}
