package com.phoenixx.bot.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.phoenixx.bot.objects.MessageObject;
import com.phoenixx.bot.objects.Ticket;
import com.phoenixx.bot.utils.References;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


/**
 * @author Phoenixx
 * - Crafting Dead Discord Bot
 * - 2019-02-22
 * - 6:55 PM
 **/
public class TicketManager
{
    public static int currentTickets = 0;

    //TODO ForceClose command for admins / support to close tickets
    //TODO Maybe a html transcript?

    private static ArrayList<Ticket> allTickets = new ArrayList<>();

    private final File ticketFolder = new File("Crafting Dead Bot/Tickets/");
    private static String ticketDir = "Crafting Dead Bot/Tickets/";
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void createTicket(MessageReceivedEvent event, Member ticketOwner, String ticketSubject)
    {
        int userTickets = 1;

        for(Ticket ticket : allTickets)
        {
            if(ticket.getTicketOwner() == ticketOwner)
            {
                userTickets++;
            }
        }

        if(userTickets <= ConfigHandler.maxTickets)
        {
            currentTickets++;
            References.amountOfTicketsMade++;

            Date dateCreated = new Date();

            Ticket newTicket = new Ticket(ticketOwner, ticketSubject, dateCreated, createTicketChannel(event, ticketSubject), References.amountOfTicketsMade);
            allTickets.add(newTicket);
            createTicketFile(newTicket);

            ConfigHandler.amountOfTicketsCreated++;
            ConfigHandler.saveProp(ConfigHandler.botProperties, ConfigHandler.amountOfTicketsCreated, "amountOfTicketsCreated");
            ConfigHandler.saveConfig(ConfigHandler.dir, ConfigHandler.botConfigFile, ConfigHandler.botProperties, ConfigHandler.botConfigComment);
        } else {
            event.getChannel().sendMessage("Sorry " + event.getAuthor().getAsMention() + "! You can only have up to " + ConfigHandler.maxTickets + " tickets. Please close an existing ticket by doing ``>close`` in the ticket channel").queue();

        }
    }

    private TextChannel createTicketChannel(MessageReceivedEvent event, String subject)
    {
        ArrayList<Permission> allowedPerms = new ArrayList<>();
        allowedPerms.add(Permission.MESSAGE_WRITE);
        allowedPerms.add(Permission.MESSAGE_READ);
        allowedPerms.add(Permission.MESSAGE_ATTACH_FILES);
        allowedPerms.add(Permission.MESSAGE_EMBED_LINKS);

        ArrayList<Permission> deniedPerms = new ArrayList<>();

        Role publicRole = event.getGuild().getPublicRole();
        Role supportRole = event.getGuild().getRoleById(ConfigHandler.supportRole);

        Member ticketOwner = event.getMember();

        Channel ticketChannel = event.getGuild().getController().createTextChannel("ticket-" + References.amountOfTicketsMade)
                .setParent(event.getJDA().getCategoryById(ConfigHandler.supportCategoryID))
                .addPermissionOverride(ticketOwner, allowedPerms, deniedPerms)
                .addPermissionOverride(publicRole, deniedPerms, allowedPerms)
                .addPermissionOverride(supportRole, allowedPerms, deniedPerms)
                .setTopic(subject)
                .complete();

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setDescription("Dear " + event.getAuthor().getAsMention() + "\n" + "Thank you for reaching out to our support team! We will get back to you as soon as possible.");
        embedBuilder.setColor(References.colorTicket);
        embedBuilder.addField("Subject", subject,true);

        TextChannel messageChannel = (TextChannel) ticketChannel;

        messageChannel.sendMessage(embedBuilder.build()).queue();

        event.getTextChannel().sendMessage("Hey there, "+event.getMember().getAsMention()+"! Your new support ticket is located here: " + messageChannel.getAsMention()).queue();

        EmbedBuilder madeTicketEmbed = new EmbedBuilder();
        madeTicketEmbed.setDescription(event.getAuthor().getAsMention() +" has created a new ticket " + messageChannel.getAsMention());
        madeTicketEmbed.setColor(References.colorTicket);
        madeTicketEmbed.addField("Subject", subject,true);

        event.getGuild().getTextChannelById(ConfigHandler.logChannelID).sendMessage(madeTicketEmbed.build()).queue();

        return messageChannel;
    }

    public static void closeTicket(MessageReceivedEvent event, Ticket givenTicket)
    {
        allTickets.remove(givenTicket);
        currentTickets--;

        EmbedBuilder madeTicketEmbed = new EmbedBuilder();
        madeTicketEmbed.setTitle("Ticket closed / deleted");
        madeTicketEmbed.setDescription("Ticket with ID " + givenTicket.getTicketID() + " was closed. \n**Closed by:** " + event.getMember().getAsMention() +
        "\n**Closed at:** " + event.getMessage().getCreationTime().toLocalDate() + "\n**Server name:** " +event.getMessage().getGuild().getName() +
        "\n**Ticket name:** " + givenTicket.getTicketID());
        madeTicketEmbed.setColor(References.colorTicket);
        madeTicketEmbed.addField("Ticket Information", "**Owner:** " + givenTicket.getTicketOwner().getAsMention() + "\n**Subject:** " + givenTicket.getTicketSubject() +
                "\n**Ticket Channel:** " + givenTicket.getTicketChannel() + "\n**Creation date:** " + givenTicket.getCreatedOn().toString(),false);

        event.getGuild().getTextChannelById(ConfigHandler.logChannelID).sendMessage(madeTicketEmbed.build()).queue();

        File ticketFile = new File(ticketDir + "ticket-" + givenTicket.getTicketID() + ".json");

        if(ticketFile.exists())
        {
            ticketFile.delete();
        }
        event.getTextChannel().delete().queue();
    }

