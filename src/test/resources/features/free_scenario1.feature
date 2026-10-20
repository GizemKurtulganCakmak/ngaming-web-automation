# language: en

@browser @free_scenario1
Feature: Popular Coupons - filter

  Background:
    Given kullanıcı ana sayfadadır
    When Popüler Kuponlar menüsüne tıklar
    Then Popüler Kuponlar sayfası açılmalıdır

  @filterTest
  Scenario: Filter coupons by two matches
    When kullanıcı "2 Maç" filtresini seçer
    Then listelenen kuponların her biri 2 etkinlik içermelidir

