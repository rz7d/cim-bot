package milktea.cim.bot.command.fun;

import com.mewna.catnip.entity.builder.EmbedBuilder;
import com.mewna.catnip.entity.message.Embed;
import com.mewna.catnip.entity.message.Message;
import milktea.cim.bot.event.MessageCommandEvent;
import milktea.cim.framework.command.Command;

import java.awt.*;
import java.util.Random;

public final class FunCommand {

    private static final Color COLOR = new Color(141, 222, 180);

    private final Random random = new Random();

    private final FunnyMessages messages;

    public FunCommand(FunnyMessages messages) {
        this.messages = messages;
    }

    @Command(name = "fun", description = "お楽しみをします。funはフンという意味です。", permission = "milktea.cim.bot.command.essentials.fun")
    public void execute(MessageCommandEvent event) {
        var message = event.message();
        var channel = message.channel();
        channel.sendMessage(createMessage(message));
    }

    public Embed createMessage(Message message) {
        var sender = message.author();
        var me = message.catnip().selfUser();

        var response = new EmbedBuilder().footer(sender.username(), sender.avatarUrl()).color(COLOR);

        if (Double.compare(random.nextDouble(), 0.001) < 0) {
            return response.title("大当たり").image(me.avatarUrl()).build();
        } else {
            return response.title("フン").description(messages.generate()).build();
        }
    }

}
