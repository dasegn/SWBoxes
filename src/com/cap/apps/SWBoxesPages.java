/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cap.apps;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Iterator;
import java.util.LinkedHashMap;
import org.semanticwb.model.WebPage;

/**
 *
 * @author daniel.martinez
 */
public class SWBoxesPages {
    private static LinkedHashMap<String,String> pages = new LinkedHashMap<String,String>(); 
    
    public static LinkedHashMap<String,String> getPages(WebPage page, String indent){
        getPageChilds(page, indent);                   
        return pages;
    }
    public static String getJsonPages(WebPage page){
        JsonArray tree = new JsonArray();        
        getJsonPageChilds(page, tree);                   
        return tree.toString();
    }   
    
    private static void getJsonPageChilds(WebPage page, JsonArray tree){
        Iterator<WebPage>  it = page.listVisibleChilds(null);        
        
        if(it.hasNext()){
            while(it.hasNext()) {
                WebPage tp = it.next();                
                if( null != tp ){
                    JsonObject object = new JsonObject();
                    object.addProperty("id", tp.getId());                                      
                    object.addProperty("text", tp.getDisplayName());                    
                    object.addProperty("url", tp.getUrl());  
                    
                    if(tp.listVisibleChilds(null).hasNext()){                        
                        JsonArray nodes = new JsonArray();                         
                        getJsonPageChilds(tp, nodes);     
                        object.add("nodes", nodes);                        
                    }
                    tree.add(object);
                }
                
            }
        }                                                    
    }   
    
    private static void getPageChilds(WebPage page, String indent){
        Iterator<WebPage>  it = page.listVisibleChilds(null);         
        
        if(it.hasNext()){
            while(it.hasNext()) {
                WebPage tp = it.next();                
                if( null != tp ){
                    pages.put(tp.getId(), indent + tp.getDisplayName());
                    if(tp.listVisibleChilds(null).hasNext()){
                        getPageChilds(tp, indent + "-");                      
                    }
                }
            }
        }                                                    
    }       
}
