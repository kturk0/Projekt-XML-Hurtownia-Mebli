import java.io.FileNotFoundException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;



class Hurtownia extends JFrame implements ActionListener{

    JButton bPracownik, bKlient, bZamowienie, bDostawa, bMebel, bPracownikD, bKlientD, bMebelD, bZamowienieD, bDostawaD;

    static JLabel l;



    public Hurtownia(){
        setSize(450,350);
        setLocation(100,100);
        setResizable(false);
        setLayout(null);
        setTitle("Hurtownia");


        bPracownik=new JButton("Dodaj nowego pracownika");
        bPracownik.setBounds(10,10,190,30);
        add(bPracownik);
        bPracownik.addActionListener(this);
        bKlient=new JButton("Dodaj nowego klienta");
        bKlient.setBounds(10,50,190,30);
        add(bKlient);
        bKlient.addActionListener(this);
        bMebel=new JButton("Dodaj mebel");
        bMebel.setBounds(10,130,190,30);
        add(bMebel);
        bMebel.addActionListener(this);
        bZamowienie=new JButton("Dodaj zamówienie");
        bZamowienie.setBounds(10,200,190,30);
        add(bZamowienie);
        bZamowienie.addActionListener(this);
        bDostawa=new JButton("Dodaj dostawę");
        bDostawa.setBounds(10,240,190,30);
        add(bDostawa);
        bDostawa.addActionListener(this);

        bPracownikD=new JButton("Usuń pracownika");
        bPracownikD.setBounds(220,10,190,30);
        add(bPracownikD);
        bPracownikD.addActionListener(this);
        bKlientD=new JButton("Usuń klienta");
        bKlientD.setBounds(220,50,190,30);
        add(bKlientD);
        bKlientD.addActionListener(this);
        bMebelD=new JButton("Usuń mebel");
        bMebelD.setBounds(220,130,190,30);
        add(bMebelD);
        bMebelD.addActionListener(this);
        bZamowienieD=new JButton("Usuń zamówienie");
        bZamowienieD.setBounds(220,200,190,30);
        add(bZamowienieD);
        bZamowienieD.addActionListener(this);
        bDostawaD=new JButton("Usuń dostawę");
        bDostawaD.setBounds(220,240,190,30);
        add(bDostawaD);
        bDostawaD.addActionListener(this);
    }

    public static void main(String[] args) {
        Hurtownia hur = new Hurtownia();
        hur.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e){
        Object zrodlo=e.getSource();
  //       Wyswietlacz w = new Wyswietlacz();
        Buttons b = new Buttons();
        ButtonsUsun u = new ButtonsUsun();
      /*   if (zrodlo==Pracownicy)
            w.showPracownicy();
        else if (zrodlo==Klienci)
            w.showKlienci();
        else if (zrodlo==Zamowienia)
            w.showZamowienia();
        else if (zrodlo==Meble)
            w.showMeble();
        else if (zrodlo==Dostawy)
            w.showDostawy();*/
        if (zrodlo==bPracownik) {
            try {
                b.dodajPracownika();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        else if (zrodlo==bKlient) {
            try {
                b.dodajKlienta();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        else if (zrodlo==bZamowienie) {
            try {
                b.dodajZamowienie();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        else if (zrodlo==bDostawa) {
            try {
                b.dodajDostawe();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        else if (zrodlo==bMebel) {
             try {
                 b.dodajMebel();
             } catch (FileNotFoundException ex) {
                 ex.printStackTrace();
             }
         }
        else if (zrodlo==bPracownikD) {
            try {
                u.usunPracownik();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        else if (zrodlo==bKlientD) {
            try {
                u.usunKlient();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        else if (zrodlo==bMebelD) {
            try {
                u.usunMebel();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        else if (zrodlo==bZamowienieD) {
            try {
                u.usunZamowienie();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        else if (zrodlo==bDostawaD) {
            try {
                u.usunDostawe();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }

    }

}