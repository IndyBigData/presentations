package com.chacha.pig;

import java.io.IOException;

import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;

import org.dom4j.Document;
import org.dom4j.io.XPP3Reader;

/**
 * @author Mark Stetzer
 */
public class XPathValue extends EvalFunc<String> {
   @Override
   public String exec(Tuple tuple) throws IOException {
      if(tuple == null || tuple.size() == 0 || tuple.get(0) == null) {
         return null;
      }
      
      String document = (String) tuple.get(0);
      String expression = (String) tuple.get(1);
      
      try {
         XPP3Reader xmlReader = new XPP3Reader();
         Document doc = xmlReader.read(document.toCharArray());
         return doc.selectSingleNode(expression).getText().trim();
      }
      catch(Exception e) {
         return null;
      }
   }
}

