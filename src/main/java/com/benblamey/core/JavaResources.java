/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.benblamey.core;

import java.net.URL;

/**
 *
 * @author ben-laptop
 */
public class JavaResources {
    
    public static String getAbsoluteResourceFilePath(Class<?> clazz, String resourceName) {
        
        URL resource = clazz.getResource("" + resourceName);
        String file = resource.getFile();
        
        file = file.replace("%20", " ");
        
        // Gives us something like this: "/C:/work/code/Ben/ben_phd_java/benblamey/nbbuild/classes/benblamey/core/javaResourcesTestResource// T"
        // We need to strip the leading '/'.
        
        file = StringUtils.trim('/', file);
        
        //System.out.println(file);
        return file;
    }
    
    public static void main(String[] args) {
        getAbsoluteResourceFilePath(JavaResources.class, "javaResourcesTestResource");
    }
    
}
