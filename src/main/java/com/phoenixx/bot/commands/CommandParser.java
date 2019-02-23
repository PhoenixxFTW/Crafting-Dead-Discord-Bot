package com.phoenixx.bot.commands;

import com.phoenixx.bot.handlers.ConfigHandler;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;

/**
 * @author Phoenixx
 * - Crafting Dead Discord Bot
 * - 2019-02-22
 * - 6:55 PM
 **/
public class CommandParser
{
    public static CommandContainer parser(String raw, MessageReceivedEvent event)
    {
        String beheaded = raw.replaceFirst(ConfigHandler.botPrefix, ""); // removes prefix from string
        String [] splitBeheaded = beheaded.split(" "); // splits each word by a space and adds to an array
        String invoke = splitBeheaded[0]; // command itself
        ArrayList<String> split = new ArrayList<>();
        for(String s : splitBeheaded)
        {
            split.add(s); // adds every word in the command to an ArrayList
        }
        String[] args = new String[split.size() - 1]; // all args get added to an array
        split.subList(1, split.size()).toArray(args);

        return new CommandContainer(raw, beheaded, splitBeheaded, invoke, args, event);
    }


    public static class CommandContainer
    {
        public final String raw;
        public final String beheaded;
        public final String[] splitBeheaded;
        public final String invoke;
        public final String[] args;
        public final MessageReceivedEvent event;

        public CommandContainer(String raw1, String beheaded1, String[] splitBeheaded1, String invoke1, String[] args1, MessageReceivedEvent event1)
        {
            this.raw = raw1;
            this.beheaded = beheaded1;
            this.splitBeheaded = splitBeheaded1;
            this.invoke = invoke1;
            this.args = args1;
            this.event = event1;
        }
    }
}
