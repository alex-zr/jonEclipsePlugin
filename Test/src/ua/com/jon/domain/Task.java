package ua.com.jon.domain;

public class Task {
	private String name;
	private String code;
	
	public Task() {
	}

	public Task(String name, String code) {
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "Task [name=" + name + ", code=" + code + "]";
	}
}
