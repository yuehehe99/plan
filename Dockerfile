# 使用JDK11环境作为基础镜像
FROM openjdk:11

# 拷贝JRA包
ADD build/libs/myplan-0.0.1-SNAPSHOT.jar myplan.jar

# 容器启动时执行
ENTRYPOINT ["java","-jar","/myplan.jar"]

# 暴露端口
EXPOSE 8080

