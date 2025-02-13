package com.mberktuncer.monad.views;

import com.mberktuncer.monad.constant.common.ViewConstants;
import com.mberktuncer.monad.constant.exception.ConfirmMessages;
import com.mberktuncer.monad.constant.exception.ErrorMessages;
import com.mberktuncer.monad.constant.view.employee.EmployeeGridProperty;
import com.mberktuncer.monad.constant.view.employee.EmployeeSearchProperty;
import com.mberktuncer.monad.model.api.CreateEmployeeRequest;
import com.mberktuncer.monad.model.entity.Employee;
import com.mberktuncer.monad.service.contract.EmployeeService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.grid.GridVariant;
import com.mberktuncer.monad.exception.EmployeeValidationException;
import com.mberktuncer.monad.exception.DuplicateEmployeeException;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;

@Route(value = ViewConstants.EMPLOYEE_ROUTE, layout = MainView.class)
@PageTitle(ViewConstants.EMPLOYEE_TITLE)
public class EmployeeView extends VerticalLayout {
    private final EmployeeService employeeService;
    private final Grid<Employee> grid;
    private final ListDataProvider<Employee> dataProvider;
    private TextField searchField;
    private TextField identityNumberField = new TextField("Identity Number");
    private TextField firstNameField = new TextField("First Name");
    private TextField lastNameField = new TextField("Last Name");
    private Dialog employeeDialog;
    private FormLayout formLayout;

    public EmployeeView(EmployeeService employeeService) {
        this.employeeService = employeeService;
        this.grid = new Grid<>(Employee.class);
        this.dataProvider = new ListDataProvider<>(employeeService.findAll());
        
        setSizeFull();
        initializeSearchField();
        configureGrid();
        createDialog();
        setupSearchFilter();
        
        add(getToolbar(), grid);
        updateList();
    }

    private void initializeSearchField() {
        searchField = new TextField();
        searchField.setPlaceholder(EmployeeSearchProperty.PLACEHOLDER_TEXT.getData());
        searchField.setWidth(EmployeeSearchProperty.FIELD_WIDTH.getData());
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setClearButtonVisible(true);
        searchField.setValueChangeMode(ValueChangeMode.LAZY);
    }

    private void configureGrid() {
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        grid.setHeight(EmployeeGridProperty.GRID_HEIGHT.getData());
        grid.setDataProvider(dataProvider);
        
        configureGridColumns();
    }

    private void configureGridColumns() {
        grid.removeAllColumns();
        
        grid.addColumn(Employee::getFirstName)
            .setHeader(EmployeeGridProperty.NAME_COLUMN_HEADER.getData())
            .setSortable(true);
            
        grid.addColumn(Employee::getLastName)
            .setHeader(EmployeeGridProperty.LASTNAME_COLUMN_HEADER.getData())
            .setSortable(true);
    }

    private void setupSearchFilter() {
        searchField.addValueChangeListener(e -> {
            String filterText = e.getValue().trim();
            filterGrid(filterText);
        });
    }

    private void filterGrid(String filterText) {
        if (filterText.isEmpty()) {
            dataProvider.clearFilters();
        } else {
            dataProvider.setFilter(Employee::getFirstName,
                name -> name != null && name.toLowerCase().contains(filterText.toLowerCase()));
        }
    }

    private void createDialog() {
        employeeDialog = new Dialog();
        employeeDialog.setHeaderTitle("Add New Employee");

        formLayout = new FormLayout();
        
        identityNumberField = new TextField("Identity Number");
        firstNameField = new TextField("First Name");
        lastNameField = new TextField("Last Name");
        
        configureForm();
        
        formLayout.add(identityNumberField, firstNameField, lastNameField);

        Button saveButton = new Button("Save", e -> addEmployee());
        Button cancelButton = new Button("Cancel", e -> employeeDialog.close());

        HorizontalLayout buttonLayout = new HorizontalLayout(saveButton, cancelButton);
        buttonLayout.setJustifyContentMode(JustifyContentMode.END);

        VerticalLayout dialogLayout = new VerticalLayout(formLayout, buttonLayout);
        employeeDialog.add(dialogLayout);
    }

    private HorizontalLayout getToolbar() {
        searchField.setPlaceholder(EmployeeSearchProperty.PLACEHOLDER_TEXT.getData());
        searchField.setClearButtonVisible(true);
        searchField.setValueChangeMode(ValueChangeMode.LAZY);

        Button addButton = new Button("Add New Employee", new Icon(VaadinIcon.PLUS));
        addButton.addClickListener(e -> {
            clearForm();
            employeeDialog.open();
        });

        HorizontalLayout toolbar = new HorizontalLayout(searchField, addButton);
        toolbar.setAlignItems(Alignment.BASELINE);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void configureForm() {
        identityNumberField.setRequired(true);
        firstNameField.setRequired(true);
        lastNameField.setRequired(true);
    }

    private void addEmployee() {
        try {
            CreateEmployeeRequest employee = new CreateEmployeeRequest(
                identityNumberField.getValue(),
                firstNameField.getValue(),
                lastNameField.getValue()
            );
            
            employeeService.save(employee);
            employeeDialog.close();
            clearForm();
            updateList();
            showSuccessNotification();
        } catch (EmployeeValidationException | DuplicateEmployeeException e) {
            showErrorNotification(e.getMessage());
        } catch (Exception e) {
            showErrorNotification(ErrorMessages.UNEXPECTED.getText());
        }
    }

    private boolean isValidInput() {
        return !identityNumberField.isEmpty() && 
               !firstNameField.isEmpty() && 
               !lastNameField.isEmpty() &&
               identityNumberField.getValue().length() == 11;
    }

    private void clearForm() {
        identityNumberField.clear();
        firstNameField.clear();
        lastNameField.clear();
    }

    private void showSuccessNotification() {
        Notification notification = Notification.show(ConfirmMessages.SAVED_EMPLOYEE.getText());
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    private void showErrorNotification(String message) {
        Notification notification = Notification.show(message);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
    }

    private void updateList() {
        dataProvider.getItems().clear();
        dataProvider.getItems().addAll(employeeService.findAll());
        dataProvider.refreshAll();
    }
}
