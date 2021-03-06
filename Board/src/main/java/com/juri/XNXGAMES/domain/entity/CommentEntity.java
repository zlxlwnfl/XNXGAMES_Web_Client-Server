package com.juri.XNXGAMES.domain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "comment")
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class CommentEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private Long postId;
	
	@Column(length = 10, nullable = false)
	private String writerId;
	
	@CreationTimestamp
	private Date regdate;
	
	@Column(nullable = false)
	private String content;
	
	@ColumnDefault("0")
	private int heartCount;
	
	@Builder
	public CommentEntity(Long postId, String writerId, String content) {
		this.postId = postId;
		this.writerId = writerId;
		this.content = content;
	}
	
}
