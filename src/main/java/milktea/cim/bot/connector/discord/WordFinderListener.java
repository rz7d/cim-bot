package milktea.cim.bot.connector.discord;

import java.text.MessageFormat;
import java.text.Normalizer;
import java.util.Collection;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class WordFinderListener extends ListenerAdapter {

  private static final String PATTERN_HMAGE_EN = "[HEhe]\\s*[MGmg]\\s*a\\s*[GMgm]\\s*[EHeh]";
  private static final String PATTERN_HMAGE_JP = "[はハ]\\s*([るル])?\\s*[まマ]\\s*[げゲ]\\s*([んン]|[どド]\\s*[んン])?|春\\s*[馬真間]\\s*源";
  private static final String PATTERN_KIZOKU_JP = "本\\s*物\\s*貴\\s*族|((半|[ハは]\\s*[ンん])\\s*(間|[マま]))\\s*(巌|[イい]\\s*[ワわ]\\s*[オお])?";
  private static final String PATTERN_ASAYAMA_JP = "(朝|[あア]\\s*[さサ])\\s*(山|[やヤ]\\s*[まマ])\\s*(貴|[たタ]\\s*[かカ])\\s*(生|[おオ])";
  private static final String PATTERN_YAMADA_JP = "(山|[やヤ]\\s*[まマ])\\s*(田|[だダ])\\s*(孝|[たタ]\\s*[かカ])\\s*(之|[ゆユ]\\s*[きキ])";
  private static final String PATTERN_HOIKORO_JP = "回\\s*鍋\\s*肉";

  private static final Collection<Pattern> PATTERNS = Stream
    .of(PATTERN_HMAGE_EN,
      PATTERN_HMAGE_JP,
      PATTERN_KIZOKU_JP,
      PATTERN_ASAYAMA_JP,
      PATTERN_YAMADA_JP,
      PATTERN_HOIKORO_JP)
    .parallel()
    .map(Pattern::compile)
    .collect(Collectors.toList());

  @Override
  public void onMessageReceived(MessageReceivedEvent event) {
    if (Objects.equals(event.getAuthor(), event.getJDA().getSelfUser()))
      return;

    for (Pattern p : PATTERNS) {
      if (sendIfFound(event, p))
        break;
    }
  }

  private static boolean sendIfFound(MessageReceivedEvent event, Pattern pattern) {
    var text = event.getMessage().getContentDisplay();
    Matcher matcher = pattern.matcher(Normalizer.normalize(text, Normalizer.Form.NFKC));
    if (!matcher.find()) {
      return false;
    }
    var found = matcher.group();

    var name = event.getAuthor().getName();
    var avatar = event.getAuthor().getAvatarUrl();

    var builder = new EmbedBuilder();
    builder.setTitle("ワード検出");
    builder.appendDescription(MessageFormat.format("\"{0}\" を発見しました:", found));
    insertInformation(text, found, matcher.start(), builder);
    builder.setFooter(name, avatar);
    event.getChannel().sendMessage(builder.build()).queue();
    return true;
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
