/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.companyofs.planzsp1;

import com.codename1.io.Preferences;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.spinner.Picker;

/**
 * Dialog to choose between Student and Teacher
 * @author Nikodem
 * 
 */
public class DialogTypeChoice extends Dialog {
    
    private boolean hasBeenChoosed;
    /**
     * 
     * @param isCancelable tells if user can cancel the choosing and dispose the dialog
     */
    public DialogTypeChoice(Boolean isCancelable) {
    hasBeenChoosed = false;
    int size = 3;
        if (isCancelable) size++;
        setLayout(new GridLayout(size, 1));
        add(new LabelFont("Kim jesteś?", "Label"));
        Picker pik = new Picker();
        pik.setSize(new Dimension(100,100));
        
        pik.setStrings(new String[]{"Nauczyciel", "Uczeń"});
        add(pik);
        Button okButton = new Button("Zatwierdź");
        
        okButton.addActionListener(e -> {
            if (pik.getSelectedString() == null)
                Dialog.show("Błąd", "Należy wybrać jedną z dostępnych opcji", "Zrozumiałem", null);
            else
            {
            hasBeenChoosed = true;
            //Close attention to this function it may not work as supposed        
            setPreferences( pik.getSelectedStringIndex()); 
            dispose();
            }
        });
        add(okButton);
        if (isCancelable)
        {
            Button cB = new Button("Anuluj");
            cB.addActionListener(e -> dispose());
            add(cB);
        }
    }
    /**
     * sets the userTypeID preference
     * @param val integer value to set as userTypeID
     */
    public void setPreferences(int val)
    {
        Preferences.setPreferencesLocation("User.preferences");
        Preferences.set("userTypeID", val);
    }
    /**
     * 
     * @return boolean value that tells if the type has been choosed
     */
    public boolean getHasBeenChoosed()
    {
        return hasBeenChoosed;
    }
}
