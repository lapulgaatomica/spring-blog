# Blog API built with Spring
This is a blog api built with spring boot, it was built in my attempt to understand how unit testing and spring security
works.

# Quick Start
Requirements:
Java 11, Maven, PostgreSql

1:  Create a database named **spring_blog** in your PostgreSql server.

2:  change the spring.datasource.username and spring.datasource.password properties in the application.properties file 
    which you will find in src/main/resources folder to match the username and password of the database you created in
    1 above

3:  If the following commands work, you can take this app for a spin

        $ git clone https://github.com/lapulgaatomica/spring-blog.git
        
        $ cd spring-blog
        
        $ ./mvnw clean package
        
        $ cd target
        
        $ java -jar blog-0.0.1-SNAPSHOT.jar
