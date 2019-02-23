package com.phoenixx.bot.listeners;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.phoenixx.bot.handlers.CommandHandler;
import com.phoenixx.bot.handlers.ConfigHandler;
import com.phoenixx.bot.handlers.TicketManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;

/**
 * @author Phoenixx
 * - Crafting Dead Discord Bot
 * - 2019-02-22
 * - 6:55 PM
 **/
public class MessageListener extends ListenerAdapter
{

    public static EmbedBuilder success() {
        return new EmbedBuilder().setColor(Color.green);
    }

    public static EmbedBuilder error() {
        return new EmbedBuilder().setColor(Color.red);
    }

    public static void sendToChat(String message, TextChannel givenChannel)
    {
        givenChannel.sendMessage(message).complete();
    }

    public void onMessageReceived(final MessageReceivedEvent event)
    {
        Message message = event.getMessage();
        TextChannel messageChannel = message.getTextChannel();

        if(message.getContentDisplay().startsWith(ConfigHandler.botPrefix) && !event.getAuthor().isBot() && !(event.getChannel() instanceof PrivateChannel))
        {
            CommandHandler.handleCommand(CommandHandler.parse.parser(message.getContentRaw(), event));
        }

        /**
         * 1) Check if channel has a ticket associated with it
         * 2) Get ticket
         * 3) Create new message object
         * 4) Create (if json file does NOT exist) and append MessageObject json data to file
         * 5) Repeat
         *
         * Notes: Remember, the json data needs to be wrapped in [ ] so that it becomes an array
         */

        //FIXME: You need to fix the check where the bot checks to see if the message was sent in the ticket channel or not
        //FIXME: Make the writer type out '[]' into the new file before putting anything else in
        // Handles channel text

        if(!message.getContentDisplay().startsWith(ConfigHandler.botPrefix) && messageChannel.getName().startsWith("ticket") && TicketManager.isChannelATicketChannel(messageChannel) && TicketManager.getTicketFromChannel(messageChannel) != null && !event.getAuthor().isBot()) {
            TicketManager.handleMessageInTicketChannel(event);
        }
    }

    /**
     * Convert a JSON string to pretty print version
     * @param jsonString
     * @return
     */
    public static String toPrettyFormat(String jsonString)
    {
        JsonParser parser = new JsonParser();
        /*if(jsonString.startsWith("[") && jsonString.endsWith("]"))
        {
            jsonString = jsonString.substring(1, jsonString.length()-1);
        }*/
        JsonObject json = parser.parse(jsonString).getAsJsonObject();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = gson.toJson(json);

        return prettyJson;
    }
}
