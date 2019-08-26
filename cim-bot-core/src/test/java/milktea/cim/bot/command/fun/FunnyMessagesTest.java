package milktea.cim.bot.command.fun;

import static org.assertj.core.api.Assertions.*;
import java.util.List;

import org.junit.jupiter.api.Test;

public class FunnyMessagesTest {

    @Test
    public void testConstructorNoArgs() {
        var o = new FunnyMessages();
        assertThat(o.messages)
                .contains("テスティング 回鍋肉")
                .contains("7つの乳首 公開")
                .contains("82万についての巨大委員会突如開幕")
                .contains("テスト")
                .contains("ワセシング・ジェイ・ユニット・木星")
                .contains("たけJ")
                .contains("ソフトバンク")
                .contains("ワカカ")
                .contains("Snipping Tool");
    }

    @Test
    public void testConstructorWithArgs() {
        var list = List.of("はまげ", "ごっくす", "３３４");
        var o = new FunnyMessages(list);
        assertThat(o.messages).containsAll(list);
    }

}
