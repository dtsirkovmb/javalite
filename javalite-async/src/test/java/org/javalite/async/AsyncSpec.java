package org.javalite.async;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.javalite.async.services.GreetingModule;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.javalite.test.jspec.JSpec.a;

/**
 * @author Igor Polevoy on 4/5/15.
 */
public class AsyncSpec {

    private static final String QUEUE_NAME = "queue1";
    private String filePath;

    @Before
    public void before() throws IOException {
        filePath = Files.createTempDirectory("async").toFile().getCanonicalPath();
        HelloCommand.reset();
    }

    @Test
    public void shouldProcessCommands() throws IOException, InterruptedException {

        Async async = new Async(filePath, false, new QueueConfig(QUEUE_NAME, new CommandListener(), 50));

        async.start();

        //send 100 messages
        for(int i = 0; i < 100; i++){
            async.send(QUEUE_NAME, new HelloCommand("Hello, Dolly " + i));
        }

        //messages will execute in about 2 seconds, because we send 100 messages, but only have 50 threads.
        //lets wait for 3 seconds, then validate result
        Thread.sleep(5000);


        async.stop();
        a(HelloCommand.counter()).shouldBeEqual(100);
    }


    @Test
    public void shouldListTopCommands() throws Exception {
        Async async = new Async(filePath, false, new QueueConfig(QUEUE_NAME, new CommandListener(), 100));

        async.start();

        async.pause(QUEUE_NAME);

        //send 100 messages
        for(int i = 0; i < 100; i++){
            async.send(QUEUE_NAME, new HelloCommand("Hello, Dolly " + i));
        }
        List<Command> commands = async.getTopCommands(10, QUEUE_NAME);
        a(commands.size()).shouldBeEqual(10);

        //drain queue
        async.resume(QUEUE_NAME);

        Thread.sleep(2000);

        commands = async.getTopCommands(10, QUEUE_NAME);
        a(commands.size()).shouldBeEqual(0);

        async.stop();
    }


    @Test
    public void shouldGetCommandsSynchronously() throws Exception {

        Async async = new Async(filePath, false, new QueueConfig(QUEUE_NAME, new CommandListener(), 0));

        async.start();
        for(int i = 0; i < 2; i++){
            async.send(QUEUE_NAME, new HelloCommand("Hello, Dolly " + i));
        }

        a(async.receiveCommand(QUEUE_NAME, HelloCommand.class).getMessage()).shouldBeEqual("Hello, Dolly 0");
        a(async.receiveCommand(QUEUE_NAME, HelloCommand.class).getMessage()).shouldBeEqual("Hello, Dolly 1");

        Command c = async.receiveCommand(QUEUE_NAME, 100); // returns null because there were only 2 commands sent
        a(c).shouldBeNull();
        
        async.stop();
    }


    @Test
    public void shouldRemoveMessages() throws Exception {

        Async async = new Async(filePath, false, new QueueConfig(QUEUE_NAME, new CommandListener(), 0));

        async.start();

        for(int i = 0; i < 2; i++){
            async.send(QUEUE_NAME, new HelloCommand("Hello, Dolly " + i));
        }

        List<Command> commands = async.getTopCommands(10, QUEUE_NAME);
        a(commands.size()).shouldBeEqual(2);

        a(commands.get(0).getJMSMessageID()).shouldNotBeNull();

        async.removeAllMessages(QUEUE_NAME);

        commands = async.getTopCommands(10, QUEUE_NAME);
        a(commands.size()).shouldBeEqual(0);

        async.stop();
    }

    @Test
    public void shouldInjectDependencyIntoCommand() throws InterruptedException {

        Injector injector = Guice.createInjector(new GreetingModule());
        Async async = new Async(filePath, false, injector, new QueueConfig(QUEUE_NAME, new CommandListener(), 1));

        async.start();

        async.send(QUEUE_NAME, new HelloInjectedCommand("The greeting is: "));

        Thread.sleep(2000);

        async.stop();
        a(HelloInjectedCommand.result).shouldBeEqual("The greeting is: hi");
    }

    @Test
    public void shouldStartStopBroker() throws IOException, InterruptedException {

        Async async = new Async(filePath, false, new QueueConfig(QUEUE_NAME, new CommandListener(), 50));
        async.start();
        for(int i = 0; i < 10; i++){
            async.send(QUEUE_NAME, new HelloCommand("Message: " + i));
        }

        Thread.sleep(3000);

        async.stop();
        a(HelloCommand.counter()).shouldBeEqual(10);

        //lets start second time
        async.start();

        for(int i = 0; i < 10; i++){
            async.send(QUEUE_NAME, new HelloCommand("Message: " + i));
        }

        Thread.sleep(3000);
        async.stop();
        a(HelloCommand.counter()).shouldBeEqual(20);
    }

    @Test
    public void shouldInjectDependencyIntoCommandListener() throws InterruptedException {

        Injector injector = Guice.createInjector(new GreetingModule());
        Async async = new Async(filePath, false, injector, new QueueConfig(QUEUE_NAME, new HelloCommandListener(), 1));
        async.start();

        async.send(QUEUE_NAME, new HelloCommand("Hi, there"));

        //SEE ASSERTION INSIDE HelloCommandListener.

        Thread.sleep(1000);
        async.stop();
    }
}
