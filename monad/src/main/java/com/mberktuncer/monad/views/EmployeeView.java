package com.mberktuncer.monad.views;

import com.mberktuncer.monad.model.Employee;
import com.mberktuncer.monad.service.EmployeeService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.util.List;

@Route(value = "employee", layout = MainView.class)
@PageTitle("Personel")
public class EmployeeView extends VerticalLayout {
    private final EmployeeService employeeService;
    private final Grid<Employee> grid;
    private final ListDataProvider<Employee> dataProvider;

    public EmployeeView(EmployeeService employeeService){
        this.employeeService = employeeService;
        setSizeFull();

        TextField searchField = new TextField();
        searchField.setPlaceholder("İsim ile ara...");
        searchField.setWidth("300px");
        searchField.setValueChangeMode(ValueChangeMode.LAZY);

        grid = new Grid<>(Employee.class);
        grid.setColumns("TC", "ad", "soyad");

        List<Employee> employees = employeeService.findAll();

        dataProvider = new ListDataProvider<>(employees);
        grid.setDataProvider(dataProvider);

        grid.getColumnByKey("TC").setHeader("TC No");
        grid.getColumnByKey("ad").setHeader("Ad");
        grid.getColumnByKey("soyad").setHeader("Soyad");
        
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

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
