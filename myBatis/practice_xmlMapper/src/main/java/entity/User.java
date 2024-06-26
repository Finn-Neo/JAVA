package entity;

/**
 * @program: mybatis
 * @author: Qiaolezi
 * @create: 2024-05-02 15:49
 * @description:
 **/
public class User {
	private Integer user_id;
	private String username;
	private String useremail;

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUseremail() {
		return useremail;
	}

	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}

	@Override
	public String toString() {
		return "User{" +
				"user_id=" + user_id +
				", username='" + username + '\'' +
				", useremail='" + useremail + '\'' +
				'}';
	}
}
