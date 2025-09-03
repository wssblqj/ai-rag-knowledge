package com.itheima.lqj.dev.tech.trigger.http;

import com.itheima.lqj.dev.tech.api.IAiService;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

/**
 * @Author 李岐鉴
 * @Date 2025/9/1
 * @Description OllamaController 类
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/ollama/")
public class OllamaController implements IAiService {


    @Resource
    private OllamaChatClient chatClient;

    /**
     * 生成数据
     * @param model
     * @param message
     * @return
     */
    @RequestMapping(value = "generate", method = RequestMethod.GET)
    @Override
    public ChatResponse generate(String model, @RequestParam String message) {
        return chatClient.call(new Prompt(message, OllamaOptions.create().withModel(model)));
    }

    /**
     * 生成流式数据
     * @param model
     * @param message
     * @return
     */
    @RequestMapping(value = "generate_stream", method = RequestMethod.GET)
    @Override
    public Flux<ChatResponse> generateStream(String model, @RequestParam String message) {
        return chatClient.stream(new Prompt(message, OllamaOptions.create().withModel(model)));
    }
}
