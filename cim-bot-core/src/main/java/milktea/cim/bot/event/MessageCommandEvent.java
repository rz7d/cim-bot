package milktea.cim.bot.event;

import java.util.Objects;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class MessageCommandEvent {

    private final MessageReceivedEvent event;

    public MessageCommandEvent(MessageReceivedEvent event) {
        this.event = Objects.requireNonNull(event);
    }

    public MessageReceivedEvent getEvent() {
        return event;
    }

}
