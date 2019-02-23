package com.phoenixx.bot.objects;


import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author Phoenixx
 * - Crafting Dead Discord Bot
 * - 2019-02-22
 * - 6:55 PM
 **/
public class Ticket
{
    private Member ticketOwner;
    private String ticketSubject;
    private Date createdOn;
    private TextChannel ticketChannel;
    private int ticketID;
    private ArrayList<MessageObject> allMessages = new ArrayList<MessageObject>();

    public Ticket(Member ticketOwner, String ticketSubject, Date createdOn, TextChannel ticketChannel, int ticketID) {
        this.ticketOwner = ticketOwner;
        this.ticketSubject = ticketSubject;
        this.createdOn = createdOn;
        this.ticketChannel = ticketChannel;
        this.ticketID = ticketID;
    }

    public Member getTicketOwner() {
        return ticketOwner;
    }

    public String getTicketSubject() {
        return ticketSubject;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public TextChannel getTicketChannel() {
        return ticketChannel;
    }

    public int getTicketID() {
        return ticketID;
    }

    public ArrayList<MessageObject> getAllMessages()
    {
        return allMessages;
    }

    public void setAllMessages(ArrayList<MessageObject> allMessages)
    {
        getAllMessages().addAll(allMessages);
    }

    public void addMessage(MessageObject messageObject)
    {
        allMessages.add(messageObject);
    }

    @Override
    public String toString()
    {
        return "{\n  \"ticketOwner\": \"" + this.getTicketOwner().getUser().getId() + "\",\n  \"ticketSubject\": \"" + this.getTicketSubject() + "\",\n  \"createdOn\": \"" + this.createdOn.getTime() +
                "\",\n  \"ticketChannel\": \"" + this.ticketChannel.getId() + "\",\n  \"ticketID\": " + this.ticketID + "\n}";
    }
}
