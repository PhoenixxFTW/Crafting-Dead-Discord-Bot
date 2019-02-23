package com.phoenixx.bot.commands.ticket;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.phoenixx.Main;
import com.phoenixx.bot.commands.Command;
import com.phoenixx.bot.handlers.ConfigHandler;
import com.phoenixx.bot.handlers.TicketManager;
import com.phoenixx.bot.objects.Ticket;
import com.phoenixx.bot.utils.Perms;
import com.phoenixx.bot.utils.References;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.concurrent.TimeUnit;

/**
 * @author Phoenixx
 * - Crafting Dead Discord Bot
 * - 2019-02-22
 * - 6:55 PM
 **/
public class TicketCommand implements Command
{
    private final EventWaiter waiter;

    public TicketCommand(EventWaiter givenWaiter)
    {
        this.waiter = givenWaiter;
    }

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

        String arg = args[0];

        if(arg.equalsIgnoreCase("all"))
        {
            for(Ticket ticket: Main.getTicketManager().getCurrentTickets())
            {
                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setDescription("Amount of current tickets loaded: " + Main.getTicketManager().getCurrentAmountTickets());
                embedBuilder.setColor(References.colorTicket);
                embedBuilder.addField("Ticket ID", String.valueOf(ticket.getTicketID()),false);
                embedBuilder.addField("Ticket Owner", ticket.getTicketOwner().getAsMention(),false);
                embedBuilder.addField("Ticket Subject", ticket.getTicketSubject(),false);
                embedBuilder.addField("Ticket Channel", ticket.getTicketChannel().getAsMention(),false);
                embedBuilder.addField("Ticket Created On", ticket.getCreatedOn().toString(),false);

                event.getChannel().sendMessage(embedBuilder.build()).queue();
            }
        } else if(arg.matches("[0-9]+")) {
            Ticket ticket = TicketManager.getTicketFromID(Integer.valueOf(arg));
            if(ticket != null)
            {
                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setDescription("Amount of current tickets loaded: " + Main.getTicketManager().getCurrentAmountTickets());
                embedBuilder.setColor(References.colorTicket);
                embedBuilder.addField("Ticket ID", String.valueOf(ticket.getTicketID()),false);
                embedBuilder.addField("Ticket Owner", ticket.getTicketOwner().getAsMention(),false);
                embedBuilder.addField("Ticket Subject", ticket.getTicketSubject(),false);
                embedBuilder.addField("Ticket Channel", ticket.getTicketChannel().getAsMention(),false);
                embedBuilder.addField("Ticket Created On", ticket.getCreatedOn().toString(),false);

                event.getChannel().sendMessage(embedBuilder.build()).queue();
            }
        } else if(arg.equalsIgnoreCase("reset")) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setDescription("Are you sure you want to reset **all** tickets? The channels associated with the tickets will not be deleted\n" + "**Repeat the command to reset all tickets.**\n" + "Your request will be voided in 20 seconds.");
            embedBuilder.setColor(References.colorTicket);

            event.getTextChannel().sendMessage(embedBuilder.build()).queue();

            waiter.waitForEvent(MessageReceivedEvent.class,
                    // make sure it's by the same user, and in the same channel
                    e -> (e.getAuthor().equals(event.getAuthor()) && e.getChannel().equals(event.getChannel())) && e.getMessage().getContentDisplay().equalsIgnoreCase(ConfigHandler.botPrefix+"reset"),
                    // respond, inserting the name they listed into the response
                    e -> Main.getTicketManager().clearAll(),
                    // if the user takes more than a minute, time out
                    20, TimeUnit.SECONDS, () -> event.getTextChannel().sendMessage("Your request to reset the bot has been voided.").complete());

        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return "USAGES: " + ConfigHandler.botPrefix + "all (This displays all tickets in memory)\n" +
                ConfigHandler.botPrefix + " <ticketID> (displays information about a specific ticket)\n" +
                ConfigHandler.botPrefix + " reset (Resets all ticket data in the bot)\n";
    }

    @Override
    public String description() {
        return "Main ticket command. Do >help ticket to get more information";
    }

    @Override
    public String commandType() {
        return References.CMDTYPE.tickets;
    }

    @Override
    public int permission()
    {
        return 3;
    }
}
