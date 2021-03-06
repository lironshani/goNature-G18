package gui;

import entity.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import message.ClientMessage;
import message.ClientMessageType;
/*
 * controller for exit control screen
 */
public class ExitControlPaneController {
	private GUIControl guiControl = GUIControl.getInstance();
    @FXML
    private TextField idNumTextField;

    @FXML
    private Button validateBtn;
    /*/
     * method called when validate button is pressed,registers the person's order as done if it was found
     * or pop ups an error if it wasn't found
     */
    @FXML
    void validateOrder(ActionEvent event) {
    	Employee emp= (Employee)guiControl.getUser();
    	String[] idAndParkName= {idNumTextField.getText(),emp.getParkName()};
    	guiControl.sendToServer(new ClientMessage(ClientMessageType.VALIDATE_ORDER_EXIT,idAndParkName));
    	if(guiControl.getServerMsg().getMessage() !=null) {
    		GUIControl.popUpMessage("Exit Registered","Your leave has been registered.\nThank you for choosing GoNature");
    	}
    	else
    		GUIControl.popUpError("No order exists for this identification number!");
    	idNumTextField.setText("");
    		
    }

}
