package dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import vo.BoardVO;

public class BoardDAO {

	SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	//전체 게시물 조회
	public List<BoardVO> selectList(HashMap<String, Integer> map){
		List<BoardVO> list = null;
		list = sqlSession.selectList("b.board_list",map);
		return list;
	}
	
	
	//게시글 추가
	public int insert(BoardVO vo) {
		  int res = sqlSession.insert("b.board_insert", vo);
		  return res;
	}
	
	//idx에 해당하는 상세 내용 한건 조회
	public BoardVO selectOne(int idx) {
		BoardVO vo = sqlSession.selectOne("b.board_one",idx);
		return vo;
	}
	
	//조회수 증가
	public int update_readhit(int idx) {
		int res = sqlSession.update("b.board_readhit",idx);
		return res;
	}
	
	//댓글추가를 위한 step+1
	public int update_step(BoardVO vo) {
		int res = sqlSession.update("b.board_update_step",vo);
		return res;
	}
	
	//댓글 추가
	public int reply(BoardVO vo) {
		int res = sqlSession.insert("b.board_reply",vo);
		return res;
	}
	
	//게시글삭제(된 것 처럼 보이기)
	public int del_update(BoardVO vo) {
		int res = sqlSession.update("b.del_update",vo);
		return res;
	}
	
	//전체 게시물 조회 
	public int getRowTotal() {
		int count = sqlSession.selectOne("b.board_count");
		return count;
	}
	
	//수정하기 
	public int board_update(BoardVO vo) {
		int res = sqlSession.update("b.board_update",vo);
		return res;
	}
	
	
	
}
