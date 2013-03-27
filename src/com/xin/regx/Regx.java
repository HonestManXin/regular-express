package com.xin.regx;

import java.util.ArrayList;
import java.util.List;

public class Regx {
	
	State root = new RootState();
	public Regx(String x)
	{
		if(x.length() == 0)
			return ;
		List<String> states = orStates(x);
		List<State> prevs = newArrayList(root);
		for(String state:states)
		{
			geneateState(state+' ',prevs);
		}
	}
	
	private List<State> geneateState(String s,List<State> prevs)
	{
		if(s.length() == 0)
		{
			return null;
		}
		char c = s.charAt(0);
		if(c == '(')
		{
			int closeIndex = findCloseParentesis(s); //找到最后的括号
			String nextInparenteis = s.substring(1,closeIndex); //去除括号
			List<String> innerStates = orStates(nextInparenteis); //找到括号中的多个|操作
			
			List<State> tempStates = new ArrayList<State>();
			for(String innerState:innerStates)
			{
				List<State> tmp = geneateState(innerState, prevs);
				tempStates.addAll(tmp);
//				for(State prev:prevs)
//					prev.addNextState(tmp);
			}
			
			String outparentesis = s.substring(closeIndex+1);
			if(tempStates.size() == 0)
			{
				return geneateState(outparentesis,prevs);
			}
			else
			{
				if(outparentesis.length()>0 && outparentesis.charAt(0) == '*') //判断括号后是否存在*
				{
					NullState nullState = new NullState();
					for(State state:tempStates)
						state.addNextState(nullState);
					for(State prev:prevs)
					{
						nullState.addNextState(prev);
						prev.addNextState(nullState);
					}
					List<State> nulllist = newArrayList(nullState);
					geneateState(outparentesis.substring(1), nulllist);
					return tempStates;
				}
				else
				{
					geneateState(outparentesis,tempStates);
					return tempStates;
				}
			}
		}
		else if(c == ' ')
		{
			State end = new EndState();
			for(State prev:prevs)
				prev.addNextState(end);
			return newArrayList(end);
		}
		else
		{
			State state = new DataState(s.charAt(0));
			for(State prev:prevs)
				prev.addNextState(state);
			s = s.substring(1);
			if(s.length()>0 && s.charAt(0) == '*')
			{
				NullState nullState = new NullState();
				state.addNextState(nullState); 
				nullState.addNextState(state);
				for(State prev:prevs)
					prev.addNextState(nullState);
				
				List<State> nulllist = newArrayList(nullState);
				geneateState(s.substring(1),nulllist);
			}
			else
			{
				List<State> datalist = newArrayList(state);
				geneateState(s,datalist);
			}
			
			return newArrayList(state);
		}
	}
	
	private int findCloseParentesis(String s)
	{
		int count  =1 ;
		int length = s.length();
		for(int i = 1 ;i<length;i++)
		{
			if(s.charAt(i) == '(')
				count ++;
			if(s.charAt(i) == ')')
				count --;
			if(count == 0)
				return i;
		}
		throw new RuntimeException();
	}
	
	private List<State> newArrayList(State state)
	{
		List<State> list = new ArrayList<State>();
		list.add(state);
		
		return list;
	}
	
	private List<String> orStates(String s)
	{
		List<String> states = new ArrayList<String>();
		int count = 0 ;
		int prev = -1;
		for(int i = 0;i<s.length();i++)
		{
			if(s.charAt(i) == '(')
				count ++;
			else if(s.charAt(i) == ')')
				count --;
			else if(s.charAt(i) == '|' && count == 0)
			{
				String tmp = s.substring(prev+1, i);
				prev = i;
				states.add(tmp);
			}
		}
		if(states.size() == 0)
			states.add(s);
		if(prev != -1)
		{
			states.add(s.substring(prev+1));
		}
		return states;
	}
	
	public boolean matcher(String s)
	{
		return root.isEqual(s);
	}
}
