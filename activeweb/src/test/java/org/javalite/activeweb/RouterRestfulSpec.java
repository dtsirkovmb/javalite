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

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockFilterConfig;

import static org.javalite.test.jspec.JSpec.a;

/**
 * @author Igor Polevoy
 */
@SuppressWarnings({"JavaDoc"})
public class RouterRestfulSpec {

    Router r = new Router("home");
    @Before
    public void before(){
        Context.setControllerRegistry(new ControllerRegistry(new MockFilterConfig()));
    }


    /**
     * GET 	/photos 	            index 	        display a list of all photos
     */
    @Test
    public void shouldRecognizeRestfulRouteIndex() throws ClassLoadException {
        Router r = new Router(null);
        Route mr = r.recognize("/photos", HttpMethod.GET);

        a(mr.getControllerClassName()).shouldBeEqual("app.controllers.PhotosController");
        a(mr.getActionName()).shouldBeEqual("index");
    }


    @Test
    public void shouldRecognizeRestfulRouteIndexForControllerInPackage() throws ClassLoadException {
        Router r = new Router(null);
        Route mr = r.recognize("/admin/special/special_rest", HttpMethod.GET);

        a(mr.getControllerClassName()).shouldBeEqual("app.controllers.admin.special.SpecialRestController");
        a(mr.getActionName()).shouldBeEqual("index");
    }


    /**
     * GET 	/photos/new_form 	    new_form        return an HTML form for creating a new photo
     */
    @Test
    public void shouldRecognizeRestfulRouteNewForm() throws ClassLoadException {

        Route mr = r.recognize("/photos/new_form", HttpMethod.GET);

        a(mr.getControllerClassName()).shouldBeEqual("app.controllers.PhotosController");
        a(mr.getActionName()).shouldBeEqual("new_form");
    }

    @Test
    public void shouldRecognizeRestfulRouteNewFormForControllerInPackage() throws ClassLoadException {

        Route mr = r.recognize("/admin/special2/special2_rest/new_form", HttpMethod.GET);

        a(mr.getControllerClassName()).shouldBeEqual("app.controllers.admin.special2.Special2RestController");
        a(mr.getActionName()).shouldBeEqual("new_form");
    }

    /**
     * POST 	/photos 	            create 	        create a new photo
     */
    @Test
    public void shouldRecognizeRestfulRouteCreate() throws ClassLoadException {

        Route mr = r.recognize("/photos", HttpMethod.POST);

        a(mr.getControllerClassName()).shouldBeEqual("app.controllers.PhotosController");
        a(mr.getActionName()).shouldBeEqual("create");
    }

    @Test
    public void shouldRecognizeRestfulRouteCreateForControllerInPackage() throws ClassLoadException {

        Route mr = r.recognize("/admin/special2/special2_rest", HttpMethod.POST);

        a(mr.getControllerClassName()).shouldBeEqual("app.controllers.admin.special2.Special2RestController");
        a(mr.getActionName()).shouldBeEqual("create");
    }


    //GET 	/photos/id 	        show            display a specific photo
    @Test
    public void shouldRecognizeRestfulRouteShow() throws ClassLoadException {

        Route mr = r.recognize("/photos/1", HttpMethod.GET);

        a(mr.getControllerClassName()).shouldBeEqual("app.controllers.PhotosController");
        a(mr.getActionName()).shouldBeEqual("show");
        a(mr.getId()).shouldBeEqual("1");
    }
    @Test
    public void shouldRecognizeRestfulRouteShowForControllerInPackage() throws ClassLoadException {

        Route mr = r.recognize("/admin/special/special_rest/1", HttpMethod.GET);

        a(mr.getControllerClassName()).shouldBeEqual("app.controllers.admin.special.SpecialRestController");
        a(mr.getActionName()).shouldBeEqual("show");
        a(mr.getId()).shouldBeEqual("1");
    }

    //GET 	/photos/id/edit_form   edit_form 	    return an HTML form for editing a photo
    @Test
    public void shouldRecognizeRestfulRouteEditForm() throws ClassLoadException {
        
        Route mr = r.recognize("/photos/1/edit_form", HttpMethod.GET);

        a(mr.getControllerClassName()).shouldBeEqual("app.controllers.PhotosController");
        a(mr.getActionName()).shouldBeEqual("edit_form");
        a(mr.getId()).shouldBeEqual("1");
    }

    @Test
    public void shouldRecognizeRestfulRouteEditFormForControllerInPackage() throws ClassLoadException {
        Route mr = r.recognize("/admin/special/special_rest/1/edit_form", HttpMethod.GET);

        a(mr.getControllerClassName()).shouldBeEqual("app.controllers.admin.special.SpecialRestController");
        a(mr.getActionName()).shouldBeEqual("edit_form");
        a(mr.getId()).shouldBeEqual("1");
    }

    //PUT 	/photos/id 	        update          update a specific photo
    @Test
    public void shouldRecognizeRestfulRouteUpdate() throws ClassLoadException {

        Route mr = r.recognize("/photos/1", HttpMethod.PUT);

        a(mr.getControllerClassName()).shouldBeEqual("app.controllers.PhotosController");
        a(mr.getActionName()).shouldBeEqual("update");
        a(mr.getId()).shouldBeEqual("1");
    }


    @Test
    public void shouldRecognizeRestfulRouteUpdateForControllerInPackage() throws ClassLoadException {

        Route mr = r.recognize("/admin/special2/special3/special3_rest/1", HttpMethod.PUT);

        a(mr.getControllerClassName()).shouldBeEqual("app.controllers.admin.special2.special3.Special3RestController");
        a(mr.getActionName()).shouldBeEqual("update");
        a(mr.getId()).shouldBeEqual("1");
    }

    //DELETE 	/photos/:id 	        destroy         delete a specific photo
    @Test
    public void shouldRecognizeRestfulRouteDestroy() throws ClassLoadException {

        Route mr = r.recognize("/photos/1", HttpMethod.DELETE);

        a(mr.getControllerClassName()).shouldBeEqual("app.controllers.PhotosController");
        a(mr.getActionName()).shouldBeEqual("destroy");
        a(mr.getId()).shouldBeEqual("1");
    }

    @Test
    public void shouldRecognizeRestfulRouteDestroyForControllerInPackage() throws ClassLoadException {

        Route mr = r.recognize("/admin/special2/special3/special3_rest/1", HttpMethod.DELETE);

        a(mr.getControllerClassName()).shouldBeEqual("app.controllers.admin.special2.special3.Special3RestController");
        a(mr.getActionName()).shouldBeEqual("destroy");
        a(mr.getId()).shouldBeEqual("1");
    }


    //OPTIONS 	/photos/
    @Test
    public void shouldRecognizeRestfulRouteOPTIONS() throws ClassLoadException {

        Route mr = r.recognize("/photos", HttpMethod.OPTIONS);

        a(mr.getControllerClassName()).shouldBeEqual("app.controllers.PhotosController");
        a(mr.getActionName()).shouldBeEqual("options");
    }

    @Test
    public void shouldRecognizeRestfulRouteOPTIONSForControllerInPackage() throws ClassLoadException {

        Route mr = r.recognize("/admin/special2/special3/special3_rest/", HttpMethod.OPTIONS);

        a(mr.getControllerClassName()).shouldBeEqual("app.controllers.admin.special2.special3.Special3RestController");
        a(mr.getActionName()).shouldBeEqual("options");
    }
}
