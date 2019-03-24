package milktea.cim.bot.main;

import java.util.Properties;
import java.util.concurrent.ForkJoinTask;

import milktea.cim.bot.connector.discord.DiscordBot;

public final class Main {

  private Main() {
  }

  public static void main(String[] args) throws Exception {
    var properties = new Properties();
    properties.load(DiscordBot.class.getResourceAsStream("/access.properties"));
    var token = properties.getProperty("token_secret");

    var bot = new DiscordBot(token);
    bot.waitForConnect();

    ForkJoinTask.helpQuiesce();
  }

}
