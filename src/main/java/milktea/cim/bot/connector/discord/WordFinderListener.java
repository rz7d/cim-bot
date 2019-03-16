package milktea.cim.bot.connector.discord;

import java.text.MessageFormat;
import java.text.Normalizer;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.stream.IntStream;

import milktea.cim.bot.reader.WordFinder;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class WordFinderListener extends ListenerAdapter {

  @Override
  public void onMessageReceived(MessageReceivedEvent event) {
    if (Objects.equals(event.getAuthor(), event.getJDA().getSelfUser()))
      return;

    var text = event.getMessage().getContentDisplay();
    Optional<? extends Matcher> result = WordFinder.matcher(Normalizer.normalize(text, Normalizer.Form.NFKC));
    if (result.isEmpty())
      return;

    Matcher matcher = result.get();
    var found = matcher.group();
    var name = event.getAuthor().getName();
    var avatar = event.getAuthor().getAvatarUrl();

    var builder = new EmbedBuilder();
    builder.setTitle("ワード検出");
    builder.appendDescription(MessageFormat.format("\"{0}\" を発見しました:", found));
    insertInformation(text, found, matcher.start(), builder);
    builder.setFooter(name, avatar);
    event.getChannel().sendMessage(builder.build()).queue();
  }

  private static void insertInformation(String text, String found, int from, EmbedBuilder builder) {
    builder
      .appendDescription("```")
      .appendDescription(text)
      .appendDescription(" \r\n")
      .appendDescription(createLine(text, from, found.length()))
      .appendDescription("```");
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
