package arun.works.subsync.gui.events;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class SubmitEventHandler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		System.out.println("Printed");
	}

}
