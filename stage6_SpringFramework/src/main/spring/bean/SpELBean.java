package main.spring.bean;

/**
 * @program: stage6_SpringFramework
 * @author: Qiaolezi
 * @create: 2024-04-18 11:45
 * @description: 不是重点，看懂就行
 **/
public class SpELBean {
	private String name;
	private Monster monster;
	private String monsterName;
	private String crySound;
	private String bookName;
	private Double result;

	public String cry(String sound) {
		return "发出" + sound + "叫声";
	}

	public static String read(String bookName) {
		return "看" + bookName;
	}

	public SpELBean() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Monster getMonster() {
		return monster;
	}

	public void setMonster(Monster monster) {
		this.monster = monster;
	}

	public String getMonsterName() {
		return monsterName;
	}

	public void setMonsterName(String monsterName) {
		this.monsterName = monsterName;
	}

	public String getCrySound() {
		return crySound;
	}

	public void setCrySound(String crySound) {
		this.crySound = crySound;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public Double getResult() {
		return result;
	}

	public void setResult(Double result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "SpELBean{" +
				"name='" + name + '\'' +
				", monster=" + monster +
				", monsterName='" + monsterName + '\'' +
				", crySound='" + crySound + '\'' +
				", bookName='" + bookName + '\'' +
				", result=" + result +
				'}';
	}
}
