package com.xin.regx;

import java.util.List;

public class DataState extends State {
	private char c;
	
	public DataState(char c)
	{
		this.c = c;
	}
	
	@Override
	boolean isEqual(String s) {
		if(s.length() == 0)
			return false;
		if(s.charAt(0) == c)
		{
			s = s.substring(1);
			List<State> nexts = this.getNextStates();
			for(State next:nexts)
			{
				if(next.isEqual(s))
					return  true;
			}
		}
		return false;
	}
}
