package edu.upenn.yiranqin.stringrelated;

import java.util.Properties;

/**
 * An URLParser that is not fully tested
 * @author qyr1987
 *
 */
public class URLParser {
		private Properties m_props;
		
		public URLParser(String requestURL){
			m_props = new Properties();
			parseURL(requestURL);
		}
		
		/**
		 * scheme://username:password@subdomain.domain.tld:port/path/file-name.suffix?query-string#hash
		 * parse the URL only care about query string and /path/file-name.suffix
		 * @param requestURL
		 */
		private void parseURL(String requestURL){
			/**
		     * If the request url is absolute url then parse out 
		     * the URI to resource and only handle the http request
		     */
		    if(requestURL.toUpperCase().startsWith("HTTP://")) {
		    	requestURL = requestURL.substring(7);
		    	String str[] = requestURL.split("/");
		    	// if only left everything before /, then the URI will be /
		    	if(str.length == 1)
		    		m_props.put("uri", "/");
		    	else{
		    		// eliminate everything before first /
		    		requestURL = requestURL.substring(str[0].length());
		    		if(requestURL.contains("?")){
		    			String tmp[] =  requestURL.split("\\?");
		    			m_props.put("uri", tmp[0]);
		    			// eliminate everything include ?
			    		requestURL = requestURL.substring(requestURL.indexOf("?")+1);
		    			if(requestURL.contains("#")){
		    				String tmpStr[] =  requestURL.split("#");
			    			m_props.put("query", tmpStr[0]);
		    			}else
		    				m_props.put("query", tmp[1]);
		    		}else
		    			m_props.put("uri", requestURL);
		    	}
		    }
		    // also handle pattern /path/file-name.suffix?query-string#hash
		    else{
		    	if(requestURL.contains("?")){
	    			String tmp[] =  requestURL.split("\\?");
	    			m_props.put("uri", tmp[0]);
	    			// eliminate everything before first ?
		    		requestURL = requestURL.substring(requestURL.indexOf("?")+1);
	    			if(requestURL.contains("#")){
	    				String tmpStr[] =  requestURL.split("#");
		    			m_props.put("query", tmpStr[0]);
	    			}else
	    				m_props.put("query", tmp[1]);
	    		}else
	    			m_props.put("uri", requestURL);
		    }
		}
		
		public String getURI(){
			return (String)m_props.get("uri");
		}
		
		public String getQueryString(){
			return (String)m_props.get("query");
		}
}
