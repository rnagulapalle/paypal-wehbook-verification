package com.example.demo;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

@SpringBootApplication
public class DemoApplication {

	public static void main(String args[]) {
		HttpPost post = new HttpPost("https://api.sandbox.paypal.com/v1/notifications/verify-webhook-signature");
		// make sure body including all the params are just string
		String jsonData = "{\n" +
				"\"transmission_id\": \"5bc2f4a0-b85e-11e8-99a0-0de0de4a06f7\",\n" +
				"\"transmission_time\": \"2018-09-14T20:40:04Z\",\n" +
				"\"cert_url\": \"https://api.sandbox.paypal.com/v1/notifications/certs/CERT-360caa42-fca2a594-aecacc47\",\n" +
				"\"auth_algo\" : \"SHA256withRSA\",\n" +
				"\"transmission_sig\": \"NK5qAqqPRTVd4fb0PqgVMp/H88sSCbJX+NvyLr+9LAIbuYP3YcOcQBm7RI94fDWcugVkKHBhieLDdNfH3RUD981qlULI7dvEoil3nFli/eLU6z5ljmQyGj44Gt2T+/4s8Ob1+zBHDS0WS+x237tzPqAY8hNRXAssFKkDw2HFXhSjFR78NjC/3DRFPdVfrNP0ZRWOJ31kxwOdfR7oomuW7AlQ7XpGo0IOGWsR2CmWmOhrTSnmXd6v6dQpJVPVu6TKwqI2wAZxKGJcE0sqnyJfezExxDA2jxE3vJid3uTTVHeJUfp6w2mXxf0p2IjjJ4763qZG/f7d1QgSMCM+9fc62Q==\",\n" +
				"\"webhook_id\": \"4RT894274S9474923\",\n" +
				"\"webhook_event\":{\"id\":\"WH-2WY97502LU897125S-6M667954G0585532E\",\"event_version\":\"1.0\",\"create_time\":\"2018-09-14T20:40:03.847Z\",\"resource_type\":\"sale\",\"event_type\":\"PAYMENT.SALE.COMPLETED\",\"summary\":\"Payment completed for $ 30.11 USD\",\"resource\":{\"id\":\"7C1506564A369232V\",\"state\":\"completed\",\"amount\":{\"total\":\"30.11\",\"currency\":\"USD\",\"details\":{\"subtotal\":\"30.00\",\"tax\":\"0.07\",\"shipping\":\"0.03\",\"insurance\":\"0.01\",\"handling_fee\":\"1.00\",\"shipping_discount\":\"-1.00\"}},\"payment_mode\":\"INSTANT_TRANSFER\",\"protection_eligibility\":\"ELIGIBLE\",\"protection_eligibility_type\":\"ITEM_NOT_RECEIVED_ELIGIBLE,UNAUTHORIZED_PAYMENT_ELIGIBLE\",\"transaction_fee\":{\"value\":\"1.17\",\"currency\":\"USD\"},\"invoice_number\":\"1536957566\",\"custom\":\"EBAY_EMS_90048630024435\",\"parent_payment\":\"PAY-4KU08690AH983051SLOOBY7Y\",\"create_time\":\"2018-09-14T20:39:30Z\",\"update_time\":\"2018-09-14T20:39:30Z\",\"links\":[{\"href\":\"https://api.sandbox.paypal.com/v1/payments/sale/7C1506564A369232V\",\"rel\":\"self\",\"method\":\"GET\"},{\"href\":\"https://api.sandbox.paypal.com/v1/payments/sale/7C1506564A369232V/refund\",\"rel\":\"refund\",\"method\":\"POST\"},{\"href\":\"https://api.sandbox.paypal.com/v1/payments/payment/PAY-4KU08690AH983051SLOOBY7Y\",\"rel\":\"parent_payment\",\"method\":\"GET\"}]},\"links\":[{\"href\":\"https://api.sandbox.paypal.com/v1/notifications/webhooks-events/WH-2WY97502LU897125S-6M667954G0585532E\",\"rel\":\"self\",\"method\":\"GET\"},{\"href\":\"https://api.sandbox.paypal.com/v1/notifications/webhooks-events/WH-2WY97502LU897125S-6M667954G0585532E/resend\",\"rel\":\"resend\",\"method\":\"POST\"}]}\n" +
				"}";

		// this token needs to be generated from client_id and secret
		String authHeader = "Bearer " + new String("A23AAGL6Mijr5VdElLaFfapWBD1H95w_qG0qbRnhbLiiaPmZhLcxZzVwZFGxW04ubILfBe9s-CUvZv04R8yxCN-Kk3ARnCzWw");
		post.setHeader("AUTHORIZATION", authHeader);
		post.setHeader("Content-Type", "application/json");
		try {
			HttpResponse response = null;
			String line = "";
			StringBuffer result = new StringBuffer();
			post.setEntity(new StringEntity(jsonData));
			HttpClient client = HttpClientBuilder.create().build();
			response = client.execute(post);
			System.out.println("Post parameters : " + jsonData);
			System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			while ((line = reader.readLine()) != null){ result.append(line); }
			System.out.println(result.toString());
		} catch (UnsupportedEncodingException e) {
			System.out.println("error while encoding api url : " + e);
		} catch (IOException e) {
			System.out.println("ioException occured while sending http request : " + e);
		} catch (Exception e) {
			System.out.println("exception occured while sending http request : " + e);
		} finally {
			post.releaseConnection();
		}
	}

}
