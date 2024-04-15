package main.spring.bean;

import java.util.*;

/**
 * @program: stage6_SpringFramework
 * @author: Qiaolezi
 * @create: 2024-03-07 11:33
 * @description: Master类
 **/
public class Master {
	private String name;//主人名称
	private List<Monster> monsterList;
	private Map<String, Monster> monsterMap;
	private Set<Monster> monsterSet;

	private String[] monsterName;
	//Properties是Hashtable的子类，K:String - V:String
	private Properties pros;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public List<Monster> getMonsterList() {
		return monsterList;
	}

	public void setMonsterList(List<Monster> monsterList) {
		this.monsterList = monsterList;
	}

	public Map<String, Monster> getMonsterMap() {
		return monsterMap;
	}

	public void setMonsterMap(Map<String, Monster> monsterMap) {
		this.monsterMap = monsterMap;
	}

	public Set<Monster> getMonsterSet() {
		return monsterSet;
	}

	public void setMonsterSet(Set<Monster> monsterSet) {
		this.monsterSet = monsterSet;
	}

	public String[] getMonsterName() {
		return monsterName;
	}

	public void setMonsterName(String[] monsterName) {
		this.monsterName = monsterName;
	}

	public Properties getPros() {
		return pros;
	}

	public void setPros(Properties pros) {
		this.pros = pros;
	}

	@Override
	public String toString() {
		return "Master{" +
				"name='" + name + '\'' +
				", monsterList=" + monsterList +
				", monsterMap=" + monsterMap +
				", monsterSet=" + monsterSet +
				", monsterName=" + Arrays.toString(monsterName) +
				", pros=" + pros +
				'}';
	}
}
