/*
Copyright 2009-2016 Igor Polevoy

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package org.javalite.async;

/**
 * Simple configuration of  queue.
 *
 * @author Igor Polevoy on 4/4/15.
 */
public class QueueConfig {
    private String name;
    private CommandListener  commandListener;
    private int listenerCount;
    private boolean durable;

    /**
     * Creates a specification of a queue for Async. By default the queue is durable (can save messages to hard drive).
     *
     * @param name human readable name of queue
     * @param commandListener CommandListener instance, must be thread safe.
     * @param listenerCount number of listeners to attach to a queue. Effectively this
     *                      is a number of processing threads.
     */
     public   QueueConfig(String name, CommandListener commandListener, int listenerCount) {
        this(name, commandListener, listenerCount, true);
    }

    /**
     * Creates a specification of a queue for Async
     *
     * @param name human readable name of queue
     * @param commandListener CommandListener instance, must be thread safe.
     * @param listenerCount number of listeners to attach to a queue. Effectively this
     *                      is a number of processing threads.
     * @param durable true to enable to save messages to hard drive, false otherwise.
     */
    public  QueueConfig(String name, CommandListener commandListener, int listenerCount, boolean durable) {
        this.name = name;
        this.commandListener = commandListener;
        this.listenerCount = listenerCount;
        this.durable = durable;
    }

    public String getName() {
        return name;
    }

    public CommandListener getCommandListener() {
        return commandListener;
    }

    public int getListenerCount() {
        return listenerCount;
    }

    public boolean isDurable() {
        return durable;
    }
}
