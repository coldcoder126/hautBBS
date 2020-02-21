package cn.coldcoder.util;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;

import java.io.IOException;

public class solrUtil {
    public static void addToSolr(String solrUrl,Object obj) throws IOException, SolrServerException {

        HttpSolrClient client = new HttpSolrClient.Builder(solrUrl).build();
        UpdateResponse response = client.addBean(obj);
        client.commit();
        client.close();
    }

    //删除，根据id
    public static void deleteFromSolr(String solrUrl,String id) throws IOException, SolrServerException {
        HttpSolrClient client = new HttpSolrClient.Builder(solrUrl).build();
        client.deleteById(id);
        client.commit();
        client.close();
    }
}
