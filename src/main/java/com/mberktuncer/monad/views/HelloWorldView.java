package com.mberktuncer.monad.views;

import com.mberktuncer.monad.constant.common.ViewConstants;
import com.mberktuncer.monad.constant.view.helloworld.HelloWorldText;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = ViewConstants.HELLO_WORLD_ROUTE, layout = MainView.class)
@PageTitle(ViewConstants.HELLO_WORLD_TITLE)
public class HelloWorldView extends HorizontalLayout {
    private TextField name;
    private Button sayHello;

    public HelloWorldView() {
        initializeComponents();
        configureLayout();
        setupEventListeners();
    }

    private void initializeComponents() {
        name = new TextField(HelloWorldText.NAME_PLACEHOLDER.getText());
        sayHello = new Button(HelloWorldText.BUTTON_TEXT.getText());
    }

    private void configureLayout() {
        add(name, sayHello);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);
        setMargin(true);
    }

    private void setupEventListeners() {
        setupButtonClickListener();
        setupKeyboardShortcut();
    }

    private void setupButtonClickListener() {
        sayHello.addClickListener(e -> showGreeting());
    }

    private void setupKeyboardShortcut() {
        sayHello.addClickShortcut(Key.ENTER);
    }

    private void showGreeting() {
        Notification.show(HelloWorldText.GREETING_PREFIX.getText() + name.getValue());
    }
}
