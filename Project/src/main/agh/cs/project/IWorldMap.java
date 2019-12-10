package agh.cs.project;

import java.util.LinkedList;

/**
 * The interface responsible for interacting with the map of the world.
 * Assumes that Vector2d and MoveDirection classes are defined.
 *
 * @author apohllo
 *
 */

public interface IWorldMap {

    /**
     * Place a element on the map.
     *
     * @param element
     *            The element to place on the map.
     * @return True if the element was placed. The element cannot be placed if the map is already occupied.
     */
    boolean place(IMapElement element);

    /**
     * Each animal should move accordingly to their genotype each turn.
     *
     * @param genotype
     *            Array of genotype
     */
    void run(Instruction[] genotype);

    /**
     * Return true if given position on the map is occupied. Should not be
     * confused with canMove since there might be empty positions where the animal
     * cannot move.
     *
     * @param position
     *            Position to check.
     * @return True if the position is occupied.
     */
    boolean isOccupied(Vector2d position);

    /**
     * Return an object at a given position.
     *
     * @param position
     *            The position of the object.
     * @return Object or null if the position is not occupied.
     */
    LinkedList objectsAt(Vector2d position);
}
