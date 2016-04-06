package edu.virginia.dialog;

import edu.virginia.engine.events.DialogEvent;
import edu.virginia.engine.events.EventDispatcher;

public class DialogManager extends EventDispatcher {
	
	public DialogManager() {}
	
	public void beginDialog() {
		DialogEvent de = new DialogEvent();
		dispatchEvent(de);
	}
}
