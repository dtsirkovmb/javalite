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


package org.javalite.activeweb;

import app.controllers.AbcPersonController;
import org.javalite.activeweb.controller_filters.RequestPropertiesLogFilter;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * @author Igor Polevoy
 */
public class RequestPropertiesLogFilterSpec extends IntegrationSpec{

    @Before
    public void before(){
        addFilter(AbcPersonController.class, new RequestPropertiesLogFilter());
    }

    @Test
    public void shouldPrintRequestParamsToLog(){

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        PrintStream pout = new PrintStream(bout);
        System.setErr(pout) ;

        controller("abc-person").param("bogus","val1").integrateViews(false).get("pass_values");
        pout.flush();
        pout.close();
        System.out.println(bout.toString());
        String res = bout.toString();
        a(res).shouldContain("uri_full_path=/abc-person/pass_values");
        a(res).shouldContain("request_url=http://localhost/abc-person/pass_values");
        a(res).shouldContain("query_string=null");
    }
}
