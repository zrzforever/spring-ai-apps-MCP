package pl.piomin.services.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private final ChatClient chatClient;

    public PersonController(ChatClient.Builder chatClientBuilder,
                            ToolCallbackProvider tools) {
        this.chatClient = chatClientBuilder
                .defaultTools(tools)
//                .defaultAdvisors(
//                        new PromptChatMemoryAdvisor(chatMemory),
//                        new SimpleLoggerAdvisor())
                .build();
    }

    @GetMapping("/nationality/{nationality}")
    String findById(@PathVariable String nationality) {
        PromptTemplate pt = new PromptTemplate("""
                Find persons with {nationality} nationality ?.
                """);
        Prompt p = pt.create(Map.of("nationality", nationality));

        return this.chatClient.prompt(p)
                .call()
                .content();
    }
}
