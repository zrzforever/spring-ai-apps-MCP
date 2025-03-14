package pl.piomin.services;

import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.ToolCallbacks;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.piomin.services.repository.PersonRepository;
import pl.piomin.services.tools.PersonTools;

import java.util.List;

@SpringBootApplication
public class SampleServer {

    public static void main(String[] args) {
        SpringApplication.run(SampleServer.class, args);
    }

    @Bean
    public ToolCallbackProvider tools(PersonRepository personRepository) {
        return ToolCallbackProvider.from(ToolCallbacks.from(new PersonTools(personRepository)));
    }

//    @Bean
//    public ToolCallbackProvider tools(PersonRepository personRepository) {
//        return MethodToolCallbackProvider.builder()
//                .toolObjects(new PersonTools(personRepository))
//                .build();
//    }

//    @Bean
//    public List<McpServerFeatures.SyncPromptRegistration> prompts() {
//        var prompt = new McpSchema.Prompt("persons-by-nationality", "Get persons by nationality",
//                List.of(new McpSchema.PromptArgument("nationality", "Person nationality", true)));
//
//        var promptRegistration = new McpServerFeatures.SyncPromptRegistration(prompt, getPromptRequest -> {
//            String nameArgument = (String) getPromptRequest.arguments().get("name");
//            var userMessage = new McpSchema.PromptMessage(McpSchema.Role.USER,
//                    new McpSchema.TextContent("How many persons are from " + nameArgument + " ?"));
//            return new McpSchema.GetPromptResult("Count persons by nationality", List.of(userMessage));
//        });
//
//        return List.of(promptRegistration);
//    }
}
