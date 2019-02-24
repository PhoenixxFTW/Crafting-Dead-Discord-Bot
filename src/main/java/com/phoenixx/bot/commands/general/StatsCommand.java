package com.phoenixx.bot.commands.general;

import com.phoenixx.bot.commands.Command;
import com.phoenixx.bot.handlers.ConfigHandler;
import com.phoenixx.bot.handlers.TicketManager;
import com.phoenixx.bot.utils.References;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * @author Phoenixx
 * - Crafting Dead Discord Bot
 * - 2019-02-22
 * - 6:55 PM
 **/
public class StatsCommand implements Command
{
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(References.colorTheme);
        embedBuilder.setDescription("Stats about the Crafting Dead Bot");
        embedBuilder.addField("Server members", String.valueOf(event.getGuild().getMembers().size()), false);
        embedBuilder.addField("Amount of tickets created", String.valueOf(References.amountOfTicketsMade), false);
        embedBuilder.addField("Amount of tickets being processed", String.valueOf(TicketManager.currentTickets), false);
        embedBuilder.addField("Bot Version", "1.0", false);
        embedBuilder.addField("Java version", System.getProperty("java.version"), false);
        embedBuilder.addField("JDA version", "3.8.0_427", false);
        embedBuilder.setFooter("Bot Created by Phoenix#5518  |  Twitter: Golden4Phoenix", "https://i.imgur.com/rLTnjje.png");

        event.getTextChannel().sendMessage(embedBuilder.build()).queue();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return "USAGE: " + ConfigHandler.botPrefix + "stats";
    }

    @Override
    public String description() {
        return "Displays stats about the bot as well as server";
    }

    @Override
    public String commandType() {
        return References.CMDTYPE.general;
    }

    @Override
    public int permission() {
        return 0;
    }
}
