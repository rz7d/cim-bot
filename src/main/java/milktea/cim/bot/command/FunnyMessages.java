package milktea.cim.bot.command;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.alibaba.fastjson.JSON;

import milktea.cim.framework.util.random.XORShift;

public class FunnyMessages {

  private static final Logger LOGGER = Logger.getLogger(FunnyMessages.class.getName());

  private final Random random = new XORShift();

  private final List<String> messages;

  public FunnyMessages() {
    String json;
    try {
      json = new String(getClass().getResourceAsStream("/fun.json").readAllBytes(), StandardCharsets.UTF_8);
    } catch (IOException exception) {
      throw new InternalError(exception);
    }
    var root = JSON.parseObject(json);
    var array = root.getJSONArray("messages");
    var size = array.size();
    var builder = new ArrayList<String>(size);
    for (var i = 0; i < size; ++i) {
      var message = array.getJSONObject(i);
      var content = message.getString("content");
      var author = message.getJSONObject("author");
      final String authorName;
      if (author != null) {
        authorName = author.getString("name");
      } else {
        authorName = "azure";
      }
      builder.add(content);
      LOGGER.log(Level.INFO, "Loading funny message \"{0}\" by {1}", new Object[] { content, authorName });
    }
    builder.trimToSize();
    this.messages = builder;
  }

  public FunnyMessages(List<String> messages) {
    this.messages = Objects.requireNonNull(messages);
  }

  public String generate() {
    return messages.get(random.nextInt(messages.size()));
  }

}
