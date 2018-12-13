package units;

import com.kashi.scoreboard.models.CombinedEvent;
import com.kashi.scoreboard.utils.CsvUtils;
import com.kashi.scoreboard.utils.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class CsvUtilsTest {

    @Test
    public void parseResults_regular_successful() {
        Set<CombinedEvent> combinedEvents = CsvUtils.parseResults(this.getClass().getResource("/results.csv").getFile(), "parse-metadata.xml");
        Assertions.assertEquals(4, combinedEvents.size());
    }

    @Test
    public void parseMetadata_invalidFile_exception() {
        Assertions.assertThrows(ParseException.class, () -> CsvUtils.parseResults("corrupt-result.csv", "parse-metadata.xml"));
    }


}
