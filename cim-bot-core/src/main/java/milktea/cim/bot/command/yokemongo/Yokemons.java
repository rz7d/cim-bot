package milktea.cim.bot.command.yokemongo;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.mewna.catnip.entity.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Yokemons {

    private static final Logger LOGGER = LoggerFactory.getLogger(Yokemons.class);

    private final Random random = new Random();

    private final List<String> pokemons;

    private final Multimap<User, String> yokedex = Multimaps.newListMultimap(new HashMap<>(), ArrayList::new);

    public Yokemons() {
        String json;
        try {
            json = new String(getClass().getResourceAsStream("/yoke.json").readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new InternalError(exception);
        }
        var root = JSON.parseObject(json);
        var array = root.getJSONArray("yokemons");
        var size = array.size();
        var builder = new ArrayList<String>(size);
        for (var i = 0; i < size; ++i) {
            var message = array.getJSONObject(i);
            var content = message.getString("name");
            var author = message.getJSONObject("author");
            final String authorName;
            if (author != null) {
                authorName = author.getString("name");
            } else {
                authorName = "azure";
            }
            builder.add(content);
            LOGGER.info("Loading pokemon \"{}\" by {}", content, authorName);
        }
        builder.trimToSize();
        this.pokemons = builder;
    }

    public Yokemons(List<String> messages) {
        Objects.requireNonNull(messages);
        this.pokemons = List.copyOf(messages);
    }

    public String generate() {
        return pokemons.get(random.nextInt(pokemons.size()));
    }

    public Multimap<User, String> yokedex() {
        return yokedex;
    }

}
