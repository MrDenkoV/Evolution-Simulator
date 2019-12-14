package agh.cs.project;

import javax.xml.bind.util.ValidationEventCollector;
import java.util.LinkedList;

/**
 * The map visualizer converts the {@link IWorldMap} map into a string
 * representation.
 *
 * @author apohllo
 */
public class MapVisualizer {
    private static final String EMPTY_CELL = " ";
    private static final String EMPTY_JUNGLE_CELL = "~";
    private static final String FRAME_SEGMENT = "-";
    private static final String CELL_SEGMENT = "|";
//    private static final String MULTIPLE_CELL = "X";
    private IWorldMap map;

    /**
     * Initializes the MapVisualizer with an instance of map to visualize.
     * @param map
     */
    public MapVisualizer(IWorldMap map) {
        this.map = map;
    }

    /**
     * Convert selected region of the map into a string. It is assumed that the
     * indices of the map will have no more than two characters (including the
     * sign).
     *
     * @param lowerLeft  The lower left corner of the region that is drawn.
     * @param upperRight The upper right corner of the region that is drawn.
     * @return String representation of the selected region of the map.
     */
    public String draw(Vector2d lowerLeft, Vector2d upperRight, Vector2d jungleLowerLeft, Vector2d jungleUpperRight) {
        StringBuilder builder = new StringBuilder();
        for (int i = upperRight.y + 1; i >= lowerLeft.y - 1; i--) {
            if (i == upperRight.y + 1) {
                builder.append(drawHeader(lowerLeft, upperRight));
            }
            builder.append(String.format("%3d: ", i));
            for (int j = lowerLeft.x; j <= upperRight.x + 1; j++) {
                if (i < lowerLeft.y || i > upperRight.y) {
                    builder.append(drawFrame(j <= upperRight.x));
                } else {
                    builder.append(CELL_SEGMENT);
                    if (j <= upperRight.x) {
                        builder.append(drawObject(new Vector2d(j, i), jungleLowerLeft, jungleUpperRight));
                    }
                }
            }
            builder.append(System.lineSeparator());
        }
        return builder.toString();
    }

    private String drawFrame(boolean innerSegment) {
        if (innerSegment) {
            return FRAME_SEGMENT + FRAME_SEGMENT;
        } else {
            return FRAME_SEGMENT;
        }
    }

    private String drawHeader(Vector2d lowerLeft, Vector2d upperRight) {
        StringBuilder builder = new StringBuilder();
        builder.append(" y\\x ");
        for (int j = lowerLeft.x; j < upperRight.x + 1; j++) {
            builder.append(String.format("%2d", j));
        }
        builder.append(System.lineSeparator());
        return builder.toString();
    }

    private String drawObject(Vector2d currentPosition, Vector2d jungleLowerLeft, Vector2d jungleUpperRight) {
        String result = null;
        if (this.map.isOccupied(currentPosition)) {
            //Object object = this.map.objectAt(currentPosition);
//            LinkedList<IMapElement> objects = this.map.objectsAt(currentPosition);
            LinkedList objects = this.map.objectsAt(currentPosition);
            if(objects.size()>1)
//                result = MULTIPLE_CELL;
                result = Integer.toString(objects.size());
            else if (objects.size()==1) {
                result = objects.get(0).toString();
            } else {
                if(currentPosition.precedes(jungleUpperRight)&&currentPosition.follows(jungleLowerLeft))
                    result = EMPTY_JUNGLE_CELL;
                else
                    result = EMPTY_CELL;
            }
        } else {
            if(currentPosition.precedes(jungleUpperRight)&&currentPosition.follows(jungleLowerLeft))
                result = EMPTY_JUNGLE_CELL;
            else
            result = EMPTY_CELL;
        }
        return result;
    }
}