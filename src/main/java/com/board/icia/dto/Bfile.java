package com.board.icia.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("bfile")
@Accessors(chain = true)
public class Bfile {
	private String bf_oriName;
	private String bf_sysName;
	private int bf_num;
	
}
