package milktea.cim.bot.reader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import milktea.cim.bot.command.fun.FunnyMessages;

public final class WordFinder {

  private static final Logger LOGGER = LoggerFactory.getLogger(FunnyMessages.class);

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
      LOGGER.info("Loading Pattern \"{}\" ({}): \"{}\"",
          name,
          new Locale(language).getDisplayLanguage(),
          pattern);
    }
    builder.trimToSize();
    this.patterns = builder;
  }

  public Optional<? extends Matcher> matcher(String text) {
    for (var pattern : patterns) {
      var matcher = pattern.matcher(text);
      if (matcher.find()) {
        LOGGER.info("Match: {}, Text: {}", matcher.group(), text);
        return Optional.of(matcher);
      }
    }
    return Optional.empty();
  }

}
