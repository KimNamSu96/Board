package service;

import java.util.List;
import java.util.Map;

import dto.FilesDto;
import dto.ListPagingData;
import jakarta.servlet.http.HttpServletRequest;

public interface DaoService<T> {

	void close();
	int getTotalRecordCount(Map map);
	int insert(T dto);
	T select(Map map);
	int update(T dto);
	int delete(T dto);
	ListPagingData<T> selectAll(Map map, HttpServletRequest req, int nowPage);
	List<FilesDto> getFileByNo(Map map);
}
