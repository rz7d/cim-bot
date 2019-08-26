package milktea.cim.bot.main;

import milktea.cim.bot.connector.discord.DiscordBot;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.concurrent.ForkJoinTask;

public final class Main {

    private Main() {
    }

    public static void main(String[] args) throws Exception {
        var properties = new Properties();
        properties.load(Files.newBufferedReader(Paths.get("./conf/access.properties")));
        var token = properties.getProperty("token_secret");

        new DiscordBot(token).connect();

        ForkJoinTask.helpQuiesce();
    }

}
