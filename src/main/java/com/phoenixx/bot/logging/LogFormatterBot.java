package com.phoenixx.bot.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * @author Phoenixx
 * - Crafting Dead Discord Bot
 * - 2019-02-22
 * - 6:55 PM
 **/
class LogFormatterBot extends Formatter
{
    private SimpleDateFormat simpleDateFormat;
    final LogHandlerBot logHandlerBot;

    public LogFormatterBot(LogHandlerBot paramLogAgent)
    {
        this.logHandlerBot = paramLogAgent;
        this.simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public String format(LogRecord paramLogRecord)
    {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(this.simpleDateFormat.format(Long.valueOf(paramLogRecord.getMillis())));
        if (LogHandlerBot.getLoggerName(this.logHandlerBot) != null) {
            localStringBuilder.append(LogHandlerBot.getLoggerName(this.logHandlerBot));
        }
        localStringBuilder.append(" [").append(paramLogRecord.getLevel().getName()).append("] ");
        localStringBuilder.append(formatMessage(paramLogRecord));
        Throwable localThrowable = paramLogRecord.getThrown();
        if (localThrowable != null)
        {
            StringWriter localStringWriter = new StringWriter();
            localThrowable.printStackTrace(new PrintWriter(localStringWriter));
            localStringBuilder.append(localStringWriter.toString());
        }
        System.out.println("" + localStringBuilder.toString());
        return localStringBuilder.toString() + "\n";
    }
}