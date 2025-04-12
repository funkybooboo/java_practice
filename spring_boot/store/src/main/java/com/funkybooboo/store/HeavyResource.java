package com.funkybooboo.store;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Lazy
public class HeavyResource {
    public HeavyResource() {
        System.out.println("HeavyResource Created");
    }
}
