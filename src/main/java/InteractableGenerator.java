/*
 * Classes implementing this interface should have methods for generating interactables to be placed in the
 * game map by the MapBuilder. The implementation for how the interactables should be generated will be totally
 * up to the class implementing the interface.
 */
public interface InteractableGenerator {
    Interactable generateInteractable();
}
