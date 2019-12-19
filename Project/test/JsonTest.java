import agh.cs.project.Json;
import org.junit.Assert;
import org.junit.Test;

public class JsonTest {
    @Test
    public void testReadJSON(){
        try {
            Json.readJSON();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals(Json.height, 50);
        Assert.assertEquals(Json.width, 25);
        Assert.assertTrue(Json.jungleRatio-0.1<1e7);
        Assert.assertEquals(Json.moveEnergy, 2);
        Assert.assertEquals(Json.plantEnergy, 20);
        Assert.assertEquals(Json.startEnergy, 100);
    }
}
