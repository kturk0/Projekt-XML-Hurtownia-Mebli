<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:foo="https://www.w3schools.com"
  exclude-result-prefixes="foo">
<xsl:template match="/">
  <html>
  <body>
  <h2>PRACOWNICY</h2>
  <table border="1">
    <tr bgcolor="#9acd32">
      <th>Imię</th>
      <th>Nazwiskoo</th>
      <th>Data urodzenia</th>
      <th>Wynagrodzenie</th>
      <th>Miasto</th>
      <th>Ulica</th>
      <th>Nr. budynku</th>
      <th>Kod pocztowy</th>
    </tr>
    <xsl:for-each select="foo:hurtownia/foo:pracownicy/foo:pracownik">
    <tr>
      <td><xsl:value-of select="foo:imie"/></td>
      <td><xsl:value-of select="foo:nazwisko"/></td>
      <td><xsl:value-of select="foo:data_ur"/></td>
      <td><xsl:value-of select="foo:wynagrodzenie"/></td>
      <td><xsl:value-of select="foo:adres/foo:miasto"/></td>
      <td><xsl:value-of select="foo:adres/foo:ulica"/></td>
      <td><xsl:value-of select="foo:adres/foo:nr_budynku"/></td>
      <td><xsl:value-of select="foo:adres/foo:kod_pocztowy"/></td>
    </tr>
    </xsl:for-each>
  </table>
  <h2>KLIENCI</h2>
  <table border="1">
    <tr bgcolor="#9acd32">
      <th>Imię</th>
      <th>Nazwiskoo</th>
      <th>Data urodzenia</th>
      <th>Wynagrodzenie</th>
      <th>Miasto</th>
      <th>Ulica</th>
      <th>Nr. budynku</th>
      <th>Kod pocztowy</th>
    </tr>
    <xsl:for-each select="foo:hurtownia/foo:klienci/foo:klient">
    <tr>
      <td><xsl:value-of select="foo:imie"/></td>
      <td><xsl:value-of select="foo:nazwisko"/></td>
      <td><xsl:value-of select="foo:data_ur"/></td>
      <td><xsl:value-of select="foo:wynagrodzenie"/></td>
      <td>
        <xsl:value-of select="foo:adres/foo:miasto"/>
      </td>
      <td>
        <xsl:value-of select="foo:adres/foo:ulica"/>
      </td>
      <td>
        <xsl:value-of select="foo:adres/foo:nr_budynku"/>
      </td>
      <td>
        <xsl:value-of select="foo:adres/foo:kod_pocztowy"/>
      </td>
    </tr>
    </xsl:for-each>
  </table>
  <h2>MEBLE</h2>
  <table border="1">
    <tr bgcolor="#9acd32">
      <th>Nazwa</th>
      <th>Kategoria</th>
      <th>Cena</th>
      <th>Stan</th>
    </tr>
    <xsl:for-each select="foo:hurtownia/foo:meble/foo:mebel">
      <tr>
        <td>
          <xsl:value-of select="foo:nazwa"/>
        </td>
        <td>
          <xsl:value-of select="foo:kategoria"/>
        </td>
        <td>
          <xsl:value-of select="foo:cena"/>
        </td>
        <td>
          <xsl:value-of select="foo:stan"/>
        </td>
      </tr>
    </xsl:for-each>
  </table>
  <h2>DOSTAWY</h2>
  <table border="1">
    <tr bgcolor="#9acd32">
      <th>ID pracownika</th>
      <th>Dostawca</th>
      <th>ID mebla</th>
      <th>Ilość</th>
      <th>Data</th>
    </tr>
    <xsl:for-each select="foo:hurtownia/foo:dostawy/foo:dostawa">
      <tr>
        <td>
          <xsl:value-of select="foo:pracownik_id"/>
        </td>
        <td>
          <xsl:value-of select="foo:dostawca"/>
        </td>
        <td>
          <xsl:value-of select="foo:mebel_id"/>
        </td>
        <td>
          <xsl:value-of select="foo:ilosc"/>
        </td>
        <td>
          <xsl:value-of select="foo:data"/>
        </td>
      </tr>
    </xsl:for-each>
  </table>
  <h2>ZAMÓWIENIA</h2>
  <table border="1">
    <tr bgcolor="#9acd32">
      <th>ID klienta</th>
      <th>ID mebla</th>
      <th>Ilość</th>
      <th>Data</th>
    </tr>
    <xsl:for-each select="foo:hurtownia/foo:zamowienia/foo:zamowienie">
      <tr>
        <td>
          <xsl:value-of select="foo:klient_id"/>
        </td>
        <td>
          <xsl:value-of select="foo:mebel_id"/>
        </td>
        <td>
          <xsl:value-of select="foo:ilosc"/>
        </td>
        <td>
          <xsl:value-of select="foo:data"/>
        </td>
      </tr>
    </xsl:for-each>
  </table>
   </body>
  </html>
</xsl:template>

</xsl:stylesheet>