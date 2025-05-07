# Springboot Tutorial

## 1. Spring Boot Application Bootstrap

* **`@SpringBootApplication`**

    * Combines `@Configuration`, `@EnableAutoConfiguration`, `@ComponentScan`
    * Scans your package (and subpackages) for beans
    * Auto-configures DataSource, JPA, transactions, etc.

* **`SpringApplication.run(...)`**

    * Starts the **ApplicationContext** (IoC container)
    * Creates/initializes all beans
    * Sets up `EntityManagerFactory` & `PlatformTransactionManager`

* **Retrieving beans manually** (rare in real apps)

  ```java
  ConfigurableApplicationContext ctx = SpringApplication.run(...);
  UserService svc = ctx.getBean(UserService.class);
  ctx.close();
  ```

---

## 2. Beans & Dependency Injection

* **Bean** = any object managed by Spring
* **Stereotypes**

    * `@Service` → business logic layer
    * `@Repository` → data-access layer + exception translation
    * `@Component` → generic Spring bean
* **Constructor Injection**

    * Preferred over field/setter injection
    * Enforces immutability of dependencies
    * Lombok’s `@AllArgsConstructor` auto-generates the constructor

---

## 3. Persistence Context & Transactions

* **`@Transactional`**

    * Begins a DB transaction on method entry
    * Commits on success, rolls back on unchecked exception
    * Allows **lazy loading** within the method scope

* **Entity States**

    * **Transient**: new, not in DB or context
    * **Persistent**: managed by `EntityManager`
    * **Detached**: disconnected from context (after `close()` or outside TX)
    * **Removed**: scheduled for deletion on flush/commit

* **EntityManager.contains(entity)**

    * `true` if entity is in the current persistence context
    * Example: before/after `save()` to show transient→persistent

---

## 4. Defining Entities & Columns

* **`@Entity`** + **`@Table(name="…")`**
* **Primary Key**

    * `@Id` + `@GeneratedValue(strategy = IDENTITY)`
    * Other strategies: `SEQUENCE`, `TABLE`, `AUTO`
* **`@Column` attributes**

    * `name`, `nullable`, `unique`, `length`, `precision/scale` (for decimals)

---

## 5. Modeling Relationships

### 5.1 One-to-Many / Many-to-One

* **Inverse side** (`User.addresses`)

  ```java
  @OneToMany(
    mappedBy="user",
    cascade={PERSIST, REMOVE},
    orphanRemoval=true,
    fetch=LAZY
  )
  private List<Address> addresses;
  ```
* **Owning side** (`Address.user`)

  ```java
  @ManyToOne(fetch=LAZY)
  @JoinColumn(name="user_id")
  private User user;
  ```
* **Key points**

    * `mappedBy` points to owning field
    * `cascade=PERSIST` auto-saves new addresses with user
    * `orphanRemoval=true` deletes DB row when removed from list
    * `LAZY` avoids fetching addresses until accessed

### 5.2 Many-to-Many

* **Owning side** (`User.tags`)

  ```java
  @ManyToMany(fetch=EAGER)
  @JoinTable(
    name="user_tags",
    joinColumns=@JoinColumn(name="user_id"),
    inverseJoinColumns=@JoinColumn(name="tag_id")
  )
  private Set<Tag> tags;
  ```
* **Inverse side** (`Tag.users`)

  ```java
  @ManyToMany(mappedBy="tags")
  private Set<User> users;
  ```
* **Key points**

    * Join table holds two FKs
    * EAGER fetch pulls tags with user (use carefully!)

### 5.3 One-to-One with Shared PK

* **Profile ↔ User**

  ```java
  @OneToOne(fetch=LAZY)
  @JoinColumn(name="id")  // FK = PK
  @MapsId                 // share primary key with User
  private User user;
  ```
* **Key points**

    * `@MapsId` tells JPA: `profiles.id == users.id`
    * Ideal for truly one-to-one, shared identity

---

## 6. Cascade Types & Orphan Removal

