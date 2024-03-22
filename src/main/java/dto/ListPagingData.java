package dto;

import java.util.List;
import java.util.Map;

//제너릭 클래스:페이징관련 데이타를 저장하는 클래스
public class ListPagingData<T> {
	//레코드 목록 데이타 저장
	private List<T> records;
	//페이징 관련 데이타 저장
	private Map map;
	//페이징 표시용 문자열 저장
	private String pagingString;
	
	public ListPagingData() {}

	public ListPagingData(List<T> records, Map map, String pagingString) {
		this.records = records;
		this.map = map;
		this.pagingString = pagingString;
	}

	public List<T> getRecords() {
		return records;
	}

	public Map getMap() {
		return map;
	}

	public String getPagingString() {
		return pagingString;
	}
	
}
