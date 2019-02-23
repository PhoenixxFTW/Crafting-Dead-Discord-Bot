package com.phoenixx.bot.commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * @author Phoenixx
 * - Crafting Dead Discord Bot
 * - 2019-02-22
 * - 6:55 PM
 **/
public interface Command
{
    boolean called(String[] args, MessageReceivedEvent event);
    void action(String[] args, MessageReceivedEvent event);
    void executed(boolean success, MessageReceivedEvent event);
    String help();
    String description();
    String commandType();
    int permission();
}
