package com.xin.regx.test;

import com.xin.regx.Regx;

public class Main {

	public static void main(String []args)
	{
		Regx regx = new Regx("ab*cd");
		System.out.println(regx.matcher("acd"));
	}
}
