package milktea.cim.bot.command.yokemongo;

import com.mewna.catnip.entity.builder.EmbedBuilder;
import milktea.cim.bot.event.MessageCommandEvent;
import milktea.cim.framework.command.Command;

import java.awt.*;

public class YokedexCommand {

    private final Yokemons yokemons;

    public YokedexCommand(Yokemons pokemons) {
        this.yokemons = pokemons;
    }

    @Command(
        name = "yokedex",
        description = "(ベータ) 不審者の一覧を表示します",
        permission = "milktea.cim.bot.command.yokemon.yokedex"
    )
    public void execute(MessageCommandEvent event) {
        final var msg = event.message();
        final var sender = msg.author();
        final var ch = msg.channel();

        var embed = new EmbedBuilder()
            .title("**マイ指名手配**")
            .color(Color.MAGENTA)
            .footer(sender.username(), sender.avatarUrl());

        yokemons.yokedex().get(sender).stream()
            .forEach(t ->
                embed.field("**" + t + "**", "レベル Y0", true)
            );
        ch.sendMessage(embed.build());
    }

}
