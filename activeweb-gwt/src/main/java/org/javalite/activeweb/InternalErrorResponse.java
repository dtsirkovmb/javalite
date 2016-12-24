/*
Copyright 2010-2011 Max Artyukhov

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
package org.javalite.activeweb;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 *
 * @author Max Artyukhov
 */
public class InternalErrorResponse extends ControllerResponse {

    @Override
    void doProcess() {
        try {
            Context.getHttpResponse().sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch(IOException ioe) {
            throw new WebException(ioe);
                    }
    }    
}
