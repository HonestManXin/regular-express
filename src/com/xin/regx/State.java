package com.xin.regx;

import java.util.ArrayList;
import java.util.List;

public abstract class State {

	private List<State> nextStates = new ArrayList<State>();
	
	public void addNextState(State state)
	{
		if(state != null)
			nextStates.add(state);
	}
	
	public void addNextState(List<State> states)
	{
		nextStates.addAll(states);
	}
	
	abstract boolean isEqual(String s);

	public List<State> getNextStates() {
		return nextStates;
	}
	
	
}
