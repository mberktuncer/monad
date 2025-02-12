package com.mberktuncer.monad.views;

import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "about", layout = MainView.class)
@PageTitle("About")
public class AboutView extends VerticalLayout {

    public AboutView() {
        add(new Paragraph("This is an application using Vaadin and Spring Boot. Developed as Manad Yazılım Interview Project."));
    }
}
