package com.funkybooboo.store.services;

import com.funkybooboo.store.entities.*;
import com.funkybooboo.store.repositories.*;
import com.funkybooboo.store.repositories.specifications.ProductSpec;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@AllArgsConstructor // Lombok generates a constructor with all final fields injected (ideal for dependency injection)
@Service // Marks this class as a Spring-managed service component (bean)
public class UserService {
    // These are injected automatically via constructor thanks to @AllArgsConstructor
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final AddressRepository addressRepository;
    private final EntityManager entityManager; // Low-level JPA API for entity state inspection and control
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    /**
     * Demonstrates how JPA entities change their state:
     * - Transient: new object not managed by persistence context
     * - Persistent: managed by EntityManager (tracked for DB sync)
     * - Detached: managed, then disconnected
     */
    @Transactional
    public void showEntityStates() {
        var user = User.builder()
                .name("Nate Stott")
                .email("nate.stott@pm.me")
                .password("password")
                .build();

        if (entityManager.contains(user)) {
            System.out.println("Persistent");
        } else {
            System.out.println("Transient / Detached");
        }

        // save() causes it to become managed (persistent)
        userRepository.save(user);

        if (entityManager.contains(user)) {
            System.out.println("Persistent");
        } else {
            System.out.println("Transient / Detached");
        }
    }

    /**
     * Loads related entities and shows navigation between User and Profile.
     */
    @Transactional
    public void showRelatedEntities() {
        var user = userRepository.findById(8L).orElseThrow();
        System.out.println(user);

        var profile = profileRepository.findById(8L).orElseThrow();
        System.out.println(profile);
        System.out.println(profile.getUser()); // Lazy/eager access depends on mapping
    }

    /**
     * Simple entity retrieval example.
     */
    public void fetchAddress() {
        var address = addressRepository.findById(8L).orElseThrow();
        System.out.println(address);
    }

    /**
     * Shows how to persist an entity graph (User and Address) using relationship methods.
     */
    public void persistRelated() {
        var user = User.builder()
                .name("John Doe")
                .email("john@email.com")
                .password("password")
                .build();

        var address = Address.builder()
                .state("TX")
                .street("124 asf f")
                .zip("231435")
                .city("asdf")
                .build();

        user.addAddress(address); // establishes bidirectional relationship
        userRepository.save(user); // cascade may be needed depending on mapping
    }

    /**
     * Demonstrates deletion of related entity (address from user).
     */
    @Transactional
    public void deleteRelated() {
        userRepository.deleteById(2L); // delete by ID

        var user = userRepository.findById(17L).orElseThrow();
        var address = user.getAddresses().getFirst();
        user.removeAddress(address); // remove from collection (may not delete unless cascade set)
        userRepository.save(user);
    }

    /**
     * Full CRUD + relationship usage for products and categories.
     */
    @Transactional
    public void managingProducts() {
        // Create category + product (1)
        var category = Category.builder().name("asdf").build();
        var product1 = Product.builder().price(new BigDecimal(124)).description("asdf 1").name("asdf 1").category(category).build();
        productRepository.save(product1);

        // Associate existing category to new product (2)
        categoryRepository.findById((byte)1).orElseThrow(); // not used?
        var product2 = Product.builder().price(new BigDecimal(124)).description("asdf 2").name("asdf 2").category(category).build();
        productRepository.save(product2);

        // Add products to user's wishlist (3)
        var user = userRepository.findById(3L).orElseThrow();
        var products = productRepository.findAll();
        products.forEach(user::addWishlist); // assumes User has a wishlist relation
        userRepository.save(user);

        // Delete product (4)
        productRepository.deleteById(4L);
    }

    /**
     * Custom repository update method using JPQL or @Modifying.
     */
    @Transactional
    public void updateProductPrices() {
        productRepository.updatePriceByCategory(BigDecimal.valueOf(10), (byte)1);
    }

    /**
     * Demonstrates dynamic query building using Example/ExampleMatcher (Query By Example).
     */
    @Transactional
    public void fetchProducts() {
        var product = new Product();
        product.setName("product");

        var matcher = ExampleMatcher.matching()
                .withIncludeNullValues()
                .withIgnorePaths("id", "description")
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        var example = Example.of(product, matcher);
        var products = productRepository.findAll(example);
        products.forEach(System.out::println);
    }

    /**
     * Example of using a custom native or JPQL query with parameters.
     */
    public void fetchProductsByCriteria() {
        var products = productRepository.findProductsByCriteria("prod", BigDecimal.valueOf(1), BigDecimal.valueOf(10));
        products.forEach(System.out::println);
    }

    /**
     * Uses Spring Data Specifications to build dynamic, composable queries.
     */
    public void fetchProductsBySpecifications(String name, BigDecimal minPrice, BigDecimal maxPrice) {
        Specification<Product> spec = Specification.where(null);

        if (name != null) {
            spec = spec.and(ProductSpec.hasName(name));
        }
        if (minPrice != null) {
            spec = spec.and(ProductSpec.hasPriceGreaterThanOrEqualTo(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductSpec.hasPriceLessThanOrEqualTo(maxPrice));
        }

        productRepository.findAll(spec).forEach(System.out::println);
    }

    /**
     * Retrieve a single user by unique field (email).
     */
    @Transactional
    public void fetchUser() {
        var user = userRepository.findByEmail("nate.stott@pm.me").orElseThrow();
        System.out.println(user);
    }

    /**
     * Retrieve all users with their addresses using a custom join fetch.
     */
    @Transactional
    public void fetchUsers() {
        var users = userRepository.findAllWithAddresses();
        users.forEach(u -> {
            System.out.println(u);
            u.getAddresses().forEach(System.out::println);
        });
    }

    /**
     * Demonstrates sorting results by multiple fields.
     */
    public void fetchSortedProducts() {
        var sort = Sort.by("name").and(Sort.by("price").descending());
        productRepository.findAll(sort).forEach(System.out::println);
    }

    /**
     * Demonstrates Spring Data JPA pagination (pages are 0-based).
     */
    public void fetchPaginatedProducts(int pageNumber, int size) {
        PageRequest pageRequest = PageRequest.of(pageNumber, size);
        Page<Product> page = productRepository.findAll(pageRequest);

        var products = page.getContent();
        products.forEach(System.out::println);

        var totalPages = page.getTotalPages();
        var totalElements = page.getTotalElements();

        System.out.println("Total Pages: " + totalPages);
        System.out.println("Total Elements: " + totalElements);
    }

    /**
     * Retrieve users considered "loyal" by custom repository method.
     */
    @Transactional
    public void printLoyalProfiles() {
        var users = userRepository.findLoyalUsers(2); // e.g. users with >= 2 purchases
        users.forEach(p -> System.out.println(p.getId() + ":" + p.getEmail()));
    }
}
