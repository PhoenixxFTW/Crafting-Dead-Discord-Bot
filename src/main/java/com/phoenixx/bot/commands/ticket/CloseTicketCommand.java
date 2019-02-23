package com.phoenixx.bot.commands.ticket;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.phoenixx.bot.commands.Command;
import com.phoenixx.bot.handlers.ConfigHandler;
import com.phoenixx.bot.handlers.TicketManager;
import com.phoenixx.bot.objects.Ticket;
import com.phoenixx.bot.utils.References;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.concurrent.TimeUnit;

/**
 * @author Phoenixx
 * - Crafting Dead Discord Bot
 * - 2019-02-22
 * - 6:55 PM
 **/

public class CloseTicketCommand implements Command
{
    private final EventWaiter waiter;

    public CloseTicketCommand(EventWaiter waiter) {
        this.waiter = waiter;
    }

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        String channelName = event.getMessage().getChannel().getName();
        Member member = event.getMember();
        TextChannel textChannel = event.getTextChannel();

        if (channelName.contains("ticket")) {
            if (TicketManager.doesUserHaveTicket(member)) {
                Ticket givenTicket = TicketManager.getUserTicket(member, textChannel);

                if (givenTicket != null && TicketManager.getTicketFromChannel(textChannel) != null) {
                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    embedBuilder.setDescription("Are you sure you want to close this ticket? The channel will be deleted.\n" + "**Repeat the command to close the ticket.**\n" + "Your request will be voided in 20 seconds.");
                    embedBuilder.setColor(References.colorTicket);

                    event.getTextChannel().sendMessage(embedBuilder.build()).queue();

                    waiter.waitForEvent(MessageReceivedEvent.class,
                            // make sure it's by the same user, and in the same channel
                            e -> (e.getAuthor().equals(event.getAuthor()) && e.getChannel().equals(event.getChannel())) && e.getMessage().getContentDisplay().equalsIgnoreCase(ConfigHandler.botPrefix+"close"),
                            // respond, inserting the name they listed into the response
                            e -> TicketManager.closeTicket(event, givenTicket),
                            // if the user takes more than a minute, time out
                            20, TimeUnit.SECONDS, () -> event.getTextChannel().sendMessage("Ticket ``close`` request has been voided.").complete());
                }
            }
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event)
    {

    }

    @Override
    public String help() {
        return "USAGE: " + ConfigHandler.botPrefix + "close";
    }

    @Override
    public String description() {
        return "Closes an existing ticket";
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
