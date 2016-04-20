package edu.virginia.quest;

import edu.virginia.engine.Sys;
import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.EventDispatcher;
import edu.virginia.engine.events.IEventListener;

public class Quest extends EventDispatcher implements IEventListener {
	protected String questId;
	
	public Quest(){
		this.addEventListener(Sys.instance, "LUCIDITY_CHANGE_EVENT");
		this.addEventListener(Sys.instance, "DIALOG_EVENT");
		Sys.MC.addEventListener(this, "INTERACT_EVENT");
	}

	@Override
	public void handleEvent(Event event) {
		// TODO Auto-generated method stub
		
	}

}
