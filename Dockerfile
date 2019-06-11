FROM oracle/graalvm-ce:1.0.0-rc15 as graalvm
COPY . /home/app/twitter-demo-kotlin-v2
WORKDIR /home/app/twitter-demo-kotlin-v2
RUN native-image --no-server -cp build/libs/twitter-demo-kotlin-v2-*.jar

FROM frolvlad/alpine-glibc
EXPOSE 8080
COPY --from=graalvm /home/app/twitter-demo-kotlin-v2 .
ENTRYPOINT ["./twitter-demo-kotlin-v2"]
