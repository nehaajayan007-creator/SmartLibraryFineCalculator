package library;

public class StudentFinePolicy implements FinePolicy {

    private static final double FINE_PER_DAY = 5.0;

    @Override
    public double calculateFine(long daysLate) {
        return daysLate * FINE_PER_DAY;
    }
}