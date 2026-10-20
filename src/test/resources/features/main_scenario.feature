# language: en
@browser @main_scenario
Feature: Popüler kuponun navigasyon boyunca korunması
  Scenario: Anonim kullanıcı seçtiği kuponu farklı sayfalarda görür
    Given kullanıcı ana sayfadadır
    When Popüler Kuponlar menüsüne tıklar
    Then Popüler Kuponlar sayfası açılmalıdır
    When ilk uygun kuponun bilgileri kaydedilir
    Then seçilen kuponun etkinlik bilgileri dolu olmalıdır
    When seçilen kupon sepete eklenir
    Then kupon sepeti görüntülenmelidir
    Then sepetteki etkinlik bilgileri seçilen kuponla aynı olmalıdır
    When kullanıcı İddaa sayfasına gider
    Then açılan sayfanın yolu "/iddaa" olmalıdır
    Then sepetteki etkinlik bilgileri seçilen kuponla aynı olmalıdır
    When kullanıcı Canlı Sonuçlar sayfasına gider
    Then açılan sayfanın yolu "/iddaa/canli-skor/futbol" olmalıdır
    Then sepetteki etkinlik bilgileri seçilen kuponla aynı olmalıdır
    When kullanıcı Dakika Bahis sayfasına gider
    Then açılan sayfanın yolu "/iddaa/dakika-bahis" olmalıdır
    Then sepetteki etkinlik bilgileri seçilen kuponla aynı olmalıdır