* **Cascade options**

    * `PERSIST` → save child when parent saved
    * `MERGE` → merge child when parent merged
    * `REMOVE` → delete child when parent deleted
    * `REFRESH` → refresh child when parent refreshed
    * `DETACH` → detach child when parent detached
    * `ALL` → all of the above

* **`orphanRemoval=true`**

    * Removing child from collection → deletes it from DB
    * Works like `REMOVE` but only on “orphaned” children

---

## 7. Fetch Strategies

* **LAZY** (default for collections)

    * Improves performance by delaying load
    * Requires open transaction or fetch-override

* **EAGER** (default for single-valued)

    * Immediately fetches related entity
    * Can cause N+1 queries if not careful

* **Override per-query** with `@EntityGraph` (see Repositories)

---

## 8. Spring Data Repositories

### 8.1 Core Interfaces

* **CrudRepository\<T,ID>**

    * Basic CRUD: `save()`, `findById()`, `findAll()`, `deleteById()`
* **JpaRepository\<T,ID>** *(extends Crud + PagingAndSorting)*

    * Adds `findAll(Pageable)`, `findAll(Sort)`, `flush()`, etc.
* **JpaSpecificationExecutor<T>**

    * Enables `findAll(Specification<T>)` for dynamic filters

### 8.2 Derived Query Methods

* **Naming conventions**

    * `findBy…`, `readBy…`, `getBy…`
    * Keywords: `And`, `Or`, `Between`, `LessThan`, `GreaterThan`, `Like`, `OrderBy`, `Distinct`, `Top`, `First`, etc.
* **Examples**

  ```java
  List<Product> findByName(String name);
  List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);
  List<Product> findTop5ByNameOrderByPriceDesc(String name);
  ```

### 8.3 `@Query` (JPQL & Native)

* **JPQL**

  ```java
  @Query("select p from Product p where p.price between :min and :max")
  List<Product> findProductsJPQL(@Param("min")BigDecimal min,@Param("max")BigDecimal max);
  ```
* **Native**

  ```java
  @Query(
    value="select * from products p where p.price between :min and :max",
    nativeQuery=true
  )
  List<Product> findProductsSql(@Param("min")BigDecimal min,@Param("max")BigDecimal max);
  ```

### 8.4 Modifying & Stored Procedures

* **`@Modifying`**

    * For `UPDATE` / `DELETE` JPQL queries
    * Must call inside `@Transactional`

  ```java
  @Modifying
  @Query("update Product p set p.price = :newPrice where p.category.id = :catId")
  void updatePriceByCategory(BigDecimal newPrice, Byte catId);
  ```
* **`@Procedure`**

    * Maps to a named stored procedure

  ```java
  @Procedure("findProductsByPrice")
  List<Product> findProducts(BigDecimal min, BigDecimal max);
  ```

### 8.5 Entity Graphs

* **Purpose**: override fetch strategy per-query
* **Usage**:

  ```java
  @EntityGraph(attributePaths={"tags","addresses"})
  Optional<User> findByEmail(String email);
  ```
* **Benefit**: solves N+1 without fetch-join in JPQL

---

## 9. Paging, Sorting & Query-by-Example

### 9.1 Paging & Sorting

* **Sorting**:

  ```java
  Sort sort = Sort.by("name").ascending().and(Sort.by("price").descending());
  productRepo.findAll(sort);
  ```
* **Pagination**:

  ```java
  PageRequest pr = PageRequest.of(page, size);
  Page<Product> page = productRepo.findAll(pr);
  List<Product> content = page.getContent();
  int totalPages = page.getTotalPages();
  long totalElements = page.getTotalElements();
  ```

### 9.2 Query-by-Example (QBE)

* **Probe object** sets example values
* **ExampleMatcher** configures matching rules

  ```java
  Product probe = new Product();
  probe.setName("prod");

  ExampleMatcher matcher = ExampleMatcher.matching()
    .withIgnorePaths("id","description")
    .withStringMatcher(CONTAINING)
    .withIgnoreCase();

  Example<Product> example = Example.of(probe, matcher);
  List<Product> matches = productRepo.findAll(example);
  ```

