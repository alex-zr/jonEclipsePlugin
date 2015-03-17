package ua.com.jon.domain;

import java.util.ArrayList;
import java.util.List;

public class Sprint {
	private String name;
	private List<Task> tasks = new ArrayList<Task>();
	
	public Sprint() {
	}

	public Sprint(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Task> getTasks() {
		return tasks;
	}
	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	@Override
	public String toString() {
		return "Sprint [name=" + name + ", tasks=" + tasks + "]";
	}
}
