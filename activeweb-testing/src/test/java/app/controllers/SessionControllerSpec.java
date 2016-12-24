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

package app.controllers;

import org.javalite.activeweb.ControllerException;
import org.javalite.activeweb.ControllerSpec;
import org.javalite.activeweb.freemarker.UserTag;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests SessionTagController
 *  
 * @author Igor Polevoy
 */
public class SessionControllerSpec extends ControllerSpec {

    @Before
    public void before(){
        setTemplateLocation("src/test/views");
        registerTag("user", new UserTag());
    }

    @Test
    public void shouldAccessSessionObject(){
        request().get("index");
        a(responseContent()).shouldBeEqual("User is: John, as well as: John, as well as: John");
    }


    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectSettingNameSessionIntoParams(){
        request().get("bad_action");
    }
}
