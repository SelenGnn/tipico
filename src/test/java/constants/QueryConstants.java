package constants;

public final class QueryConstants {

    public static final String ADD_RESULTS = "INSERT INTO selen_test (DEPARTMENT, JOB_TITLE, LOCATION) VALUES ('%s', '%s', '%s');";

    private QueryConstants() {
        throw new IllegalStateException("QueryConstants");
    }
}