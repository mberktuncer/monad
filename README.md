## Proje Genel Bakış

Bu proje, çalışan bilgilerinin yönetimini sağlayan bir web uygulamasıdır. Vaadin framework'ü ve Spring Boot kullanılarak geliştirilmiştir. Sistem, çalışanların eklenmesi, listelenmesi, güncellenmesi ve silinmesi (soft-delete) işlemlerini içermektedir.

## Kullanılan Teknolojiler

- Java 21
- Spring Boot 3.2.2
- Vaadin 24.3.3
- H2 Database
- JPA/Hibernate
- Lombok
- Maven

## Sistem Mimarisi

Proje, katmanlı mimari (Layered Architecture) prensiplerine uygun olarak geliştirilmiştir:

1. **Presentation Layer (UI)**: Vaadin view'ları
2. **Service Layer**: İş mantığı işlemleri
3. **Repository Layer**: Veritabanı işlemleri
4. **Entity Layer**: Veri modelleri

## EmployeeView Detaylı İnceleme

EmployeeView, sistemin ana sayfasıdır ve aşağıdaki özellikleri içermektedir:

### 1. Çalışan Listeleme
- Grid yapısı ile çalışanların görüntülenmesi
- Sütunlar: Ad, Soyad (TC kimlik no gizlilik nedeniyle sütunda yer almamaktadır)
- Sıralanabilir grid kolonları

### 2. Arama Özelliği
- İsme göre anlık arama (real-time search)
- Case-insensitive arama
- Lazy loading desteği
- Sadece aktif çalışanlar arasında arama (Soft delete yapılan çalışanlar gösterilmez)

### 3. Yeni Çalışan Ekleme
- Dialog tabanlı form yapısı
- Validasyon kontrolleri:
  - TC Kimlik No kontrolü (11 hane)
  - Zorunlu alan kontrolleri
  - Mükerrer kayıt kontrolü
- Hata mesajları ve başarılı işlem bildirimleri

### 4. Çalışan Güncelleme
- Grid üzerinde çalışana çift tıklama ile güncelleme dialog'u açılır
- Dialog içeriği:
  - Ad güncelleme alanı
  - Soyad güncelleme alanı
  - TC Kimlik No değiştirilemez
- Validasyon kontrolleri:
  - İsim ve soyisim zorunluluğu
  - Minimum karakter kontrolü
- Başarılı güncelleme sonrası bildirim
- İptal butonu ile dialog kapatma özelliği

### 5. Soft-Delete İşlemi
- Çalışanlar fiziksel olarak silinmez
- Silinen çalışanlar veritabanında tutulur
- Silme işlemi öncesi onay dialog'u
- Geri alınabilir silme işlemi

## Dummy Veri Yönetimi

Sistem ilk çalıştığında örnek veriler DataInitializationConfiguration sınıfı tarafından otomatik olarak yüklenir:

- `@PostConstruct` annotation'ı ile uygulama başlatıldığında çalışır
- `employees.txt` dosyasından verileri okur
- CSV formatındaki verileri parse eder (TC No, Ad, Soyad)
- Her bir çalışan için yeni Employee nesnesi oluşturur
- Oluşturulan çalışanları veritabanına kaydeder
- Varsayılan olarak tüm çalışanlar aktif (status=1) olarak eklenir

## Önemli Özellikler

### Soft-Delete Mekanizması
- Veri kaybını önler
- Geçmiş kayıtların korunmasını sağlar
- Raporlama için veri bütünlüğü
- İstatistiksel analizler için veri saklama

### Validation Sistemi
- TC Kimlik No format kontrolü
- Zorunlu alan kontrolleri
- Özel hata mesajları

### Notification Sistemi
- İşlem sonuçları için bildirimler
- Hata mesajları
- Başarılı işlem bildirimleri
- Onay mesajları