package milktea.cim.bot.connector.discord;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Collection;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import milktea.cim.bot.command.fun.FunCommand;
import milktea.cim.bot.command.fun.FunnyMessages;
import milktea.cim.bot.event.MessageCommandEvent;
import milktea.cim.framework.command.CommandBus;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class CommandMessageListener extends ListenerAdapter {

    private static final Collection<String> prefixes = Stream.of("y!", "有!")
            .map(p -> Normalizer.normalize(p, Form.NFKC)).collect(Collectors.toList());

    private final CommandBus bus = new CommandBus();

    public CommandMessageListener() {
        bus.register(new FunCommand(new FunnyMessages()));
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        final var text = Normalizer.normalize(event.getMessage().getContentRaw(), Form.NFKC).trim().toLowerCase();
        for (var prefix : prefixes) {
            if (text.startsWith(prefix)) {
                var tokenizer = new StringTokenizer(text.substring(prefix.length()));
                var command = tokenizer.nextToken();
                bus.execute(command, new MessageCommandEvent(event));
                return;
            }
        }
    }

}
