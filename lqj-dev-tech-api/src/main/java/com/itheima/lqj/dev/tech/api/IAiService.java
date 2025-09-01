package com.itheima.lqj.dev.tech.api;

import org.springframework.ai.chat.ChatResponse;
import reactor.core.publisher.Flux;

/**
 * @Author 李岐鉴
 * @Date 2025/9/1
 * @Description IAiService 类
 */
public interface IAiService {

    ChatResponse generate(String model, String message);

    Flux<ChatResponse> generateStream(String model, String message);
}
