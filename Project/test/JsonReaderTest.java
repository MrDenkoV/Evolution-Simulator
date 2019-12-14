import agh.cs.project.JsonReader;
import org.junit.Assert;
import org.junit.Test;

public class JsonReaderTest {
    @Test
    public void testReadJSON(){
        try {
            JsonReader.readJSON();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals(JsonReader.height, 50);
        Assert.assertEquals(JsonReader.width, 25);
        Assert.assertTrue(JsonReader.jungleRatio-0.1<1e7);
        Assert.assertEquals(JsonReader.moveEnergy, 2);
        Assert.assertEquals(JsonReader.plantEnergy, 20);
        Assert.assertEquals(JsonReader.startEnergy, 100);
    }
}
