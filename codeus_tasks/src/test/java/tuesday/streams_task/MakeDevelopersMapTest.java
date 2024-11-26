package tuesday.streams_task;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MakeDevelopersMapTest {

    private static List<MakeDevelopersMap.Developer> developers;
    private static List<MakeDevelopersMap.Assignment> assignments;
    private static List<MakeDevelopersMap.Task> tasks;
    private static final Map<String, List<String>> expected = new HashMap<>();

    @BeforeAll
    static void setUp() {
        /* Initialize developers */
        developers = List.of(
                new MakeDevelopersMap.Developer(1, "John Doe"),
                new MakeDevelopersMap.Developer(2, "Jane Doe"),
                new MakeDevelopersMap.Developer(3, "Jack Doe")
        );

        /* Initialize assignments */
        assignments = List.of(
                new MakeDevelopersMap.Assignment(1, 1),
                new MakeDevelopersMap.Assignment(2, 1),
                new MakeDevelopersMap.Assignment(3, 1),
                new MakeDevelopersMap.Assignment(4, 2),
                new MakeDevelopersMap.Assignment(5, 2),
                new MakeDevelopersMap.Assignment(6, 2),
                new MakeDevelopersMap.Assignment(7, 2),
                new MakeDevelopersMap.Assignment(8, 3),
                new MakeDevelopersMap.Assignment(9, 3),
                new MakeDevelopersMap.Assignment(10, 3),
                new MakeDevelopersMap.Assignment(13, 1)
        );

        /* Task initialization */
        tasks = List.of(
                new MakeDevelopersMap.Task(1, "One"),
                new MakeDevelopersMap.Task(2, "Two"),
                new MakeDevelopersMap.Task(3, "Three"),
                new MakeDevelopersMap.Task(4, "Four"),
                new MakeDevelopersMap.Task(5, "Five"),
                new MakeDevelopersMap.Task(6, "Six"),
                new MakeDevelopersMap.Task(7, "Seven"),
                new MakeDevelopersMap.Task(8, "Eight"),
                new MakeDevelopersMap.Task(9, "Nine"),
                new MakeDevelopersMap.Task(10, "Ten"),
                new MakeDevelopersMap.Task(11, "Eleven"),
                new MakeDevelopersMap.Task(12, "Twelve")
        );

        // Expected results
        expected.put("John Doe", List.of("One", "Two", "Three"));
        expected.put("Jane Doe", List.of("Four", "Five", "Six", "Seven"));
        expected.put("Jack Doe", List.of("Eight", "Nine", "Ten"));
    }

    @Test
    void checkReport() {
        var actual = MakeDevelopersMap.report(tasks, developers, assignments);

        assertEquals(3, actual.size());
        assertEquals(expected, actual);
    }

    @Test
    void checkReportWithStreams() {
        var actual = MakeDevelopersMap.reportWithStreams(tasks, developers, assignments);

        assertEquals(3, actual.size());
        assertEquals(expected, actual);
    }

    @Test
    void checkReportWithAlterStreams() {
        var actual = MakeDevelopersMap.reportAlterStreams(tasks, developers, assignments);

        assertEquals(3, actual.size());
        assertEquals(expected, actual);
    }
}