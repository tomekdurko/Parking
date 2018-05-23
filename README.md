# Parking
Aplikacja REST z wykorzystaniem javy do obsługi parkingu miejskiego
<h2>Technologie</h2>
<ul>
  <li>Spring MVC</li>
<li>Maven</li>
  </ul>
<h2>Operacje CRUD</h2>

<code>GET  /parking/{id}</code> pobieranie szczegółów jednej transakcji parkowania o podanym id <br />
<code>POST /parking/start</code> rozpoczęcie parkowania <br />
<code>PUT  /parking/stop/{id}</code> koniec parkowania samochodu o podanym id <br />
<code>GET  /parking/payment/{id}</code> pobieranie informacji o czasie trwania postoju oraz opłaty za postój o podanym id<br />
