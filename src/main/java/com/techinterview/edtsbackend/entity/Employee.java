package com.techinterview.edtsbackend.entity;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.Objects;


@Entity
@Table(name = "employees")
public class Employee extends Base {

    public Employee() {
    }


    public Employee(BigInteger id, BigInteger salary, Integer gradeId, User user) {
        super();
        this.id = id;
        this.salary = salary;
        this.gradeId = gradeId;
        this.user = user;
    }

    @Id
    @Column(name = "id", nullable = false)
    private BigInteger id;

    @Column()
    private BigInteger salary;

    @Column()
    private Integer gradeId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "user_id", referencedColumnName = "id")
    private User user;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getSalary() {
        return salary;
    }

    public void setSalary(BigInteger salary) {
        this.salary = salary;
    }

    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", salary=" + salary +
                ", gradeId=" + gradeId +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) && Objects.equals(salary, employee.salary) && Objects.equals(gradeId, employee.gradeId) && Objects.equals(user, employee.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, salary, gradeId, user);
    }

}
