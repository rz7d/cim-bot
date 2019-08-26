package milktea.cim.bot.connector.discord;

import com.mewna.catnip.Catnip;
import com.mewna.catnip.CatnipOptions;
import com.mewna.catnip.entity.user.Presence;
import com.mewna.catnip.entity.user.Presence.OnlineStatus;
import com.mewna.catnip.shard.DiscordEvent;
import milktea.cim.framework.event.EventBus;
import milktea.cim.framework.extension.Extension;

public final class DiscordBot extends Extension {

    private final EventBus eventBus = new EventBus();
    private final Catnip discord;

    public DiscordBot(String token) {
        final var options = new CatnipOptions(token);
        options
            .presence(Presence.of(OnlineStatus.ONLINE, Presence.Activity.of("♥ Catnip", Presence.ActivityType.WATCHING)));

        this.discord = Catnip.catnip(options);
        discord.on(DiscordEvent.MESSAGE_CREATE, eventBus::fire);
        eventBus.register(new WordFinderListener());
        eventBus.register(new CommandMessageListener());
    }

    public void connect() {
        discord.connect();
    }

}
