package com.phoenixx.bot.commands.general;

import com.phoenixx.bot.commands.Command;
import com.phoenixx.bot.handlers.ConfigHandler;
import com.phoenixx.bot.utils.References;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.Date;

/**
 * @author Phoenixx
 * - Crafting Dead Discord Bot
 * - 2019-02-22
 * - 6:55 PM
 **/
public class PingCommand implements Command
{
    private static long inputTime;

    public static void setInputTime(long inputTimeLong) {
        inputTime = inputTimeLong;
    }

    private Color getColorByPing(long ping) {
        if (ping < 100)
            return Color.cyan;
        if (ping < 400)
            return Color.green;
        if (ping < 700)
            return Color.yellow;
        if (ping < 1000)
            return Color.orange;
        return Color.red;
    }

    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    public void action(String[] args, MessageReceivedEvent event) {
        long processing = new Date().getTime() - inputTime;
        long ping = event.getJDA().getPing();

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(References.colorTheme);
        embedBuilder.setTitle(":ping_pong:   **Pong!**");
        embedBuilder.setDescription(String.format("The bot took `%s` milliseconds to response.\nIt took `%s` milliseconds to parse the command and the ping is `%s` milliseconds.", processing + ping, processing, ping));
        event.getTextChannel().sendMessage(embedBuilder.build()).queue();
    }

    public void executed(boolean success, MessageReceivedEvent event) {

    }

    public String help() {

        return "USAGE: " + ConfigHandler.botPrefix + "ping";
    }

    @Override
    public String description() {
        return "Pong!";
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
