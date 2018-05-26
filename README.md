jHipster
yeoman - code generator using scaffolding
JDL - JHipster Domain Language

Client Side:
  Angular - front-end SPA technology
  HTML5 / CSS3
  Bootstrap - Responsive Applications
  Angular Translation - Internationalization
  rxjs
  jQuery - Javascript Library
  Karma - Test Runner
  Gatling - Load testing
  Hazelcast - Distributed Cache
  Websockets - real-time bi-directional messaging
  Yarn - Dependency Management (similar to NPM and Bower)
  Webpack - Module bundler
  Browsersync - Development tool: Live reloads and reduces repetetive tasks
  Protracter - End-to-end Testing of Angular Applications
  Gzip, Cache, and minify
  Sass - extension of Css
  TypeScript
  Javasript

Server Side:  
  Spring Boot - back-end / server-side MVC technology
  Spring Security - 
  Spring Social - Federated Logins
  Spring Actuator
  Spring User Management
  Spring Auditing & Login
  Hibernate - ORM Framework
  Dropwizard 
  Jackson
  MySQL
  Liquidbase - Database versioning
  Cucumber - Behavior driven development.  Specifications and Test Documentation
  Elastic Search - Search across domain objects
  Swagger - Automatic Document Generation
  Java 8 
  Gradle - Build Tool
    
Run: 
  mvnw
  yarn start
  
Build and run jBlog Application
  mkdir jblog
  cd jblog
  jhipster
    monolithic
    
  jhipster entity entity_nanme // edd new functionality to perform CRUD on data source called entity_nanme  
    
  mvnw
  yarn start
  
JDL - JHipster Domain Language: generates JPA Class in Spring, controllers, Angular code, and etc.
yo jhipster:import-jdl my_file.jdl or jhipster-uml my_file.jdl

Continuous Integration configured!
  Merge the Pull Request at https://github.com/screamatthewind/jhipster/pulls
  Open your GitHub branch at https://github.com/screamatthewind/jhipster/tree/jhipster-travis-ci-179911394531795

Monolithic

  A single-tiered application in which the user interface and data access code are combined into a single program from a single platform
  The application can perform every step needed to coplete a particular function
  
Microservice

  A variant of service-oriented architecture (SOA) that structures an application as a collection of loosely couple services
  Each service is fine-grained and lightweight
  Parallelizes development by enabling small autonomous teams to develop, deploy, and scale their respective services
  Enables continuous delivery and deployment
  
  Registry - configuration and monitoring
  Gateway - handles web traffic and serves Angular application up
  Microservices - 1 to many, stateless, REST interface, several instances can be launched in parallel
  JHipster Console - Monitoring and Alerting
  

  Sample Conference Application  
    Conference Microservice
    Blog Microservice
    Registration Microservice  
    Pre-conference Training

  
  

  