package com.phoenixx.bot.commands.ticket;

import com.phoenixx.Main;
import com.phoenixx.bot.commands.Command;
import com.phoenixx.bot.handlers.ConfigHandler;
import com.phoenixx.bot.utils.References;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * @author Phoenixx
 * - Crafting Dead Discord Bot
 * - 2019-02-22
 * - 6:55 PM
 **/
public class CreateTicketCommand implements Command
{

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        String subject = "";
        if(args.length > 0)
        {
            for(String word: args){
                subject+=word+" ";
            }
        } else {
            subject = "No subject given";
        }
        Main.getTicketManager().createTicket(event, event.getMember(), subject);
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event)
    {

    }

    @Override
    public String help() {
        return "USAGE: >new [subject]";
    }

    @Override
    public String description() {
        return "Create a new ticket. You can also do " + ConfigHandler.botPrefix + "new [subject]";
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
