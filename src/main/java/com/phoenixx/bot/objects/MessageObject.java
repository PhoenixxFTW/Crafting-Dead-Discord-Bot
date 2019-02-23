package com.phoenixx.bot.objects;

import java.util.Date;

/**
 * @author Phoenixx
 * - Crafting Dead Discord Bot
 * - 2019-02-22
 * - 6:55 PM
 **/
public class MessageObject
{
    private String messageOwner;
    private String messageOwnerID;
    private String message;
    private String messageID;
    private Date createdOn;
    private boolean isEdited;

    public MessageObject(String messageOwner, String messageOwnerID, String message, String messageID, Date createdOn, boolean isEdited) {
        this.messageOwner = messageOwner;
        this.messageOwnerID = messageOwnerID;
        this.messageID = messageID;
        this.message = message;
        this.createdOn = createdOn;
        this.isEdited = isEdited;
    }

    public String getMessageOwner() {
        return messageOwner;
    }

    public String getMessageOwnerID() {
        return messageOwnerID;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageID() {
        return messageID;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public boolean isEdited() {
        return isEdited;
    }

    @Override
    public String toString()
    {
        if(isEdited)
        {
            this.message+=" (edited)";
        }

        return "{\n  \"messageOwner\": \"" + this.getMessageOwner() + "\",  \n \"messageOwnerID\": \"" + this.getMessageOwnerID() + "\",  \n  \"message\": \"" + this.getMessage() + "\", \n \"messageID\": \"" + this.getMessageID() + "\",  \n  \"createdOn\": \"" + this.createdOn.getTime() +"\n}";
    }
}
