package milktea.cim.bot.reader;

import java.util.Collection;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class WordFinder {

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

  public static Optional<? extends Matcher> matcher(String text) {
    for (var pattern : PATTERNS) {
      var matcher = pattern.matcher(text);
      if (matcher.find()) {
        return Optional.of(matcher);
      }
    }
    return Optional.empty();
  }

}
