package com.phoenixx.bot.utils;

import com.phoenixx.bot.handlers.ConfigHandler;
import net.dv8tion.jda.core.entities.Member;

import java.awt.*;
import java.util.Date;

/**
 * @author Phoenixx
 * - Crafting Dead Discord Bot
 * - 2019-02-22
 * - 6:55 PM
 **/
public class References
{
    public static int amountOfTicketsMade = 0;

    public static String ID_RANK_SUPPORTER = "420248893008117804";
    public static String ID_RANK_STAFF = "343132312155979778";

    public static String BOT_OWNER_ID = "182700785996398592";

    public static String ID_RANK_BOT_ADMIN = "466330893347192843";
    public static String ID_SUPPORT_ROLE = ConfigHandler.supportRole;

    public static Color colorTheme = new Color(180, 0, 6);
    public static Color colorTicket = new Color(5, 169, 99);

    public static Date lastRestart;

    public static boolean isStaff(Member givenMember)
    {
        if (givenMember.canInteract(givenMember.getGuild().getRoleById(ID_RANK_STAFF))) {
            return true;
        }
        return false;
    }

    public static boolean isSupport(Member givenMember)
    {
        if (givenMember.canInteract(givenMember.getGuild().getRoleById(ID_RANK_SUPPORTER))) {
            return true;
        }
        return false;
    }

    public static int daysBetween(Date date1, Date date2)
    {
        return (int)((date2.getTime() - date1.getTime()) / 86400000L);
    }

    public static long secondsBetween(Date date1, Date date2)
    {
        return (date2.getTime() - date1.getTime()) / 1000L;
    }

    public class CMDTYPE {
        public static final String botOwner = "Bot Developer Commands";
        public static final String serverOwner = "Server Owner Commands";
        public static final String administration = "Admin Commands";
        public static final String moderators = "Mod Commands";
        public static final String general = "General Commands";
        public static final String tickets = "Ticket Commands";
        public static final String settings = "Bot Settings commands";
        public static final String etc = "Etc";
    }
}