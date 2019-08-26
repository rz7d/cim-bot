package milktea.cim.bot.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;

public class WordFinderTest {

    @Test
    public void testMatcher() {
        WordFinder finder = new WordFinder();
        assertEquals(Optional.empty(), finder.matcher(""));
        for (String word : new String[] { "朝山貴生", "朝山タカオ", "朝山たかお" })
            assertEquals(word, finder.matcher(word).get().group());
        finder.matcher("ハマゲ").get();
    }

}
