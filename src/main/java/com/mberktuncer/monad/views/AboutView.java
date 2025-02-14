package com.mberktuncer.monad.views;

import com.mberktuncer.monad.constant.common.ViewConstants;
import com.mberktuncer.monad.constant.view.about.AboutMessage;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = ViewConstants.ABOUT_ROUTE, layout = MainView.class)
@PageTitle(ViewConstants.ABOUT_TITLE)
public class AboutView extends VerticalLayout {
    public AboutView() {
        add(new Paragraph(AboutMessage.APPLICATION_ABOUT.getMessage()));
    }
}
