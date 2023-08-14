package com.techinterview.edtsbackend.data.employee;

import java.util.HashMap;
import java.util.Map;

public enum EmployeeGrade {
    MANAGER(1, 0.1) {
        @Override
        public String pretty() {
            return "Manager";
        }
    },
    SUPERVISOR(2, 0.06) {
        @Override
        public String pretty() {
            return "Supervisor";
        }
    },
    STAFF(3,  0.03) {
        @Override
        public String pretty() {
            return "Staff";
        }
    };

    public abstract String pretty();

    public final int gradeId;

    public final double bonus;

    private static final Map<Integer, EmployeeGrade> BY_GRADE_ID = new HashMap<>();

    static {
        for (var eGrade: values()) {
            BY_GRADE_ID.put(eGrade.gradeId, eGrade);
        }
    }

    EmployeeGrade(int gradeId, double bonus) {
        this.gradeId = gradeId;
        this.bonus = bonus;
    }

    public static EmployeeGrade getByGradeId(int gradeId) {
        return BY_GRADE_ID.get(gradeId);
    }
}
