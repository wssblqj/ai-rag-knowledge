# 普通镜像构建，随系统版本构建 amd/arm
docker build -t lqjdocker517/ai-rag-knowledge-app -f ./Dockerfile .

# 兼容 amd、arm 构建镜像
# docker buildx build --load --platform liunx/amd64,linux/arm64 -t fuzhengwei/ai-rag-knowledge-app:1.2 -f ./Dockerfile . --push
