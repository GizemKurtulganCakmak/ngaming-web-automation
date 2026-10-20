NGaming Web UI Automation
- Java, Selenium WebDriver, Cucumber ve JUnit 5 kullanılarak geliştirilmiş web arayüzü test otomasyon projesidir.
- Test edilen uygulama: https://www.nesine.com

Projenin Amacı:
- Bu proje, Popüler Kuponlar üzerinden seçilen bir kuponun sepete aktarılmasını ve farklı sayfalara geçildiğinde sepet içeriğinin korunmasını doğrulamak amacıyla geliştirilmiştir.
- Testler Page Object Model (POM) mimarisiyle yapılandırılmıştır.

Kullanılan Teknolojiler:
- Java 21	Programlama dili
- Maven	Bağımlılık ve proje yönetimi
- Selenium WebDriver 4	Tarayıcı otomasyonu
- Cucumber 7	BDD senaryoları
- JUnit 5 / JUnit Platform	Test yürütme
- PicoContainer	Senaryo bazlı dependency injection
- Allure Report	Test sonuçlarının raporlanması
- Chrome / ChromeDriver	Test tarayıcısı

Mimari Yaklaşım:
1. Page Object Model
- Sayfa elementleri ve kullanıcı etkileşimleri Page Object sınıflarında yönetilir.
- Bu yaklaşım test adımlarının okunabilirliğini artırır, kod tekrarını azaltır ve locator değişikliklerini merkezi olarak yönetmeyi sağlar.
2. WebDriver Yönetimi
- DriverFactory, WebDriver örneklerini ThreadLocal<WebDriver> yapısında saklar.
- Her tarayıcı senaryosu için ayrı Chrome oturumu oluşturulur. Test tamamlandığında WebDriver kapatılır ve ThreadLocal temizlenir.
3. PicoContainer ve ScenarioContext
- PicoContainer, aynı senaryoya ait Step Definition sınıflarının ortak ScenarioContext nesnesini kullanmasını sağlar.
- Seçilen kuponun kimliği ve etkinlik bilgileri senaryo kapsamında saklanır. Böylece paralel çalışan farklı senaryoların verileri birbirine karışmaz.
4. Hooks
- Cucumber Hooks kullanılarak tarayıcı yaşam döngüsü yönetilir.
- @Before("@browser"): WebDriver oluşturulur.
- @After("@browser"): Başarısız senaryolarda ekran görüntüsü alınır ve tarayıcı kapatılır.

Ana E2E Test Senaryosu:
- Ana senaryo aşağıdaki kullanıcı yolculuğunu kapsar:
1. Nesine ana sayfasına git.
2. Popüler Kuponlar sayfasını aç.
3. Uygun bir kupon seç ve etkinlik bilgilerini kaydet.
4. Kuponu sepete ekle.
5. Sepetteki etkinlik isimleri, tarihleri ve sayısını karşılaştır.
6. İddaa sayfasına git ve sepeti doğrula.
7. Canlı Sonuçlar sayfasına git ve sepeti doğrula.
8. Dakika Bahis sayfasına git ve sepeti doğrula.
- Etkinlik isimleri ve tarihleri canlı verilerden okunur. Sabit maç isimleri üzerinden doğrulama yapılmaz.
- Testler anonim tarayıcı oturumunda gerçekleştirilir. Gerçek bahis onayı veya ödeme işlemleri otomasyon kapsamı dışındadır.

Kurulum:
1. Gereksinimler:
- JDK 21
- Maven 3.9 veya üzeri
- Google Chrome
- IntelliJ IDEA veya uyumlu bir Java IDE
- Allure CLI (raporu görüntülemek için)

2. Java ve Maven sürümlerini kontrol etmek için:
- java -version
- mvn -version

3. Bağımlılıkları derlemek için:
- mvn clean test-compile

Testlerin Çalıştırılması:
1. Tüm testleri çalıştırmak için:
- mvn clean test
2. Yalnızca ana E2E senaryosunu çalıştırmak için:
- mvn clean test '-Dcucumber.filter.tags=@mainE2E'
3. Yalnızca Popüler Kuponlar senaryosunu çalıştırmak için:
- mvn clean test '-Dcucumber.filter.tags=@popularCoupons'
4. Paralel doğrulama senaryolarını çalıştırmak için:
- mvn clean test '-Dcucumber.filter.tags=@parallelValidation'
5. Paralelliği geçici olarak kapatmak için:
- mvn clean test \
'-Dcucumber.filter.tags=@mainE2E' \
'-Dcucumber.execution.parallel.enabled=false'

Not: Test etiketleri mevcut .feature dosyalarındaki etiketlerle eşleşmelidir. BUILD SUCCESS tek başına yeterli değildir; gerçekten çalıştırılan senaryo sayısı ve adım sonuçları kontrol edilmelidir.

Paralel Test Yürütme:
- Cucumber JUnit Platform üzerinden paralel senaryo çalıştırma desteklenir.
src/test/resources/junit-platform.properties:

cucumber.execution.parallel.enabled=true
cucumber.execution.parallel.config.strategy=fixed
cucumber.execution.parallel.config.fixed.parallelism=2
cucumber.execution.execution-mode.feature=concurrent

- Her senaryo bağımsız WebDriver oturumu ve senaryo verisi kullanır.
- Ana E2E içindeki adımlar sıralı çalışır. Paralellik bağımsız senaryolar arasında uygulanır.

Allure Raporlama:
- Allure test sonuçlarını senaryo, adım, süre, hata ve ekran görüntüsü bilgileriyle raporlar.
Allure CLI kurulumu:
- brew install allure
Kurulumu doğrulamak için:
- allure --version
Test sonuçları:
- target/allure-results/
Raporu tarayıcıda açmak için:
- allure serve target/allure-results
HTML raporu üretmek için:
- allure generate target/allure-results \
-o target/allure-report --clean
Üretilen raporu görüntülemek için:
- allure open target/allure-report

Dinamik Sayfa Yönetimi:
- Web uygulamasında bazı bileşenler her test çalışmasında görünmeyebilir.
- Bunlara örnek olarak çerez bildirimi, tutorial popup’ı ve açılır kupon sepeti verilebilir.
- Bu bileşenler Selenium Explicit Wait ve koşullu görünürlük kontrolleriyle yönetilir.
- Sepet kapalıysa header üzerindeki kupon alanından yeniden açılır. Sepetin etkinlik isimleri ve tarihleri tekrar doğrulanır.

Bilinen Sınırlamalar:
- Popüler kupon içerikleri canlı olarak değişebilir.
- Bazı elementlerin locator’ları uygulama güncellemeleriyle değişebilir.
- Bugün ve Yarın gibi tarih gösterimleri normalizasyon gerektirebilir.
- Tutorial ve çerez popup’ları farklı zamanlarda görünebilir.
 -Navigasyon sonrasında sepet kapalı durumda olabilir.
- Canlı ortam verileri nedeniyle testlerin son koşum sonuçları ayrıca doğrulanmalıdır.


Hazırlayan: Gizem Kurtulgan Çakmak

Proje: NGaming Web UI Automation Case Study