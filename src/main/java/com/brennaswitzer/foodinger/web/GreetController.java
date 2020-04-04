package com.brennaswitzer.foodinger.web;

import com.brennaswitzer.foodinger.wire.Greeting;
import com.brennaswitzer.foodinger.wire.Join;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GreetController {

    @MessageMapping("/join")
    @SendTo("/topic/greetings")
    public Greeting greet(Join msg) throws Exception {
        Thread.sleep(1000);
        return new Greeting("Hello, " + msg.getName() + "!");
    }

}
