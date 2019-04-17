package view;

import org.hibernate.Session;

import javax.swing.*;

public interface EntityView<T> {

    void setSession(Session session);

    void setModel(T s);

    void invoke();
}
