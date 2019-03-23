package milktea.cim.bot.reader;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class WordFinder {

  private static final Logger LOGGER = Logger.getLogger(WordFinder.class.getName());

  private final Collection<Pattern> patterns;

  public WordFinder() {
    String json;
    try {
      json = new String(getClass().getResourceAsStream("/finder.json").readAllBytes(), StandardCharsets.UTF_8);
    } catch (IOException exception) {
      throw new InternalError(exception);
    }
    var array = JSON.parseArray(json);
    var size = array.size();
    var builder = new ArrayList<Pattern>(size);
    for (var i = 0; i < size; ++i) {
      var message = array.getJSONObject(i);
      var name = message.getString("name");
      var language = message.getString("language");
      var pattern = message.getString("pattern");

      builder.add(Pattern.compile(pattern));
      LOGGER.info(() -> MessageFormat.format(
        "Loading Pattern \"{0}\" ({1}): \"{2}\"",
        name,
        new Locale(language).getDisplayLanguage(),
        pattern));
    }
    builder.trimToSize();
    this.patterns = builder;
  }

  public Optional<? extends Matcher> matcher(String text) {
    for (var pattern : patterns) {
      var matcher = pattern.matcher(text);
      if (matcher.find()) {
        LOGGER.info(() -> MessageFormat.format("Match: {0}, Text: {1}", matcher.group(), text));
        return Optional.of(matcher);
      }
    }
    return Optional.empty();
  }

}
