package milktea.cim.bot.reader;

import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class WordSearcher {

  private final Pattern pattern;

  public WordSearcher(Pattern pattern) {
    this.pattern = pattern;
  }

  public Matcher matcher(String text) {
    return pattern.matcher(Normalizer.normalize(text, Normalizer.Form.NFKC));
  }

}
