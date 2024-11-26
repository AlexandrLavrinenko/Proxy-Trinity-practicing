package tuesday.streams_task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

/**
 * Return Map where:
 * Key is Developer Name
 * Value is List of Tasks Title assigned to Developer
 * <p>
 * Handle non-consistent cases:
 * 1. Developer::getId not present in Assignment::getDeveloperId - add Developer::getName with Collections::emptyList
 * 2. Assignment::getTaskId not present in Task::getId - Skip Assignment
 */
public class MakeDevelopersMap {

    /**
     * Method without Stream API.
     *
     * @param tasks       list of {@link Task}.
     * @param developers  list of {@link Developer}.
     * @param assignments list of {@link Assignment}.
     * @return Map with key is Developer's name and
     * value is List of Tasks Title assigned to Developer.
     */
    public static Map<String, List<String>> report(
            List<Task> tasks,
            List<Developer> developers,
            List<Assignment> assignments) {
        var reportMap = new HashMap<String, List<String>>();

        for (Developer developer : developers) {
            var assigments = assignments.stream()
                    .filter(as -> developer.getId() == as.getDeveloperId())
                    .map(Assignment::getTaskId)
                    .toList();

            var tasksList = tasks.stream()
                    .filter(tsk -> assigments.contains(tsk.getId()))
                    .map(Task::getTitle)
                    .toList();

            reportMap.put(developer.getName(), tasksList);
        }

        return reportMap;
    }

    /**
     * Method with Stream API.
     *
     * @param tasks       list of {@link Task}.
     * @param developers  list of {@link Developer}.
     * @param assignments list of {@link Assignment}.
     * @return Map with key is Developer's name and
     * value is List of Tasks Title assigned to Developer.
     */
    public static Map<String, List<String>> reportWithStreams(
            List<Task> tasks,
            List<Developer> developers,
            List<Assignment> assignments) {

        Map<Integer, String> taskMap = tasks.stream()
                .collect(toMap(Task::getId, Task::getTitle));

        Map<Integer, String> developerMap = developers.stream()
                .collect(toMap(Developer::getId, Developer::getName));

        Map<String, List<String>> report = developers.stream()
                .collect(toMap(Developer::getName, dev -> new ArrayList<>()));

        assignments.stream()
                .filter(assignment -> taskMap.containsKey(assignment.getTaskId()))
                .forEach(assignment -> {
                    String developerName = developerMap.get(assignment.getDeveloperId());
                    if (developerName != null) {
                        report.get(developerName).add(taskMap.get(assignment.getTaskId()));
                    }
                });

        return report;
    }

    // Another one option of resolve
    public static Map<String, List<String>> reportAlterStreams(
            List<Task> tasks,
            List<Developer> developers,
            List<Assignment> assignments) {

        // Create a task map for quick access to task titles by their identifiers
        Map<Integer, String> taskMap = tasks.stream()
                .collect(toMap(Task::getId, Task::getTitle));

        return developers.stream()
                .collect(toMap(
                        Developer::getName,
                        dev -> assignments.stream()
                                .filter(a -> a.getDeveloperId() == dev.getId())
                                .filter(a -> taskMap.containsKey(a.getTaskId()))
                                .map(a -> taskMap.get(a.getTaskId()))
                                .toList(),
                        // в разі конфлікту залишаємо наявне значення
                        (existing, replacement) -> existing
                ));
    }

    public void printMap(Map<String, List<String>> map) {
        map.forEach((k, v) -> System.out.println(k + ": " + v));
    }

    // Inner classes
    public static class Task {
        int id;
        String title;

        public Task(int id, String title) {
            this.id = id;
            this.title = title;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

    }

    public static class Developer {
        int id;
        String name;

        public Developer(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

    }

    public static class Assignment {
        int taskId;
        int developerId;

        public Assignment(int taskId, int developerId) {
            this.taskId = taskId;
            this.developerId = developerId;
        }

        public int getTaskId() {
            return taskId;
        }

        public int getDeveloperId() {
            return developerId;
        }

    }
}
