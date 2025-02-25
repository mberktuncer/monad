package com.mberktuncer.monad.views;

import com.mberktuncer.monad.constant.common.ViewConstants;
import com.mberktuncer.monad.constant.exception.ConfirmMessages;
import com.mberktuncer.monad.constant.view.employee.*;
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
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import java.util.Set;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.ButtonVariant;
import java.util.List;
import com.mberktuncer.monad.presenters.EmployeePresenter;

@Route(value = ViewConstants.EMPLOYEE_ROUTE, layout = MainView.class)
@PageTitle(ViewConstants.EMPLOYEE_TITLE)
public class EmployeeView extends VerticalLayout {
    private final EmployeeService employeeService;
    private final Grid<Employee> grid;
    private final ListDataProvider<Employee> dataProvider;
    private TextField searchField;
    private TextField identityNumberField = new TextField(EmployeeGridProperty.IDENTITY_COLUMN_HEADER.getData());
    private TextField firstNameField = new TextField(EmployeeGridProperty.NAME_COLUMN_HEADER.getData());
    private TextField lastNameField = new TextField(EmployeeGridProperty.LASTNAME_COLUMN_HEADER.getData());
    private Dialog employeeDialog;
    private FormLayout formLayout;
    private Button deleteButton;
    private Button refreshButton;
    private EmployeePresenter presenter;

    public EmployeeView(EmployeeService employeeService) {
        this.employeeService = employeeService;
        this.grid = new Grid<>(Employee.class);
        this.dataProvider = new ListDataProvider<>(employeeService.findAll());
        
        setSizeFull();
        initializeSearchField();
        configureGrid();
        createDialog();
        setupSearchFilter();
        
        setupLayout();
        
        presenter = new EmployeePresenter(this, employeeService);
        
        presenter.loadInitialData();
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
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        
        configureGridColumns();
        
        grid.addItemDoubleClickListener(event -> {
            Employee employee = event.getItem();
            openUpdateDialog(employee);
        });
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
        employeeDialog.setHeaderTitle(EmployeeAddDialogProperty.DIALOG_TITLE.getText());

        formLayout = new FormLayout();
        
        identityNumberField = new TextField(EmployeeGridProperty.IDENTITY_COLUMN_HEADER.getData());
        firstNameField = new TextField(EmployeeGridProperty.NAME_COLUMN_HEADER.getData());
        lastNameField = new TextField(EmployeeGridProperty.LASTNAME_COLUMN_HEADER.getData());
        
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

        Button addButton = new Button(EmployeeSaveProperty.BUTTON_ADD.getText(), new Icon(VaadinIcon.PLUS));
        addButton.addClickListener(e -> {
            clearForm();
            employeeDialog.open();
        });

        deleteButton = new Button(EmployeeDeleteProperty.BUTTON_DELETE.getText(), new Icon(VaadinIcon.TRASH));
        deleteButton.setEnabled(false);
        deleteButton.addClickListener(e -> deleteSelectedEmployees());

        HorizontalLayout toolbar = new HorizontalLayout(searchField, addButton, deleteButton);
        toolbar.setAlignItems(Alignment.BASELINE);
        toolbar.addClassName("toolbar");
        
        grid.addSelectionListener(selection -> deleteButton.setEnabled(!selection.getAllSelectedItems().isEmpty()));
        
        return toolbar;
    }

    private void configureForm() {
        identityNumberField.setRequired(true);
        firstNameField.setRequired(true);
        lastNameField.setRequired(true);
    }

    private void addEmployee() {
        try {
            presenter.addEmployee(
                identityNumberField.getValue(),
                firstNameField.getValue(),
                lastNameField.getValue()
            );
            employeeDialog.close();
            clearForm();
        } catch (Exception e) {
            showErrorNotification(e.getMessage());
        }
    }

    private void clearForm() {
        identityNumberField.clear();
        firstNameField.clear();
        lastNameField.clear();
    }

    public void showSuccessNotification(String message) {
        Notification notification = Notification.show(message);
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    public void showErrorNotification(String message) {
        Notification notification = Notification.show(message);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
    }

    private void updateList() {
        dataProvider.getItems().clear();
        dataProvider.getItems().addAll(employeeService.getAllActiveEmployees());
        dataProvider.refreshAll();
    }

    private void deleteSelectedEmployees() {
        Set<Employee> selected = grid.getSelectedItems();
        if (!selected.isEmpty()) {
            presenter.deleteEmployees(selected);
            grid.deselectAll();
        }
    }

    private void openUpdateDialog(Employee employee) {
        Dialog updateDialog = new Dialog();
        updateDialog.setHeaderTitle(EmployeeUpdateDialogProperty.DIALOG_TITLE.getText());

        FormLayout updateForm = new FormLayout();
        
        TextField updateFirstNameField = new TextField(EmployeeGridProperty.NAME_COLUMN_HEADER.getData());
        TextField updateLastNameField = new TextField(EmployeeGridProperty.LASTNAME_COLUMN_HEADER.getData());
        
        updateFirstNameField.setValue(employee.getFirstName());
        updateLastNameField.setValue(employee.getLastName());
        
        updateForm.add(updateFirstNameField, updateLastNameField);

        Button updateButton = new Button(EmployeeUpdateProperty.BUTTON_UPDATE.getText(), e -> {
            try {
                CreateEmployeeRequest request = new CreateEmployeeRequest(
                    employee.getIdentityNumber(),
                    updateFirstNameField.getValue(),
                    updateLastNameField.getValue()
                );
                
                employeeService.update(employee.getIdentityNumber(), request);
                updateDialog.close();
                updateList();
                showSuccessNotification(ConfirmMessages.UPDATED_EMPLOYEE.getText());
            } catch (Exception ex) {
                showErrorNotification(ex.getMessage());
            }
        });
        
        Button cancelButton = new Button(EmployeeUpdateProperty.CANCEL_BUTTON.getText(), 
            e -> updateDialog.close());

        HorizontalLayout buttonLayout = new HorizontalLayout(updateButton, cancelButton);
        buttonLayout.setJustifyContentMode(JustifyContentMode.END);

        VerticalLayout dialogLayout = new VerticalLayout(updateForm, buttonLayout);
        updateDialog.add(dialogLayout);
        updateDialog.open();
    }

    public void addRefreshClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        refreshButton.addClickListener(listener);
    }

    public void updateGrid(List<Employee> employees) {
        dataProvider.getItems().clear();
        dataProvider.getItems().addAll(employees);
        dataProvider.refreshAll();
    }

    private void setupLayout() {
        Div tooltip = new Div();
        tooltip.setText("Double click on a row to edit employee");
        tooltip.getStyle().set("font-size", "small");
        tooltip.getStyle().set("color", "var(--lumo-secondary-text-color)");
        
        refreshButton = new Button("Refresh", new Icon(VaadinIcon.REFRESH));
        refreshButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setWidthFull();
        buttonLayout.setJustifyContentMode(JustifyContentMode.END);
        buttonLayout.add(refreshButton);
        
        add(getToolbar(), tooltip, grid, buttonLayout);
    }
}
