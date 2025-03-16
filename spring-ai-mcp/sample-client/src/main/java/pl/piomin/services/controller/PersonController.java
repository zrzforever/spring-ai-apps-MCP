package pl.piomin.services.controller;

import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private final ChatClient chatClient;

    @Autowired
    private List<McpSyncClient> mcpSyncClients;

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
    String findByNationality(@PathVariable String nationality) {

        PromptTemplate pt = new PromptTemplate("""
                Find persons with {nationality} nationality.
                """);
        Prompt p = pt.create(Map.of("nationality", nationality));
        return this.chatClient.prompt(p)
                .call()
                .content();
    }

    @GetMapping("/count-by-nationality/{nationality}")
    String countByNationality(@PathVariable String nationality) {
        PromptTemplate pt = new PromptTemplate("""
                How many persons come from {nationality} ?
                """);
        Prompt p = pt.create(Map.of("nationality", nationality));

        return this.chatClient.prompt(p)
                .call()
                .content();
    }

    Prompt loadPromptByName(String name, String nationality) {
        var r = new McpSchema.GetPromptRequest(name, Map.of("nationality", nationality));
        var c = (McpSchema.TextContent) mcpSyncClients.getFirst().getPrompt(r).messages().getFirst().content();
        PromptTemplate pt = new PromptTemplate(c.text());
        Prompt p = pt.create(Map.of("nationality", nationality));
        return p;
    }
}
