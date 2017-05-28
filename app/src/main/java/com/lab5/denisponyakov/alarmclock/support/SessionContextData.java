package com.lab5.denisponyakov.alarmclock.support;

import com.lab5.denisponyakov.alarmclock.model.AlarmDescription;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Singleton data context
public class SessionContextData {

    ///////////////////////////////////////////////////////////////
    // Singleton
    private Map<String, Object> blackboard;

    private static SessionContextData instance = null;

    protected SessionContextData() {
        this.blackboard = new HashMap<>();
    }

    public static SessionContextData getInstance() {
        if (instance == null) {
            instance = new SessionContextData();
        }

        return instance;
    }

    ///////////////////////////////////////////////////////////////

    private final String alarmsListKey = "alarmsList";
    private final String currentAlarmKey = "currentAlarm";

    ///////////////////////////////////////////////////////////////

    public void put(String key, Object object) {
        blackboard.put(key, object);
    }

    public Object get(String key) {
        return blackboard.containsKey(key) ? blackboard.get(key) : null;
    }

    public boolean hasKey(String key) {
        return blackboard.containsKey(key);
    }

    public List<AlarmDescription> getAlarmsList() {
        if (!hasKey(alarmsListKey)) {
            put(alarmsListKey, new ArrayList<>());
        }

        return (List<AlarmDescription>) get(alarmsListKey);
    }

    public void setCurrentAlarm(CrudContainer<AlarmDescription> alarmContainer) {
        put(currentAlarmKey, alarmContainer);
    }

    public CrudContainer<AlarmDescription> getCurrentAlarm() {
        return (CrudContainer<AlarmDescription>) get(currentAlarmKey);
    }
}
