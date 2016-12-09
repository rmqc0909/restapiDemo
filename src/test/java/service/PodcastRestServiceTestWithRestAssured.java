package service;

import org.junit.Test;

import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.equalTo;

public class PodcastRestServiceTestWithRestAssured {

	@Test
	public void testPodcastFetchSuccessful(){
		expect().
			body("id", equalTo("2")).
			body("title", equalTo("- The Naked Scientists Podcast - Stripping Down Science")).
		when().
			get("/restdemo/podcasts/2");
	}
}
