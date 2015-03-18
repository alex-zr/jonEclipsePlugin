package ua.com.jon.domain;

public class Task {
	private String sprintName;
	private String name;
	private String code;
	
	public Task() {
	}

	public Task(String sprintName, String name, String code) {
		this.sprintName = sprintName;
		this.name = name;
		this.code = code;		
	}
	
	public String getFullName() {
		return sprintName + " " + name;
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

	public String getSprintName() {
		return sprintName;
	}

	public void setSprintName(String sprintName) {
		this.sprintName = sprintName;
	}
	
	@Override
	public String toString() {
		return "Task [sprint =" + sprintName + ", name=" + name + ", code=" + code + "]";
	}
}
