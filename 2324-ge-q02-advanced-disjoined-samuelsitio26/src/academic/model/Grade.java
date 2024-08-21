package academic.model;

public enum Grade {
    A, AB, B, BC, C, D, E, None;
    
    public static double convertGradeToPoint(Grade grade) {
        switch (grade) {
            case A:
                return 4.0;
            case AB:
                return 3.5;
            case B:
                return 3.0;
            case BC:
                return 2.5;
            case C:
                return 2.0;
            case D:
                return 1.0;
            case E:
                return 0.0;
            default:
                return 0.0;
        }
    }
}
