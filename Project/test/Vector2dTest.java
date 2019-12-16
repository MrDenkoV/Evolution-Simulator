import agh.cs.project.JsonReader;
import agh.cs.project.Vector2d;
import org.junit.Assert;
import org.junit.Test;

public class Vector2dTest {

    @Test
    public void testEquals(){
        Assert.assertEquals(new Vector2d(1, -1), new Vector2d(1, -1));
        Assert.assertNotEquals(new Vector2d(1, -1), new Vector2d(1, 0));
        Assert.assertNotEquals(new Vector2d(1, -1), new Vector2d(-1, -1));
    }

    @Test
    public void testToString(){
        Assert.assertEquals(new Vector2d(1, 1).toString(), "(1,1)");
        Assert.assertEquals(new Vector2d(1, 2).toString(), "(1,2)");
    }

    @Test
    public void testPrecedes(){
        Assert.assertTrue((new Vector2d(1, 1)).precedes(new Vector2d(2, 2)));
        Assert.assertTrue((new Vector2d(1, 2)).precedes(new Vector2d(2, 2)));
        Assert.assertTrue((new Vector2d(2, 1)).precedes(new Vector2d(2, 2)));
    }

    @Test
    public void testFollows(){
        Assert.assertTrue((new Vector2d(2, 2)).follows(new Vector2d(1, 1)));
        Assert.assertTrue((new Vector2d(2, 2)).follows(new Vector2d(1, 2)));
        Assert.assertTrue((new Vector2d(2, 2)).follows(new Vector2d(2, 1)));
    }

    @Test
    public void testUpperRight(){
        Assert.assertEquals((new Vector2d(1,1).upperRight(new Vector2d(2,3))), new Vector2d(2,3));
    }

    @Test
    public void testLowerLeft(){
        Assert.assertEquals((new Vector2d(1,1).lowerLeft(new Vector2d(2,3))), new Vector2d(1,1));
    }

    @Test
    public void testAdd(){
        JsonReader.width=20;
        JsonReader.height=40;

        Assert.assertEquals((new Vector2d(1,2).add(new Vector2d(3, 4))), new Vector2d(4,6));
        Assert.assertNotEquals((new Vector2d(1,2).add(new Vector2d(3, 4))), new Vector2d(5,7));

        Vector2d zeros=new Vector2d(0,0);
        Vector2d upperRight=new Vector2d(20, 40);
        Vector2d ones=new Vector2d(1,1);
        Assert.assertEquals(zeros, upperRight.add(ones));
        Assert.assertEquals(zeros.subtract(ones), upperRight);
    }

    @Test
    public void testSubtract(){
        Assert.assertEquals((new Vector2d(4,6 ).subtract(new Vector2d(1,2))), new Vector2d(3, 4));
        Assert.assertNotEquals((new Vector2d(3,5 ).subtract(new Vector2d(1,2))), new Vector2d(3, 4));
    }

    @Test
    public void testOpposite(){

        Assert.assertNotEquals((new Vector2d(-1, 1)).opposite(), new Vector2d(-1, 1));
        Assert.assertEquals((new Vector2d(1, -1)).opposite(), new Vector2d(-1, 1));
    }
}
