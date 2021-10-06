package com.git.myworkspace.opendata.covid;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class CovidService {

	private final String SERVICE_KEY = "ephHE7sqwohfWoq2hSxkRTYX6s6YD8iN9pSmEcIgQGMchtKYsZ4UBquDToO6PwKvjSU1XNWYU4lWc35E4%2F5zbw%3D%3D";

	private CovidSidoDailyRepository repo;

	@Autowired
	public CovidService(CovidSidoDailyRepository repo) {
		this.repo = repo;
	}

	@Scheduled(fixedRate = 1000 * 60 * 60 * 1)
	@CacheEvict(value = "covid-daily", allEntries = true)
	public void requestCovidSidoDaily() throws IOException {


		StringBuilder builder = new StringBuilder();
		builder.append("http://openapi.data.go.kr/openapi");
		builder.append("/service/rest/Covid19");
		builder.append("/getCovid19SidoInfStateJson");
		builder.append("?serviceKey=" + SERVICE_KEY);
		builder.append("&pageNo=1&numOfRows=10");
		builder.append("&startCreateDt=20211001");

		URL url = new URL(builder.toString());

		// 3. Http�� �����Ϸ��� url�� http ���ӿ� ��ü�� �ٲ����
		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		// 4. byte[] �迭�� �����͸� �о��
		byte[] result = con.getInputStream().readAllBytes();

		// 5. byte[] -> ���ڿ� (XML) ��ȯ
		String data = new String(result, "UTF-8");

		/* ---------------------- ������ ��û�ϰ� XML �޾ƿ��� �� ----------------- */

		/* ---------------------- XML -> JSON -> Object(Java) ���� ----------------- */

		// XML(���ڿ�) -> JSON(���ڿ�)
		String json = XML.toJSONObject(data).toString(2); // �� 2��???
//		System.out.println(json);

		// JSON(���ڿ�) -> Java(object) ��ü
		CovidSidoDailyResponse response = new Gson().fromJson(json, CovidSidoDailyResponse.class);
//		System.out.println(response);

		/* ---------------------- XML -> JSON -> Object(Java) �� ----------------- */

		//

		/* ---------------------- ���� ��ü -> entity ��ƼƼ ��ü ���� ----------------- */

		List<CovidSidoDaily> list = new ArrayList<CovidSidoDaily>();
		for (CovidSidoDailyResponse.Item item : response.getResponse().getBody().getItems().getItem()) {

			CovidSidoDaily record = CovidSidoDaily.builder().stdDay(item.getStdDay()).gubun(item.getGubun())
					.gubunEn(item.getGubunEn()).overFlowCnt(item.getOverFlowCnt()).localOccCnt(item.getLocalOccCnt())
					.build();

			list.add(record);
		}
		repo.saveAll(list);

	}
}