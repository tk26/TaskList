package com.tejaskumthekar.tasklist;

/**
 * Created by tk on 10/8/2015.
 */

public class Task {
    private int id;
    private String name;
    private int priority;

    public Task() {}

    public Task (String taskname, int taskpriority )
    {
        super();
        this.name=taskname;
        this.priority=taskpriority;

    }

    @Override
    public String toString() {
        return "Task [id=" + id + ", name=" + name + ", priority=" +   priority
                + "]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
