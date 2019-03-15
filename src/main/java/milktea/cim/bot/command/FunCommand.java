package milktea.cim.bot.command;

import java.awt.Color;
import java.util.List;

import milktea.cim.bot.event.MessageCommandEvent;
import milktea.cim.framework.command.Command;
import milktea.cim.framework.util.random.XORShift;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public final class FunCommand {

  private static final Color COLOR = new Color(141, 222, 180);
  private static final List<String> FUNNY_MESSAGES = List.of(
    "回鍋肉",
    "速報 山田孝之の乳首 7つに増える",
    "山田孝之 82万だったことが判明",
    "エビデンス",
    "ワセス",
    "テイクJ",
    "こんにちワイマックス",
    "趣がある");

  private final XORShift random = new XORShift();

  @Command(name = "fun", description = "お楽しみをします。funはフンという意味です。", permission = "cf.azuredev.cim.bot.command.utilities.fun")
  public void execute(MessageCommandEvent args) {
    var event = args.getEvent();
    var channel = event.getChannel();
    channel.sendMessage(createMessage(event)).queue();
  }

  public MessageEmbed createMessage(MessageReceivedEvent event) {
    var sender = event.getAuthor();
    var me = event.getJDA().getSelfUser();

    var builder = new EmbedBuilder();
    builder.setFooter(sender.getName(), sender.getAvatarUrl());
    builder.setColor(COLOR);

    if (Double.compare(random.nextDouble(), 0.001) < 0) {
      return builder
        .setTitle("大当たり")
        .setImage(me.getAvatarUrl())
        .build();
    }
    return builder
      .setTitle("フン")
      .appendDescription(generate())
      .build();
  }

  public String generate() {
    return FUNNY_MESSAGES.get(random.nextInt(FUNNY_MESSAGES.size()));
  }

}
