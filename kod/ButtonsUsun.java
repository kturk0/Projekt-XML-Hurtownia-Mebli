
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


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


class ButtonsUsun {
    Document document;
    DOMImplementationLS impl;
    DOMConfiguration config;
    LSParser builder;
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
    void usunPracownik() throws FileNotFoundException {
        setup();
        List<String> lista=new ArrayList<String>();
        NodeList pracownicy = document.getElementsByTagName("pracownik");
        for(int i =0; i < pracownicy.getLength(); i++) {
            NodeList Pnodes = pracownicy.item(i).getChildNodes();
            List<Node> pracownicyElem = new ArrayList<>();
            for (int j = 0; j <  Pnodes.getLength(); j++) {
        
                if (Pnodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                    pracownicyElem.add(Pnodes.item(j));
                }
            }
            NamedNodeMap atr = pracownicy.item(i).getAttributes();
            String t=atr.getNamedItem("idP").getTextContent();
            String z=pracownicyElem.get(0).getTextContent().trim();
            String y=pracownicyElem.get(1).getTextContent().trim();
            lista.add(t);
            lista.add(z);
            lista.add(y);
        }
        String[] pracownicyNazwy = new String[lista.size()/3];
        String idP = "un";
        String imieP = "un";
        String nazwiskoP;
        for(int i = 0; i < lista.size(); i++)
        {
            if(i%3 == 0)
                idP = lista.get(i);
            else if (i%3 == 1)
                imieP = lista.get(i);
            else if (i%3 == 2)
            {
                nazwiskoP = lista.get(i);
                pracownicyNazwy[i/3] = idP + " / " + imieP + " " + nazwiskoP;
            }
        }
        JComboBox pracownicyBox = new JComboBox(pracownicyNazwy);
        JPanel myPanel = new JPanel();
        myPanel.setLayout(new GridLayout(0, 1));
        myPanel.add(new JLabel("ID / IMIĘ NAZWISKO:"));
        myPanel.add(pracownicyBox);
        int wynik = JOptionPane.showConfirmDialog(null, myPanel, "Wybierz pracownika do usunięcia", JOptionPane.OK_CANCEL_OPTION);
        if (wynik == JOptionPane.OK_OPTION) {
            String selected_Id = lista.get(pracownicyBox.getSelectedIndex() * 3);
            NodeList dostawy = document.getElementsByTagName("pracownik_id");
            int records = 0;
            for(int i=0; i<dostawy.getLength(); i++) {
                if (dostawy.item(i).getTextContent().equals(selected_Id))
                    records++;
            }
            if (records > 0)
            {
                JFrame msgFrame = new JFrame();
                msgFrame.setLocation(400, 400);
                JOptionPane.showMessageDialog(msgFrame, "PRACOWNIK JEST WPISANY W " + records + " DOSTAWACH\nUSUNIĘCIE NIEMOŻLIWE",
                        "Błąd", JOptionPane.PLAIN_MESSAGE);
            }
            else {
                Node usuwany = pracownicy.item(pracownicyBox.getSelectedIndex());
                usuwany.getParentNode().removeChild(usuwany);
                LSSerializer domWriter = impl.createLSSerializer();
                // pobranie konfiguratora dla serializatora
                config = domWriter.getDomConfig();
                config.setParameter("xml-declaration", Boolean.TRUE);
                // pozyskanie i konfiguracja Wyjscia
                LSOutput dOut = impl.createLSOutput();
                dOut.setByteStream(new FileOutputStream("hurtownia.xml"));
                System.out.println("Serializing document... ");
                domWriter.write(document, dOut);
                JFrame msgFrame = new JFrame();
                msgFrame.setLocation(400, 400);
                JOptionPane.showMessageDialog(msgFrame, "USUNIĘTO PRACOWNIKA",
                        "Sukces", JOptionPane.PLAIN_MESSAGE);
                }
            }



    }
    void usunKlient() throws FileNotFoundException {
        setup();
        List<String> lista=new ArrayList<String>();
        NodeList klienci = document.getElementsByTagName("klient");
        for(int i =0; i < klienci.getLength(); i++) {
            NodeList Knodes = klienci.item(i).getChildNodes();
            List<Node> klienciElem = new ArrayList<>();
            for (int j = 0; j <  Knodes.getLength(); j++) {

                if (Knodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                    klienciElem.add(Knodes.item(j));
                }
            }
            NamedNodeMap atr = klienci.item(i).getAttributes();
            String t=atr.getNamedItem("idK").getTextContent();
            String z=klienciElem.get(0).getTextContent().trim();
            String y=klienciElem.get(1).getTextContent().trim();
            lista.add(t);
            lista.add(z);
            lista.add(y);
        }
        String[] klienciNazwy = new String[lista.size()/3];
        String idP = "un";
        String imieP = "un";
        String nazwiskoP;
        for(int i = 0; i < lista.size(); i++)
        {
            if(i%3 == 0)
                idP = lista.get(i);
            else if (i%3 == 1)
                imieP = lista.get(i);
            else if (i%3 == 2)
            {
                nazwiskoP = lista.get(i);
                klienciNazwy[i/3] = idP + " / " + imieP + " " + nazwiskoP;
            }
        }
        JComboBox klienciBox = new JComboBox(klienciNazwy);
        JPanel myPanel = new JPanel();
        myPanel.setLayout(new GridLayout(0, 1));
        myPanel.add(new JLabel("ID / IMIĘ NAZWISKO:"));
        myPanel.add(klienciBox);
        int wynik = JOptionPane.showConfirmDialog(null, myPanel, "Wybierz klienta do usunięcia", JOptionPane.OK_CANCEL_OPTION);
        if (wynik == JOptionPane.OK_OPTION) {
            String selected_Id = lista.get(klienciBox.getSelectedIndex() * 3);
            NodeList zamowienia = document.getElementsByTagName("klient_id");
            int records = 0;
            for(int i=0; i<zamowienia.getLength(); i++) {
                if (zamowienia.item(i).getTextContent().equals(selected_Id))
                    records++;
            }
            if (records > 0)
            {
                JFrame msgFrame = new JFrame();
                msgFrame.setLocation(400, 400);
                JOptionPane.showMessageDialog(msgFrame, "PRACOWNIK JEST WPISANY W " + records + " ZAMÓWIENIACH\nUSUNIĘCIE NIEMOŻLIWE",
                        "Błąd", JOptionPane.PLAIN_MESSAGE);
            }
            else {
                Node usuwany = klienci.item(klienciBox.getSelectedIndex());
                usuwany.getParentNode().removeChild(usuwany);
                LSSerializer domWriter = impl.createLSSerializer();
                // pobranie konfiguratora dla serializatora
                config = domWriter.getDomConfig();
                config.setParameter("xml-declaration", Boolean.TRUE);
                // pozyskanie i konfiguracja Wyjscia
                LSOutput dOut = impl.createLSOutput();
                dOut.setByteStream(new FileOutputStream("hurtownia.xml"));
                System.out.println("Serializing document... ");
                domWriter.write(document, dOut);
                JFrame msgFrame = new JFrame();
                msgFrame.setLocation(400, 400);
                JOptionPane.showMessageDialog(msgFrame, "USUNIĘTO KLIENTA",
                        "Sukces", JOptionPane.PLAIN_MESSAGE);
            }
        }

    }

