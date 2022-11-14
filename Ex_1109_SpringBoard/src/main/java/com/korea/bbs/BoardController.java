package com.korea.bbs;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dao.BoardDAO;
import util.Common;
import util.Paging;
import vo.BoardVO;

@Controller
public class BoardController {

	@Autowired
	HttpServletRequest request;
	
	BoardDAO board_dao;
	
	public void setBoard_dao(BoardDAO board_dao) {
		this.board_dao = board_dao;
	}
	
	//페이지별 목록 조회
	@RequestMapping(value= {"/","/list.do"})
	public String list(Model model,String page) {
		int nowPage = 1;
		
		//board_list.do 는 null 인경우 
		//board_list.do?page=  는 empty 인경우 
		
		if(page != null && !page.isEmpty()) {
			nowPage = Integer.parseInt(page);
		}
		
		//한페이지에 표시가 될 게시물의 시작과 끝번호를 계산하기 
		//페이지가 1 이면 1~10까지 계산이 되야됩니다  
		//page 2 이면 11~20까지 계산되어야 합니다. ㄴㄴㄴ 이런 로직을 구성 
		
		int start =(nowPage-1)*Common.Board.BLOCKLIST+1;
		int end = start + Common.Board.BLOCKLIST-1;
		
		//페이징 처리를위해 해쉬맵 사용  dao로  보낼수 없기떄문에 
		HashMap<String, Integer> map = new HashMap <String,Integer> ();
		map.put("start", start);
		map.put("end",end);
				
		
		//전체목록 가져오기 -> 페이지 번호에 따른 게시글 조회
		List<BoardVO> list = board_dao.selectList(map);
		
		//전체 게시물 수 조회
		int rowTotal = board_dao.getRowTotal();
		
		//페이지 메뉴 생성하기 
		String pageMenu = Paging.getPaging("board_list.do", 
											nowPage, //현재 페이지 번호 
											rowTotal, //전체 게시물 수 
											Common.Board.BLOCKLIST,//한페이지에 표기할 게시물 수
											Common.Board.BLOCKPAGE);//페이지 메뉴수 
		
		//조회수를 위해 저장해뒀던 show라는 정보를 세션에서 제거
		request.getSession().removeAttribute("show");
		
		//바인딩 
		model.addAttribute("list", list);
		model.addAttribute("pageMenu", pageMenu);
		
		
		return Common.VIEW_PATH+"board_list.jsp?page="+nowPage;
	}
	
	//게시물 상세 조회하기 
	@RequestMapping("/view.do")
	public String view(Model model,int idx,int page) {
		//idx에 해당하는 게시글 한 건 얻어오기 
		BoardVO vo = board_dao.selectOne(idx);
		
		//조회수 증가 처리 (f5 연타했을떄 조회수가 급상승 하는것을 방지 )
		HttpSession session = request.getSession();
		String show = (String)session.getAttribute("show");
		
		if(show == null) {
			int res = board_dao.update_readhit(idx);
			//세션에 "show" 라는 이름으로 저장 
			session.setAttribute("show", "0");
		}
		//바인딩 
		model.addAttribute("vo", vo);
		//포워딩 
		return Common.VIEW_PATH+"board_view.jsp";
	}
	
	//글 추가 페이지로 이동 
	@RequestMapping("/insert_form.do")
	public String insert_form() {
		return Common.VIEW_PATH+"insert_form.jsp";
	}
	
	//게시글 등록 
	@RequestMapping("/insert.do")
	public String insert(BoardVO vo) {
		//제목,작성자,내용,비밀번호

		//접속자의 ip 구하기 
		String ip = request.getRemoteAddr();
		vo.setIp(ip);
		
		//DB에 insert 하기 
		//select - > List,vo
		//insert ,update ,delete  -> int 
		int res = board_dao.insert(vo);
		
		if(res > 0) {
			return "redirect:list.do";
		}
		
		return null;
	}
	
	//게시글 삭제 하기 
	@RequestMapping("/del.do")
	@ResponseBody
	public String delete(int idx) {
		
		//삭제 하고자 하는 게시글이 존재하는지 먼저 확인 
		BoardVO baseVo = board_dao.selectOne(idx);
		
		baseVo.setSubject("삭제된 게시글입니다.");
		baseVo.setName("unknown");
		
		//기준글이 삭제된 것 처럼 업데이트를 사용 
		int res = board_dao.del_update(baseVo);
		
		if(res == 1) {
			return "[{'param':'yes'}]";
		}
		return "[{'param':'no'}]";
	}
	
	//답글 작성 화면으로 이동 
	@RequestMapping("/reply_form.do")
	public String reply_form(int idx,int page) {
		return Common.VIEW_PATH+"reply_form.jsp?idx="+idx+"&page="+page;
	}
	
	@RequestMapping("/reply.do")
	public String reply(int idx,String name,String subject,String content,String pwd,int page) {
		
		//기준글 idx를 사용해서 게시물의 정보 얻기 
		BoardVO basevo = board_dao.selectOne(idx);
		
		//기준글의 step보다 큰값은 step = step+1 처리 
		int res = board_dao.update_step(basevo);
		
		String ip = request.getRemoteAddr();
		
		//댓글 VO로 포장 
		BoardVO vo = new BoardVO();
		vo.setName(name);
		vo.setSubject(subject);
		vo.setContent(content);
		vo.setPwd(pwd);
		vo.setIp(ip);
		
		//댓글이 들어갈 위치 선정 
		vo.setRef(basevo.getRef());
		vo.setStep(basevo.getStep()+1);
		vo.setDepth(basevo.getDepth()+1);
		
		
		//댓글 등록하기 
		res= board_dao.reply(vo);
		
		//페이지 정보를 파라미터로 전달 
		return "redirect:list.do?page="+page;
		
	}
	
}
