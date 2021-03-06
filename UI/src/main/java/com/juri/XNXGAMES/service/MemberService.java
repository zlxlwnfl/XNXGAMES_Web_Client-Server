package com.juri.XNXGAMES.service;

import java.util.Arrays;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.juri.XNXGAMES.domain.MemberRole;
import com.juri.XNXGAMES.domain.entity.MemberEntity;
import com.juri.XNXGAMES.domain.repository.MemberRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {
	
	private MemberRepository memberRepository;
	
	// ID 중복체크
	public boolean checkIdPossible(String memberId) {
		return !memberRepository.findByMemberId(memberId).isPresent();
	}
	
	@Transactional
    public String join(MemberEntity memberEntity) {
		MemberRole role = MemberRole.ADMIN;
		
        memberEntity.setPassword(new BCryptPasswordEncoder().encode(memberEntity.getPassword()));
        memberEntity.setRoles(Arrays.asList(role));

        return memberRepository.save(memberEntity).getMemberId();
    }

	@Override
	public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
		return memberRepository.findByMemberId(memberId).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 아이디입니다."));
	}

}
