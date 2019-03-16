package milktea.cim.bot.reader;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;

public class WordFinderTest {

  @Test
  public void testMatcher() {
    assertEquals(Optional.empty(), WordFinder.matcher(""));
    for (String word : new String[] { "朝山貴生", "朝山タカオ", "朝山たかお" })
      assertEquals(word, WordFinder.matcher(word).get().group());
    WordFinder.matcher("ハマゲ").get();
  }

}
