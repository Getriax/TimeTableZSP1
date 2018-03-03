/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.companyofs.planzsp1;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.io.Preferences;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.table.TableLayout;
import com.codename1.util.StringUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Basic form that is displayed after initialization
 * @author Nikodem Strawa
 * 
 */
public class MyForm extends Form
{
    private ArrayList<TextArea> godziny;
   private ArrayList<TextArea> lekcje;
   private ArrayList<TextArea> sale;
   private ArrayList<String> classNames;
   private ArrayList<String> hrefs;
   private JsoupInterfaceIntegration nat;
   private int classChoosed = 1;
   private int personType = 0;
   private int currentDay;
   private Date currentTime;
   private TableLayout tb;
 

    /**
     *
     * @param nat Acces to native (only for android) class that provides JSOUP functionality
     */
    public MyForm(JsoupInterfaceIntegration nat) 
    { 
        
        setScrollable(false);
        
        this.nat = nat;
        tb = new TableLayout(9, 4);
        setLayout(tb);
        
        
        setToolbarSideMenuCompnents();
        Preferences.setPreferencesLocation("User.preferences");
        if (Preferences.get("classID", -1) > 0 && Preferences.get("userTypeID", -1) >= 0) {
            personType = Preferences.get("userTypeID", 0);
            classChoosed = Preferences.get("classID", 1);
            classNamesAndHrefsTableFill();
        }
        else {
            Dialog t = new DialogTypeChoice(false);
            t.show();
            personType = Preferences.get("userTypeID", 0);
            classNamesAndHrefsTableFill();
            Dialog d = new DialogClassChoice(classNames, false, personType);
            d.show();
            classChoosed = Preferences.get("classID", 1);
        }
        MakeOverflowMenu();
        MakeTopTableElements();
        MakeLessonPlan(true);        

        
    }
     /**
     *
     * Makes components at the left ToolbarSideMenu
     */
    private void setToolbarSideMenuCompnents()
    {
        Label lab = new Label("Wybierz dzień");
        lab.setUIID("Dzien");
        lab.getUnselectedStyle().setBorder(Border.createCompoundBorder(null, Border.createLineBorder(2), null, null));
        getToolbar().addComponentToSideMenu(lab);
        String[] dni = {"Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek"};
        int[] vals = {0,1,2,3,4};
        for (int i = 0; i < dni.length; i++)
        {
           int vlaue = i;
           getToolbar().addCommandToSideMenu(dni[i], null, e -> {changeDay(vlaue); buildAgain();});
        }
            
    }
     /**
     *
     * Fills the classNames and href arrays with appropraite values 
     */
    private void classNamesAndHrefsTableFill() 
    {
        ConnectionRequest con = new ConnectionRequest("http://zsp1.chojnice.pl/plan/lista.html");
        NetworkManager.getInstance().addToQueueAndWait(con);
        String response = new String(con.getResponseData());
        String doc = nat.parseCode(response);
        ArrayList<String> nazwy = getElementsByTag(doc, "a");
        hrefs = new ArrayList<>();
        classNames = new ArrayList<>();
        for (String el : nazwy)
        {
            String tekst = nat.getText(el);
            String href = nat.getHref(el);
            if (personType == 0) {
                if (href.indexOf("o") == -1 && href.indexOf("s") == -1) {
                    hrefs.add(href);
                    classNames.add(tekst);
                }
            }
            else {
                if (href.indexOf("o") > -1) {
                    hrefs.add(href);
                    classNames.add(tekst);
                }
            }
            
        }
    }
     /**
     *
     * Creates the first row of the table
     */
    private void MakeTopTableElements()
    {
        setTitle(classNames.get(classChoosed - 1)); 
        Label lp = new Label("Lp.", "LabelTop");
        Label h = new Label("Godzina", "LabelTop");
        Label lekcja = new Label("Lekcja", "LabelTop");
        Label sala = new Label("Sala", "LabelTop");
        int hp;
        if (!(godziny == null))
            hp = godziny.size() > 8 ? 11 : 12;
        else
            hp = 11;
        add(tb.createConstraint().widthPercentage(10).heightPercentage(hp), lp);
        add(tb.createConstraint().widthPercentage(25).heightPercentage(hp), h);
        add(tb.createConstraint().widthPercentage(45).heightPercentage(hp), lekcja);
        add(tb.createConstraint().widthPercentage(20).heightPercentage(hp), sala);
    }
     /**
     * Makes the rest of table with values from given url
     * @param changeDay tells if the day has changed
     */
   private void MakeLessonPlan(boolean changeDay)
    {
        String planURL = "http://zsp1.chojnice.pl/plan/" + hrefs.get(classChoosed - 1);
        ConnectionRequest con = new ConnectionRequest(planURL);
        NetworkManager.getInstance().addToQueueAndWait(con);
        String response = new String(con.getResponseData());
        String doc = nat.parseCode(response);
        godziny = new ArrayList<>();
        lekcje = new ArrayList<>();
        sale = new ArrayList<>();
        int heightPercentage[] = {0,0,0,0,0,0,0,0,0,0};
        int heightIndex = 0;
        ArrayList<String> hours = getElementsByClass(doc, "g");
        
        for (String el : hours)
        {
            
           String code = new String(el);
           String tekst = nat.getText(code);
           TextArea g = new LabelTextArea(tekst, "LowLabel");
           godziny.add(g);
        }
        TableLayout.setDefaultRowHeight((int) Math.floor(100/hours.size()) - 1);   
        
         currentTime = new Date();
         Calendar c = Calendar.getInstance();
         c.setTime(currentTime);
         int[] daysValues = {0,0,0,1,2,3,4,0};
         if (changeDay)
         currentDay = daysValues[c.get(Calendar.DAY_OF_WEEK)];
         
        String tabela = nat.getElementByClass(doc, "tabela", 0);
        ArrayList<String> wiersze = getElementsByTag(tabela, "tr");

        for (int i = 1; i < wiersze.size(); i++)
        {
            String wierszKod = wiersze.get(i);
            wierszKod = "<table>" + wierszKod + "</table>";
            String komorka = nat.getElementByClass(wierszKod, "l", currentDay);
            ArrayList<String> pclass = getElementsByClass(komorka, "p");
            ArrayList<String> sclassArray = getElementsByClass(komorka, "s");
            ArrayList<String> pclassArray = new ArrayList<>();
            for (String pElement : pclass)
            {
                String codeOfElement = new String(pElement);
                String txtOfElement = nat.getText(codeOfElement);
                if (txtOfElement.indexOf("#") < 0)
                    pclassArray.add(codeOfElement);
            }
            
             TextArea lessonTxt = new LabelTextArea("LowLabel");
             TextArea salaTxt = new LabelTextArea("LowLabel");
             if(pclassArray.isEmpty())
             {
                 
                 heightPercentage[heightIndex] = 1;
                 heightIndex++;
             }
             else
             {
                 for (String e : pclassArray)
                 {
                     if (lessonTxt.getText().equals(""))
                        lessonTxt.setText(nat.getText(e));
                     else
                         lessonTxt.setText(lessonTxt.getText() + "\n" + nat.getText(e));
                     heightPercentage[heightIndex] += 1;
                 }
                 for (String el : sclassArray)
                 {
                     if (salaTxt.getText().equals(""))
                        salaTxt.setText(nat.getText(el));
                     else
                         salaTxt.setText(salaTxt.getText() + "\n" + nat.getText(el));
                 }
                 heightIndex++;
             }

             
             lekcje.add(lessonTxt);
             sale.add(salaTxt);
         }
            boolean poprzednie = true;
         for (int i = 0; i < godziny.size(); i++)
         {
             LabelTextArea l_p = new LabelTextArea("" + (i+1), "LowLabel");
             boolean kolorek = false;
                if (currentDay == daysValues[c.get(Calendar.DAY_OF_WEEK)])
                {
                    List<String> s = StringUtil.tokenize(godziny.get(i).getText(), "-");
                    for (int g = 0; g < s.size(); g++)
                    s.set(g, s.get(g).trim());
                    List<String> czasyOne = StringUtil.tokenize(s.get(0), ":");
                    List<String> czasyTwo = StringUtil.tokenize(s.get(1), ":");
                    int timeCurrent = timeToSeconds(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
                    int timeOne = timeToSeconds(Integer.valueOf(czasyOne.get(0)), Integer.valueOf(czasyOne.get(1)));
                    int timeTwo = timeToSeconds(Integer.valueOf(czasyTwo.get(0)), Integer.valueOf(czasyTwo.get(1)));
                    if (timeCurrent > timeOne && timeCurrent < timeTwo || timeCurrent + 600 > timeOne && timeCurrent < timeTwo && poprzednie)
                    {
                        kolorek = true;
                        poprzednie = false;
                    }
                }
                    
                if (kolorek)
                {
                    int color = 10594767;
                    l_p.getAllStyles().setBgColor(color, true);
                    godziny.get(i).getAllStyles().setBgColor(color, true);
                    lekcje.get(i).getAllStyles().setBgColor(color, true);
                    sale.get(i).getAllStyles().setBgColor(color, true);
                }

             
             
             add(tb.createConstraint().widthPercentage(10), l_p);
             add(tb.createConstraint().widthPercentage(25), godziny.get(i));
             add(tb.createConstraint().widthPercentage(45), lekcje.get(i));
             add(tb.createConstraint().widthPercentage(20), sale.get(i));
         }
    }

    /**
     * builds form again and refreshes it
     */
    public void buildAgain()
    {
        removeAll();
        MakeTopTableElements();
        MakeLessonPlan(false);
        repaint();
    }

    /**
     * Creates the menu at the right 
     */
    public void MakeOverflowMenu()
    {
        Image im = FontImage.createMaterial(FontImage.MATERIAL_EDIT, UIManager.getInstance().getComponentStyle("Command"));
        Command chClass = new Command("Zmień", im) {
            @Override
            public void actionPerformed(ActionEvent evt) {
                DialogTypeChoice ch = new DialogTypeChoice(true);
                ch.show();
                if (ch.getHasBeenChoosed()) {
                    if (Preferences.get("userTypeID", 0) != personType) {
                        personType = Preferences.get("userTypeID", 0);
                        classNamesAndHrefsTableFill();
                    }
                    Dialog d = new DialogClassChoice(classNames, false, personType);
                    d.show();
                    classChoosed = Preferences.get("classID", 1);
                    buildAgain();
                }
            }
        };
        getToolbar().addCommandToOverflowMenu(chClass);
        
        im = FontImage.createMaterial(FontImage.MATERIAL_FACE, UIManager.getInstance().getComponentStyle("Command"));
        Command about = new Command("O twórcach", im)
        {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Dialog.show("O twórcach", "Aplikację zaprojektował: \nNikodem Strawa \n", "Ok", null);
            }
        };
        getToolbar().addCommandToOverflowMenu(about);
        
        im = FontImage.createMaterial(FontImage.MATERIAL_EXIT_TO_APP, UIManager.getInstance().getComponentStyle("Command"));
        Command exit = new Command("Wyjdź", im)
        {
            @Override
            public void actionPerformed(ActionEvent evt) {
                System.exit(0);
            }
        };
        getToolbar().addCommandToOverflowMenu(exit);
    }
    /**
     * changes currentDay value 
     * @param val integer value of the day
     */
    private void changeDay(int val)
    {
        currentDay = val;
    }
    /**
     * 
     * @param code html code with the classes to find
     * @param className the name of a class to find
     * @return Array with (full) elements (ex. <a href="someurl.com" class="someClass"> link </a>)
     */
    private ArrayList<String> getElementsByClass(String code, String className)
    {
        ArrayList<String> retList = new ArrayList<>();
        int index = 0;
        while (nat.getElementByClass(code, className, index) != null)
        {
            retList.add(nat.getElementByClass(code, className, index));
            index++;
        }
        return retList;
    }
    /**
     * 
     * @param code html code with the tags to find
     * @param tagName the name of a tag to find
     * @return Array with (full) elements (ex. <a href="someurl.com"> link </a>)
     */
    private ArrayList<String> getElementsByTag(String code, String tagName)
    {
        ArrayList<String> retList = new ArrayList<>();
        int index = 0;
        while (nat.getElementByTag(code, tagName, index) != null)
        {
            retList.add(nat.getElementByTag(code, tagName, index));
            index++;
        }
        return retList;
    }

    /**
     *
     * @param Hour integer value of hours
     * @param Minute integer value of minutes
     * @return integer of converted time (H:M) to seconds
     */
    public int timeToSeconds(int Hour, int Minute)
    {
        return Hour * 3600 + Minute * 60;
    }
}
