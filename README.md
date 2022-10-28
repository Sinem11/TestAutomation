# TestAutomation
<h3>Test Otomasyon Projesi</h3>
    <p><b>Kullanılan Dil ve Framework: </b>Java, Selenium</p>
    <b>Bilgilendirme: </b> Test çalıştırılmadan önce kod içerisinde testWithLogin metodunda **** olarak belirtilen userEmail, userPassword, userName değişkenlerine uygun değerler yazılmalıdır."keyword " değişkene istenilen ürün yazılarak farklı ürünler için test edilebilir.
    <h4>Test Adımları</h4>
    <h5>1. Kullanıcı girişi yapılarak sepete ürün eklenmesi</h5>
    <ul type="square">
        <li>Kullanıcı Hepsiburada.com sitesini ziyaret eder.</li>
        <li>Kullanıcı giriş işlemi yapılır.</li>
        <li>Yönlendirmeden sonra anasayfada kullanıcı giriş işleminin yapıldığı doğrulanır</li>
        <li>Kullanıcı, burada satın almak istediği ürün için arama yapacaktır.</li>
        <li>Kullanıcı, Arama sonucunda ekrana gelen ürün listesinden (veya tek bir sonuç da dönmüş olabilir) ürün seçer.</li>
        <li>Seçilen ürün için 2 tane farklı satıcıdan ürün seçilip sepete eklenir.</li>
        <li>Seçilen ürünün doğru olarak eklendiği ‘Sepetim’ sayfasında doğrulanmalıdır.</li>
    </ul>
   <h5>2. Kullanıcı girişi yapılmadan belirtilen ürünü sepete ekleme</h5>
    <ul type="square">
        <li>Kullanıcı Hepsiburada.com sitesini ziyaret eder.</li>
      <li>Kullanıcı, Arama sonucunda ekrana gelen ürün listesinden (veya tek bir sonuç da dönmüş olabilir) ürün seçer.</li>
      <li>Seçilen ürün için 2 tane farklı satıcıdan ürün seçilip sepete eklenir.</li>
      <li>Seçilen ürünün doğru olarak eklendiği ‘Sepetim’ sayfasında doğrulanmalıdır.</li>
        </ul>
