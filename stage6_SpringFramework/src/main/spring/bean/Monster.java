package main.spring.bean;

/**
 * @program: Java
 * @author: Qiaolezi
 * @create: 2024-03-04 21:38
 * @description: JavaBean
 **/
public class Monster {
	private Integer monsterId;
	private String name;
	private String skill;
	public Monster() {};

	public Monster(Integer monsterId, String name, String skill) {
		this.monsterId = monsterId;
		this.name = name;
		this.skill = skill;
	}

	public Integer getMonsterId() {
		return monsterId;
	}

	public void setMonsterId(Integer monsterId) {
		this.monsterId = monsterId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	@Override
	public String toString() {
		return "Monster{" +
				"id=" + monsterId +
				", name='" + name + '\'' +
				", skill='" + skill + '\'' +
				'}';
	}
}
