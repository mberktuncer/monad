package com.mberktuncer.monad.views;

import com.mberktuncer.monad.constant.view.main.MainViewEnums;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;

import java.util.Optional;

public class MainView extends AppLayout {
    private final Tabs menu;
    private H1 viewTitle;

    public MainView() {
        this.menu = new Tabs();
        initializeLayout();
        initializeMenu();
    }

    private void initializeLayout() {
        setPrimarySection(Section.DRAWER);
        addToNavbar(true, createHeaderContent());
    }

    private void initializeMenu() {
        configureMenuTabs(menu);
        addToDrawer(createDrawerContent(menu));
    }

    private Component createHeaderContent() {
        HorizontalLayout layout = createHeaderLayout();
        addDrawerToggleToHeader(layout);
        addViewTitleToHeader(layout);
        return layout;
    }

    private HorizontalLayout createHeaderLayout() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setId(MainViewEnums.HEADER_LAYOUT_ID.getText());
        layout.setWidthFull();
        layout.setSpacing(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        return layout;
    }

    private void addDrawerToggleToHeader(HorizontalLayout layout) {
        layout.add(new DrawerToggle());
    }

    private void addViewTitleToHeader(HorizontalLayout layout) {
        viewTitle = new H1();
        layout.add(viewTitle);
    }

    private Component createDrawerContent(Tabs menu) {
        VerticalLayout layout = createDrawerLayout();
        HorizontalLayout logoLayout = createLogoLayout();
        layout.add(logoLayout, menu);
        return layout;
    }

    private VerticalLayout createDrawerLayout() {
        VerticalLayout layout = new VerticalLayout();
        configureDrawerLayout(layout);
        return layout;
    }

    private void configureDrawerLayout(VerticalLayout layout) {
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.getThemeList().set(MainViewEnums.DRAWER_THEME.getText(), true);
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);
    }

    private HorizontalLayout createLogoLayout() {
        HorizontalLayout logoLayout = new HorizontalLayout();
        logoLayout.setId(MainViewEnums.DRAWER_LAYOUT_ID.getText());
        logoLayout.add(new H1(MainViewEnums.DRAWER_LAYOUT_H1.getText()));
        return logoLayout;
    }

    private void configureMenuTabs(Tabs tabs) {
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        tabs.setId(MainViewEnums.MENU_ID.getText());
        tabs.add(createMenuItems());
    }

    private Tab[] createMenuItems() {
        return new Tab[] {
            createTab(MainViewEnums.TAB_HELLO_WORLD.getText(), HelloWorldView.class),
            createTab(MainViewEnums.TAB_EMPLOYEES.getText(), EmployeeView.class),
            createTab(MainViewEnums.TAB_ABOUT.getText(), AboutView.class)
        };
    }

    private static Tab createTab(String text, Class<? extends Component> navigationTarget) {
        final Tab tab = new Tab();
        configureTab(tab, text, navigationTarget);
        return tab;
    }

    private static void configureTab(Tab tab, String text, Class<? extends Component> navigationTarget) {
        tab.add(new RouterLink(text, navigationTarget));
        ComponentUtil.setData(tab, Class.class, navigationTarget);
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        updateSelectedTab();
        updateViewTitle();
    }

    private void updateSelectedTab() {
        getTabForComponent(getContent()).ifPresent(menu::setSelectedTab);
    }

    private void updateViewTitle() {
        viewTitle.setText(getCurrentPageTitle());
    }

    private Optional<Tab> getTabForComponent(Component component) {
        return menu.getChildren()
                .filter(tab -> ComponentUtil.getData(tab, Class.class)
                        .equals(component.getClass()))
                .findFirst().map(Tab.class::cast);
    }

    private String getCurrentPageTitle() {
        return getContent().getClass().getAnnotation(PageTitle.class).value();
    }
}
