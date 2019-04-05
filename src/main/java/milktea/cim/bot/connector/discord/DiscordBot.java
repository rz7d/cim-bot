package milktea.cim.bot.connector.discord;

import javax.security.auth.login.LoginException;

import milktea.cim.framework.event.EventHandler;
import milktea.cim.framework.event.extension.ExtensionDisableEvent;
import milktea.cim.framework.event.extension.ExtensionEnableEvent;
import milktea.cim.framework.extension.Extension;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;

public final class DiscordBot extends Extension {

    @EventHandler
    public void onEnable(ExtensionEnableEvent event) {
        if (!event.getExtension().equals(this))
            return;
        discord.getPresence().setStatus(OnlineStatus.ONLINE);
    }

    @EventHandler
    public void onDisable(ExtensionDisableEvent event) {
        if (!event.getExtension().equals(this))
            return;
        discord.getPresence().setStatus(OnlineStatus.OFFLINE);
    }

    private final JDA discord;

    public DiscordBot(String token) throws LoginException {
        this.discord = new JDABuilder().setToken(token)
                .addEventListener(new WordFinderListener(), new CommandMessageListener()).setGame(Game.watching("回鍋肉"))
                .build();
    }

    public void waitForConnect() throws InterruptedException {
        discord.awaitReady();
    }

}
