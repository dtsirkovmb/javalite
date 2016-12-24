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

package org.javalite.activeweb.controller_filters;

/**
 * @author Igor Polevoy
 */
public abstract class AbstractLoggingFilter extends HttpSupportFilter{
    private Level level;
    
    public enum Level {
        INFO, WARNING, DEBUG, ERROR, DISABLED
    }


    /**
     * Creates a filter with preset log level.
     *
     * @param level log level
     */
    public AbstractLoggingFilter(Level level) {
        this.level = level;
    }

    /**
     * Creates a filter with default "INFO" level.
     */
    public  AbstractLoggingFilter() {
        this(Level.INFO);
    }

    /**
     * Sets a log level at run time if needed.
     *
     * @param level log level
     */
    public void logAtLevel(Level level) {
        this.level = level;
    }

    @Override
    public final void before() {
        log(getMessage() + ", session: " + session().id());
    }

    protected void log(String message){
        if (level.equals(Level.DISABLED)) {
            return;
        }
        if (level.equals(Level.INFO)) {
            logInfo(message);
        }
        if (level.equals(Level.WARNING)) {
            logWarning(message);
        }
        if (level.equals(Level.DEBUG)) {
            logDebug(message);
        }
        if (level.equals(Level.ERROR)) {
            logError(message);
        }
    }

    public Level getLevel() {
        return level;
    }

    protected abstract String getMessage();
}
