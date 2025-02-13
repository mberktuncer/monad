package com.mberktuncer.monad.views;

import com.mberktuncer.monad.constant.common.ViewConstants;
import com.mberktuncer.monad.constant.view.employee.EmployeeGridProperty;
import com.mberktuncer.monad.constant.view.employee.EmployeeSearchProperty;
import com.mberktuncer.monad.model.Employee;
import com.mberktuncer.monad.service.contract.EmployeeService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.grid.GridVariant;

import java.util.List;

@Route(value = ViewConstants.EMPLOYEE_ROUTE, layout = MainView.class)
@PageTitle(ViewConstants.EMPLOYEE_TITLE)
public class EmployeeView extends VerticalLayout {
    private final EmployeeService employeeService;
    private final Grid<Employee> grid;
    private final ListDataProvider<Employee> dataProvider;

    public EmployeeView(EmployeeService employeeService){
        this.employeeService = employeeService;
        setSizeFull();

        TextField searchField = new TextField();
        searchField.setPlaceholder(EmployeeSearchProperty.PLACEHOLDER_TEXT.getData());
        searchField.setWidth(EmployeeSearchProperty.FIELD_WIDTH.getData());
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setClearButtonVisible(true);
        searchField.setValueChangeMode(ValueChangeMode.LAZY);

        grid = new Grid<>(Employee.class);
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        grid.setHeight(EmployeeGridProperty.GRID_HEIGHT.getData());

        List<Employee> employees = employeeService.findAll();

        dataProvider = new ListDataProvider<>(employees);
        grid.setDataProvider(dataProvider);

        grid.removeAllColumns();
        grid.addColumn(Employee::getIdentityNumber)
            .setHeader(EmployeeGridProperty.IDENTITY_COLUMN_HEADER.getData())
            .setWidth(EmployeeGridProperty.IDENTITY_COLUMN_WIDTH.getData())
            .setFlexGrow(0);
        grid.addColumn(Employee::getFirstName)
            .setHeader(EmployeeGridProperty.NAME_COLUMN_HEADER.getData())
            .setSortable(true);
            
        grid.addColumn(Employee::getLastName)
            .setHeader(EmployeeGridProperty.LASTNAME_COLUMN_HEADER.getData())
            .setSortable(true);

        searchField.addValueChangeListener(e -> {
            String filterText = e.getValue().trim();
            if (filterText.isEmpty()){
                dataProvider.clearFilters();
            }else{
                dataProvider.setFilter(Employee::getFirstName,
                        name -> name != null && name.toLowerCase().contains(filterText.toLowerCase()));
            }
        });

        add(searchField, grid);
    }

}
