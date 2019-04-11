package model;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public interface IModel {
    @Transient
    String getModelName();

    @Transient
    String getModelPluralName();

    @Transient
    String[] getColumnsNames();

    @Transient
    Object[] getColumns();

    @Transient
    Class[] getColumnsTypes();

    @Transient
    Class[] getSubColumnsTypes();

    @Transient
    Class[] getSubColumnsTargetTypes();

    void setColumns(Object[] objects);

    void setId(String id);

    void setParentId(String id);

    void setDeleted(boolean deleted);
}