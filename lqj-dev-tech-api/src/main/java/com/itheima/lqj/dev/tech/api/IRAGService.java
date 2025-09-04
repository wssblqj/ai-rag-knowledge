package com.itheima.lqj.dev.tech.api;

import com.itheima.lqj.dev.tech.api.response.Response;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author 李岐鉴
 * @Date 2025/9/2
 * @Description IRAGService 类
 */
public interface IRAGService {

    Response<List<String>> queryRagTagList();

    Response<String> uploadFile(String ragTag, List<MultipartFile> files);

    Response<String> analyzeGitRepository(String repoUrl, String userName, String token) throws Exception;
}
