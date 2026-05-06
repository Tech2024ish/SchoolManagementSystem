# --- Build stage: compile the WAR ---
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /build
COPY pom.xml ./
RUN mvn -B dependency:go-offline
COPY src ./src
RUN mvn -B clean package -DskipTests

# --- Runtime stage: Tomcat 10.1 on JDK 21 ---
FROM tomcat:10.1-jdk21-temurin
RUN rm -rf /usr/local/tomcat/webapps/ROOT
COPY --from=build /build/target/SchoolManagementSystem.war /usr/local/tomcat/webapps/ROOT.war
# Lock the shutdown listener to localhost so platform health checks can't hit it.
RUN sed -i 's|<Server port="8005"|<Server port="8005" address="127.0.0.1"|' /usr/local/tomcat/conf/server.xml
EXPOSE 8080
CMD ["catalina.sh", "run"]
