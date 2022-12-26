package com.cqrs.cqrs.core.domain;

import com.cqrs.cqrs.core.events.BaseEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AggregatorRoot {
    protected String id;
    private int version = -1;
    private final List<BaseEvent> changes = new ArrayList<>();
    private Logger logger = Logger.getLogger(AggregatorRoot.class.getName());

    public String getId() {
        return this.id;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getVersion() {
        return version;
    }

    public List<BaseEvent> getUncommittedChanges() {
        return changes;
    }

    public void markChangesAsCommitted() {
        this.changes.clear();
    }

    protected void applyChanges(BaseEvent event, boolean isNewEvent) {
        try {
            var method = getClass().getDeclaredMethod("apply",
                    event.getClass());
            method.setAccessible(true);
            method.invoke(this, event);
        } catch (NoSuchMethodException e) {
            logger.log(Level.WARNING,
                    "no method found for " + event.getClass());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error applying event");
        } finally {
            if (isNewEvent) {
                changes.add(event);
            }
        }
    }

    public void raiseEvent(BaseEvent event) {
        applyChanges(event, true);
    }

    public void replayEvents(Iterator<BaseEvent> events) {
        events.forEachRemaining(event -> applyChanges(event, false));
    }
}
