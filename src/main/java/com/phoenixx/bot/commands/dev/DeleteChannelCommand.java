package com.phoenixx.bot.commands.dev;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.phoenixx.bot.commands.Command;
import com.phoenixx.bot.handlers.ConfigHandler;
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
public class DeleteChannelCommand implements Command
{
    private final EventWaiter waiter;

    public DeleteChannelCommand(EventWaiter waiter)
    {
        this.waiter = waiter;
    }

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event)
    {
        if (Perms.check(3, event))
        {
            return;
        }

        String channelID = args[0].substring(2, args[0].length()-1);

        if(event.getGuild().getTextChannelById(channelID) != null)
        {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setDescription("Are you sure you want to delete this channel?\n If there is a ticket associated with this channel it will be deleted\n" + "**Repeat the command to delete the channel.**\n" + "Your request will be voided in 10 seconds.");
            embedBuilder.setColor(References.colorTheme);

            //TODO Check for ticket channel

            event.getTextChannel().sendMessage(embedBuilder.build()).queue();

            waiter.waitForEvent(MessageReceivedEvent.class,
                    // make sure it's by the same user, and in the same channel
                    e -> (e.getAuthor().equals(event.getAuthor()) && e.getChannel().equals(event.getChannel())) && e.getMessage().getContentDisplay().equalsIgnoreCase(ConfigHandler.botPrefix+"deleteChannel"),
                    // respond
                    e -> event.getGuild().getTextChannelById(channelID).delete().queue(),
                    // if the user takes more than 10 sec, time out
                    10, TimeUnit.SECONDS, () -> event.getTextChannel().sendMessage("Channel ``deletion`` request has been voided.").queue());
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return "USAGE: " + ConfigHandler.botPrefix + "deleteChannel <#channel>";
    }

    @Override
    public String description() {
        return "Used to delete a certain channel.";
    }

    @Override
    public String commandType() {
        return References.CMDTYPE.administration;
    }

    @Override
    public int permission() {
        return 3;
    }
}
