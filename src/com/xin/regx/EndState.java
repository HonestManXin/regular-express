package com.xin.regx;

public class EndState extends State {

	@Override
	boolean isEqual(String s) {
		if(s.length() == 0)
			return true;
		return false;
	}

}
