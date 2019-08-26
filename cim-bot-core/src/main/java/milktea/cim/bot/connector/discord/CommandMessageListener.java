package milktea.cim.bot.connector.discord;

import com.mewna.catnip.entity.message.Message;
import milktea.cim.bot.command.fun.FunCommand;
import milktea.cim.bot.command.fun.FunnyMessages;
import milktea.cim.bot.event.MessageCommandEvent;
import milktea.cim.framework.command.CommandBus;
import milktea.cim.framework.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Collection;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandMessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandMessageListener.class);
    private static final Collection<String> prefixes = Stream.of("y!", "有!")
        .map(p -> Normalizer.normalize(p, Form.NFKC)).collect(Collectors.toList());

    private final CommandBus bus = new CommandBus();

    public CommandMessageListener() {
        bus.register(new FunCommand(new FunnyMessages()));
    }

    @EventHandler
    public void onMessageReceived(Message message) {
        final var text = Normalizer.normalize(message.content(), Form.NFKC).trim().toLowerCase();
        for (var prefix : prefixes) {
            if (text.startsWith(prefix)) {
                var tokenizer = new StringTokenizer(text.substring(prefix.length()));
                var command = tokenizer.nextToken();
                LOGGER.info(message.author() + " issued server command: " + text);
                bus.execute(command, new MessageCommandEvent(bus, message));
                return;
            }
        }
    }

}
