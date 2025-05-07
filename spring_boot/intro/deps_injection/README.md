# 🌱 Spring Boot Tutorial

An exhaustive guide to every core concept, annotation, pattern, and feature you’ve seen (and more!) in your Spring Boot sample project. Use this as a reference to understand **how** and **why** Spring Boot works the way it does.

---

## 📄 Table of Contents

1. [Spring Boot Overview](#spring-boot-overview)
2. [Application Startup & Bootstrapping](#application-startup--bootstrapping)
3. [The IoC Container: ApplicationContext vs BeanFactory](#the-ioc-container-applicationcontext-vs-beanfactory)
4. [Bean Definition & Registration](#bean-definition--registration)
    - [Component Scanning](#component-scanning)
    - [Manual Registration (`@Configuration` + `@Bean`)](#manual-registration-configuration--bean)
    - [Conditional Beans & `@Conditional`](#conditional-beans--conditional)
    - [Meta-annotations & Custom Stereotypes](#meta-annotations--custom-stereotypes)
5. [Dependency Injection (DI) Strategies](#dependency-injection-di-strategies)
    - [Constructor Injection](#constructor-injection)
    - [Field & Setter Injection](#field--setter-injection)
    - [Resolving Ambiguities: `@Qualifier`, `@Primary` & `@Autowired`](#resolving-ambiguities-qualifier-primary--autowired)
6. [Bean Scopes & Lifecycle](#bean-scopes--lifecycle)
    - [Scopes: `singleton`, `prototype`, `request`, `session`, `application`](#scopes-singleton-prototype-request-session-application)
    - [Lifecycle Callbacks](#lifecycle-callbacks)
    - [`BeanPostProcessor` & `BeanFactoryPostProcessor`](#beanpostprocessor--beanfactorypostprocessor)
7. [Lazy & Eager Initialization](#lazy--eager-initialization)
8. [Externalized Configuration](#externalized-configuration)
    - [`application.properties` vs `application.yml`](#applicationproperties-vs-applicationyml)
    - [`@Value`](#value)
    - [`@ConfigurationProperties`](#configurationproperties)
    - [Profiles & `@Profile`](#profiles--profile)
9. [Auto-Configuration & Starters](#auto-configuration--starters)
    - [How Auto-Config Works](#how-auto-config-works)
    - [Disabling or Customizing Auto-Config](#disabling-or-customizing-auto-config)
10. [Spring MVC Web Layer](#spring-mvc-web-layer)
    - [Controllers: `@Controller` vs `@RestController`](#controllers-controller-vs-restcontroller)
    - [Mapping Requests](#mapping-requests)
    - [Path Variables, Query Params & Request Bodies](#path-variables-query-params--request-bodies)
    - [Views, Static Resources & Templates](#views-static-resources--templates)
    - [Error Handling: `@ExceptionHandler` & `@ControllerAdvice`](#error-handling-exceptionhandler--controlleradvice)
11. [Eventing & Application Events](#eventing--application-events)
12. [Asynchronous & Scheduling](#asynchronous--scheduling)
    - [`@Async`](#async)
    - [`@Scheduled`](#scheduled)
13. [Transactions & Data Access](#transactions--data-access)
    - [`@Transactional`](#transactional)
    - [Spring Data Repositories](#spring-data-repositories)
14. [Security Basics (Overview)](#security-basics-overview)
15. [Caching & Performance](#caching--performance)
    - [`@Cacheable`, `@CacheEvict`](#cacheable-cacheevict)
16. [Metrics & Actuator](#metrics--actuator)
17. [Testing Strategies](#testing-strategies)
    - [`@SpringBootTest`](#springboottest)
    - [Slice Tests (`@WebMvcTest`, `@DataJpaTest`)](#slice-tests-webmvctest-datapptest)
    - [`@MockBean`](#mockbean)
18. [Logging Best Practices](#logging-best-practices)
19. [Putting It All Together: Sample Code Annotations](#putting-it-all-together-sample-code-annotations)
20. [Next Steps & Further Reading](#next-steps--further-reading)

---

## Spring Boot Overview

- **Starter POMs** bundle common dependencies under a single artifact (`spring-boot-starter-web`, `spring-boot-starter-data-jpa`, etc.).
- **Auto-configuration** uses `spring.factories` to detect classpath libraries and configure beans automatically.
- **Embedded Server**: run as `java -jar app.jar`—no external servlet container required.
- **Opinionated Defaults**: sensible conventions reduce boilerplate.

---

## Application Startup & Bootstrapping

```java
@SpringBootApplication
public class StoreApplication {
  public static void main(String[] args) {
    SpringApplication.run(StoreApplication.class, args);
  }
}
````

* `@SpringBootApplication` = `@Configuration` + `@EnableAutoConfiguration` + `@ComponentScan`.
* `SpringApplication.run(...)`:

    1. Creates `SpringApplication` instance
    2. Sets up default `ApplicationEnvironment`
    3. Reads `application.properties` / `yml`
    4. Creates `ApplicationContext` (e.g., `AnnotationConfigServletWebServerApplicationContext`)
    5. Registers `SpringBootExceptionReporter`, `ApplicationListeners`
    6. Performs auto-configuration
    7. Refreshes context and starts embedded server
    8. Calls `CommandLineRunner` and `ApplicationRunner` beans

### CommandLineRunner & ApplicationRunner

* Implement these interfaces to run code **after** context initialization:

  ```java
  @Component
  public class StartupRunner implements CommandLineRunner {
    @Override
    public void run(String... args) { System.out.println("App started!"); }
  }
  ```

---

## The IoC Container: ApplicationContext vs BeanFactory

* **BeanFactory**:

    * Core container, lazy-loading, minimal features.
* **ApplicationContext** (extends BeanFactory):

    * Pre-instantiates singletons
    * Internationalization support
    * Event publication
    * AOP integration

**Accessing beans**:

```java
ApplicationContext ctx = SpringApplication.run(...);
MyService svc = ctx.getBean(MyService.class);
```

---

## Bean Definition & Registration

### Component Scanning

* Enabled by `@ComponentScan` (in `@SpringBootApplication`).
* Scans base package and subpackages for:

    * `@Component`
    * `@Service`
    * `@Repository`
    * `@Controller` / `@RestController`

#### Stereotypes

* `@Component`: generic
* `@Service`: business logic
* `@Repository`: DAO (exception translation)
* `@Controller`: MVC views
* `@RestController`: REST endpoints

**Example**:

```java
@Service
public class UserService { /* business methods */ }
```

---

### Manual Registration (`@Configuration` + `@Bean`)

* Use for third-party classes or conditional logic:

  ```java
  @Configuration
  public class AppConfig {
    @Bean
    public PaymentService paymentService(@Value("${payment.gateway}") String gateway) {
      if ("stripe".equals(gateway)) return new StripePaymentService();
      else return new PayPalPaymentService();
    }
  }
  ```

---

### Conditional Beans & `@Conditional`

* `@ConditionalOnProperty("feature.x.enabled")`
* `@ConditionalOnMissingBean(MyService.class)`
* `@ConditionalOnClass(name="com.example.SomeClass")`

```java
@Bean
@ConditionalOnProperty(name="app.featureX", havingValue="true")
public FeatureXService featureXService() { … }
```

---

### Meta-annotations & Custom Stereotypes

* Create custom annotations that themselves are annotated with `@Component` or `@Service`:

  ```java
  @Target(TYPE)
  @Retention(RUNTIME)
  @Service
  public @interface BusinessComponent { }
  ```

---

## Dependency Injection (DI) Strategies

### Constructor Injection (Preferred)

* Enforce required dependencies.
* Use `final` fields:

  ```java
  @Service
  public class OrderService {
    private final PaymentService ps;
    public OrderService(PaymentService ps) { this.ps = ps; }
  }
  ```

### Field & Setter Injection

* **Field**:

  ```java
  @Autowired
  private PaymentService ps;
  ```

* **Setter**:

  ```java
  private PaymentService ps;
  @Autowired
  public void setPaymentService(PaymentService ps) { this.ps = ps; }
  ```

### Resolving Ambiguities: `@Qualifier`, `@Primary` & `@Autowired`

```java
@Service @Primary
public class StripePaymentService implements PaymentService { … }

@Service @Qualifier("paypal")
public class PayPalPaymentService implements PaymentService { … }

@Autowired
public OrderService(@Qualifier("paypal") PaymentService ps) { … }
```

* `@Primary`: default when multiple candidates.
* `@Qualifier`: select by name.
* `@Autowired(required=false)`: optional dependency.

---

## Bean Scopes & Lifecycle

### Scopes

| Scope         | Description                         | Usage               |
| ------------- | ----------------------------------- | ------------------- |
| `singleton`   | One instance per context (default)  | Stateless services  |
| `prototype`   | New instance each injection/request | State-heavy objects |
| `request`     | One per HTTP request                | Web apps            |
| `session`     | One per HTTP session                | Web apps            |
| `application` | One per ServletContext              | Webapps             |

```java
@Bean
@Scope("prototype")
public TaskContext taskContext() { return new TaskContext(); }
```

### Lifecycle Callbacks

* **`@PostConstruct`**: after bean creation & DI
* **`@PreDestroy`**: before bean destruction

```java
@PostConstruct
public void init() { /* startup logic */ }

@PreDestroy
public void cleanup() { /* shutdown logic */ }
```

### `BeanPostProcessor` & `BeanFactoryPostProcessor`

* **`BeanFactoryPostProcessor`**: modify bean definitions before instantiation.
* **`BeanPostProcessor`**: intercept each bean before/after initialization.

```java
@Component
public class MyBPP implements BeanPostProcessor {
  public Object postProcessBeforeInitialization(Object bean, String name) { … }
  public Object postProcessAfterInitialization(Object bean, String name) { … }
}
```

---

## Lazy & Eager Initialization

* **`@Lazy`** on bean: defer creation until first request.
* **Global lazy**: `spring.main.lazy-initialization=true`.
* On injection point:

  ```java
  public OrderService(@Lazy PaymentService ps) { … }
  ```

---

## Externalized Configuration

### `application.properties` vs `application.yml`

```properties
server.port=8080
```

```yaml
server:
  port: 8080
```

### `@Value`

* Inject single property:

  ```java
  @Value("${app.name:MyApp}")
  private String appName;
  ```

### `@ConfigurationProperties`

* Bind groups of related properties:

  ```java
  @Component
  @ConfigurationProperties(prefix="payment.stripe")
  public class StripeProps {
    private String apiUrl;
    private boolean enabled;
    private Duration timeout;
    // getters/setters
  }
  ```

* Supports type conversion, nested objects, JSR-303 validation (`@Validated`).

### Profiles & `@Profile`

* Activate profiles via `--spring.profiles.active=dev`.
* Annotate beans for specific profiles:

  ```java
  @Profile("dev")
  @Bean
  public DevDataSource devDataSource() { … }
  ```

---

## Auto-Configuration & Starters

* **Starters**: `spring-boot-starter-web`, `-data-jpa`, `-security`, etc.

* **Auto-Config**: looks at classpath & your beans to configure defaults.

* Disable with:

  ```java
  @SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
  ```

* Write your own auto-config by adding `META-INF/spring.factories`.

---

## Spring MVC Web Layer

### Controllers: `@Controller` vs `@RestController`

* `@Controller` returns view names.
* `@RestController` returns data (JSON/XML).

### Mapping Requests

* `@RequestMapping(path="/", method=GET)`
* Shorthands: `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`.

### Path Variables, Query Params & Request Bodies

```java
@GetMapping("/users/{id}")
public ResponseEntity<User> getUser(
    @PathVariable Long id,
    @RequestParam(defaultValue="false") boolean verbose) { … }

@PostMapping("/users")
public ResponseEntity<User> createUser(@RequestBody UserDto dto) { … }
```

### Views, Static Resources & Templates

* **Static**: `/static`, `/public` for CSS/JS/images.
* **Templates**: Thymeleaf/JSP under `/templates`.
* Configure view resolver if not default.

### Error Handling: `@ExceptionHandler` & `@ControllerAdvice`

```java
@ControllerAdvice
public class GlobalErrorHandler {
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<String> handleNotFound(ResourceNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }
}
```

---

## Eventing & Application Events

* Publish events via `ApplicationEventPublisher`:

  ```java
  @Component
  public class OrderPublisher {
    @Autowired private ApplicationEventPublisher pub;
    public void publish(Order o) {
      pub.publishEvent(new OrderEvent(this, o));
    }
  }
  ```

* Listen via `@EventListener` or `ApplicationListener<T>`:

  ```java
  @Component
  public class OrderListener {
    @EventListener
    public void onOrderEvent(OrderEvent e) { … }
  }
  ```

---

## Asynchronous & Scheduling

### `@Async`

* Enable with `@EnableAsync` on a config class.
* Mark methods:

  ```java
  @Async
  public CompletableFuture<Void> process() { … }
  ```

### `@Scheduled`

* Enable with `@EnableScheduling`.
* Schedule methods:

  ```java
  @Scheduled(fixedRate = 5000)
  public void report() { System.out.println("Every 5 seconds"); }
  ```

---

## Transactions & Data Access

### `@Transactional`

* Apply at class or method level:

  ```java
  @Service
  @Transactional
  public class PaymentService { … }
  ```

* Propagation, isolation, read-only, rollback rules.

### Spring Data Repositories

* Extend `JpaRepository<T, ID>` for CRUD + paging + sorting.
* Query derivation from method names (`findByEmail`), `@Query` for custom SQL/JPQL.

---

## Security Basics (Overview)

* Add `spring-boot-starter-security`.
* Secure endpoints with HTTP Basic, form login, JWT.
* Use `@EnableWebSecurity` + extend `WebSecurityConfigurerAdapter` (or `SecurityFilterChain` bean in newer versions).

---

## Caching & Performance

### `@Cacheable`, `@CacheEvict`

* Enable with `@EnableCaching`.

* Annotate methods:

  ```java
  @Cacheable("users")
  public User getUser(Long id) { … }
  ```

* Evict cache:

  ```java
  @CacheEvict(value="users", key="#id")
  public void updateUser(Long id, User u) { … }
  ```

---

## Metrics & Actuator

* Add `spring-boot-starter-actuator`.

* Exposes endpoints (`/actuator/health`, `/metrics`, `/beans`, `/env`).

* Configure exposure in `application.properties`:

  ```properties
  management.endpoints.web.exposure.include=health,metrics,beans
  ```

* Micrometer integration for Prometheus, Datadog, etc.

---

## Testing Strategies

### `@SpringBootTest`

* Full context load.
* Good for end-to-end integration tests.

### Slice Tests (`@WebMvcTest`, `@DataJpaTest`, `@RestClientTest`)

* Load only specific layers:

  ```java
  @WebMvcTest(HomeController.class)
  public class HomeControllerTest { … }

  @DataJpaTest
  public class UserRepositoryTest { … }
  ```

### `@MockBean`

* Replace real beans with mocks in tests:

  ```java
  @MockBean
  private PaymentService ps;
  ```

---

## Logging Best Practices

* Use **SLF4J** + **Logback**.

* Inject logger:

  ```java
  private static final Logger log = LoggerFactory.getLogger(MyClass.class);
  log.debug("Detail…");
  ```

* Control levels:

  ```properties
  logging.level.org.springframework=INFO
  logging.level.com.funkybooboo=DEBUG
  ```

---

## Putting It All Together: Sample Code Annotations

Refer to each class in your project and note which of the above concepts it uses. For example:

* **`AppConfig`**: `@Configuration`, `@Bean`, `@ConditionalOnProperty`, `@Value`
* **`OrderService`**: `@Service`, constructor injection, `@PostConstruct`, `@PreDestroy`
* **`HomeController`**: `@Controller`, `@RequestMapping`, property injection
* **`HeavyResource`**: `@Service`, `@Lazy`
* **`InMemoryUserRepository`**: `@Repository`, bean scope defaults
* **`StripePaymentService` / `PayPalPaymentService`**: `@Component` or `@Service`, `@Value`

---

## Next Steps & Further Reading

* **Spring Security**: deep dive into OAuth2, JWT, role-based access.
* **Spring Data**: custom repository implementations, reactive repositories.
* **Spring Cloud**: config server, Eureka, Ribbon, Feign.
* **Reactive**: WebFlux, Reactor.
* **GraphQL**: `spring-boot-starter-graphql`.
* **Microservices Patterns**: circuit breaker with Resilience4j, distributed tracing.

> **Key References**
>
> * [Spring Boot Reference Guide](https://docs.spring.io/spring-boot/docs/current/reference/html/)
> * [Spring Framework Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/)
> * [Baeldung Spring Tutorials](https://www.baeldung.com/spring-boot)
> * [Spring by Pivotal YouTube Channel](https://www.youtube.com/spring)
