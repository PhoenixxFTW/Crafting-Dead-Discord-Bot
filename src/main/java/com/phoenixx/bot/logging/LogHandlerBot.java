package com.phoenixx.bot.logging;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Phoenixx
 * - Crafting Dead Discord Bot
 * - 2019-02-22
 * - 6:55 PM
 **/
public class LogHandlerBot {

    private final Logger logger;
    private final String location;
    private final String loggerName;

    public LogHandlerBot(String paramString1, String paramString3)
    {
        this.logger = Logger.getLogger(paramString1);
        this.loggerName = paramString1;
        this.location = paramString3;
    }

    public void setup()
    {
        LogFormatterBot localLogFormatterBot = new LogFormatterBot(this);
        try
        {
            this.logger.setUseParentHandlers(false);
            FileHandler localFileHandler = new FileHandler(this.location, true);
            localFileHandler.setFormatter(localLogFormatterBot);
            this.logger.addHandler(localFileHandler);
        }
        catch (Exception localException)
        {
            this.logger.log(Level.WARNING, "Failed to log " + this.loggerName + " to " + this.location, localException);
        }
    }

    public void info(String message)
    {
        this.logger.log(Level.INFO, message);
    }

    public Logger getLogger()
    {
        return this.logger;
    }

    public void warning(String message)
    {
        this.logger.log(Level.WARNING, message);
    }

    public void warning(String message, Object... args)
    {
        this.logger.log(Level.WARNING, message, args);
    }

    public void warning(String message, Throwable paramThrowable)
    {
        this.logger.log(Level.WARNING, message, paramThrowable);
    }

    public void severe(String message)
    {
        this.logger.log(Level.SEVERE, message);
    }

    public void severe(String message, Throwable paramThrowable)
    {
        this.logger.log(Level.SEVERE, message, paramThrowable);
    }

    public void clean(String message)
    {
        this.logger.log(Level.FINE, message);
    }

    public void login(String message)
    {
        this.logger.log(Level.INFO,"[LOGIN] " + message);
    }

    public void logout(String message)
    {
        this.logger.log(Level.INFO,"[LOGOUT] " + message);
    }

    public void commands(String message)
    {
        this.logger.log(Level.INFO,"[COMMAND] " + message);
    }

    static String getLoggerName(LogHandlerBot logHandlerBot)
    {
        return " [Crafting Dead]";
    }
}
