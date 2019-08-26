package milktea.cim.bot.connector.discord;

import com.mewna.catnip.entity.builder.EmbedBuilder;
import com.mewna.catnip.entity.message.Message;
import milktea.cim.bot.reader.WordFinder;
import milktea.cim.framework.event.EventHandler;

import java.text.MessageFormat;
import java.text.Normalizer;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.stream.IntStream;

public class WordFinderListener {

    private final WordFinder finder = new WordFinder();

    @EventHandler
    public void onMessageReceived(Message message) {
        if (Objects.equals(message.author(), message.catnip().selfUser()))
            return;

        var text = message.content();
        Optional<? extends Matcher> result = finder.matcher(Normalizer.normalize(text, Normalizer.Form.NFKC));
        if (result.isEmpty())
            return;

        Matcher matcher = result.get();
        var found = matcher.group();
        var name = message.author().username();
        var avatar = message.author().avatarUrl();

        var builder = new EmbedBuilder()
            .title("ワード検出")
            .description(MessageFormat.format("\"{0}\" を発見しました:", found));
        insertInformation(text, found, matcher.start(), builder);
        builder.footer(name, avatar);
        message.channel().sendMessage(builder.build());
    }

    private static void insertInformation(String text, String found, int from, EmbedBuilder builder) {
        builder.description(
            "```" +
                text +
                " \r\n" +
                createLine(text, from, found.length()) +
                "```");
    }

    public static String repeat(char c, int count) {
        var builder = new StringBuilder();
        IntStream.range(0, count).forEach(__ -> builder.append(c));
        return builder.toString();
    }

    public static String createLine(String text, int from, int length) {
        var builder = new StringBuilder();
        int count = 0;
        for (int c : text.codePoints().toArray()) {
            if (Character.isWhitespace(c)) {
                builder.append((char) c);
                continue;
            }
            count += Character.charCount(c);
            if (count <= from || count > from + length) {
                builder.append(isHalfwidth(c) ? ' ' : '　');
            } else {
                builder.append(isHalfwidth(c) ? '―' : 'ー');
            }
        }
        return builder.toString();
    }

    private static boolean isHalfwidth(int c) {
        return ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z') || ('0' <= c && c <= '9');
    }

}
