package com.juri.XNXGAMES.service;

import java.util.List;

import com.juri.XNXGAMES.DTO.CommentGetListDTO;
import com.juri.XNXGAMES.DTO.CommentPutDTO;

public interface CommentService {

	public void insertComment(CommentPutDTO commentDTO);
	public void modifyComment(CommentPutDTO commentDTO);
	public List<CommentGetListDTO> getCommentList(Long postId);
	public void deleteComment(Long commentId);
	
}
