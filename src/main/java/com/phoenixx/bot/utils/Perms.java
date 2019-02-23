package com.phoenixx.bot.utils;

import com.phoenixx.bot.listeners.MessageListener;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;

/**
 * @author Phoenixx
 * - Crafting Dead Discord Bot
 * - 2019-02-22
 * - 6:55 PM
 **/
public class Perms {

    public static boolean isBotOwner(User user) {
        return Long.parseLong(user.getId()) == Long.valueOf(References.BOT_OWNER_ID);
    }

    public static boolean isBotOwner(User user, TextChannel channel) {
        if (user.getId().equals(String.valueOf(References.BOT_OWNER_ID)))
            return true;
        channel.sendMessage(MessageListener.error().setDescription("Only the bots owner (" + user.getJDA().getUserById(References.BOT_OWNER_ID).getAsMention() + ") can use this command.").build()).queue();
        return false;

    }

    public static int getLvl(Member member) {

        if (isBotOwner(member.getUser())) // Owner of bot
            return 4;
        if (member.equals(member.getGuild().getOwner()) || isBotOwner(member.getUser())) // Owner of guild & Owner of bot
            return 3;
        if (member.getRoles().stream().anyMatch(role -> References.ID_RANK_BOT_ADMIN.equals(role.getId())) || isBotOwner(member.getUser())) {
            return 2;
        } else if (member.getRoles().stream().anyMatch(role -> References.ID_SUPPORT_ROLE.equals(role.getId())) || isBotOwner(member.getUser())) {
            return 1;
        }
        return 0;
    }

    public static boolean check(int required, MessageReceivedEvent event) {
        if (required > getLvl(event.getMember())) {
            event.getTextChannel().sendMessage(new EmbedBuilder().setColor(Color.red).setDescription("Sorry but you need permission level `" + required + "` or above!\n(Your current permission level is `" + getLvl(event.getMember()) + "`).").build()).queue();
            return true;
        }
        return false;
    }
}
