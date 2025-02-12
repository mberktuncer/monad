package com.mberktuncer.monad.views;

import com.mberktuncer.monad.model.Employee;
import com.mberktuncer.monad.service.EmployeeService;
import com.mberktuncer.monad.service.impl.EmployeeServiceImpl;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "employee", layout = MainView.class)
@PageTitle("Personel")
public class EmployeeView extends VerticalLayout {
    private final EmployeeService employeeService;

    public EmployeeView(EmployeeService employeeService){
        this.employeeService = employeeService;
        setSizeFull();

        TextField searchField = new TextField();
        searchField.setPlaceholder("İsim ile ara...");
        searchField.setWidth("300px");

        Grid<Employee> grid = new Grid<>(Employee.class);
        grid.setColumns("TC", "ad", "soyad");

        List<Employee> employees = employeeService.findAll();

        ListDataProvider<Employee> dataProvider = new ListDataProvider<>(employees);
        grid.setDataProvider(dataProvider);

        searchField.addValueChangeListener(e -> {
            String filterText = e.getValue().trim();
            if (filterText.isEmpty()){
                dataProvider.clearFilters();
            }else{
                dataProvider.setFilter(Employee::getAd,
                        name -> name != null && name.toLowerCase().contains(filterText.toLowerCase()));
            }
        });

        add(searchField, grid);
    }

}
