package units;

import com.kashi.scoreboard.models.EventMetadata;
import com.kashi.scoreboard.models.EventType;
import com.kashi.scoreboard.utils.XmlUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class XmlUtilsTest {

    @Test
    public void parseMetadata_regularTestFile_successful() {
        List<EventMetadata> metadataList = XmlUtils.parseMetadata("test-metadata.xml");
        Assertions.assertEquals(2, metadataList.size());
        Assertions.assertEquals("Filed1", metadataList.get(0).getEventName());
        Assertions.assertEquals(EventType.FIELD, metadataList.get(0).getEventType());
    }

    @Test
    public void parseMetadata_invalidFile_exception() {
        Assertions.assertThrows(RuntimeException.class, () -> XmlUtils.parseMetadata("corrupt-metadata.xml"));
    }

}