---

## 10. Criteria API & Custom Repositories

### 10.1 Why Criteria API?

* **Dynamic** queries built programmatically
* **Type-safe** (no string JPQL)
* **IDE-refactorable**

### 10.2 Building a Criteria Query

```java
CriteriaBuilder cb = em.getCriteriaBuilder();
CriteriaQuery<Product> cq = cb.createQuery(Product.class);
Root<Product> root = cq.from(Product.class);
List<Predicate> predicates = new ArrayList<>();

if (name != null)
  predicates.add(cb.like(root.get("name"), "%" + name + "%"));
if (minPrice != null)
  predicates.add(cb.greaterThanOrEqualTo(root.get("price"), minPrice));
if (maxPrice != null)
  predicates.add(cb.lessThanOrEqualTo(root.get("price"), maxPrice));

cq.select(root).where(predicates.toArray(new Predicate[0]));
List<Product> result = em.createQuery(cq).getResultList();
```

### 10.3 Wiring a Custom Repository

* **Interface**:

  ```java
  public interface ProductCriteriaRepository {
    List<Product> findProductsByCriteria(String name,BigDecimal min,BigDecimal max);
  }
  ```
* **Implementation** (`Impl` suffix required):

  ```java
  @Repository
  public class ProductCriteriaRepositoryImpl implements ProductCriteriaRepository {
    @PersistenceContext private EntityManager em;
    // …Criteria code…
  }
  ```
* **Extend** main repo:

  ```java
  public interface ProductRepository
    extends JpaRepository<Product,Long>,
            ProductCriteriaRepository,
            JpaSpecificationExecutor<Product> { … }
  ```

---

## 11. Specifications: Composable Filters

* **Specification<T>** = predicate builder

  ```java
  public static Specification<Product> hasName(String name) {
    return (root,query,cb) -> cb.like(root.get("name"), "%" + name + "%");
  }
  ```
* **Combine**:

  ```java
  Specification<Product> spec = Specification.where(null);
  if (name != null) spec = spec.and(ProductSpec.hasName(name));
  if (min != null)  spec = spec.and(ProductSpec.hasPriceGreaterThanOrEqualTo(min));
  List<Product> list = productRepo.findAll(spec);
  ```

---

## 12. Projections: DTOs & Interfaces

### 12.1 DTO Projections

* **Constructor expression** in JPQL:

  ```java
  @Query("select new com...ProductSummaryDTO(p.id,p.name) from Product p")
  List<ProductSummaryDTO> findSummary();
  ```
* **DTO class**:

  ```java
  @Getter @AllArgsConstructor
  public class ProductSummaryDTO { Long id; String name; }
  ```

### 12.2 Interface Projections

* **Interface**:

  ```java
  public interface UserSummary { Long getId(); String getEmail(); }
  ```
* **Repository**:

  ```java
  @Query("select u.id as id,u.email as email from User u")
  List<UserSummary> findLoyalUsers(int pts);
  ```

---

## 13. Service Layer Patterns

* **`@Service`** + **`@Transactional`**
* Keeps **business logic** out of controllers
* Coordinates multiple repositories & EntityManager
* Demonstrates entity-state (`EntityManager.contains`)
* Manages relationships via **helper methods** (`addAddress`, `removeTag`, etc.)

---

## 14. Best Practices & Tips

* **Use LAZY** for large collections; override with EntityGraph or fetch-join.
* **Keep entities Skinny**: mapping + helper methods only.
* **Use DTOs/Projections** for API responses to avoid lazy pitfalls.
* **Validate inputs** with Bean Validation (`@NotNull`, `@Size`, etc.).
* **Test**:

    * Unit test repos with H2 & `@DataJpaTest`
    * Service tests with mocks (Mockito)
* **Monitor** SQL logs (`spring.jpa.show-sql=true`) to catch N+1 early.
* **Manage transactions** at service layer only.
* **Consistent naming**: custom repo impl must end with `Impl`.
