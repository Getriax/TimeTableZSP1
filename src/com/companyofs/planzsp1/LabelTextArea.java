/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.companyofs.planzsp1;

import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.TextArea;

/**
 * Label with some functions of textarea
 * @author Nikodem
 * 
 */
public class LabelTextArea extends TextArea
{
    /**
     * 
     * @param uuid the uuid of style in theme.res
     */
    public LabelTextArea(String uuid) 
    {
        setEditable(false);
        setFocusable(false);
        setUIID(uuid);
        MakeFont();
    }
    /**
     * 
     * @param text text to be displayed in the label
     * @param uuid the uuid of style in theme.res
     */
    public LabelTextArea(String text, String uuid) 
    {
        setEditable(false);
        setFocusable(false);
        setUIID(uuid);
        setText(text);
        setVerticalAlignment(TextArea.TOP);
        MakeFont();
    }
    /**
     * Costimize the font in the label depends on the screen size
     */
    public void MakeFont()
    {
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
