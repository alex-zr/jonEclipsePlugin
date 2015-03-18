package ua.com.jon.service;

import java.util.ArrayList;
import java.util.List;

import ua.com.jon.domain.Sprint;
import ua.com.jon.domain.Task;

public class RemoteService {
	
	public List<Sprint> getUserSprints(String login, String pass) {
		Sprint sprint1 = new Sprint("week1");
		sprint1.getTasks().add(new Task(sprint1.getName(), "Task1", "public class Task1 {}"));
		sprint1.getTasks().add(new Task(sprint1.getName(), "Task2", "public class Task2 {}"));
		Sprint sprint2 = new Sprint("week2");
		sprint2.getTasks().add(new Task(sprint2.getName(), "Task1", "public class Task1 {}"));
		sprint2.getTasks().add(new Task(sprint2.getName(), "Task2", "public class Task2 {}"));
		
		ArrayList<Sprint> sprints = new ArrayList<>();
		sprints.add(sprint1);
		sprints.add(sprint2);
		return sprints;
	}
	
	
}
