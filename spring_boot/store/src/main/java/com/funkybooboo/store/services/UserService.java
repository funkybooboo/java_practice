package com.funkybooboo.store.services;

import com.funkybooboo.store.entities.Address;
import com.funkybooboo.store.entities.User;
import com.funkybooboo.store.repositories.AddressRepository;
import com.funkybooboo.store.repositories.ProfileRepository;
import com.funkybooboo.store.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final AddressRepository addressRepository;
    private final EntityManager entityManager;
    
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
        var user = userRepository.findById(2L).orElseThrow();
        System.out.println(user);

        var profile = profileRepository.findById(2L).orElseThrow();
        System.out.println(profile);
        System.out.println(profile.getUser());
    }
    
    public void fetchAddress() {
        var address = addressRepository.findById(1L).orElseThrow();
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
}
