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
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;

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
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setClearButtonVisible(true);
        searchField.setValueChangeMode(ValueChangeMode.LAZY);

        grid = new Grid<>(Employee.class);
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        grid.setHeight("400px");

        List<Employee> employees = employeeService.findAll();

        dataProvider = new ListDataProvider<>(employees);
        grid.setDataProvider(dataProvider);

        grid.removeAllColumns();
        grid.addColumn(Employee::getTC)
            .setHeader("TC No")
            .setWidth("150px")
            .setFlexGrow(0);
        
        grid.addColumn(Employee::getAd)
            .setHeader("Ad")
            .setSortable(true);
            
        grid.addColumn(Employee::getSoyad)
            .setHeader("Soyad")
            .setSortable(true);

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
