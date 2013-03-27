package com.xin.regx;

import java.util.List;

public class NullState extends State {

	@Override
	boolean isEqual(String s) {
		
		List<State> nexts = this.getNextStates();
		for(State next:nexts)
		{
			if(next.isEqual(s))
				return  true;
		}
		
		return false;
	}

}
