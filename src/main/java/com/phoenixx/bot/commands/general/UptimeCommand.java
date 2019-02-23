package com.phoenixx.bot.commands.general;

import com.phoenixx.bot.commands.Command;
import com.phoenixx.bot.handlers.ConfigHandler;
import com.phoenixx.bot.utils.References;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Phoenixx
 * - Crafting Dead Discord Bot
 * - 2019-02-22
 * - 6:55 PM
 **/
public class UptimeCommand implements Command {

    private String getTime(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    private String getTimeDiff(Date date1, Date date2) {
        long diff = date1.getTime() - date2.getTime();
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);
        return diffDays + " d, " + parseTimeNumbs(diffHours) + " h, " + parseTimeNumbs(diffMinutes) + " min, " + parseTimeNumbs(diffSeconds) + " sec";
    }

    private String parseTimeNumbs(long time) {
        String timeString = time + "";
        if (timeString.length() < 2)
            timeString = "0" + time;
        return timeString;
    }

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(References.colorTheme);
        embedBuilder.setTitle(":alarm_clock:   **UPTIME**");
        embedBuilder.addField("Last restart", getTime(References.lastRestart, "dd.MM.yyyy - HH:mm:ss (z)"), false);
        embedBuilder.addField("Online since", getTimeDiff(new Date(), References.lastRestart), false);
        event.getTextChannel().sendMessage(embedBuilder.build()).queue();

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return "USAGE: " + ConfigHandler.botPrefix + "uptime";
    }

    @Override
    public String description() {
        return "Get stats about the uptime of the bot.";
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
