/*
 * Copyright 2012 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.guvnor.testscenario.backend.server;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import org.drools.guvnor.models.testscenarios.shared.Scenario;
import org.drools.guvnor.models.testscenarios.shared.SingleScenarioResult;
import org.jboss.errai.bus.server.annotations.Service;
import org.kie.commons.io.IOService;
import org.kie.guvnor.testscenario.service.TestScenarioEditorService;
import org.uberfire.backend.server.util.Paths;
import org.uberfire.backend.vfs.Path;
import org.uberfire.client.workbench.widgets.events.ResourceOpenedEvent;

@Service
@ApplicationScoped
public class TestScenarioEditorServiceImpl
        implements TestScenarioEditorService {


    private IOService ioService;
    private Paths paths;
    private Event<ResourceOpenedEvent> resourceOpenedEvent;

    public TestScenarioEditorServiceImpl() {
    }

    @Inject
    public TestScenarioEditorServiceImpl(@Named("ioStrategy") IOService ioService,
                                         Paths paths,
                                         Event<ResourceOpenedEvent> resourceOpenedEvent) {
        this.ioService = ioService;
        this.paths = paths;
        this.resourceOpenedEvent = resourceOpenedEvent;
    }

    @Override
    public SingleScenarioResult runScenario(String packageName, Scenario scenario) {
        return null;  //TODO -Rikkola-
    }

    @Override
    public Scenario loadScenario(Path path) {
        final String xml = ioService.readAllString(paths.convert(path));

        //Signal opening to interested parties
        resourceOpenedEvent.fire(new ResourceOpenedEvent(path));

        return new TestScenarioContentHandler().unmarshal(xml);
    }
}