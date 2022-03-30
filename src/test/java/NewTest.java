import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linecorp.armeria.client.WebClient;
import io.reactivex.rxjava3.core.Flowable;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class NewTest {

	@Test
	@SneakyThrows
	public void test() {
		final ObjectMapper mapper = new ObjectMapper();
		WebClient client = WebClient.of();
		String[] characterNames = {"욘온순", "딸기크림슈가맨", "탱애옥", "죽잘싸", };
		Flowable.
		CompletableFuture.completedFuture(characterNames)
				.thenApply(representNm -> {
					String characterNm = URLEncoder.encode(representNm, StandardCharsets.UTF_8);
					String resp = client.get("http://152.70.248.4:5000/userinfo/"+ representNm).aggregate().join().contentUtf8();
				})

		JsonNode playerNode = mapper.readTree(resp);
		JsonNode characterList = playerNode.get("CharacterList");
		List<JsonNode> ninaveList = new ArrayList<>();
		for (JsonNode character : characterList) {
			if (character.get("Server").toString().replace("\"", "").equals("니나브")) {
				ninaveList.add(character);
			}
		}

		orehaPrinter(ninaveList);


	}

	private void orehaPrinter(List<JsonNode> characterList) {
		String representNm = characterList.get(0).get("Name").toString();
		System.out.println("오레하하드=====");
		System.out.println(representNm);
		for (JsonNode character : characterList) {
			if (getLevel(character) > 1355) {
				System.out.print(character.get("Name")+"[]"+",");
			}
		}


	}

	private Double getLevel(JsonNode character) {
		return Double.parseDouble(
				character.get("Level").toString()
						.replace("\"", "")
						.replace("Lv.","")
						.replace(",","")
		);
	}


//	private void levelSeperator(JsonNode characterNode) {
//		JsonNode
//
//	}
//
//	List<>
// 1325 ~ 1414 오레하 2종
// 1370 ~ 1474 아르고스
//
}
