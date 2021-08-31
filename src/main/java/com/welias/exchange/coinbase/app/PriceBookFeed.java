package com.welias.exchange.coinbase.app;

import com.welias.exchange.coinbase.app.ui.TerminalPriceBookRenderer;
import com.welias.exchange.coinbase.app.websocket.CoinbaseWebSocketClient;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.concurrent.CountDownLatch;

public class PriceBookFeed
{
    public static void main(String[] args)
    {
        CountDownLatch doneSignal = new CountDownLatch(1);
        Runtime.getRuntime().addShutdownHook(new Thread(doneSignal::countDown));

        Options options = buildCLIOptions();
        CommandLine cmd = null;
        try
        {
            cmd = ((CommandLineParser) new DefaultParser()).parse(options, args);
        }
        catch (ParseException e)
        {
            System.out.println(e.getMessage());
            new HelpFormatter().printHelp("utility-name", options);

            System.exit(0);
        }

        String instrument = cmd.getOptionValue("instrument");

        CoinbaseWebSocketClient webSocketClient = new CoinbaseWebSocketClient();
        TerminalPriceBookRenderer terminalPriceBookRenderer = new TerminalPriceBookRenderer();
        webSocketClient.subscribe(instrument);
        webSocketClient.registerListener(terminalPriceBookRenderer);

        try
        {
            doneSignal.await();
            System.out.println("Exiting...");
            webSocketClient.close();
        }
        catch (InterruptedException ignored)
        {
        }
    }

    private static Options buildCLIOptions()
    {
        Options options = new Options();

        Option input = new Option("i", "instrument", true, "the instrument to retrieve pricing for");
        input.setRequired(true);
        options.addOption(input);
        return options;
    }
}
