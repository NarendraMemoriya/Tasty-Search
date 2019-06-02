package com.tasty_search.controllers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexDataController {
	private String urlString = "http://localhost:8983/solr/Tasty_Search";
	private HttpSolrClient solr = new HttpSolrClient.Builder(urlString).build();
	
	
	@GetMapping("/indexData")
	public int indexData() {
		int count = 0;
		BufferedReader br = null;
		solr.setParser(new XMLResponseParser());
		try {
			br = new BufferedReader(new FileReader("/home/shopclues/Downloads/foods.txt"));
			String line;
			try {
				line = br.readLine();
				HashMap<String,String> hmap = new HashMap<String,String>();
				while (line != null && count < 50000) {
					while(!line.isEmpty()) {
						String[] dataArr = line.split(":");
						hmap.put(dataArr[0].split("/")[1], dataArr[1].trim());
						line = br.readLine();
					}
					index(hmap);
					hmap.clear();
					line = br.readLine();
					count++;
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SolrServerException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return count;
	}
	
	public void index(HashMap<String,String> hmap) throws SolrServerException, IOException {

		SolrInputDocument document = new SolrInputDocument();
		for(Map.Entry<String,String> entry: hmap.entrySet() ) {
			document.addField(entry.getKey().toString(), entry.getValue());			
		}
		solr.add(document);
		solr.commit();
		
	}
}
