package milktea.cim.bot.event;

import com.mewna.catnip.entity.message.Message;
import milktea.cim.framework.command.CommandBus;

public final class MessageCommandEvent {

    private final CommandBus commandBus;

    private final Message message;

    public MessageCommandEvent(CommandBus commandBus, Message message) {
        this.commandBus = commandBus;
        this.message = message;
    }

    public CommandBus commandBus() {
        return commandBus;
    }

    public Message message() {
        return message;
    }

}
