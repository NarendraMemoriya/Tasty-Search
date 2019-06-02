package com.tasty_search.controllers;

import java.io.IOException;
import java.util.Iterator;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchTextController {
	private String urlString = "http://localhost:8983/solr/Tasty_Search";
	private HttpSolrClient solr = new HttpSolrClient.Builder(urlString).build();
	
	@RequestMapping("/searchText")
	public String searchText(@RequestParam String search_text) throws SolrServerException, IOException {
		SolrQuery query = new SolrQuery();
		String str = "summary:"+search_text+" OR text:"+search_text;
		query.set("q", str);
		query.setRows(20);
		QueryResponse response = solr.query(query);
		
        SolrDocumentList docList = response.getResults();
        JSONArray resultArray = new JSONArray();

        Iterator<SolrDocument> itr = docList.iterator();

        while (itr.hasNext()) {
            SolrDocument ele = itr.next();
            JSONObject json = new JSONObject();
            for(String keys:ele.getFieldNames())
            {
                json.put(keys,ele.get(keys));
            }
             resultArray.put(json);
        }
		
		return resultArray.toString(1);
	}
}