    public static boolean doesUserHaveTicket(Member givenMember)
    {
        for(Ticket ticket : allTickets)
        {
            if(ticket.getTicketOwner() == givenMember)
            {
                return true;
            }
        }
        return false;
    }

    public static Ticket getUserTicket(Member givenMember, TextChannel givenChannel)
    {
        for(Ticket ticket : allTickets)
        {
            if(ticket.getTicketOwner() == givenMember && ticket.getTicketChannel() == givenChannel)
            {
                return ticket;
            }
        }
        return null;
    }

    public static Ticket getTicketFromID(int givenID)
    {
        for(Ticket ticket : allTickets)
        {
            if(ticket.getTicketID() == givenID)
            {
                return ticket;
            }
        }
        return null;
    }

    public static Ticket getTicketFromChannel(TextChannel textChannel)
    {
        for(Ticket ticket : allTickets)
        {
            if(ticket.getTicketChannel() == textChannel)
            {
                return ticket;
            }
        }
        return null;
    }

    public static boolean isChannelATicketChannel(TextChannel textChannel)
    {
        for(Ticket ticket : allTickets)
        {
            if(ticket.getTicketChannel() == textChannel)
            {
                return true;
            }
        }
        return false;
    }

    private void createTicketFile(Ticket givenTicket)
    {
        String ticketLogDirectory = "Crafting Dead Bot/Tickets/ChatLogs/";
        File ticketFile = new File(ticketDir + "ticket-" + givenTicket.getTicketID() + ".json");
        File ticketChatLogFile = new File(ticketLogDirectory + "Ticket-" + givenTicket.getTicketID() + "(ChatLog).json");

        new File(ticketDir).mkdirs();
        new File(ticketLogDirectory).mkdirs();

        Writer writer;
        Writer writer2;
        try {
            writer = new FileWriter(ticketFile);
            writer2 = new FileWriter(ticketChatLogFile);

            if (!ticketFile.exists()) {
                ticketFile.createNewFile();
            }

            writer.write(givenTicket.toString());
            writer.close();

            if (!ticketChatLogFile.exists()) {
                ticketChatLogFile.createNewFile();
            }

            writer2.write("[\n\n]");
            writer2.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadAllTickets(Guild givenGuild)
    {
        File[] listOfFiles = ticketFolder.listFiles();
        if (listOfFiles != null) {
            for (File ticketFile : listOfFiles) {
                if (ticketFile.isFile()) {
                    parseTicketFileData(ticketFile, givenGuild);
                }
            }
        }
        References.amountOfTicketsMade = ConfigHandler.amountOfTicketsCreated;
    }

    private void parseTicketFileData(File givenTicketFile, Guild givenGuild)
    {
        JsonObject jsonObject = null;
        try {
            jsonObject = gson.fromJson(new FileReader(givenTicketFile), JsonObject.class);

            String ticketOwner = jsonObject.get("ticketOwner").getAsString();
            String ticketSubject = jsonObject.get("ticketSubject").getAsString();
            String ticketChannel = jsonObject.get("ticketChannel").getAsString();
            String createdOn = jsonObject.get("createdOn").getAsString();
            int ticketID = jsonObject.get("ticketID").getAsInt();

            Ticket ticketObjectFromFile = new Ticket(givenGuild.getMemberById(ticketOwner), ticketSubject, new Date(Long.parseLong(createdOn)), givenGuild.getTextChannelById(ticketChannel), ticketID);

            if(givenGuild.getTextChannelById(ticketChannel) != null)
            {

/*
                System.out.println("================================================================");
                System.out.println("Adding new ticket to allTickets:");
                System.out.println("TICKET-ID = " + ticketObjectFromFile.getTicketID());
                System.out.println("CREATED-ON = " + ticketObjectFromFile.getCreatedOn());
                System.out.println("OWNER = " + ticketObjectFromFile.getTicketOwner());
                System.out.println("TICKET-CHANNEL = " + ticketObjectFromFile.getTicketChannel());
                System.out.println("TICKET-SUBJECT = " + ticketObjectFromFile.getTicketSubject());
                */

                allTickets.add(ticketObjectFromFile);
                currentTickets++;
            } else {

                try {
                    givenTicketFile.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void handleMessageInTicketChannel(MessageReceivedEvent event){
        String ticketLogDirectory = "Crafting Dead Bot/Tickets/ChatLogs/";
        Ticket givenTicket = TicketManager.getTicketFromChannel(event.getTextChannel());
        MessageObject newMessageObject = new MessageObject(event.getMember().getUser().getName()+"#"+event.getMember().getUser().getDiscriminator(), event.getMember().getUser().getId(), event.getMessage().getContentDisplay(), event.getMessage().getId(), new Date(), event.getMessage().isEdited());

        if(givenTicket != null)
        {
            File ticketFile = new File(ticketLogDirectory + "Ticket-" + givenTicket.getTicketID() + "(ChatLog).json");

            try {
                ArrayList<MessageObject> allMessages = new ArrayList<>();
                Path path = ticketFile.toPath();

                try(Reader reader = Files.newBufferedReader(path)){
                    MessageObject[] data = gson.fromJson(reader, MessageObject[].class);
                    allMessages.addAll(Arrays.asList(data));
                }

                allMessages.add(newMessageObject);
                FileWriter fileWriter = new FileWriter(ticketFile, false);
                fileWriter.write(gson.toJson(allMessages));
                fileWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void clearAll()
    {
        allTickets.clear();
        currentTickets = 0;
    }

    public int getCurrentAmountTickets()
    {
        return currentTickets;
    }

    public ArrayList<Ticket> getCurrentTickets()
    {
        return allTickets;
    }
}
