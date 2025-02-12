package com.mberktuncer.monad.views;

import com.mberktuncer.monad.constant.views.helloworld.Common;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "helloworld", layout = MainView.class)
@PageTitle("Hello World")
public class HelloWorldView extends HorizontalLayout {
    private TextField name;
    private Button sayHello;

    public HelloWorldView() {
        name = new TextField(Common.TEXT_FIELD.getText());
        sayHello = new Button(Common.SAY_HELLO_BUTTON_TEXT.getText());
        add(name, sayHello);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);

        sayHello.addClickListener(e -> {
            Notification.show(Common.NOTIFICATION_TEXT.getText() + name.getValue());
        });

        sayHello.addClickShortcut(Key.ENTER);

        setMargin(true);
    }

}
