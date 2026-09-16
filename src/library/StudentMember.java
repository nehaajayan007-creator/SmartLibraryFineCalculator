package library;

public class StudentMember extends Member {

    public StudentMember(String memberId, String name, String email) {
        super(memberId, name, email);
    }

    @Override
    public String toString() {
        return "Student Member | "
                + super.toString();
    }
}
