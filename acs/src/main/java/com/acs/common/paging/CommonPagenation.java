package com.acs.common.paging;

import java.util.HashMap;

public class CommonPagenation {
	/**
	 * 목록 페이징 정보 가져오기
	 * @param pageNumber:현재페이지, rowsPerPage:한개의페이지에 보여즐 row 갯수, totalCnt:게시판총 row 갯수
	 * @returns HashMap<String, Object>
	 */
	public HashMap<String, Object> getPageInfo(int pageNumber, int rowsPerPage, int totalCnt){			
			HashMap<String,Object> returnMap = new HashMap<String,Object>();
			
			int startPage = 1;
			int endPage = 1;
			int totalPage = 1;
			
			String checkVal = String.valueOf(pageNumber);
			if(checkVal!=null && !checkVal.isEmpty()){
				totalPage = totalCnt / rowsPerPage;
				if (totalCnt % rowsPerPage > 0) {
				    totalPage++;
				}
				startPage = ( (pageNumber - 1) * rowsPerPage ) + 1;
				endPage =  ((startPage + rowsPerPage) - 1)/pageNumber;
				System.out.println(startPage + "   >>>>>> endPage     :: " + endPage);
				
			}
			
			returnMap.put("startPage",  startPage);
			returnMap.put("endPage",  endPage);
			returnMap.put("totalCnt",  totalCnt);
			returnMap.put("pageNumber",  pageNumber);
			returnMap.put("rowsPerPage",  rowsPerPage);
			returnMap.put("totalPage",  totalPage);
			
			return returnMap;
	}
	
}
