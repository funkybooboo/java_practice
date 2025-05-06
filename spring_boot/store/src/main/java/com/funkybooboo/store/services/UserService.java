package com.funkybooboo.store.services;

import com.funkybooboo.store.entities.*;
import com.funkybooboo.store.repositories.*;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final AddressRepository addressRepository;
    private final EntityManager entityManager;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public void showEntityStates() {
        var user = User.builder()
                .name("Nate Stott")
                .email("nate.stott@pm.me")
                .password("password")
                .build();
        
        if (entityManager.contains(user)) {
            System.out.println("Persistent");
        }
        else {
            System.out.println("Transient / Detached");
        }
        
        userRepository.save(user);

        if (entityManager.contains(user)) {
            System.out.println("Persistent");
        }
        else {
            System.out.println("Transient / Detached");
        }
    }
    
    @Transactional
    public void showRelatedEntities() {
        var user = userRepository.findById(8L).orElseThrow();
        System.out.println(user);

        var profile = profileRepository.findById(8L).orElseThrow();
        System.out.println(profile);
        System.out.println(profile.getUser());
    }
    
    public void fetchAddress() {
        var address = addressRepository.findById(8L).orElseThrow();
        System.out.println(address);
    }
    
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
        
        user.addAddress(address);
        
        userRepository.save(user);
    }
    
    @Transactional
    public void deleteRelated() {
        userRepository.deleteById(2L);
        
        var user = userRepository.findById(17L).orElseThrow();
        var address = user.getAddresses().getFirst();
        user.removeAddress(address);
        userRepository.save(user);
    }
    
    @Transactional
    public void managingProducts() {
        // 1
        var category = Category.builder().name("asdf").build();
        var product1 = Product.builder().price(new BigDecimal(124)).description("asdf 1").name("asdf 1").category(category).build();
        
        productRepository.save(product1);
        
        // 2
        categoryRepository.findById((byte)1).orElseThrow();
        var product2 = Product.builder().price(new BigDecimal(124)).description("asdf 2").name("asdf 2").category(category).build();

        productRepository.save(product2);
        
        // 3
        var user = userRepository.findById(3L).orElseThrow();
        var products = productRepository.findAll();
        products.forEach(user::addWishlist);
        userRepository.save(user);
        
        // 4
        productRepository.deleteById(4L);
    }
    
    @Transactional
    public void updateProductPrices() {
        productRepository.updatePriceByCategory(BigDecimal.valueOf(10), (byte)1);
    }
    
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
    
    public void fetchProductsByCriteria() {
        var products = productRepository.findProductsByCriteria("prod", BigDecimal.valueOf(1), BigDecimal.valueOf(10));
        products.forEach(System.out::println);
    }
    
    @Transactional
    public void fetchUser() {
        var user = userRepository.findByEmail("nate.stott@pm.me").orElseThrow();
        System.out.println(user);
    }

    @Transactional
    public void fetchUsers() {
        var users = userRepository.findAllWithAddresses();
        users.forEach(u -> {
            System.out.println(u);
            u.getAddresses().forEach(System.out::println);
        });
    }
    
    @Transactional
    public void printLoyalProfiles() {
        var users = userRepository.findLoyalUsers(2);
        users.forEach(p -> System.out.println(p.getId() + ":" + p.getEmail()));
    }
}
