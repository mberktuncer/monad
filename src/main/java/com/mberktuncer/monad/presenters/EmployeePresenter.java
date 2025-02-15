package com.mberktuncer.monad.presenters;

import com.mberktuncer.monad.constant.exception.ConfirmMessages;
import com.mberktuncer.monad.model.entity.Employee;
import com.mberktuncer.monad.service.contract.EmployeeService;
import com.mberktuncer.monad.views.EmployeeView;
import com.mberktuncer.monad.model.api.CreateEmployeeRequest;

import java.util.List;
import java.util.Set;

public class EmployeePresenter {
    private final EmployeeView view;
    private final EmployeeService employeeService;

    public EmployeePresenter(EmployeeView view, EmployeeService employeeService) {
        this.view = view;
        this.employeeService = employeeService;
        initializeView();
    }

    private void initializeView() {
        view.addRefreshClickListener(event -> refreshEmployees());
    }

    public void loadInitialData() {
        updateEmployeeList();
    }

    public void refreshEmployees() {
        try {
            employeeService.addRandomEmployeeFromAdditionalFile();
            view.showSuccessNotification(ConfirmMessages.SAVED_EMPLOYEE.getText());
            updateEmployeeList();
        } catch (Exception e) {
            view.showErrorNotification(e.getMessage());
        }
    }

    public void addEmployee(String identityNumber, String firstName, String lastName) {
        try {
            CreateEmployeeRequest employee = new CreateEmployeeRequest(
                identityNumber,
                firstName,
                lastName
            );
            
            employeeService.save(employee);
            view.showSuccessNotification(ConfirmMessages.SAVED_EMPLOYEE.getText());
            updateEmployeeList();
        } catch (Exception e) {
            view.showErrorNotification(e.getMessage());
        }
    }

    public void deleteEmployees(Set<Employee> employees) {
        try {
            for (Employee employee : employees) {
                employeeService.softDeleteEmployees(employee.getIdentityNumber());
            }
            view.showSuccessNotification(ConfirmMessages.DELETED_EMPLOYEE.getText());
            updateEmployeeList();
        } catch (Exception e) {
            view.showErrorNotification(e.getMessage());
        }
    }

    private void updateEmployeeList() {
        List<Employee> employees = employeeService.getAllActiveEmployees();
        view.updateGrid(employees);
    }

} 