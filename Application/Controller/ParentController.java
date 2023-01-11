package Application.Controller;

import javafx.scene.effect.Effect;

/**
 * The ParentController interface is implemented by the controller classes
 * that have popups. Those classes are CalendarController, CategoriesController,
 * EventsListController, and HomeController
 *
 * @author Waewarin Chindarassami
 */
interface ParentController {

    /**
     * Closes the popup in the parent controller
     *
     * @param stringNeeded String - string needed for the parent controller to
     *                     use when the popup is closed
     */
    public void closePopUp(String stringNeeded);

    /**
     * Sets the effects of the components in the parent controller for when the
     * popup appears and closes
     *
     * @param effect Effect - effect to use for the companents
     */
    public void setEffect(Effect effect);
}