package org.example;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

class ProcessService extends Service<Void> {
    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(300);
                return null;
            }
        };
    }
}