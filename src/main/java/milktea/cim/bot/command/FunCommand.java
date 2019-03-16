package milktea.cim.bot.command;

import java.awt.Color;
import java.util.Random;

import milktea.cim.bot.event.MessageCommandEvent;
import milktea.cim.framework.command.Command;
import milktea.cim.framework.util.random.XORShift;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public final class FunCommand {

  private static final Color COLOR = new Color(141, 222, 180);

  private final Random random = new XORShift();

  private final FunnyMessages messages;

  public FunCommand(FunnyMessages messages) {
    this.messages = messages;
  }

  @Command(name = "fun", description = "お楽しみをします。funはフンという意味です。", permission = "cf.azuredev.cim.bot.command.utilities.fun")
  public void execute(MessageCommandEvent args) {
    var event = args.getEvent();
    var channel = event.getChannel();
    channel.sendMessage(createMessage(event)).queue();
  }

  public MessageEmbed createMessage(MessageReceivedEvent event) {
    var sender = event.getAuthor();
    var me = event.getJDA().getSelfUser();

    var builder = new EmbedBuilder()
      .setFooter(sender.getName(), sender.getAvatarUrl())
      .setColor(COLOR);

    if (Double.compare(random.nextDouble(), 0.001) < 0) {
      return builder
        .setTitle("大当たり")
        .setImage(me.getAvatarUrl())
        .build();
    } else {
      return builder
        .setTitle("フン")
        .appendDescription(messages.generate())
        .build();
    }
  }

}
