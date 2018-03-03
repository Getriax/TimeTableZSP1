/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.companyofs.planzsp1;

import com.codename1.system.NativeInterface;

/**
 * Interface that provides integration with JSOUP library
 * @author Nikodem
 * 
 */
public interface JsoupInterfaceIntegration extends NativeInterface
{
    String getElementByClass(String fromCode, String className, int index);
    String getElementByTag(String formCode, String tagName, int index);
    String getText(String HtmlElement);
    String getHref(String htmlElement);
    String parseCode(String code);
}
