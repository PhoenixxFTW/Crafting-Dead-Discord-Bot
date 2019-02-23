package com.phoenixx.bot.commands.admin;

import com.phoenixx.Main;
import com.phoenixx.bot.commands.Command;
import com.phoenixx.bot.handlers.ConfigHandler;
import com.phoenixx.bot.utils.Perms;
import com.phoenixx.bot.utils.References;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * @author Phoenixx
 * - Crafting Dead Discord Bot
 * - 2019-02-22
 * - 6:55 PM
 **/
public class RestartCommand implements Command {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        if (Perms.check(3, event))
        {
            return;
        }

        final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        final File currentJar;
        try {
            currentJar = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            /* is it a jar file? */
            if(!currentJar.getName().endsWith(".jar"))
                return;
            /* Build command: java -jar application.jar */
            final ArrayList<String> command = new ArrayList<String>();
            command.add(javaBin);
            command.add("-jar");
            command.add(currentJar.getPath());

            final ProcessBuilder builder = new ProcessBuilder(command);
            try {
                builder.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        event.getTextChannel().sendMessage("Restarting Crafting Dead discord bot, standby...").queue();
    }

    @Override
    public String help() {
        return "USAGE: "+ ConfigHandler.botPrefix +"restart";
    }

    @Override
    public String description() {
        return "Restarts discord bot";
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
