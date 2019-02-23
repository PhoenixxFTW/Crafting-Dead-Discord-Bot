package com.phoenixx.bot.handlers;

import java.io.*;

/**
 * @author Phoenixx
 * - Crafting Dead Discord Bot
 * - 2019-02-22
 * - 6:55 PM
 **/
public class HtmlHandler
{
    public static void createHtml() throws IOException {
        BufferedReader txtfile = null;
        OutputStream htmlfile = null;
        try {
            txtfile = new BufferedReader(new FileReader("c:\\test.txt"));
            htmlfile = new FileOutputStream(new File("c:\\test.html"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        PrintStream printhtml = new PrintStream(htmlfile);

        String[] txtbyLine = new String[500];
        String temp = "";
        String txtfiledata = "";

        String htmlheader="<html><head>";
        htmlheader+="<title>Equivalent HTML</title>";
        htmlheader+="</head><body>";
        String htmlfooter="</body></html>";
        int linenum = 0 ;

        while ((txtfiledata = txtfile.readLine())!= null)
        {
            txtbyLine[linenum] = txtfiledata;
            linenum++;
        }
        for(int i=0;i<linenum;i++)
        {
            if(i == 0)
            {
                temp = htmlheader + txtbyLine[0];
                txtbyLine[0] = temp;
            }
            if(linenum == i+1)
            {
                temp = txtbyLine[i] + htmlfooter;
                txtbyLine[i] = temp;
            }
            printhtml.println(txtbyLine[i]);
        }

        printhtml.close();
        htmlfile.close();
        txtfile.close();
    }
}
