# language: en

@browser @free_scenario2
Feature: Popular Coupons - tabs control

  Background:
    Given kullanıcı ana sayfadadır
    When Popüler Kuponlar menüsüne tıklar
    Then Popüler Kuponlar sayfası açılmalıdır

  @tabTest
  Scenario: Switch between popular coupon tabs
    When kullanıcı "Kazananlar" sekmesine geçer
    Then "Kazananlar" sekmesi aktif olmalıdır
    When kullanıcı "Hemen Oyna" sekmesine geçer
    Then "Hemen Oyna" sekmesi aktif olmalıdır