    void usunMebel() throws FileNotFoundException {
        setup();
        List<String> lista = new ArrayList<String>();
        NodeList meble = document.getElementsByTagName("mebel");
        for (int i = 0; i < meble.getLength(); i++) {
            NodeList Mnodes = meble.item(i).getChildNodes();
            List<Node> mebleElem = new ArrayList<>();
            for (int j = 0; j < Mnodes.getLength(); j++) {

                if (Mnodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                    mebleElem.add(Mnodes.item(j));
                }
            }
            NamedNodeMap atr = meble.item(i).getAttributes();
            String t = atr.getNamedItem("idM").getTextContent();
            String z = mebleElem.get(0).getTextContent().trim();
            lista.add(t);
            lista.add(z);
        }

        String[] mebleNazwy = new String[lista.size() / 2];
        String idM = "un";
        String nazwaM;
        for (int i = 0; i < lista.size(); i++) {
            if (i % 2 == 0)
                idM = lista.get(i);
            else {
                nazwaM = lista.get(i);
                mebleNazwy[i / 2] = idM + " / " + nazwaM;
            }
        }
        JComboBox mebleBox = new JComboBox(mebleNazwy);
        JPanel myPanel = new JPanel();
        myPanel.setLayout(new GridLayout(0, 1));
        myPanel.add(new JLabel("ID / NAZWA:"));
        myPanel.add(mebleBox);
        int wynik = JOptionPane.showConfirmDialog(null, myPanel, "Wybierz mebel do usunięcia", JOptionPane.OK_CANCEL_OPTION);
        if (wynik == JOptionPane.OK_OPTION) {
            String selected_Id = lista.get(mebleBox.getSelectedIndex() * 2);
            NodeList zamowienia = document.getElementsByTagName("mebel_id");
            int records = 0;
            for (int i = 0; i < zamowienia.getLength(); i++) {
                if (zamowienia.item(i).getTextContent().equals(selected_Id))
                    records++;
            }

            if (records > 0) {
                JFrame msgFrame = new JFrame();
                msgFrame.setLocation(400, 400);
                JOptionPane.showMessageDialog(msgFrame, "MEBEL JEST WPISANY W " + records + " DOSTAWACH/ZAMOWIENIACH\nUSUNIĘCIE NIEMOŻLIWE",
                        "Błąd", JOptionPane.ERROR_MESSAGE);
            }
            else {
                Node usuwany = meble.item(mebleBox.getSelectedIndex());
                usuwany.getParentNode().removeChild(usuwany);
                LSSerializer domWriter = impl.createLSSerializer();
               // pobranie konfiguratora dla serializatora
                config = domWriter.getDomConfig();
                config.setParameter("xml-declaration", Boolean.TRUE);
                // pozyskanie i konfiguracja Wyjscia
                LSOutput dOut = impl.createLSOutput();
                dOut.setByteStream(new FileOutputStream("hurtownia.xml"));
                System.out.println("Serializing document... ");
                domWriter.write(document, dOut);
                JFrame msgFrame = new JFrame();
                msgFrame.setLocation(400, 400);
                JOptionPane.showMessageDialog(msgFrame, "USUNIĘTO MEBEL",
                        "Sukces", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }
    void usunZamowienie() throws FileNotFoundException {
        setup();
        List<String> lista = new ArrayList<String>();
        NodeList zamowienia = document.getElementsByTagName("zamowienie");
        for (int i = 0; i < zamowienia.getLength(); i++) {
            NodeList Znodes = zamowienia.item(i).getChildNodes();
            List<Node> zamowieniaElem = new ArrayList<>();
            for (int j = 0; j < Znodes.getLength(); j++) {
                if (Znodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                    zamowieniaElem.add(Znodes.item(j));
                }
            }
            String t = zamowieniaElem.get(1).getTextContent().trim();
            String z = zamowieniaElem.get(3).getTextContent().trim();
            lista.add(t);
            lista.add(z);
        }
        String[] zamowieniaNazwy = new String[lista.size()/2];
        String idZ = "un";
        String dataZ;
        for(int i = 0; i < lista.size(); i++)
        {
            if(i%2 == 0)
                idZ = lista.get(i);
            else {
                dataZ = lista.get(i);
                zamowieniaNazwy[i/2] = idZ + " / " + dataZ;
            }
        }
        JComboBox zamowieniaBox = new JComboBox(zamowieniaNazwy);
        JPanel myPanel = new JPanel();
        myPanel.setLayout(new GridLayout(0, 1));
        myPanel.add(new JLabel("ID MEBLA / DATA:"));
        myPanel.add(zamowieniaBox);
        int wynik = JOptionPane.showConfirmDialog(null, myPanel, "Wybierz zamówienie do usunięcia", JOptionPane.OK_CANCEL_OPTION);
        if (wynik == JOptionPane.OK_OPTION) {
            Node usuwany = zamowienia.item(zamowieniaBox.getSelectedIndex());
            usuwany.getParentNode().removeChild(usuwany);
            LSSerializer domWriter = impl.createLSSerializer();
            // pobranie konfiguratora dla serializatora
            config = domWriter.getDomConfig();
            config.setParameter("xml-declaration", Boolean.TRUE);
            // pozyskanie i konfiguracja Wyjscia
            LSOutput dOut = impl.createLSOutput();
            dOut.setByteStream(new FileOutputStream("hurtownia.xml"));
            System.out.println("Serializing document... ");
            domWriter.write(document, dOut);
            JFrame msgFrame = new JFrame();
            msgFrame.setLocation(400, 400);
            JOptionPane.showMessageDialog(msgFrame, "USUNIĘTO ZAMÓWIENIE",
                    "Sukces", JOptionPane.PLAIN_MESSAGE);


        }
    }
    void usunDostawe() throws FileNotFoundException {
        setup();
        List<String> lista = new ArrayList<String>();
        NodeList dostawy = document.getElementsByTagName("dostawa");
        for (int i = 0; i < dostawy.getLength(); i++) {
            NodeList Dnodes = dostawy.item(i).getChildNodes();
            List<Node> dostawyElem = new ArrayList<>();
            for (int j = 0; j < Dnodes.getLength(); j++) {
                if (Dnodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                    dostawyElem.add(Dnodes.item(j));
                }
            }
            String t = dostawyElem.get(2).getTextContent().trim();
            String z = dostawyElem.get(4).getTextContent().trim();
            lista.add(t);
            lista.add(z);
        }
        String[] dostawyNazwy = new String[lista.size()/2];
        String idZ = "un";
        String dataZ;
        for(int i = 0; i < lista.size(); i++)
        {
            if(i%2 == 0)
                idZ = lista.get(i);
            else {
                dataZ = lista.get(i);
                dostawyNazwy[i/2] = idZ + " / " + dataZ;
            }
        }
        JComboBox dostawyBox = new JComboBox(dostawyNazwy);
        JPanel myPanel = new JPanel();
        myPanel.setLayout(new GridLayout(0, 1));
        myPanel.add(new JLabel("ID MEBLA/ DATA:"));
        myPanel.add(dostawyBox);
        int wynik = JOptionPane.showConfirmDialog(null, myPanel, "Wybierz dostawę do usunięcia", JOptionPane.OK_CANCEL_OPTION);
        if (wynik == JOptionPane.OK_OPTION) {
            Node usuwany = dostawy.item(dostawyBox.getSelectedIndex());
            usuwany.getParentNode().removeChild(usuwany);
            LSSerializer domWriter = impl.createLSSerializer();
            // pobranie konfiguratora dla serializatora
            config = domWriter.getDomConfig();
            config.setParameter("xml-declaration", Boolean.TRUE);
            // pozyskanie i konfiguracja Wyjscia
            LSOutput dOut = impl.createLSOutput();
            dOut.setByteStream(new FileOutputStream("hurtownia.xml"));
            System.out.println("Serializing document... ");
            domWriter.write(document, dOut);
            JFrame msgFrame = new JFrame();
            msgFrame.setLocation(400, 400);
            JOptionPane.showMessageDialog(msgFrame, "USUNIĘTO DOSTAWĘ",
                    "Sukces", JOptionPane.PLAIN_MESSAGE);


        }

    }

}