package milktea.cim.bot.command.fun;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

public class FunnyMessagesTest {

    @Test
    public void testConstructorNoArgs() {
        var o = new FunnyMessages();
        assertEquals(true, o.messages.contains("テスティング 回鍋肉"));
        assertEquals(true, o.messages.contains("7つの乳首 公開"));
        assertEquals(true, o.messages.contains("82万についての巨大委員会突如開幕"));
        assertEquals(true, o.messages.contains("テスト"));
        assertEquals(true, o.messages.contains("ワセシング・ジェイ・ユニット・木星"));
        assertEquals(true, o.messages.contains("たけJ"));
        assertEquals(true, o.messages.contains("ソフトバンク"));
        assertEquals(true, o.messages.contains("ワカカ"));
        assertEquals(true, o.messages.contains("Snipping Tool"));
    }

    @Test
    public void testConstructorWithArgs() {
        var list = List.of("はまげ", "ごっくす", "３３４");
        var o = new FunnyMessages(list);
        for (var x : list) {
            assertEquals(true, o.messages.contains(x));
        }
    }

}
