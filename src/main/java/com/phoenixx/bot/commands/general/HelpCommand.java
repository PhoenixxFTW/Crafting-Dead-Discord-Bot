package com.phoenixx.bot.commands.general;

import com.phoenixx.bot.commands.Command;
import com.phoenixx.bot.handlers.CommandHandler;
import com.phoenixx.bot.handlers.ConfigHandler;
import com.phoenixx.bot.utils.References;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Phoenixx
 * - Crafting Dead Discord Bot
 * - 2019-02-22
 * - 6:55 PM
 **/
public class HelpCommand implements Command
{
    EmbedBuilder embedBuilder = new EmbedBuilder();

    private String getPermPre(int lvl) {
        switch (lvl) {
            case 1: return ":small_blue_diamond:";
            case 2: return ":small_orange_diamond:";
            case 3: return ":small_red_triangle_down:";
            //case 4: return ":diamonds:";
            default: return ":white_small_square:";
        }
    }

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event)
    {
        if (args.length > 0) {
            if (CommandHandler.commands.containsKey(args[0])) {
                if (CommandHandler.commands.get(args[0]).help() != null) {
                    event.getTextChannel().sendMessage(embedBuilder.setColor(References.colorTheme).setDescription(CommandHandler.commands.get(args[0]).help()).build()).queue();
                } else
                    event.getTextChannel().sendMessage(embedBuilder.setColor(Color.red).setDescription(":warning:  There are currently no information for the command  *"+ ConfigHandler.botPrefix + args[0] + "* !").build()).queue();
            } else
                event.getTextChannel().sendMessage(embedBuilder.setColor(Color.red).setDescription(":warning:  The command list does not contains information for the command *"+ ConfigHandler.botPrefix + args[0] + "* !").build()).queue();
            return;
        }

        event.getMessage().delete().queue();

        Map<String, String> cmds = new TreeMap<>();
        CommandHandler.commands.forEach((s, command) -> cmds.put(s, command.description()));

        StringBuilder allCommands = new StringBuilder();

        try {

            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("Crafting Dead Discord Bot Commands", "http://craftingdead.com/");
            embedBuilder.setDescription("***Legend:***\n" +
                    "  :white_small_square:  -  Usable for everyone\n" +
                    "  :small_blue_diamond:  -  Only for groups ` Mod, Support" + "`\n" +
                    "  :small_orange_diamond:  -  Only for groups ` Manager, Admin" + "`\n" +
                    "  :small_red_triangle_down:  -  Only for the owner of the server\n" +
                    "*For arguments <> means required, [] means optional*");

            embedBuilder.setColor(References.colorTheme);

            cmds.keySet().stream()
                    .filter(s -> CommandHandler.commands.get(s).commandType().equals(References.CMDTYPE.general))
                    .forEach(s1 -> allCommands.append("**"+getPermPre(CommandHandler.commands.get(s1).permission())+ ConfigHandler.botPrefix + s1 + "**   -   `" + cmds.get(s1) + "`\n"));

            embedBuilder.addField("General commands",allCommands.toString(), false);

            allCommands.delete(0, allCommands.length());

            cmds.keySet().stream()
                    .filter(s -> CommandHandler.commands.get(s).commandType().equals(References.CMDTYPE.tickets))
                    .forEach(s1 -> allCommands.append("**"+getPermPre(CommandHandler.commands.get(s1).permission())+ ConfigHandler.botPrefix + s1 + "**   -   `" + cmds.get(s1) + "`\n"));

            if(allCommands != null && !allCommands.toString().isEmpty())
            {
                embedBuilder.addField("Ticket commands",allCommands.toString(), false);
            }

            allCommands.delete(0, allCommands.length());

            cmds.keySet().stream()
                    .filter(s -> CommandHandler.commands.get(s).commandType().equals(References.CMDTYPE.administration))
                    .forEach(s1 -> allCommands.append("**"+getPermPre(CommandHandler.commands.get(s1).permission())+ ConfigHandler.botPrefix + s1 + "**   -   `" + cmds.get(s1) + "`\n"));

            if(allCommands != null && !allCommands.toString().isEmpty())
            {
                embedBuilder.addField("Administration commands",allCommands.toString(), false);
            }

            event.getChannel().sendMessage(embedBuilder.build()).complete();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return null;
    }

    @Override
    public String description() {
        return "Help command. Do " + ConfigHandler.botPrefix + "help [command] for help on a specific command";
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
