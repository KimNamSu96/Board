package dto;

import java.util.List;
import java.util.Map;

public class ListPagingDataBuilder {

	private List records;
	private Map map;
	private String pagingString;
	
	public ListPagingDataBuilder records(List<BoardDto> dtoList) {
		this.records = dtoList;
		return this;
	}
	public ListPagingDataBuilder map(Map map) {
		this.map = map;
		return this;
	}
	public ListPagingDataBuilder pagingString(String pagingString) {
		this.pagingString = pagingString;
		return this;
	}
	
	public ListPagingData builder(){
		return new ListPagingData(records, map, pagingString); 
	}	
	
}
