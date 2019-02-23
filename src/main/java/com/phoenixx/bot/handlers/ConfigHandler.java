package com.phoenixx.bot.handlers;

import com.phoenixx.Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * @author Phoenixx
 * - Crafting Dead Discord Bot
 * - 2019-02-22
 * - 6:55 PM
 **/
public class ConfigHandler
{
    public static final Properties botProperties = new Properties();
    public static final File dir = new File("Crafting Dead Bot/Bot Configs");

    public static final File botConfigFile = new File(dir, "BotConfig.cfg");

    public static String botPrefix = ">";
    public static String discordBotToken = "null";

    public static String serverID = "null";
    public static String supportCategoryID = "null";
    public static String supportRole = "null";
    public static String logChannelID = "null";

    public static int maxTickets = 3;

    public static int amountOfTicketsCreated = 0;

    public static String botConfigComment = "~Bot Configuration File~";

    public ConfigHandler()
    {
        /** Load bot config */
        loadConfig(dir, botConfigFile, botProperties);

        botPrefix = (String) loadProp(botProperties, botPrefix, ">", "botPrefix");
        discordBotToken = (String) loadProp(botProperties, discordBotToken, "null", "discordBotToken");

        serverID = (String) loadProp(botProperties, serverID, "null", "serverID");
        supportCategoryID = (String) loadProp(botProperties, supportCategoryID, "null", "supportCategoryID");
        supportRole = (String) loadProp(botProperties, supportRole, "null", "supportRole");
        logChannelID = (String) loadProp(botProperties, logChannelID, "null", "logChannelID");

        maxTickets = (Integer) loadProp(botProperties, maxTickets, 3, "maxTickets");
        amountOfTicketsCreated = (Integer) loadProp(botProperties, amountOfTicketsCreated, 0, "amountOfTicketsCreated");

        /** Save bot config */
        saveConfig(dir, botConfigFile, botProperties, botConfigComment);
    }

    public static void loadDataToArray(String[] data, ArrayList arrayList)
    {
        arrayList.clear();
        if(data != null)
        {
            for(String d : data)
            {
                arrayList.add(d);
            }
        }
    }

    public static String[] decodeList(String loc, String splitby)
    {
        if(loc == "null")
        {
            return null;
        }

        String[] data;

        if(splitby == null)
        {
            data = loc.split("\\|");
            return data;
        }else{
            data = loc.split("\\"+splitby);
        }

        if(!loc.contains(splitby))
        {
            data = new String[1];
            data[0] = loc;
        }

        return data;
    }

    public static Object loadProp(Properties p, Object o, Object def, String pname)
    {
        if (p.containsKey(pname))
        {
            o = parseObject(o, p.getProperty(pname));
            Main.getLogHandlerBot().info("Loaded config property '" + pname + "' = " + o);
            return o;
        }
        p.setProperty(pname, toStringObject(def));
        Main.getLogHandlerBot().info("Setup config property '" + pname + "'(" + def + ")");
        return def;
    }

    public static void saveProp(Properties p, Object o, String pname)
    {
        p.setProperty(pname, toStringObject(o));
        System.out.println("UPDATED PROP: " + pname + " TO " + toStringObject(o));
    }

    private static String toStringObject(Object o)
    {
        if ((o instanceof Boolean)) {
            return ((Boolean)o).toString();
        }
        if ((o instanceof String)) {
            return (String)o;
        }
        if ((o instanceof Integer)) {
            return ((Integer)o).toString();
        }
        if ((o instanceof Float)) {
            return o.toString();
        }
        return "";
    }

    private static Object parseObject(Object o, String property)
    {
        if(o instanceof Boolean)
        {
            return Boolean.parseBoolean(property);
        }

        if(o instanceof String)
        {
            return property;
        }

        if(o instanceof Integer)
        {
            return Integer.parseInt(property);
        }

        if(o instanceof Float)
        {
            return Float.parseFloat(property);
        }

        return "";
    }

    public static void loadConfig(File dir, File file, Properties prop)
    {
        try
        {
            dir.mkdir();
            if ((!file.exists()) && (!file.createNewFile())) {
                return;
            }
            if (file.canRead())
            {
                FileInputStream fileinputstream = new FileInputStream(file);
                prop.load(fileinputstream);
                fileinputstream.close();
            }
            Main.getLogHandlerBot().info("Successfully loaded bot Config!");
        }
        catch (IOException ex)
        {
            Main.getLogHandlerBot().severe("Could not load bot Config!");
        }
    }

    public static void saveConfig(File dir, File file, Properties prop, String comment)
    {
        try
        {
            dir.mkdir();
            if ((!file.exists()) && (!file.createNewFile())) {
                return;
            }
            if (file.canWrite())
            {
                FileOutputStream fileoutputstream = new FileOutputStream(file);
                prop.store(fileoutputstream, comment);
                fileoutputstream.close();
            }
            Main.getLogHandlerBot().info("Successfully saved bot Config!");
        }
        catch (IOException ex)
        {
            Main.getLogHandlerBot().severe("Could not save bot Config!");
        }
    }
}
