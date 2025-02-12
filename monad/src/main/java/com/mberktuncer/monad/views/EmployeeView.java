package com.mberktuncer.monad.views;

import com.mberktuncer.monad.constant.views.employee.GridData;
import com.mberktuncer.monad.constant.views.employee.SearchField;
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
        searchField.setPlaceholder(SearchField.SEARCH_FIELD_TEXT.getData());
        searchField.setWidth(SearchField.SEARCH_FIELD_WIDTH.getData());
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setClearButtonVisible(true);
        searchField.setValueChangeMode(ValueChangeMode.LAZY);

        grid = new Grid<>(Employee.class);
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        grid.setHeight(GridData.GRID_HEIGHT_SIZE.getData());

        List<Employee> employees = employeeService.findAll();

        dataProvider = new ListDataProvider<>(employees);
        grid.setDataProvider(dataProvider);

        grid.removeAllColumns();
        grid.addColumn(Employee::getTC)
            .setHeader(GridData.COLUMN_IDENTITY_NO_TEXT.getData())
            .setWidth(GridData.COLUMN_IDENTITY_NO_WIDTH_SIZE.getData())
            .setFlexGrow(0);
        grid.addColumn(Employee::getAd)
            .setHeader(GridData.COLUMN_NAME_TEXT.getData())
            .setSortable(true);
            
        grid.addColumn(Employee::getSoyad)
            .setHeader(GridData.COLUMN_LAST_NAME_TEXT.getData())
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
