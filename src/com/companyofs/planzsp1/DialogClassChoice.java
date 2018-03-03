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
import java.util.ArrayList;

/**
 * Dialog to pick the class of the student or the name of the teacher
 * @author Nikodem
 * 
 */
public class DialogClassChoice extends Dialog
{
private String[] classNames;
    /**
     * 
     * @param names the names of the classes or the names of the teachers to choose from
     * @param isCancelable tells if user can cancel the choosing and dispose the dialog
     * @param studentOrTeacher integer value that tells what was choosed in previous dialog (0 - teacher, 1 - student)
     */
    public DialogClassChoice(ArrayList<String> names, boolean isCancelable, int studentOrTeacher) 
    {
        int size = 3;
        if (isCancelable) size++;
        classNames = new String[names.size()];
        setLayout(new GridLayout(size, 1));
        if (studentOrTeacher == 0)
            add(new LabelFont("Wybierz siebie", "Label"));
        else 
            add(new LabelFont("Wybierz swoją klasę", "Label"));
        Picker pik = new Picker();
        pik.setSize(new Dimension(100,100));
        for (int i = 0; i < names.size(); i++)
        {
            classNames[i] = names.get(i);
        }
        pik.setStrings(classNames);
        add(pik);
        Button okButton = new Button("Zatwierdź");
        
        okButton.addActionListener(e -> {
            if (pik.getSelectedString() == null)
                Dialog.show("Błąd", "Należy wybrać klasę", "Zrozumiałem", null);
            else
            {
            setPreferences(pik.getSelectedStringIndex() + 1); 
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
     * sets the classID preference
     * @param val integer value to set as classID
     */
    public void setPreferences(int val)
    {
        Preferences.setPreferencesLocation("User.preferences");
        Preferences.set("classID", val);
    }
}
