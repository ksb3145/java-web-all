package board.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.jni.File;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import net.sf.json.JSONObject;

//import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.BoardVO;
import board.FileVO;
import board.service.BoardService;
import board.service.CommentService;
import common.util.StringUtil;

//@WebServlet("/BoardServlet")
public class BoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String fileDir = "";
       
    private BoardService service;
	public BoardServlet() {
    	service = new BoardService();
    }
    
    @Override
	protected void doGet( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		doProc(req, resp);
	}
	
	@Override
	protected void doPost( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		doProc(req, resp);
	}
	
	protected void doProc( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {

		// 한글깨짐 처리
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset-UTF-8");
		
		int page = 1;
		String location = "";
		String command = StringUtil.strNullCheck(req.getParameter("command"));
		String pno = StringUtil.strNullCheck(req.getParameter("page"));
		
		if(" ".equals(pno) || null == pno) pno = "1";
		page = Integer.parseInt(pno);
		
		
		System.out.println("page ::: "+page);
		
		if(command == " "){
			ServletContext cxt = getServletContext();
			fileDir = cxt.getRealPath("/upload");
			
			//System.out.println(fileDir);
			
			BoardServlet bs = new BoardServlet();
			bs.boardMultipart(req,resp);
			
		}
		else if(command.equals("bbsList")){	// 게시판 리스트
			
			
			HashMap<Object, Object> resultData = new HashMap<Object, Object>();
			HashMap<Object, Object> params = new HashMap<Object, Object>();
			
		/* S: 페이징 ____ 나중에 소스 분리하기 */
			// 페이징 셋팅
			String paging = "";
			String url = "/BoardServlet?command=bbsList";
			
			//검색
			String searchType 	= (req.getParameter("searchType") == null) 	? "" : req.getParameter("searchType"); 	// 검색타입
			String keyword 		= (req.getParameter("keyword") == null) 	? "" : req.getParameter("keyword"); 	// 검색어

			if(searchType != ""){
				resultData.put("searchType", searchType);
				params.put("searchType",searchType);
				location += "&searchType="+searchType;
				url += "&searchType="+searchType;
			}
			if(keyword != ""){
				resultData.put("keyword", keyword);
				params.put("keyword",keyword);
				location += "&keyword="+keyword;
			}
			
			int totalCnt = service.boardTotalCnt(params);	// 총 게시물 수
			int cntPage = 10; 								// 페이지 수
			int limit = 10;									// 게시물 수
			int offset = (page-1)*limit; 					// (페이지번호 - 1) * 로우 출력 사이즈
			int totalPage = totalCnt / limit;				// 총 페이지 수
			int startPage = ((page - 1) / 10) * 10 + 1;		// 시작 페이지
			int endPage = startPage + cntPage - 1;			// 마지막 페이지
			
			int boardNumber = totalCnt-(cntPage*(page-1));
			
			// 예외사항 처리
			if (totalCnt % limit > 0) totalPage++; 			// 나머지가 있는 경우 "총 페이지 수"에 +1
			if (totalPage < page) page = totalPage; 		// 현재 페이지가 총 페이지 수 보다 클 경우 총 페이지 번호로 치환
			if (endPage > totalPage) endPage = totalPage; 	// 총 페이지가 마지막 페이지보다 클 경우 마지막 페이지로 치환
						
			// S: tag
			paging += "<ul class='pagination'>";
			if (startPage > 1){
				paging += "<li class='page-item'><a class='page-link' href='"+ url +"&page=1'>처음</a></li>";
			}
			if (page > 1) {
				paging += "<li class='page-item'><a class='page-link' href='"+ url +"&page="+ (page-1) +"'>이전</a></li>";
			}
			
			for (int i=startPage; i<=endPage; i++) {
				paging += "<li class='page-item'>";
				paging += "<a class='page-link' href='"+ url +"&page="+ i +"'>";
				    if (i == page){
				    	paging += "<b>"+ i +"</b>";
				    }else{
				    	paging += i;
				    }
			    paging += "</a>";
			    paging += "</li>";
			}

			if (page < totalPage) {
				paging += "<li class='page-item'><a class='page-link' href='"+ url +"&page="+ (page+1) +"'>다음</a></li>";
			}
			if (endPage < totalPage) {
				paging += "<li class='page-item'><a class='page-link' href='"+ url +"&page="+ totalPage +"'>끝</a></li>";
			}
			paging += "</ul>";
			// E: tag
		/* E: 페이징 */
			
			
			resultData.put("totalCnt", totalCnt);
			resultData.put("page", page);
			resultData.put("paging", paging);
			resultData.put("rownum", boardNumber);
			
			params.put("page", page);
			params.put("limit", limit);
			params.put("offset", offset);
			
			req.setAttribute("boardList", service.boardList(params));
			//req.setAttribute("msg", "게시판 조회 성공");
			req.setAttribute("resultData", resultData);
			
			location= "/WEB-INF/views/board/list.jsp";
			
			RequestDispatcher rd = req.getRequestDispatcher(location);
			rd.forward(req, resp);
			
		}else if(command.equals("bbsWriteForm")){	
			// 게시글 입력양식
			
			BoardVO bvo = null;
			String no 		= (req.getParameter("boardId") == null) ? "" 	: req.getParameter("boardId");
			String reqFrm	= (req.getParameter("reqFrm") == null) ? "" 	: req.getParameter("reqFrm");	//요청양식(수정 or 답글)
			String pid		= (req.getParameter("pid") == null) ? "" 		: req.getParameter("pid");		//답글 부모키
			
			req.setAttribute("page", page);
			
			// 수정+답글
			if("" != reqFrm){
				int boardId = Integer.parseInt(no);	// 게시판id.. / 답글의 부모값
				
				bvo = new BoardVO();
				bvo = service.boardView(boardId);	// 상세페이지 내용
				System.out.println("page2 ::: "+page);
				
				req.setAttribute("reTitle", "RE: "+bvo.getTitle());
				req.setAttribute("reqFrm", reqFrm );
				req.setAttribute("boardId", bvo.getId());
				req.setAttribute("pid", bvo.getPid());
				
				req.setAttribute("boardDetail", bvo );
			}
			
			
			location= "/WEB-INF/views/board/write.jsp";
			RequestDispatcher rd = req.getRequestDispatcher(location);
			rd.forward(req, resp);
			
		//}else if(command.equals("bbsInsert")){	

			// 게시글 등록
//			int result = 0, group = 0, insertId = 0;
//			String reqFrm 	= req.getParameter("reqFrm");	// 게시글 or 답변 수정
//			String boardId 	= req.getParameter("boardId");	// 개시글 key (답글일 경우)
//			String userId 	= req.getParameter("userId");
//			String title 		= req.getParameter("title");
//			String content 	= req.getParameter("content");
//			String bGroup 	= (req.getParameter("bGroup") == null) ? "" : req.getParameter("bGroup");
//			
//			BoardVO bvo = new BoardVO();
//			bvo.setUserId(userId);
//			bvo.setTitle(title);
//			bvo.setContent(content);
//			
//			if(boardId != "" && reqFrm.equals("bbsReplyInsert")){ 
//				// 답글은 원글의 그룹넘 
//				int pid = Integer.parseInt(boardId);	// 부모글의 key
//				group = Integer.parseInt(bGroup);
//				
//				bvo.setPid(pid);
//				bvo.setbGroup(group);
//				
//				insertId = service.boardReInsert(bvo); // 답글 등록
//				if(0<insertId){
//					bvo.setId(insertId);
//					result = service.boardSortNOUpdate(bvo);
//					System.out.println("up:"+result);
//				}
//			}else{
//				insertId = service.boardInsert(bvo);	// 게시글 등록
//				if(0<insertId){
//					// 원글 자신의 키값을 그룹 값에 넣어줌
//					bvo.setId(insertId);
//					bvo.setbGroup(insertId);
//					result = service.boardGroupNOUpdate(bvo);
//					System.out.println("ins:"+result);
//				}
//			}
//			
//			if(result>0){
//				// 등록 성공
//				req.setAttribute("msg", "성공");	
//			}else{
//				// 등록 실패
//				req.setAttribute("msg","실패!");
//			}
//			
//			location = "/BoardServlet?command=bbsList&page="+page;
//			
//			RequestDispatcher rd = req.getRequestDispatcher(location);
//			rd.forward(req, resp);
		}else if(command.equals("bbsUpdate")){	
			//게시글 수정
			
			String no = req.getParameter("boardId");
			String userId = req.getParameter("userId");
			String title = req.getParameter("title");
			String content = req.getParameter("content");
			
			int boardId = Integer.parseInt(no);	// 게시판id
			page = Integer.parseInt(req.getParameter("page"));	// 페이지
			
			BoardVO bvo = new BoardVO();
			bvo.setId(boardId);
			bvo.setUserId(userId);
			bvo.setTitle(title);
			bvo.setContent(content);
			
			int result = service.boardUpdate(bvo);
			
			if(result>0){
				// 등록 성공
				location = "/BoardServlet?command=bbsView&no="+boardId+"&page="+page;
			}else{
				// 등록 실패
				location = "/BoardServlet?command=bbsView&no="+boardId+"&page="+page;
			}
			
			RequestDispatcher rd = req.getRequestDispatcher(location);
			rd.forward(req, resp);
			
		}else if(command.equals("bbsDelete")){	
			// 게시글 삭제
			String userId = req.getParameter("userId");
			String boardId = req.getParameter("boardId");
			int bid = Integer.parseInt(boardId);
			
			BoardVO bvo = new BoardVO();
			bvo.setId(bid);
			bvo.setUserId(userId);
			
			//1. 삭제 할 글 => 답글 존재?
			BoardVO resultGroupNum = service.selectBoardGroupKey(bvo);
			
			// 그룹넘 null => 답글 없음
			if(!resultGroupNum.equals("")){
				//1-1. 답글 있음.
				bvo.setbGroup(resultGroupNum.getbGroup());
			}
			
			BoardServlet bs = new BoardServlet();
			String resultCode = bs.delContents(bvo);

			// JSONObject는 HashMap을 상속
			JSONObject json = new JSONObject(); 
			json.put("code", resultCode);
			json.put("url", "/BoardServlet?command=bbsList&page=1");
			
		    // 헤더설정
		    resp.setContentType("application/json");
		    resp.setCharacterEncoding("UTF-8");
		    
		    PrintWriter out = resp.getWriter();
		    out.print(json);
		    
		}else if(command.equals("bbsView")){
			
			String no = req.getParameter("no");
			int boardId = Integer.parseInt(no);
			
			// 게시판 상세페이지 조회
			req.setAttribute("boardDetail", service.boardView(boardId));
			// 게시판 댓글
			CommentService cmtService = new CommentService();
			req.setAttribute("commentList", cmtService.selectCommentList(boardId) );
			req.setAttribute("page", page);
			//req.setAttribute("code", "OK");
			//req.setAttribute("msg", "게시판 조회 성공");
			
			location= "/WEB-INF/views/board/view.jsp";
			RequestDispatcher rd = req.getRequestDispatcher(location);
			rd.forward(req, resp);
		}
	}
	

	// 게시글/댓글 삭제 (삭제=상태값 변경)
	public String delContents(BoardVO bvo){
		String resultVal = "";
		int resultData = 0;
		
		CommentService cmtService = new CommentService();
		
		// 코멘트 카운트 체크 빠짐..!<추가하기>
		int cmtResult = cmtService.commentBidDel(bvo.getId());	// 코멘트 삭제
		
		//if( cmtResult>0){
			if(bvo.getbGroup() == 0){
				resultData = service.boardDel(bvo);	// 게시글 삭제
			}else{
				resultData = service.boardGroupDel(bvo);	// 게시글 그룹 삭제 (원글의 하위글까지 모두 삭제)
			}
			if(resultData>0){
				resultVal = "OK";
			}else{
				resultVal = "Fail";
			}
//		}else{
//			resultVal = "Fail";
//		}
		
		return resultVal;
	}
	
	// 파일첨부..
	public void boardMultipart(HttpServletRequest req, HttpServletResponse resp){
		// 설정
		String location = "", pno = "" , command = "";
		// 게시판 입력값
		String reqFrm = "",  boardId = "", bGroup = "", userId = "", title = "", content = "";
		// 파일
		String fileName = "" , realFileName = "";
		
		try {
	        // 파일 업로드...
	        MultipartRequest multi = new MultipartRequest(req, fileDir, 5*1024*1024, "EUC-KR", new DefaultFileRenamePolicy());
	        
	        // 한글깨짐 처리
	        req.setCharacterEncoding("UTF-8");
			resp.setContentType("text/html;charset-UTF-8");
			
	        // 입력값
	        int result = 0, group = 0, insertId = 0;
	        pno = multi.getParameter("page");
	        command = multi.getParameter("command");
	        reqFrm 	= multi.getParameter("reqFrm");			// 게시글 or 답변 수정
			boardId 	= multi.getParameter("boardId");			// 개시글 key (답글일 경우)
			userId 	= multi.getParameter("userId");
			title 		= multi.getParameter("title");
			content 	= multi.getParameter("content");
			bGroup 	= (multi.getParameter("bGroup") == null) ? "" : multi.getParameter("bGroup");
			
	        BoardVO bvo = new BoardVO();
			bvo.setUserId(userId);
			bvo.setTitle(title);
			bvo.setContent(content);
			
			if(boardId != "" && reqFrm.equals("bbsReplyInsert")){ 
				// 답글은 원글의 그룹넘 
				int pid = Integer.parseInt(boardId);	// 부모글의 key
				group = Integer.parseInt(bGroup);
				
				bvo.setPid(pid);
				bvo.setbGroup(group);
				
				insertId = service.boardReInsert(bvo); // 답글 등록
				if(0<insertId){
					bvo.setId(insertId);
					result = service.boardSortNOUpdate(bvo);
					System.out.println("up:"+result);
				}
			}else{
				insertId = service.boardInsert(bvo);	// 게시글 등록
				if(0<insertId){
					// 원글 자신의 키값을 그룹 값에 넣어줌
					bvo.setId(insertId);
					bvo.setbGroup(insertId);
					result = service.boardGroupNOUpdate(bvo);
					System.out.println("ins:"+result);
				}
			}
			
			if(result>0){
				// 등록 성공
				
				String uploadDate = new SimpleDateFormat("yyMMddHmsS").format(new Date());	//현재시간

				// S: 파일관련
				Enumeration files = multi.getFileNames();	//Enumeration 
				String str = (String) files.nextElement();			// 파일
				fileName = multi.getFilesystemName(str);		// 파일명
				
		        int i = -1;
		        i = fileName.lastIndexOf(".");	// 파일 확장자 위치
		        realFileName = uploadDate+fileName.substring(i,fileName.length());	// 현재시간과 확장자 합치기..
		        
		        java.io.File oldFile = new  java.io.File(fileDir+"/"+fileName);
		        java.io.File newFile = new java.io.File(fileDir+"/"+realFileName);
		        // 파일명 날짜로 변경..
		        oldFile.renameTo(newFile);
				// E: 파일관련
		        
		        //파일첨부 게시판 등록..
		        FileVO fvo = new FileVO();
		        fvo.setBid(bvo.getId());
		        fvo.setFileName(realFileName);
		        fvo.setFileOrgName(fileName);
		        fvo.setFilePath(fileDir);
		        //fvo.setFileSize(fileSize);
		        
		        int fileResult = service.FileUpload(fvo);
		        
		        if(fileResult>0){
		        	req.setAttribute("msg", "성공");
		        }else{
		        	req.setAttribute("msg", "실패");
		        }
		        
			}else{
				// 등록 실패
				req.setAttribute("msg", "실패");	
			}
			
			location = "/BoardServlet?command=bbsList&page="+pno;
			
			RequestDispatcher rd = req.getRequestDispatcher(location);
			rd.forward(req, resp);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getUrl(HttpServletRequest req){
		// request URL
        Enumeration param = req.getParameterNames();
        String strParam = "";
        while(param.hasMoreElements()) {
            String name = (String)param.nextElement();
            String value = req.getParameter(name);
            strParam += name + "=" + value + "&";
        }
        return req.getRequestURL() + "?" + strParam;
	}

}
