package milktea.cim.bot.command.fun;

import com.mewna.catnip.Catnip;
import com.mewna.catnip.entity.message.Message;
import com.mewna.catnip.entity.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FunCommandTest {

    private final FunnyMessages messages = new FunnyMessages();

    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new FunCommand(messages));
    }

    @Test
    public void testCreateMessage() {
        var discord = mock(Catnip.class);
        when(discord.selfUser()).thenReturn(mock(User.class));

        var event = mock(Message.class);
        when(event.catnip()).thenReturn(discord);

        var author = mock(User.class);
        when(event.author()).thenReturn(author);

        when(author.avatarUrl()).thenReturn("https://example.com/example.png");
        when(author.username()).thenReturn("Example User");

        var o = new FunCommand(messages);
        var result = o.createMessage(event);
        assertNotNull(result);
    }

}
