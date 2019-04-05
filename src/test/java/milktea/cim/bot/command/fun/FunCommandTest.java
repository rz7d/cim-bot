package milktea.cim.bot.command.fun;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.SelfUser;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class FunCommandTest {

    private final FunnyMessages messages = new FunnyMessages();

    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new FunCommand(messages));
    }

    @Test
    public void testCreateMessage() {
        var jda = mock(JDA.class);
        when(jda.getSelfUser()).thenReturn(mock(SelfUser.class));

        var event = mock(MessageReceivedEvent.class);
        when(event.getJDA()).thenReturn(jda);

        var author = mock(User.class);
        when(event.getAuthor()).thenReturn(author);

        var o = new FunCommand(messages);
        var result = o.createMessage(event);
        assertNotNull(result);
    }

}
