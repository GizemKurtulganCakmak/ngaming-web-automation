package com.gizemcakmak.automation.model;

import java.util.List;

public record CouponSnapshot(List<CouponEvent> events) {
    public CouponSnapshot {
        events = List.copyOf(events);
        if (events.isEmpty()) throw new IllegalArgumentException("Boş kupon");
    }
}

