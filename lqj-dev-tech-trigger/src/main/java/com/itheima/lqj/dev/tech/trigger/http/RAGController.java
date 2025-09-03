package com.itheima.lqj.dev.tech.trigger.http;

import com.itheima.lqj.dev.tech.api.IRAGService;
import com.itheima.lqj.dev.tech.api.response.Response;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.PgVectorStore;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author 李岐鉴
 * @Date 2025/9/2
 * @Description RAGController 类
 */
@RestController
@RequestMapping("/api/v1/rag/")
@CrossOrigin("*")
@Slf4j
public class RAGController implements IRAGService {


    @Resource
    private OllamaChatClient ollamaChatClient;

    @Resource
    private TokenTextSplitter tokenTextSplitter;

    @Resource
    private SimpleVectorStore simpleVectorStore;

    @Resource
    private PgVectorStore pgVectorStore;

    @Resource
    private RedissonClient redissonClient;

    @RequestMapping(value = "query_rag_tag_list", method = RequestMethod.GET)
    @Override
    public Response<List<String>> queryRagTagList() {
        RList<String> elements = redissonClient.getList("ragTag");
        return Response.<List<String>> builder()
                .code("0000")
                .info("查询成功")
                .data(elements)
                .build();
    }

    @RequestMapping(value = "file/upload", method = RequestMethod.POST, headers = "Content-Type=multipart/form-data")
    @Override
    public Response<String> uploadFile(String ragTag, @RequestParam("file") List<MultipartFile> files) {
        log.info("上传知识库开始 {}", ragTag);
        for (MultipartFile file : files) {
            TikaDocumentReader documentReader = new TikaDocumentReader(file.getResource());
            List<Document> documents = documentReader.get();
            List<Document> splitDocuments = tokenTextSplitter.apply(documents);
            documents.forEach(doc -> {
                doc.getMetadata().put("knoeledge", ragTag);
            });
            splitDocuments.forEach(doc -> {
                doc.getMetadata().put("knoeledge", ragTag);
            });
            pgVectorStore.accept(splitDocuments);
            RList<Object> elements = redissonClient.getList("ragTag");
            if (!elements.contains(ragTag)) {
                elements.add(ragTag);
            }

        }
        log.info("上传知识库完成 {}", ragTag);
        return Response.<String> builder().code("0000").info("上传成功").data(ragTag).build();
    }
}
