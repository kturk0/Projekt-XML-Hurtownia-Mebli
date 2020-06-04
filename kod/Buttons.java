
import org.w3c.dom.*;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSParser;
import org.w3c.dom.ls.LSSerializer;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.*;

//w nowym pliku dodano nowego pracownika

import java.io.FileOutputStream;

// rejestr do tworzenia implementacji DOM
import org.w3c.dom.bootstrap.DOMImplementationRegistry;

// Implementacja DOM Level 3 Load & Save
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSParser; // Do serializacji (zapisywania) dokumentow
import org.w3c.dom.ls.LSSerializer;
import org.w3c.dom.ls.LSOutput;

// Konfigurator i obsluga bledow
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMError;
import org.w3c.dom.DOMErrorHandler;

// Do pracy z dokumentem
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;





/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


class Buttons {
    Document document;
    DOMImplementationLS impl;
    DOMConfiguration config;
    LSParser builder;
    static Boolean errorChecker = false;
    void setup() {
        try {
            /*
             * ustawienie systemowej wlasnosci (moze byc dokonane w innym
             * miejscu, pliku konfiguracyjnym w systemie itp.) konkretna
             * implementacja DOM
             */
            DOMImplementationRegistry registry = DOMImplementationRegistry
                    .newInstance();

            // pozyskanie implementacji Load & Save DOM Level 3 z rejestru
            impl = (DOMImplementationLS) registry
                    .getDOMImplementation("LS");

            // stworzenie DOMBuilder
             builder = impl.createLSParser(
                    DOMImplementationLS.MODE_SYNCHRONOUS, null);
            // pozyskanie konfiguratora - koniecznie zajrzec do dokumentacji co
            // mozna poustawiac
            config = builder.getDomConfig();

            // stworzenie DOMErrorHandler i zarejestrowanie w konfiguratorze
            DOMErrorHandler errorHandler = getErrorHandler();
            config.setParameter("error-handler", errorHandler);

            // set validation feature
            config.setParameter("validate", Boolean.TRUE);

            // set schema language
            config.setParameter("schema-type",
                    "http://www.w3.org/2001/XMLSchema");
            // set schema location
            config.setParameter("schema-location", "hurtownia.xsd");
            config.setParameter("schema-location", "hurtownia.xsl");


            System.out.println("Parsowanie " + "hurtownia.xml" + "...");
            // sparsowanie dokumentu i pozyskanie "document" do dalszej pracy
            document = builder.parseURI("hurtownia.xml");



        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static DOMErrorHandler getErrorHandler() {
        return new DOMErrorHandler() {
            public boolean handleError(DOMError error) {
                errorChecker = true;
                short severity = error.getSeverity();
                if (severity == error.SEVERITY_ERROR) {
                    System.out.println("[dom3-error]: " + error.getMessage());
                }
                if (severity == error.SEVERITY_WARNING) {
                    System.out.println("[dom3-warning]: " + error.getMessage());
                }
                if (severity == error.SEVERITY_FATAL_ERROR) {
                    System.out.println("[dom3-fatal-error]: "
                            + error.getMessage());
                }
                return true;
            }
        };
    }
    void dodajPracownika() throws FileNotFoundException {
        setup();
        JTextField imieField = new JTextField(5);
        JTextField nazwiskoField = new JTextField(5);
        JTextField data_urField = new JTextField(5);
        JTextField wynagrodzenieField = new JTextField(5);
        JTextField miastoField = new JTextField(5);
        JTextField ulicaField = new JTextField(5);
        JTextField nr_budynkuField = new JTextField(5);
        JTextField kod_pocztowyField = new JTextField(5);


        JPanel myPanel = new JPanel();
        myPanel.setLayout(new GridLayout(0, 1));
        myPanel.add(new JLabel("IMIĘ:"));
        myPanel.add(imieField);
        //  myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("NAZWISKO:"));
        myPanel.add(nazwiskoField);
        myPanel.add(new JLabel("DATA UR (YYYY-MM-DD):"));
        myPanel.add(data_urField);
        myPanel.add(new JLabel("WYNAGRODZENIE:"));
        myPanel.add(wynagrodzenieField);
        myPanel.add(new JLabel("MIASTO:"));
        myPanel.add(miastoField);
        myPanel.add(new JLabel("ULICA:"));
        myPanel.add(ulicaField);
        myPanel.add(new JLabel("NR BUDYNKU:"));
        myPanel.add(nr_budynkuField);
        myPanel.add(new JLabel("KOD POCZTOWY"));
        myPanel.add(kod_pocztowyField);
        int wynik = JOptionPane.showConfirmDialog(null, myPanel, "Wpisz dane nowego pracownika", JOptionPane.OK_CANCEL_OPTION);
        if (wynik == JOptionPane.OK_OPTION) {
            Element nowyPracownik = document.createElement("pracownik");
            NodeList nodes = document.getElementsByTagName("pracownik");
            int noweID = Integer.parseInt(nodes.item(nodes.getLength()-1).getAttributes().item(0).getNodeValue())+1;
            nowyPracownik.setAttribute("idP", Integer.toString(noweID));
            Element imie = document.createElement("imie");
            imie.appendChild(document.createTextNode(imieField.getText()));
            Element nazwisko = document.createElement("nazwisko");
            nazwisko.appendChild(document.createTextNode(nazwiskoField.getText()));
            Element data = document.createElement("data_ur");
            data.appendChild(document.createTextNode(data_urField.getText()));
            Element wynagrodzenie = document.createElement("wynagrodzenie");
            wynagrodzenie.appendChild(document.createTextNode(wynagrodzenieField.getText()));
            Element miasto = document.createElement("miasto");
            miasto.appendChild(document.createTextNode(miastoField.getText()));
            Element ulica = document.createElement("ulica");
            ulica.appendChild(document.createTextNode(ulicaField.getText()));
            Element nr = document.createElement("nr_budynku");
            nr.appendChild(document.createTextNode(nr_budynkuField.getText()));
            Element kod = document.createElement("kod_pocztowy");
            kod.appendChild(document.createTextNode(kod_pocztowyField.getText()));
            Element adres = document.createElement("adres");
            adres.appendChild(miasto);
            adres.appendChild(ulica);
            adres.appendChild(nr);
            adres.appendChild(kod);

            nowyPracownik.appendChild(imie);
            nowyPracownik.appendChild(nazwisko);
            nowyPracownik.appendChild(data);
            nowyPracownik.appendChild(wynagrodzenie);
            nowyPracownik.appendChild(adres);
            document.getElementsByTagName("pracownicy").item(0).appendChild(nowyPracownik);
            // pozyskanie serializatora
            LSSerializer domWriter = impl.createLSSerializer();
            // pobranie konfiguratora dla serializatora
            config = domWriter.getDomConfig();
            config.setParameter("xml-declaration", Boolean.TRUE);
            // pozyskanie i konfiguracja Wyjscia
            LSOutput dOut = impl.createLSOutput();
            dOut.setByteStream(new FileOutputStream("hurtowniaERRORCHECK.xml"));
            System.out.println("Serializing document... ");
            domWriter.write(document, dOut);
            document = builder.parseURI("hurtowniaERRORCHECK.xml");
            if(!errorChecker) {
                dOut.setByteStream(new FileOutputStream("hurtownia.xml"));
                System.out.println("Serializing document... ");
                domWriter.write(document, dOut);
                JFrame msgFrame = new JFrame();
                msgFrame.setLocation(400, 400);
                JOptionPane.showMessageDialog(msgFrame, "DODANO PRACOWNIKA",
                        "Sukces", JOptionPane.PLAIN_MESSAGE);
            }
            else {
                errorChecker = false;
                JFrame msgFrame = new JFrame();
                msgFrame.setLocation(400, 400);
                JOptionPane.showMessageDialog(msgFrame, "Błędne dane",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
            }

        }
    }
    void dodajKlienta() throws FileNotFoundException {
        setup();
        JTextField imieField = new JTextField(5);
        JTextField nazwiskoField = new JTextField(5);
        JTextField data_urField = new JTextField(5);
        JTextField miastoField = new JTextField(5);
        JTextField ulicaField = new JTextField(5);
        JTextField nr_budynkuField = new JTextField(5);
        JTextField kod_pocztowyField = new JTextField(5);


        JPanel myPanel = new JPanel();
        myPanel.setLayout(new GridLayout(0, 1));
        myPanel.add(new JLabel("IMIĘ:"));
        myPanel.add(imieField);
        myPanel.add(new JLabel("NAZWISKO:"));
        myPanel.add(nazwiskoField);
        myPanel.add(new JLabel("DATA UR (YYYY-MM-DD):"));
        myPanel.add(data_urField);
        myPanel.add(new JLabel("MIASTO:"));
        myPanel.add(miastoField);
        myPanel.add(new JLabel("ULICA:"));
        myPanel.add(ulicaField);
        myPanel.add(new JLabel("NR BUDYNKU:"));
        myPanel.add(nr_budynkuField);
        myPanel.add(new JLabel("KOD POCZTOWY"));
        myPanel.add(kod_pocztowyField);
        int wynik = JOptionPane.showConfirmDialog(null, myPanel, "Wpisz dane nowego klienta", JOptionPane.OK_CANCEL_OPTION);
        if (wynik == JOptionPane.OK_OPTION) {
            Element nowyKLient = document.createElement("klient");
            NodeList nodes = document.getElementsByTagName("klient");
            int noweID = Integer.parseInt(nodes.item(nodes.getLength()-1).getAttributes().item(0).getNodeValue())+1;
            nowyKLient.setAttribute("idK", Integer.toString(noweID));
            Element imie = document.createElement("imie");
            imie.appendChild(document.createTextNode(imieField.getText()));
            Element nazwisko = document.createElement("nazwisko");
            nazwisko.appendChild(document.createTextNode(nazwiskoField.getText()));
            Element data = document.createElement("data_ur");
            data.appendChild(document.createTextNode(data_urField.getText()));
            Element miasto = document.createElement("miasto");
            miasto.appendChild(document.createTextNode(miastoField.getText()));
            Element ulica = document.createElement("ulica");
            ulica.appendChild(document.createTextNode(ulicaField.getText()));
            Element nr = document.createElement("nr_budynku");
            nr.appendChild(document.createTextNode(nr_budynkuField.getText()));
            Element kod = document.createElement("kod_pocztowy");
            kod.appendChild(document.createTextNode(kod_pocztowyField.getText()));
            Element adres = document.createElement("adres");
            adres.appendChild(miasto);
            adres.appendChild(ulica);
            adres.appendChild(nr);
            adres.appendChild(kod);
            nowyKLient.appendChild(imie);
            nowyKLient.appendChild(nazwisko);
            nowyKLient.appendChild(data);
            nowyKLient.appendChild(adres);
            document.getElementsByTagName("pracownicy").item(0).appendChild(nowyKLient);
            // pozyskanie serializatora
            LSSerializer domWriter = impl.createLSSerializer();
            // pobranie konfiguratora dla serializatora
            config = domWriter.getDomConfig();
            config.setParameter("xml-declaration", Boolean.TRUE);
            // pozyskanie i konfiguracja Wyjscia
            LSOutput dOut = impl.createLSOutput();
            dOut.setByteStream(new FileOutputStream("hurtowniaERRORCHECK.xml"));
            System.out.println("Serializing document... ");
            domWriter.write(document, dOut);
            document = builder.parseURI("hurtowniaERRORCHECK.xml");
            if(!errorChecker) {
                dOut.setByteStream(new FileOutputStream("hurtownia.xml"));
                System.out.println("Serializing document... ");
                domWriter.write(document, dOut);
                JFrame msgFrame = new JFrame();
                msgFrame.setLocation(400, 400);
                JOptionPane.showMessageDialog(msgFrame, "DODANO PRACOWNIKA",
                        "Sukces", JOptionPane.PLAIN_MESSAGE);
            }
            else {
                errorChecker = false;
                JFrame msgFrame = new JFrame();
                msgFrame.setLocation(400, 400);
                JOptionPane.showMessageDialog(msgFrame, "Błędne dane",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
            }

        }
    }
    void dodajMebel() throws FileNotFoundException {
        setup();
        String[] nazwy = new String[4];
        nazwy[0]= "lampy";
        nazwy[1]= "stoły";
        nazwy[2]= "fotele";
        nazwy[3]="łóżka";

        JTextField nazwaField = new JTextField(5);
        JComboBox kategorieBox = new JComboBox(nazwy);
        JTextField cenaField = new JTextField(5);
        JTextField stanField = new JTextField(5);
        JPanel myPanel = new JPanel();
        myPanel.setLayout(new GridLayout(0, 1));
        myPanel.add(new JLabel("NAZWA:"));
        myPanel.add(nazwaField);
        myPanel.add(new JLabel("KATEGORIA:"));
        myPanel.add(kategorieBox);
        myPanel.add(new JLabel("CENA:"));
        myPanel.add(cenaField);
        myPanel.add(new JLabel("STAN NA MAGAZYNIE"));
        myPanel.add(stanField);
        int wynik = JOptionPane.showConfirmDialog(null, myPanel, "Wpisz dane nowego mebla", JOptionPane.OK_CANCEL_OPTION);
        if (wynik == JOptionPane.OK_OPTION) {
            Element nowyMebel = document.createElement("mebel");
            NodeList nodes = document.getElementsByTagName("mebel");
            int noweID = Integer.parseInt(nodes.item(nodes.getLength()-1).getAttributes().item(0).getNodeValue())+1;
            nowyMebel.setAttribute("idM", Integer.toString(noweID));
            Element nazwa = document.createElement("nazwa");
            nazwa.appendChild(document.createTextNode(nazwaField.getText()));
            Element kategoria = document.createElement("kategoria");
            kategoria.appendChild(document.createTextNode(nazwy[kategorieBox.getSelectedIndex()]));
            Element cena = document.createElement("cena");
            cena.appendChild(document.createTextNode(cenaField.getText()));
            Element stan = document.createElement("stan");
            stan.appendChild(document.createTextNode(stanField.getText()));
            nowyMebel.appendChild(nazwa);
            nowyMebel.appendChild(kategoria);
            nowyMebel.appendChild(cena);
            nowyMebel.appendChild(stan);
            document.getElementsByTagName("meble").item(0).appendChild(nowyMebel);

            // pozyskanie serializatora
            LSSerializer domWriter = impl.createLSSerializer();
            // pobranie konfiguratora dla serializatora
            config = domWriter.getDomConfig();
            config.setParameter("xml-declaration", Boolean.TRUE);
            // pozyskanie i konfiguracja Wyjscia
            LSOutput dOut = impl.createLSOutput();
            dOut.setByteStream(new FileOutputStream("hurtowniaERRORCHECK.xml"));
            System.out.println("Serializing document... ");
            domWriter.write(document, dOut);
            document = builder.parseURI("hurtowniaERRORCHECK.xml");
            if(!errorChecker) {
                dOut.setByteStream(new FileOutputStream("hurtownia.xml"));
                System.out.println("Serializing document... ");
                domWriter.write(document, dOut);
                JFrame msgFrame = new JFrame();
                msgFrame.setLocation(400, 400);
                JOptionPane.showMessageDialog(msgFrame, "DODANO MEBEL",
                        "Sukces", JOptionPane.PLAIN_MESSAGE);
            }
            else
            {
                errorChecker = false;
                JFrame msgFrame = new JFrame();
                msgFrame.setLocation(400, 400);
                JOptionPane.showMessageDialog(msgFrame, "Błędne dane",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
            }

        }

    }
    void dodajZamowienie() throws FileNotFoundException {
        setup();
        JTextField klientField = new JTextField(5);
        JTextField mebelField = new JTextField(5);
        JTextField iloscField = new JTextField(5);
        JTextField dataField = new JTextField(5);
        JPanel myPanel = new JPanel();
        myPanel.setLayout(new GridLayout(0, 1));
        myPanel.add(new JLabel("ID klienta:"));
        myPanel.add(klientField);
        myPanel.add(new JLabel("ID mebla:"));
        myPanel.add(mebelField);
        myPanel.add(new JLabel("Ilość:"));
        myPanel.add(iloscField);
        myPanel.add(new JLabel("Data (YYYY-MM-DD):"));
        myPanel.add(dataField);
        int wynik = JOptionPane.showConfirmDialog(null, myPanel, "Wpisz dane nowego zamówienia", JOptionPane.OK_CANCEL_OPTION);
        if (wynik == JOptionPane.OK_OPTION) {
            Element noweZamowienie = document.createElement("zamowienie");

            Element klient = document.createElement("klient_id");
            klient.appendChild(document.createTextNode(klientField.getText()));
            Element mebel = document.createElement("mebel_id");
            mebel.appendChild(document.createTextNode(mebelField.getText()));
            Element ilosc = document.createElement("ilosc");
            ilosc.appendChild(document.createTextNode(iloscField.getText()));
            Element data = document.createElement("data");
            data.appendChild(document.createTextNode(dataField.getText()));
            noweZamowienie.appendChild(klient);
            noweZamowienie.appendChild(mebel);
            noweZamowienie.appendChild(ilosc);
            noweZamowienie.appendChild(data);

            document.getElementsByTagName("zamowienia").item(0).appendChild( noweZamowienie);

            // pozyskanie serializatora
            LSSerializer domWriter = impl.createLSSerializer();
            // pobranie konfiguratora dla serializatora
            config = domWriter.getDomConfig();
            config.setParameter("xml-declaration", Boolean.TRUE);
            // pozyskanie i konfiguracja Wyjscia
            LSOutput dOut = impl.createLSOutput();
            dOut.setByteStream(new FileOutputStream("hurtowniaERRORCHECK.xml"));
            System.out.println("Serializing document... ");
            domWriter.write(document, dOut);
            document = builder.parseURI("hurtowniaERRORCHECK.xml");
            if(!errorChecker) {
                dOut.setByteStream(new FileOutputStream("hurtownia.xml"));
                System.out.println("Serializing document... ");
                domWriter.write(document, dOut);
                JFrame msgFrame = new JFrame();
                msgFrame.setLocation(400, 400);
                JOptionPane.showMessageDialog(msgFrame, "DODANO ZAMÓWIENIE",
                        "Sukces", JOptionPane.PLAIN_MESSAGE);
            }
            else
            {
                errorChecker = false;
                JFrame msgFrame = new JFrame();
                msgFrame.setLocation(400, 400);
                JOptionPane.showMessageDialog(msgFrame, "Błędne dane",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
            }

        }
    }
   void dodajDostawe() throws FileNotFoundException {
        setup();
        JTextField pracownikField = new JTextField(5);
        JTextField dostawcaField = new JTextField(5);
        JTextField mebelField = new JTextField(5);
        JTextField iloscField = new JTextField(5);
        JTextField dataField = new JTextField(5);
        JPanel myPanel = new JPanel();
        myPanel.setLayout(new GridLayout(0, 1));
        myPanel.add(new JLabel("ID pracownika:"));
        myPanel.add(pracownikField);
        myPanel.add(new JLabel("Dostawca:"));
        myPanel.add(dostawcaField);
        myPanel.add(new JLabel("ID mebla:"));
        myPanel.add(mebelField);
        myPanel.add(new JLabel("Ilość:"));
        myPanel.add(iloscField);
        myPanel.add(new JLabel("Data (YYYY-MM-DD):"));
        myPanel.add(dataField);
        int wynik = JOptionPane.showConfirmDialog(null, myPanel, "Wpisz dane nowej dostawy", JOptionPane.OK_CANCEL_OPTION);
        if (wynik == JOptionPane.OK_OPTION) {
            Element nowaDostawa = document.createElement("dostawa");

            Element pracownik = document.createElement("pracownik_id");
            pracownik.appendChild(document.createTextNode(pracownikField.getText()));
            Element dostawca = document.createElement("dostawca");
            pracownik.appendChild(document.createTextNode(dostawcaField.getText()));
            Element mebel = document.createElement("mebel_id");
            mebel.appendChild(document.createTextNode(mebelField.getText()));
            Element ilosc = document.createElement("ilosc");
            ilosc.appendChild(document.createTextNode(iloscField.getText()));
            Element data = document.createElement("data");
            data.appendChild(document.createTextNode(dataField.getText()));
            nowaDostawa.appendChild(pracownik);
            nowaDostawa.appendChild(dostawca);
            nowaDostawa.appendChild(mebel);
            nowaDostawa.appendChild(ilosc);
            nowaDostawa.appendChild(data);

            document.getElementsByTagName("dostawy").item(0).appendChild(nowaDostawa);

            // pozyskanie serializatora
            LSSerializer domWriter = impl.createLSSerializer();
            // pobranie konfiguratora dla serializatora
            config = domWriter.getDomConfig();
            config.setParameter("xml-declaration", Boolean.TRUE);
            // pozyskanie i konfiguracja Wyjscia
            LSOutput dOut = impl.createLSOutput();
            dOut.setByteStream(new FileOutputStream("hurtowniaERRORCHECK.xml"));
            System.out.println("Serializing document... ");
            domWriter.write(document, dOut);
            document = builder.parseURI("hurtowniaERRORCHECK.xml");
            if(!errorChecker) {
                dOut.setByteStream(new FileOutputStream("hurtownia.xml"));
                System.out.println("Serializing document... ");
                domWriter.write(document, dOut);
                JFrame msgFrame = new JFrame();
                msgFrame.setLocation(400, 400);
                JOptionPane.showMessageDialog(msgFrame, "DODANO DOSTAWĘ",
                        "Sukces", JOptionPane.PLAIN_MESSAGE);
            }
            else
            {
                errorChecker = false;
                JFrame msgFrame = new JFrame();
                msgFrame.setLocation(400, 400);
                JOptionPane.showMessageDialog(msgFrame, "Błędne dane",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
            }

        }
    }


}