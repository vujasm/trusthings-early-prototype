/*
 * #%L
 * trusthings-client-simple
 * %%
 * Copyright (C) 2014 INNOVA S.p.A
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

import com.google.common.io.CharStreams;
import com.inn.trusthings.integration.TrustFilterByExclusion;
import com.inn.trusthings.integration.TrustScorer;
import com.inn.trusthings.module.Factory;
import com.inn.trusthings.service.interfaces.TrustManager;


public class TrustEngineExample {
	
	public void runExample(){
		
		try {
			//create trust manager
			TrustManager trustManager =  Factory.createInstance(TrustManager.class);
			//load (from json file) and set trust criteria 
			InputStream is = TrustEngineExample.class.getResourceAsStream("/criteria/criteria1.json");
			String	criteria = CharStreams.toString(new InputStreamReader(is));
			trustManager.setGlobalTrustCriteria(criteria);
			is.close();
			
			//add some descriptions (trust profiles)
			URI service_a = URI.create("http://localhost/services/CITY_TRAFFIC_SERVICE_A");			
			URI service_b = URI.create("http://localhost/services/CITY_TRAFFIC_SERVICE_B");
			URI service_d = URI.create("http://localhost/services/CITY_TRAFFIC_SERVICE_D");
			InputStream r1is = TrustEngineExample.class.getResourceAsStream("/modelrepo/city_traffic_service_A.ttl");
			trustManager.addResourceDescription(service_a,r1is);
			r1is.close();
			InputStream r2is = TrustEngineExample.class.getResourceAsStream("/modelrepo/city_traffic_service_B.ttl");
			trustManager.addResourceDescription(service_b, r2is);
			r2is.close();
			InputStream r3is = TrustEngineExample.class.getResourceAsStream("/modelrepo/city_traffic_service_D.ttl");
			trustManager.addResourceDescription(service_d, r3is);
			r3is.close();
			/*
			 * SCORING
			 */
			//create trust scorer and pass trustManager
			TrustScorer s = new TrustScorer(trustManager);
			//obtain and print trust indexes for resources
			System.out.println(service_a.toASCIIString()+" has trust index value:"+s.apply(service_a));
			System.out.println(service_b.toASCIIString()+" has trust index value:"+s.apply(service_b));
			System.out.println(service_b.toASCIIString()+" has trust index value:"+s.apply(service_d));
			
			/*
			 * FILTERING
			 */
			//create trust filer to filter out those not trusted
			TrustFilterByExclusion f = new TrustFilterByExclusion(trustManager);
			//obtain trust indexes for resources
			System.out.println(service_a.toASCIIString()+" is trusted = "+f.apply(service_a));
			System.out.println(service_b.toASCIIString()+" is trusted = "+f.apply(service_b));
			System.out.println(service_b.toASCIIString()+" is trusted = "+f.apply(service_d));
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		new TrustEngineExample().runExample();
	}

}

