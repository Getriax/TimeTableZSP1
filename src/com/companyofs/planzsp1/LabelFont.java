/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.companyofs.planzsp1;

import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.Label;

/**
 * Label with costumized font
 * @author Nikodem
 * 
 */
public class LabelFont extends Label
{
    /**
     * 
     * @param text text to be displayed in the label
     * @param uuid the uuid of style in theme.res
     */
     public LabelFont(String text, String uuid) 
    {
        setUIID(uuid);
        setText(text);
        Font font;
        int ScreenWidth = Display.getInstance().getDisplayWidth();
        if (ScreenWidth < 2000)
            font = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL);
        else if (ScreenWidth < 4000)
            font = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
        else
            font = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE);
        getAllStyles().setFont(font, true);
    }
}
