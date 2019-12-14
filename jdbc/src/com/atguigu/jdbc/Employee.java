package com.atguigu.jdbc;

import java.util.Objects;

/**
 * @author wststart
 * @create 2019-06-23 19:57
 */
public class Employee {
    private int employeeId;
    private String firstname;
    private String lastname;
    private String email;
    private String phonenumber;
    private String jobId;
    private double salary;
    private double commissionPct;
    private int managerId;
    private int departmentId;

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return employeeId == employee.employeeId &&
                Double.compare(employee.salary, salary) == 0 &&
                Double.compare(employee.commissionPct, commissionPct) == 0 &&
                managerId == employee.managerId &&
                departmentId == employee.departmentId &&
                Objects.equals(firstname, employee.firstname) &&
                Objects.equals(lastname, employee.lastname) &&
                Objects.equals(email, employee.email) &&
                Objects.equals(phonenumber, employee.phonenumber) &&
                Objects.equals(jobId, employee.jobId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(employeeId, firstname, lastname, email, phonenumber, jobId, salary, commissionPct, managerId, departmentId);
    }
}
