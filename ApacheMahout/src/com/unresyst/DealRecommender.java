/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.unresyst;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;


import org.apache.commons.cli2.OptionException; 
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.slopeone.SlopeOneRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.impl.model.jdbc.*;

import java.sql.*;
import java.io.*;
import java.util.*;

import org.postgresql.ds.*;

public final class DealRecommender implements Recommender {
  
  private final Recommender recommender;
  
  public DealRecommender() throws IOException, TasteException, InterruptedException {
    // run pg:ingress to open up hole in Heroku firewall
    // according to Heroku documentation, connections made through this hole
    // will remain open after the hole closes (60 seconds)
		runCommand("source \"$HOME/.rvm/scripts/rvm\"",
               "rvm use ree-1.8.7-head@deallist-backend > /dev/null",
               "heroku pg:ingress --app rewardsnap-ds-production");

    PGPoolingDataSource source = new PGPoolingDataSource();
    source.setServerName("ec2-174-129-209-243.compute-1.amazonaws.com");
    source.setDatabaseName("ds20669xa45e2uv");
    source.setUser("uq88n3pavsbyot");
    source.setPassword("p8tj3rn061zvbthpc9e7nfbxv0");

    PostgreSQLJDBCDataModel model = new PostgreSQLJDBCDataModel(
        source, "ratings", "user_id", "deal_id", "rating", "created_at");
    recommender = new CachingRecommender(new SlopeOneRecommender(model));
  }
  
  @Override
  public List<RecommendedItem> recommend(long userID, int howMany) throws TasteException {
    return recommender.recommend(userID, howMany);
  }
  
  @Override
  public List<RecommendedItem> recommend(long userID, int howMany, IDRescorer rescorer) throws TasteException {
    return recommender.recommend(userID, howMany, rescorer);
  }
  
  @Override
  public float estimatePreference(long userID, long itemID) throws TasteException {
    return recommender.estimatePreference(userID, itemID);
  }
  
  @Override
  public void setPreference(long userID, long itemID, float value) throws TasteException {
    recommender.setPreference(userID, itemID, value);
  }
  
  @Override
  public void removePreference(long userID, long itemID) throws TasteException {
    recommender.removePreference(userID, itemID);
  }
  
  @Override
  public DataModel getDataModel() {
    return recommender.getDataModel();
  }
  
  @Override
  public void refresh(Collection<Refreshable> alreadyRefreshed) {
    recommender.refresh(alreadyRefreshed);
  }
  
  @Override
  public String toString() {
    return "hello from my DealRecommender";
  }
  

  // would be great to rewrite this if I go to a JRuby solution
  private static String runCommand(String... commands) throws IOException, InterruptedException {
    // generate a script file containg the command to run
    final File scriptFile = new File("/tmp/runcommand.sh");
    PrintWriter w = new PrintWriter(scriptFile);
    w.println("#!/bin/sh");
    for (String command : commands) {
      w.println(command);
    }
    w.close();
 
    // make the script executable
    //System.out.println("absolute path: " + scriptFile.getAbsolutePath());
    Process p = Runtime.getRuntime().exec("chmod +x " + scriptFile.getAbsolutePath());
    p.waitFor();
 
    // execute the script
    p = Runtime.getRuntime().exec(scriptFile.getAbsolutePath());
    p.waitFor();
	  BufferedReader stdin = new BufferedReader(new InputStreamReader(p.getInputStream()));
	  BufferedReader stderr = new BufferedReader(new InputStreamReader(p.getErrorStream()));
    String toReturn = "";
	  String line = "";
	  while ((line = stdin.readLine()) != null) {
	    toReturn += line + "\n";
	  }
    while ((line = stderr.readLine()) != null) {
	    toReturn += "err: " + line + "\n";
    }

    scriptFile.delete();
    return toReturn;
  }
}
