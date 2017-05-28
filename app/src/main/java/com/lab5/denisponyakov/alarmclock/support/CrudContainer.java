package com.lab5.denisponyakov.alarmclock.support;

import android.support.annotation.NonNull;

import com.lab5.denisponyakov.alarmclock.model.CrudObject;

public class CrudContainer<T extends CrudObject> {

    private Class<T> metaclass;
    private CrudAction action;
    private T object;
    private T tempObject;

    public CrudContainer(Class<T> metaclass) {
        this.metaclass = metaclass;
    }

    public CrudContainer setCreateMode() {
        try {
            this.tempObject = this.metaclass.newInstance();
            this.action = CrudAction.Create;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    @NonNull
    public CrudContainer setUpdateMode(T object) throws NullPointerException {
        if (object == null)
            throw new NullPointerException();

        try {
            this.object = object;
            this.tempObject = this.metaclass.newInstance();
            this.tempObject.update(this.object);
            this.action = CrudAction.Update;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public CrudAction getAction() {
        return this.action;
    }

    public T getObject() {
        return this.tempObject;
    }

    public void commit() {
        this.object.update(this.tempObject);
    }
}
