package com.gizemcakmak.automation.context;

import com.gizemcakmak.automation.model.CouponSnapshot;

public class ScenarioContext
{
    private String selectedCouponId;
    private CouponSnapshot selectedCoupon;
    public String getSelectedCouponId() { return selectedCouponId; }
    public void setSelectedCouponId(String value) { selectedCouponId = value; }
    public CouponSnapshot getSelectedCoupon() {
        if (selectedCoupon == null) throw new IllegalStateException("Kupon henüz seçilmedi");
        return selectedCoupon;
    }
    public void setSelectedCoupon(CouponSnapshot value) { selectedCoupon = value; }
}
