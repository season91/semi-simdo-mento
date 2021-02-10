package simdo.module.movie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simdo.infra.common.util.http.HttpUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author choayoung
 */

@Service
@RequiredArgsConstructor
@Transactional
public class MovieService {

    private final MovieRepository movieRepository;

    // 영화 기본 정보 가지고 올 영화 API
    public Map<String, Object> kmdbAPI(){
        HttpUtil util = new HttpUtil();
        ObjectMapper om = new ObjectMapper();

        // 쿼리문 변경해가면서 h2에 데이터 넣자!
        //1. 아이엠히어
        String url = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?ServiceKey=RLYJPR31F2X100MT6HX3&query=알랭샤바&actor=배두나&detail=Y&collection=kmdb_new2&listCount=1";

        //2. 라라랜드
        //String url = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?ServiceKey=RLYJPR31F2X100MT6HX3&query=엠마&actor=라이언고슬링&detail=Y&collection=kmdb_new2&listCount=1";

        String json = util.get(url);

        Map resultMap = null;
        try {
            // Data 값 가져오기.
            Map resMap = om.readValue(json, Map.class);
            List<String> dataList = (List<String>) resMap.get("Data");
            String dataStr = om.writeValueAsString(dataList.get(0));
            Map dataMap = om.readValue(dataStr, Map.class);

            // Result값 가져오기.
            List<String> resultList = (List<String>) dataMap.get("Result");
            String resultStr = om.writeValueAsString(resultList.get(0));
            resultMap = om.readValue(resultStr, Map.class);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(resultMap);
        return  resultMap;
    }

    // 영화 썸네일 가지고 올 네이버영화 API
    public String naverMovieAPI(){
        HttpUtil util = new HttpUtil();
        String clientId = "1TOE19GYAcgawcD0ESm1";
        String clientSecret = "tmgwvMjtQF";
        ObjectMapper om = new ObjectMapper();
        String title = null;

        try {
            //1. 아이엠히어
            title = URLEncoder.encode("아이엠히어", "UTF-8");

            //2. 라라랜드
            //title = URLEncoder.encode("라라랜드", "UTF-8");

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패", e);
        }

        String apiURL = "https://openapi.naver.com/v1/search/movie.json?query=" + title + "&yearfrom=2015&yearto=2019"; // json 결과

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);

        String jsonRes = util.get(apiURL, requestHeaders);

        String thumbnail = null;
        try {
            Map resMap = om.readValue(jsonRes, Map.class);
            List<String> itemsList = (List<String>) resMap.get("items");
            String itemsStr = om.writeValueAsString(itemsList.get(0));
            Map itemsMap = om.readValue(itemsStr, Map.class);
            thumbnail = (String) itemsMap.get("image");

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(thumbnail);
        return thumbnail;
    }

    // json 한번더 파싱해야 하는 경우 사용할 메서드
    public Map<String, Object> listSeparation(Map<String, Object> map, String beforecategory, String aftercategory){
        ObjectMapper om = new ObjectMapper();
        Map<String, Object> resMap = null;
        Map<String, Object> beforeMap = (Map<String, Object>) map.get(beforecategory);
        List<String> beforeList = (List<String>) beforeMap.get(aftercategory);
        try {
            String resStr = om.writeValueAsString(beforeList.get(0));
            resMap = om.readValue(resStr, Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return resMap;
    }

    // poster 배열로 잘라주고 사진 1개 반환
    public String posterSplit(Map<String, Object> map, String category){
        String posters = (String) map.get(category);
        String[] posterArr = posters.split("[|]");
        return posterArr[0];
    }

    // 영화정보들 movie에 넣어준다.
    public void saveMovie(Map movieMap, String thumbnail){
        // 감독이랑, 줄거리를 한번더 분리해줘야 한다.
        Map<String, Object> directMap = listSeparation(movieMap,"directors","director");
        Map<String, Object> plotMap = listSeparation(movieMap, "plots","plot");
        // poster 는 배열로 분리해서 첫번째 것만 가져온다.
        String posterLink = posterSplit(movieMap, "posters");

        Movie movie = Movie.builder()
                .mvNo((String) movieMap.get("DOCID"))
                .mvTitle((String) movieMap.get("title"))
                .mvTitleorg((String) movieMap.get("titleOrg"))
                .director((String) directMap.get("directorNm"))
                .genre((String) movieMap.get("genre"))
                .releaseDate(LocalDate.parse((String)movieMap.get("repRlsDate"), DateTimeFormatter.BASIC_ISO_DATE))
                .plot((String) plotMap.get("plotText"))
                .nation((String) movieMap.get("nation"))
                .runtime(Long.parseLong((String)movieMap.get("runtime")))
                .rating((String) movieMap.get("rating"))
                .poster(posterLink)
                .thumbnail(thumbnail)
                .build();
        movieRepository.save(movie);
    }
}
