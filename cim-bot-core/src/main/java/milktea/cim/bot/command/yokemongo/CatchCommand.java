package milktea.cim.bot.command.yokemongo;

import com.mewna.catnip.entity.builder.EmbedBuilder;
import milktea.cim.bot.event.MessageCommandEvent;
import milktea.cim.framework.command.Command;

import java.awt.*;

public class CatchCommand {

    private final Yokemons yokemons;

    public CatchCommand(Yokemons pokemons) {
        this.yokemons = pokemons;
    }

    @Command(
        name = "catch",
        description = "(ベータ) Discord に現れた不審者を逮捕します",
        permission = "milktea.cim.bot.command.yokemon.catch"
    )
    public void execute(MessageCommandEvent event) {
        final var msg = event.message();
        final var sender = msg.author();
        final var ch = msg.channel();

        final var yoke = yokemons.generate();
        ch.sendMessage(
            new EmbedBuilder()
                .title("あなたは **逮捕**！次を:")
                .description(yoke)
                .color(Color.MAGENTA)
                .footer(sender.username(), sender.avatarUrl())
                .build()
        );
        yokemons.yokedex().put(sender, yoke);
    }

}
