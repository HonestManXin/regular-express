package com.xin.regx;

import java.util.List;

public class RootState extends State {

	@Override
	boolean isEqual(String s) {
		if(s == null)
			throw new RuntimeException();
		
		List<State> nexts = this.getNextStates();
		if(nexts.size() == 0 && s.length() == 0)
			return true;
		else
		{
			for(State next:nexts)
			{
				if(next.isEqual(s))
					return true;
			}
			
			return false;
		}
	}

}
